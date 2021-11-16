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

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cactoos.Bytes;
import org.cactoos.Text;
import org.cactoos.bytes.BytesOf;
import org.cactoos.text.Joined;
import org.cactoos.text.Sticky;
import org.cactoos.text.TextOf;
import org.takes.Response;

/**
 * Response decorator that can print an entire response in HTTP format.
 *
 * <p>The class is immutable and thread-safe.
 *
 * @since 0.1
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class RsPrint extends RsWrap implements Text, Bytes {

    /**
     * Head print representation.
     */
    private final Text head;

    /**
     * Body print representation.
     */
    private final Text body;

    /**
     * Ctor.
     * @param res Original response
     */
    public RsPrint(final Response res) {
        super(res);
        this.head = new Sticky(new HeadPrint(res));
        this.body = new Sticky(new BodyPrint(res));
    }

    @Override
    public String asString() throws Exception {
        return new Joined(
            new TextOf(""),
            this.head,
            this.body
        ).asString();
    }

    @Override
    public byte[] asBytes() throws Exception {
        return new BytesOf(this.asString()).asBytes();
    }
}
