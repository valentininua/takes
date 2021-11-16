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
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cactoos.Text;
import org.cactoos.text.Lowered;
import org.cactoos.text.TextOf;
import org.cactoos.text.Trimmed;
import org.cactoos.text.UncheckedText;

/**
 * Media type.
 *
 * <p>The class is immutable and thread-safe.
 *
 * @since 0.6
 * @see org.takes.facets.fork.FkTypes
 * @todo #998:30min Please use {@link org.cactoos.text.Split} instead of
 *  {@link String.split} as an elegant way.
 *  To completely leverage the {@link org.cactoos.text.Split} here, it is
 *  required for the completion of issue
 *  https://github.com/yegor256/cactoos/issues/1251 and upgrading to that
 *  version of Cactoos.
 */
@ToString
@EqualsAndHashCode
final class MediaType implements Comparable<MediaType> {

    /**
     * Pattern matching non-digit symbols.
     */
    private static final Pattern NON_DIGITS = Pattern.compile("[^0-9\\.]");

    /**
     * Priority.
     */
    private final Double prio;

    /**
     * High part.
     */
    private final String high;

    /**
     * Low part.
     */
    private final String low;

    /**
     * Ctor.
     * @param text Text to parse
     */
    MediaType(final String text) {
        this.prio = MediaType.priority(text);
        this.high = MediaType.highPart(text);
        this.low = MediaType.lowPart(text);
    }

    @Override
    public int compareTo(final MediaType type) {
        int cmp = this.prio.compareTo(type.prio);
        if (cmp == 0) {
            cmp = this.high.compareTo(type.high);
            if (cmp == 0) {
                cmp = this.low.compareTo(type.low);
            }
        }
        return cmp;
    }

    /**
     * Matches.
     * @param type Another type
     * @return TRUE if matches
     * @checkstyle BooleanExpressionComplexityCheck (10 lines)
     */
    public boolean matches(final MediaType type) {
        final String star = "*";
        return (this.high.equals(star)
            || type.high.equals(star)
            || this.high.equals(type.high))
            && (this.low.equals(star)
            || type.low.equals(star)
            || this.low.equals(type.low));
    }

    /**
     * Splits the text parts.
     * @param text The text to be split.
     * @return Two first parts of the media type.
     */
    private static String[] split(final String text) {
        return text.split(";", 2);
    }

    /**
     * Returns the media type priority.
     * @param text The media type text.
     * @return The priority of the media type.
     */
    private static Double priority(final String text) {
        final String[] parts = MediaType.split(text);
        final Double priority;
        if (parts.length > 1) {
            final String num =
                MediaType.NON_DIGITS.matcher(parts[1]).replaceAll("");
            if (num.isEmpty()) {
                priority = 0.0d;
            } else {
                priority = Double.parseDouble(num);
            }
        } else {
            priority = 1.0d;
        }
        return priority;
    }

    /**
     * Returns the high part of the media type.
     * @param text The media type text.
     * @return The high part of the media type.
     */
    private static String highPart(final String text) {
        return MediaType.sectors(text)[0];
    }

    /**
     * Returns the low part of the media type.
     * @param text The media type text.
     * @return The low part of the media type.
     */
    private static String lowPart(final String text) {
        final String[] sectors = MediaType.sectors(text);
        final Text sector;
        if (sectors.length > 1) {
            sector = new Trimmed(new TextOf(sectors[1]));
        } else {
            sector = new TextOf("");
        }
        return new UncheckedText(sector).asString();
    }

    /**
     * Returns the media type sectors.
     * @param text The media type text.
     * @return String array with the sectors of the media type.
     */
    private static String[] sectors(final String text) {
        return new UncheckedText(
            new Lowered(MediaType.split(text)[0])
        ).asString()
            .split(
                "/", 2
            );
    }

}
