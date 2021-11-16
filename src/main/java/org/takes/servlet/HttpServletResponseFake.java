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
package org.takes.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.cactoos.io.InputOf;
import org.cactoos.io.OutputTo;
import org.cactoos.io.TeeInput;
import org.cactoos.iterable.Filtered;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.Lowered;
import org.cactoos.text.StartsWith;
import org.cactoos.text.TextOf;
import org.takes.Response;
import org.takes.facets.cookies.RsWithCookie;
import org.takes.rs.RsWithHeader;
import org.takes.rs.RsWithStatus;
import org.takes.rs.RsWithoutHeader;

/**
 * Fake HttpServletResponse (for unit tests).
 *
 * @since 1.14
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class HttpServletResponseFake implements HttpServletResponse {
    /**
     * A Takes response.
     */
    private final AtomicReference<Response> response;

    /**
     * Ctor.
     * @param resp A Takes Response object
     */
    public HttpServletResponseFake(final Response resp) {
        this.response = new AtomicReference<>(resp);
    }

    @Override
    public void addCookie(final Cookie cookie) {
        this.response.set(
            new RsWithCookie(
                this.response.get(),
                cookie.getName(),
                cookie.getValue()
            )
        );
    }

    @Override
    public void setHeader(final String name, final String value) {
        this.response.set(
            new RsWithHeader(
                new RsWithoutHeader(
                    this.response.get(),
                    name
                ),
                name,
                value
            )
        );
    }

    @Override
    public void setStatus(final int code) {
        this.response.set(
            new RsWithStatus(
                this.response.get(),
                code
            )
        );
    }

    @Override
    public void sendError(
        final int code,
        final String reason
    ) throws IOException {
        this.response.set(
            new RsWithStatus(
                this.response.get(),
                code,
                reason
            )
        );
    }

    @Override
    public Collection<String> getHeaders(final String header) {
        final String prefix = String.format(
            "%s",
            new Lowered(header)
        );
        try {
            return new ListOf<>(
                new Filtered<>(
                    hdr -> new StartsWith(
                        new Lowered(hdr), new TextOf(prefix)
                    ).value(),
                    this.response.get().head()
                )
            );
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new LengthOf(
            new TeeInput(
                new InputOf(this.response.get().body()),
                new OutputTo(baos)
            )
        ).intValue();
        return new ServletOutputStreamTo(baos);
    }

    @Override
    public String getHeader(final String header) {
        throw new UnsupportedOperationException("#getHeaders()");
    }

    @Override
    public boolean containsHeader(final String header) {
        throw new UnsupportedOperationException("#containsHeader()");
    }

    @Override
    public String encodeURL(final String url) {
        throw new UnsupportedOperationException("#encodeURL()");
    }

    @Override
    public String encodeRedirectURL(final String url) {
        throw new UnsupportedOperationException("#encodeRedirectURL()");
    }

    /**
     * Encode a URL.
     * @deprecated It not should be used
     * @param url URL to be encoded
     * @return The encoded URL
     */
    @Deprecated
    public String encodeUrl(final String url) {
        throw new UnsupportedOperationException("#encodeUrl()");
    }

    /**
     * Encode a redirect URL.
     * @deprecated It not should be used
     * @param url URL to be encoded
     * @return The encoded redirect URL
     */
    @Deprecated
    public String encodeRedirectUrl(final String url) {
        throw new UnsupportedOperationException("#encodeRedirectUrl()");
    }

    @Override
    public void sendError(final int code) throws IOException {
        throw new UnsupportedOperationException("#sendError()");
    }

    @Override
    public void sendRedirect(final String location) throws IOException {
        throw new UnsupportedOperationException("#sendRedirect()");
    }

    @Override
    public void setDateHeader(final String name, final long time) {
        throw new UnsupportedOperationException("#setDateHeader()");
    }

    @Override
    public void addDateHeader(final String name, final long date) {
        throw new UnsupportedOperationException("#addDateHeader()");
    }

    @Override
    public void addHeader(final String name, final String value) {
        throw new UnsupportedOperationException("#addHeader()");
    }

    @Override
    public void setIntHeader(final String name, final int value) {
        throw new UnsupportedOperationException("#setIntHeader()");
    }

    @Override
    public void addIntHeader(final String name, final int value) {
        throw new UnsupportedOperationException("#addIntHeader()");
    }

    /**
     * Set the response code status and reason.
     * @deprecated It not should be used
     * @param code The status code
     * @param reason The reason originates this code
     */
    @Deprecated
    public void setStatus(final int code, final String reason) {
        throw new UnsupportedOperationException("#setStatus()");
    }

    @Override
    public int getStatus() {
        throw new UnsupportedOperationException("#getStatus()");
    }

    @Override
    public Collection<String> getHeaderNames() {
        throw new UnsupportedOperationException("#getHeaderNames()");
    }

    @Override
    public String getCharacterEncoding() {
        throw new UnsupportedOperationException("#getCharacterEncoding()");
    }

    @Override
    public String getContentType() {
        throw new UnsupportedOperationException("#getContentType()");
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        throw new UnsupportedOperationException("#getWriter()");
    }

    @Override
    public void setCharacterEncoding(final String encoding) {
        throw new UnsupportedOperationException("#setCharacterEncoding()");
    }

    @Override
    public void setContentLength(final int length) {
        throw new UnsupportedOperationException("#setContentLength()");
    }

    @Override
    public void setContentLengthLong(final long length) {
        throw new UnsupportedOperationException("#setContentLengthLong()");
    }

    @Override
    public void setContentType(final String type) {
        throw new UnsupportedOperationException("#setContentType()");
    }

    @Override
    public void setBufferSize(final int size) {
        throw new UnsupportedOperationException("#setBufferSize()");
    }

    @Override
    public int getBufferSize() {
        throw new UnsupportedOperationException("#getBufferSize()");
    }

    @Override
    public void flushBuffer() throws IOException {
        throw new UnsupportedOperationException("#flushBuffer()");
    }

    @Override
    public void resetBuffer() {
        throw new UnsupportedOperationException("#resetBuffer()");
    }

    @Override
    public boolean isCommitted() {
        throw new UnsupportedOperationException("#isCommited()");
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("#reset()");
    }

    @Override
    public void setLocale(final Locale locale) {
        throw new UnsupportedOperationException("#setLocale()");
    }

    @Override
    public Locale getLocale() {
        throw new UnsupportedOperationException("#getLocale()");
    }
}
