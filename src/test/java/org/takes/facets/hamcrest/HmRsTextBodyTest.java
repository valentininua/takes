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
package org.takes.facets.hamcrest;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.takes.rs.RsWithBody;

/**
 * Test case for {@link HmRsTextBody}.
 *
 * @since 2.0
 */
final class HmRsTextBodyTest {

    /**
     * HmRsTextBody can test if body equals text.
     */
    @Test
    void testsBodyValueContainsText() {
        final String same = "<h1>Hello</h1>";
        MatcherAssert.assertThat(
            new RsWithBody(same),
            new HmRsTextBody(same)
        );
    }

    /**
     * HmRsTextBody can test if body doesn't equal to text.
     */
    @Test
    void testsBodyValueDoesNotContainsText() {
        MatcherAssert.assertThat(
            new RsWithBody("Some response"),
            new IsNot<>(new HmRsTextBody("expected something else"))
        );
    }
}
