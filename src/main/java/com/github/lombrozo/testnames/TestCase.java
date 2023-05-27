/*
 * MIT License
 *
 * Copyright (c) 2022-2023 Volodya
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

import java.util.Collection;
import java.util.Collections;
import lombok.Data;

/**
 * The test case.
 *
 * @since 0.1.0
 */
public interface TestCase {

    /**
     * The name of test case.
     *
     * @return The name of test case as string.
     */
    String name();

    /**
     * The suppressed rules.
     * @return The suppressed rules.
     */
    Collection<String> suppressed();

    /**
     * The method assertions.
     * @return The list of assertions.
     */
    Collection<Assertion> assertions();

    /**
     * The fake test case.
     *
     * @since 0.1.0
     */
    @Data
    final class Fake implements TestCase {

        /**
         * The name of test.
         */
        private final String name;

        /**
         * The suppressed rules.
         */
        private final Collection<String> suppressed;

        /**
         * Ctor.
         */
        public Fake() {
            this("FakeCase");
        }

        /**
         * Primary ctor.
         *
         * @param name The name of test case
         * @checkstyle ParameterNameCheck (6 lines)
         */
        public Fake(final String name) {
            this(name, Collections.emptyList());
        }

        /**
         * Ctor.
         * @param name The name of test case
         * @param suppressed The suppressed rules
         */
        public Fake(final String name, final Collection<String> suppressed) {
            this.name = name;
            this.suppressed = suppressed;
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public Collection<String> suppressed() {
            return Collections.unmodifiableCollection(this.suppressed);
        }

        @Override
        public Collection<Assertion> assertions() {
            return Collections.emptyList();
        }
    }
}
