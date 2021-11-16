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
package org.takes.rs;

import java.io.IOException;
import org.cactoos.text.Joined;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.TextIs;

/**
 * Test case for {@link RsWithHeader}.
 * @since 0.1
 */
final class RsWithHeaderTest {

    /**
     * RsWithHeader can add headers.
     * @throws IOException If some problem inside
     */
    @Test
    void addsHeadersToResponse() throws IOException {
        MatcherAssert.assertThat(
            new RsPrint(
                new RsWithHeader(
                    new RsWithHeader(new RsEmpty(), "host", "b.example.com"),
                    "Host", "a.example.com"
                )
            ),
            new TextIs(
                new Joined(
                    "\r\n",
                    "HTTP/1.1 204 No Content",
                    "host: b.example.com",
                    "Host: a.example.com",
                    "",
                    ""
                )
            )
        );
    }

    /**
     * RsWithHeader can't add invalid headers.
     */
    @Test
    void notAddsInvalidHeadersToResponse() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {
                new RsWithHeader(
                    new RsWithHeader(new RsEmpty(), "host:", "c.example.com"),
                    "Host MY", "d.example.com"
                ).head();
            }
        );
    }
}
