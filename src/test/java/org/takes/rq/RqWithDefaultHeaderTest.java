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
package org.takes.rq;

import java.io.IOException;
import java.util.Collections;
import org.cactoos.text.Joined;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.StartsWith;

/**
 * Test case for {@link RqWithDefaultHeader}.
 * @since 0.31
 */
final class RqWithDefaultHeaderTest {

    /**
     * Carriage return constant.
     */
    private static final String CRLF = "\r\n";

    /**
     * RqWithDefaultHeader can provide default header value.
     * @throws IOException If some problem inside
     */
    @Test
    void providesDefaultHeader() throws IOException {
        final String req = "GET /";
        MatcherAssert.assertThat(
            new RqPrint(
                new RqWithDefaultHeader(
                    new RqFake(Collections.singletonList(req), "body"),
                    "X-Default-Header1",
                    "X-Default-Value1"
                )
            ),
            new StartsWith(
                new Joined(
                    RqWithDefaultHeaderTest.CRLF,
                    req,
                    "X-Default-Header1: X-Default-Value"
                )
            )
        );
    }

    /**
     * RqWithDefaultHeader can override default value.
     * @throws IOException If some problem inside
     */
    @Test
    void allowsOverrideDefaultHeader() throws IOException {
        final String req = "POST /";
        final String header = "X-Default-Header2";
        MatcherAssert.assertThat(
            new RqPrint(
                new RqWithDefaultHeader(
                    new RqWithHeader(
                        new RqFake(Collections.singletonList(req), "body2"),
                        header,
                        "Non-Default-Value2"
                    ),
                    header,
                    "X-Default-Value"
                )
            ),
            new StartsWith(
                new Joined(
                    RqWithDefaultHeaderTest.CRLF,
                    req,
                    "X-Default-Header2: Non-Default-Value"
                )
            )
        );
    }
}
