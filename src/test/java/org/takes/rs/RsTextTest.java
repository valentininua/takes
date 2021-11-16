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
import java.net.HttpURLConnection;
import org.cactoos.text.Joined;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.TextIs;

/**
 * Test case for {@link RsText}.
 * @since 0.1
 */
final class RsTextTest {

    /**
     * RsText can build a plain text response.
     * @throws IOException If some problem inside
     */
    @Test
    void makesPlainTextResponse() throws IOException {
        final String body = "hello, world!";
        MatcherAssert.assertThat(
            new RsPrint(new RsBuffered(new RsText(body))),
            new TextIs(
                new Joined(
                    "\r\n",
                    "HTTP/1.1 200 OK",
                    String.format("Content-Length: %s", body.length()),
                    "Content-Type: text/plain",
                    "",
                    body
                )
            )
        );
    }

    /**
     * RsText can build a response with a status.
     * @throws IOException If some problem inside
     */
    @Test
    void makesTextResponseWithStatus() throws IOException {
        MatcherAssert.assertThat(
            new HeadPrint(
                new RsText(
                    new RsWithStatus(HttpURLConnection.HTTP_NOT_FOUND),
                    "something not found"
                )
            ).asString(),
            Matchers.containsString(
                Integer.toString(HttpURLConnection.HTTP_NOT_FOUND)
            )
        );
    }
}
