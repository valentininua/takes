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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.cactoos.Text;
import org.cactoos.text.TextOf;
import org.cactoos.text.Trimmed;
import org.cactoos.text.UncheckedText;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.takes.Head;

/**
 * Header Matcher.
 *
 * <p>This "matcher" tests given item headers.
 * <p>The class is immutable and thread-safe.
 *
 * @param <T> Item type. Should be able to return own headers
 * @since 0.31.2
 */
public final class HmHeader<T extends Head> extends TypeSafeMatcher<T> {

    /**
     * Values string used in description of mismatches.
     */
    private static final String VALUES_STR = " -> values: ";

    /**
     * Header matcher.
     */
    private final Matcher<String> header;

    /**
     * Value matcher.
     */
    private final Matcher<Iterable<String>> value;

    /**
     * Mismatched header values.
     */
    private Collection<String> failed;

    /**
     * Ctor.
     * @param hdrm Header matcher
     * @param vlm Value matcher
     */
    public HmHeader(final Matcher<String> hdrm,
        final Matcher<Iterable<String>> vlm) {
        super();
        this.header = hdrm;
        this.value = vlm;
    }

    /**
     * Ctor.
     * @param hdr Header name
     * @param vlm Value matcher
     */
    public HmHeader(final String hdr,
        final Matcher<Iterable<String>> vlm) {
        this(Matchers.equalToIgnoringCase(hdr), vlm);
    }

    /**
     * Ctor.
     * @param hdr Header name
     * @param val Header value
     */
    public HmHeader(final String hdr, final String val) {
        this(
            Matchers.equalToIgnoringCase(hdr),
            Matchers.hasItems(val)
        );
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("header: ")
            .appendDescriptionOf(this.header)
            .appendText(HmHeader.VALUES_STR)
            .appendDescriptionOf(this.value);
    }

    @Override
    public boolean matchesSafely(final T item) {
        try {
            final Iterator<String> headers = HmHeader.headers(item).iterator();
            final Collection<String> values = new ArrayList<>(0);
            while (headers.hasNext()) {
                final String[] parts = HmHeader.split(headers.next());
                final UncheckedText first = new UncheckedText(
                    new Trimmed(new TextOf(parts[0]))
                );
                if (this.header.matches(first.asString())) {
                    final Text second = new Trimmed(new TextOf(parts[1]));
                    values.add(new UncheckedText(second).asString());
                }
            }
            final boolean result = this.value.matches(values);
            if (!result) {
                this.failed = values;
            }
            return result;
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public void describeMismatchSafely(final T item,
        final Description description) {
        description.appendText("header was: ")
            .appendDescriptionOf(this.header)
            .appendText(HmHeader.VALUES_STR)
            .appendValue(this.failed);
    }

    /**
     * Returns item's headers.
     * @param item To extract headers from
     * @return Header lines
     * @throws IOException If something goes wrong
     * @checkstyle NonStaticMethodCheck (2 lines)
     */
    private static Iterable<String> headers(final Head item) throws
        IOException {
        return item.head();
    }

    /**
     * Splits the given header to [name, value] array.
     * @param header Header
     * @return Array in which the first element is header name,
     *  the second is header value
     */
    private static String[] split(final String header) {
        return header.split(":", 2);
    }
}
