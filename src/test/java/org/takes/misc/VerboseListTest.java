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

package org.takes.misc;

import java.util.Collections;
import java.util.List;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests for {@link VerboseList}.
 *
 * @since 0.32
 */
@SuppressWarnings("PMD.TooManyMethods")
@ExtendWith(MockitoExtension.class)
final class VerboseListTest {

    /**
     * Custom exception message.
     */
    private static final String MSG = "Error message";

    /**
     * Decorated List.
     */
    @Mock
    private List<Object> origin;

    /**
     * Decorator.
     */
    private VerboseList<Object> list;

    /**
     * Creates decorator.
     */
    @BeforeEach
    void setUp() {
        this.list = new VerboseList<Object>(
            this.origin,
            new TextOf(VerboseListTest.MSG)
        );
    }

    /**
     * VerboseList should delegate size method to decorated List.
     */
    @Test
    void delegatesSize() {
        this.list.size();
        Mockito.verify(this.origin).size();
    }

    /**
     * VerboseList should delegate isEmpty method to decorated List.
     */
    @Test
    void delegatesIsEmpty() {
        this.list.isEmpty();
        Mockito.verify(this.origin).isEmpty();
    }

    /**
     * VerboseList should delegate contains method to decorated List.
     */
    @Test
    void delegatesContains() {
        final Object obj = new Object();
        this.list.contains(obj);
        Mockito.verify(this.origin).contains(obj);
    }

    /**
     * VerboseList should return {@link VerboseIterator}.
     */
    @Test
    void returnsVerboseIterator() {
        MatcherAssert.assertThat(
            this.list.iterator(),
            Matchers.instanceOf(VerboseIterator.class)
        );
    }

    /**
     * VerboseList should delegate toArray method to decorated List.
     */
    @Test
    void delegatesToArray() {
        this.list.toArray();
        Mockito.verify(this.origin).toArray();
        final Object[] array = new Object[1];
        this.list.toArray(array);
        Mockito.verify(this.origin).toArray(array);
    }

    /**
     * VerboseList should delegate add method to decorated List.
     */
    @Test
    void delegatesAdd() {
        final int index = 5;
        final Object obj = new Object();
        this.list.add(obj);
        Mockito.verify(this.origin).add(obj);
        this.list.add(index, obj);
        Mockito.verify(this.origin).add(index, obj);
    }

    /**
     * VerboseList should delegate remove method to decorated List.
     */
    @Test
    void delegatesRemove() {
        final int index = 5;
        final Object obj = new Object();
        this.list.remove(obj);
        Mockito.verify(this.origin).remove(obj);
        this.list.remove(index);
        Mockito.verify(this.origin).remove(index);
    }

    /**
     * VerboseList should delegate containsAll method to decorated List.
     */
    @Test
    void delegatesContainsAll() {
        final List<Object> collection = Collections.emptyList();
        this.list.containsAll(collection);
        Mockito.verify(this.origin).containsAll(collection);
    }

    /**
     * VerboseList should delegate addAll method to decorated List.
     */
    @Test
    void delegatesAddAll() {
        final List<Object> collection = Collections.emptyList();
        this.list.addAll(collection);
        Mockito.verify(this.origin).addAll(collection);
        final int index = 5;
        this.list.addAll(index, collection);
        Mockito.verify(this.origin).addAll(index, collection);
    }

    /**
     * VerboseList should delegate removeAll method to decorated List.
     */
    @Test
    void delegatesRemoveAll() {
        final List<Object> collection = Collections.emptyList();
        this.list.removeAll(collection);
        Mockito.verify(this.origin).removeAll(collection);
    }

    /**
     * VerboseList should delegate retainAll method to decorated List.
     */
    @Test
    void delegatesRetainAll() {
        final List<Object> collection = Collections.emptyList();
        this.list.retainAll(collection);
        Mockito.verify(this.origin).retainAll(collection);
    }

    /**
     * VerboseList should delegate clear method to decorated List.
     */
    @Test
    void delegatesClear() {
        this.list.clear();
        Mockito.verify(this.origin).clear();
    }

    /**
     * VerboseList should delegate get method to decorated List.
     */
    @Test
    void delegatesGet() {
        final int index = 5;
        this.list.get(index);
        Mockito.verify(this.origin).get(index);
    }

    /**
     * VerboseList should delegate set method to decorated List.
     */
    @Test
    void delegatesSet() {
        final int index = 5;
        final Object obj = new Object();
        this.list.set(index, obj);
        Mockito.verify(this.origin).set(index, obj);
    }

