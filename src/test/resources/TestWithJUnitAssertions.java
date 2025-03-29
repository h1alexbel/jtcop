/*
 * MIT License
 *
 * Copyright (c) 2022-2025 Volodya Lombrozo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.lombrozo.testnames;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestWithJUnitAssertions {

    /**
     * Default explanation for assertions.
     */
    @SuppressWarnings({"JTCOP.Monkey"})
    private final static String DEFAULT_EXPLANATION = "JUnit explanation";

    /**
     * Default supplier for assertions.
     */
    @SuppressWarnings({"JTCOP.Donkey", "JTCOP.Elephant"})
    private final static Supplier<String> DEFAULT_SUPPLIER = () -> TestWithJUnitAssertions.DEFAULT_EXPLANATION;

    /**
     * Unnecessary number.
     */
    @SuppressWarnings("JTCOP.Crocodile")
    private final int number = 1;

    @Test
    void withMessages() {
        Assertions.assertEquals("1", "1", DEFAULT_EXPLANATION);
        Assertions.assertEquals("1", "1", DEFAULT_SUPPLIER);
        Assertions.assertTrue(true, DEFAULT_EXPLANATION);
        Assertions.assertTrue(true, DEFAULT_SUPPLIER);
        Assertions.assertTrue(true, "JUnit explanation");
        Assertions.assertTrue(true, String.format("JUnit %s", "explanation"));
        Assertions.assertFalse(false, DEFAULT_EXPLANATION);
        Assertions.assertFalse(false, DEFAULT_SUPPLIER);
        Assertions.assertNotEquals("1", "2", DEFAULT_EXPLANATION);
        Assertions.assertNotEquals("1", "2", DEFAULT_SUPPLIER);
        Assertions.assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3}, DEFAULT_EXPLANATION);
        Assertions.assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3}, DEFAULT_SUPPLIER);
        Assertions.assertLinesMatch(
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            DEFAULT_EXPLANATION
        );
        Assertions.assertLinesMatch(
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            DEFAULT_SUPPLIER
        );
        Assertions.assertInstanceOf(Integer.class, 1, DEFAULT_SUPPLIER);
        Assertions.assertInstanceOf(Integer.class, 1, DEFAULT_EXPLANATION);
        Assertions.assertNotSame(new Object(), new Object(), DEFAULT_EXPLANATION);
        Assertions.assertNotSame(new Object(), new Object(), DEFAULT_SUPPLIER);
        Assertions.assertSame(1, 1, DEFAULT_EXPLANATION);
        Assertions.assertSame(1, 1, DEFAULT_SUPPLIER);
        Assertions.assertNull(null, DEFAULT_EXPLANATION);
        Assertions.assertNull(null, DEFAULT_SUPPLIER);
        Assertions.assertNotNull(new Object(), DEFAULT_EXPLANATION);
        Assertions.assertNotNull(new Object(), DEFAULT_SUPPLIER);
        Assertions.assertTimeout(
            Duration.ZERO,
            () -> {
            },
            DEFAULT_EXPLANATION
        );
        Assertions.assertTimeout(
            Duration.ZERO,
            () -> {
            },
            DEFAULT_SUPPLIER
        );
        Assertions.assertTimeoutPreemptively(
            Duration.ZERO,
            () -> {
            },
            DEFAULT_EXPLANATION
        );
        Assertions.assertTimeoutPreemptively(
            Duration.ZERO,
            () -> {
            },
            DEFAULT_SUPPLIER
        );
        Assertions.assertThrowsExactly(
            IllegalStateException.class,
            () -> {
                throw new IllegalStateException();
            },
            DEFAULT_EXPLANATION
        );
        Assertions.assertThrowsExactly(
            IllegalStateException.class,
            () -> {
                throw new IllegalStateException();
            },
            DEFAULT_SUPPLIER
        );
        Assertions.assertIterableEquals(
            new int[]{1, 2, 3},
            new int[]{1, 2, 3},
            DEFAULT_EXPLANATION
        );
        Assertions.assertIterableEquals(
            new int[]{1, 2, 3},
            new int[]{1, 2, 3},
            DEFAULT_SUPPLIER
        );
        Assertions.assertDoesNotThrow(
            () -> {
            },
            DEFAULT_SUPPLIER
        );
        Assertions.assertDoesNotThrow(
            () -> {
            },
            DEFAULT_EXPLANATION
        );
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> {
                throw new IllegalStateException();
            },
            DEFAULT_EXPLANATION
        );
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> {
                throw new IllegalStateException();
            },
            DEFAULT_SUPPLIER
        );
    }

    @Test
    void withoutMessages() {
        Assertions.assertEquals("1", "1");
        Assertions.assertTrue(true);
        Assertions.assertFalse(false);
        Assertions.assertNotEquals("1", "2");
        Assertions.assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3});
        Assertions.assertLinesMatch(Collections.EMPTY_LIST, Collections.EMPTY_LIST);
        Assertions.assertInstanceOf(Integer.class, 1);
        Assertions.assertNotSame(new Object(), new Object());
        Assertions.assertSame(1, 1);
        Assertions.assertNull(null);
        Assertions.assertNotNull(new Object());
        Assertions.assertTimeout(
            Duration.ZERO,
            () -> {
            }
        );
        Assertions.assertTimeoutPreemptively(
            Duration.ZERO,
            () -> {
            }
        );
        Assertions.assertThrowsExactly(
            IllegalStateException.class,
            () -> {
                throw new IllegalStateException();
            }
        );
        Assertions.assertIterableEquals(
            new int[]{1, 2, 3},
            new int[]{1, 2, 3}
        );
        Assertions.assertDoesNotThrow(
            () -> {
            }
        );
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> {
                throw new IllegalStateException();
            }
        );
    }

    @Test
    void specialAssertions() {
        Assertions.fail("JUnit explanation");
        Assertions.fail(new IllegalStateException());
        Assertions.fail();
        Assertions.fail("JUnit explanation", new IllegalStateException());
        Assertions.fail(() -> "JUnit explanation");
        Assertions.assertAll(
            () -> {
            }
        );
    }

    /**
     * This is test for the issur #453.
     * You can read more about the issue right here:
     * https://github.com/volodya-lombrozo/jtcop/issues/453
     */
    @Test
    void generatesSyntaxForGrammar() {
        final List<String> definitions = new ArrayList<>(0);
        final String top = "rule";
        String[] programs = definitions.stream().toArray(String[]::new);
        String message = "We expect that the randomly generated code will be verified without errors";
        try {
            Assertions.assertDoesNotThrow(
                () -> Stream.generate(() -> top)
                    .limit(50)
                    .peek(System.out::println)
                    .count(),
                message
            );
        } catch (Exception exception) {
            Assertions.fail(message, exception);
        }
    }
}