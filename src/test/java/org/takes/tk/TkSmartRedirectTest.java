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
package org.takes.tk;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.takes.facets.hamcrest.HmHeader;
import org.takes.rq.RqFake;

/**
 * Test case for {@link TkSmartRedirect}.
 * @since 0.10
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class TkSmartRedirectTest {
    /**
     * TkRedirect should carry on the query and the fragment.
     * @throws Exception If some problem inside
     */
    @Test
    void redirectCarriesQueryAndFragment() throws Exception {
        MatcherAssert.assertThat(
            new TkSmartRedirect("http://www.google.com/abc?b=2").act(
                new RqFake("GET", "/hi?a=1#test")
            ),
            new HmHeader<>(
                "Location",
                "http://www.google.com/abc?b=2&a=1#test"
            )
        );
    }

    /**
     * TkRedirect should carry on the query and the fragment.
     * @throws Exception If some problem inside
     */
    @Test
    void redirectCarriesQueryAndFragmentOnEmptyUrl() throws Exception {
        MatcherAssert.assertThat(
            new TkSmartRedirect().act(
                new RqFake("GET", "/hey-you?f=1#xxx")
            ),
            new HmHeader<>(
                "Location",
                "/?f=1#xxx"
            )
        );
    }

}
