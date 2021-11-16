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
package org.takes.facets.forward;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.StartsWith;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqFake;
import org.takes.rs.ResponseOf;
import org.takes.rs.RsEmpty;
import org.takes.rs.RsPrint;

/**
 * Test case for {@link TkForward}.
 * @since 0.2
 * @checkstyle ClassDataAbstractionCouplingCheck (100 lines)
 */
final class TkForwardTest {

    /**
     * TkForward can catch RsForward.
     * @throws Exception If some problem inside
     */
    @Test
    void catchesExceptionCorrectly() throws Exception {
        final Take take = new Take() {
            @Override
            public Response act(final Request request) throws RsForward {
                throw new RsForward("/");
            }
        };
        MatcherAssert.assertThat(
            new RsPrint(
                new TkForward(take).act(new RqFake())
            ),
            new StartsWith("HTTP/1.1 303 See Other")
        );
    }

    /**
     * TkForward can catch RsForward throws by Response.
     * @throws Exception If some problem inside
     */
    @Test
    void catchesExceptionThrownByResponse() throws Exception {
        final Take take =
            request -> new ResponseOf(
                () -> new RsEmpty().head(),
                () -> {
                    throw new RsForward("/b");
                }
            );
        MatcherAssert.assertThat(
            new RsPrint(
                new TkForward(take).act(new RqFake())
            ),
            new StartsWith("HTTP/1.1 303")
        );
    }

}