    /**
     * VerboseList should delegate indexOf method to decorated List.
     */
    @Test
    void delegatesIndexOf() {
        final Object obj = new Object();
        this.list.indexOf(obj);
        Mockito.verify(this.origin).indexOf(obj);
    }

    /**
     * VerboseList should delegate lastIndexOf method to decorated List.
     */
    @Test
    void delegatesLastIndexOf() {
        final Object obj = new Object();
        this.list.lastIndexOf(obj);
        Mockito.verify(this.origin).lastIndexOf(obj);
    }

    /**
     * VerboseList should delegate listIterator method to decorated List.
     */
    @Test
    void delegatesListIterator() {
        this.list.listIterator();
        Mockito.verify(this.origin).listIterator();
        final int index = 5;
        this.list.listIterator(index);
        Mockito.verify(this.origin).listIterator(index);
    }

    /**
     * VerboseList should delegate subList method to decorated List.
     */
    @Test
    void delegatesSubList() {
        final int from = 3;
        final int toidx = 5;
        this.list.subList(from, toidx);
        Mockito.verify(this.origin).subList(from, toidx);
    }

    /**
     * VerboseList should wraps OutOfBoundsException thrown by addAll method.
     */
    @Test
    void wrapsIndexOutOfBoundsExceptionFromAddAll() {
        final int index = 5;
        final List<Object> collection = Collections.emptyList();
        final Exception cause = new IndexOutOfBoundsException();
        Mockito.doThrow(cause).when(this.origin).addAll(index, collection);
        this.assertThat(() -> this.list.addAll(index, collection), cause);
    }

    /**
     * VerboseList should wraps OutOfBoundsException thrown by get method.
     */
    @Test
    void wrapsIndexOutOfBoundsExceptionFromGet() {
        final int index = 5;
        final Exception cause = new IndexOutOfBoundsException();
        Mockito.doThrow(cause).when(this.origin).get(index);
        this.assertThat(() -> this.list.get(index), cause);
    }

    /**
     * VerboseList should wraps OutOfBoundsException thrown by set method.
     */
    @Test
    void wrapsIndexOutOfBoundsExceptionFromSet() {
        final int index = 5;
        final Object obj = new Object();
        final Exception cause = new IndexOutOfBoundsException();
        Mockito.doThrow(cause).when(this.origin).set(index, obj);
        this.assertThat(() -> this.list.set(index, obj), cause);
    }

    /**
     * VerboseList should wraps OutOfBoundsException thrown by add method.
     */
    @Test
    void wrapsIndexOutOfBoundsExceptionFromAdd() {
        final int index = 5;
        final Object obj = new Object();
        final Exception cause = new IndexOutOfBoundsException();
        Mockito.doThrow(cause).when(this.origin).add(index, obj);
        this.assertThat(() -> this.list.add(index, obj), cause);
    }

    /**
     * VerboseList should wraps OutOfBoundsException thrown by remove method.
     */
    @Test
    void wrapsIndexOutOfBoundsExceptionFromRemove() {
        final int index = 5;
        final Exception cause = new IndexOutOfBoundsException();
        Mockito.doThrow(cause).when(this.origin).remove(index);
        this.assertThat(() -> this.list.remove(index), cause);
    }

    /**
     * VerboseList should wraps OutOfBoundsException thrown by listIterator
     * method.
     */
    @Test
    void wrapsIndexOutOfBoundsExceptionFromListIterator() {
        final int index = 5;
        final Exception cause = new IndexOutOfBoundsException();
        Mockito.doThrow(cause).when(this.origin).listIterator(index);
        this.assertThat(() -> this.list.listIterator(index), cause);
    }

    /**
     * VerboseList should wraps OutOfBoundsException thrown by subList method.
     */
    @Test
    void wrapsIndexOutOfBoundsExceptionFromSubList() {
        final int from = 2;
        final int toidx = 5;
        final Exception cause = new IndexOutOfBoundsException();
        Mockito.doThrow(cause).when(this.origin).subList(from, toidx);
        this.assertThat(() -> this.list.subList(from, toidx), cause);
    }

    /**
     * Assert cause.
     * @param exec Code block.
     * @param cause Cause.
     */
    private void assertThat(final Executable exec, final Exception cause) {
        MatcherAssert.assertThat(
            Assertions.assertThrows(IndexOutOfBoundsException.class, exec),
            Matchers.allOf(
                Matchers.hasProperty("message", Matchers.is(VerboseListTest.MSG)),
                Matchers.hasProperty("cause", Matchers.is(cause))
            )
        );
    }
}
