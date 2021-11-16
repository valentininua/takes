/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2019 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.takes.facets.fork;

import java.util.regex.Pattern;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.takes.rq.RqFake;
import org.takes.rq.RqMethod;
import org.takes.tk.TkEmpty;

/**
 * Test case for {@link FkRegex}.
 * @since 0.4
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class FkRegexTest {

    /**
     * Test path for trailing slash.
     */
    private static final String TESTPATH = "/h/tail/";

    /**
     * FkRegex can match by regular expression.
     * @throws Exception If some problem inside
     */
    @Test
    void matchesByRegularExpression() throws Exception {
        MatcherAssert.assertThat(
            new FkRegex("/h[a-z]{2}", new TkEmpty()).route(
                new RqFake("GET", "/hel?a=1")
            ).has(),
            Matchers.is(true)
        );
        MatcherAssert.assertThat(
            new FkRegex(
                Pattern.compile("/h[a-z]{2}"),
                new TkEmpty()
            ).route(
                new RqFake("GET", "/hel?a=1")
            ).has(),
            Matchers.is(true)
        );
        MatcherAssert.assertThat(
            new FkRegex("/", new TkEmpty()).route(
                new RqFake("PUT", "/?test")
            ).has(),
            Matchers.is(true)
        );
    }

    /**
     * FkRegex can remove trailing slash from URI (default).
     * @throws Exception If some problem inside
     */
    @Test
    void removesTrailingSlash() throws Exception {
        MatcherAssert.assertThat(
            new FkRegex("/h/tail", new TkEmpty()).route(
                new RqFake(RqMethod.POST, FkRegexTest.TESTPATH)
            ).has(),
            Matchers.is(true)
        );
    }

    /**
     * FkRegex can keep trailing slash from URI.
     * @throws Exception If some problem inside
     */
    @Test
    void keepsTrailingSlash() throws Exception {
        MatcherAssert.assertThat(
            new FkRegex(FkRegexTest.TESTPATH, new TkEmpty())
                .setRemoveTrailingSlash(false)
                .route(
                    new RqFake(RqMethod.POST, FkRegexTest.TESTPATH)
                ).has(),
            Matchers.is(true)
        );
    }

}
