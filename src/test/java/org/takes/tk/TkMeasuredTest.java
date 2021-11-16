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
import org.llorllale.cactoos.matchers.TextHasString;
import org.takes.rq.RqFake;
import org.takes.rs.RsPrint;

/**
 * Test case for {@link TkMeasured}.
 * @since 0.10
 */
final class TkMeasuredTest {

    /**
     * TkMeasured can create a response with HTTP header "X-Take-Millis".
     * @throws Exception If some problem inside
     */
    @Test
    void createsMeasuredResponse() throws Exception {
        final String header = "X-Takes-Millis";
        MatcherAssert.assertThat(
            new RsPrint(
                new TkMeasured(new TkText("default header response")).act(
                    new RqFake()
                )
            ),
            new TextHasString(header)
        );
    }

    /**
     * TkMeasured can create a response with custom HTTP header.
     * @throws Exception If some problem occurs
     */
    @Test
    void createsMeasuredResponseWithCustomHeader() throws Exception {
        final String header = "X-Custom-Take-Millis";
        MatcherAssert.assertThat(
            new RsPrint(
                new TkMeasured(
                    new TkText("custom header response"),
                    header
                ).act(new RqFake())
            ),
            new TextHasString(header)
        );
    }
}
