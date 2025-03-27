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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.Data;

/**
 * The bulk of test cases.
 *
 * @since 0.1.0
 */
public interface TestClass {

    /**
     * Test class fields.
     * @return Test class fields.
     */
    Collection<Field> fields();

    /**
     * The name of class.
     *
     * @return The name of class as string
     */
    String name();

    /**
     * All cases.
     *
     * @return All cases as collection
     */
    Collection<TestCase> all();

    /**
     * Path to the class.
     * @return Path to the class.
     */
    Path path();

    /**
     * The suppressed rules.
     * @return The suppressed rules.
     */
    Collection<String> suppressed();

    /**
     * The characteristics of the test class.
     * @return The characteristics of the test class.
     */
    TestClassCharacteristics characteristics();

    /**
     * The fake test class.
     *
     * @since 0.2
     * @checkstyle ParameterNumberCheck (500 lines)
     */
    @Data
    final class Fake implements TestClass {

        /**
         * The default name of the Fake test class.
         */
        private static final String DEFAULT_NAME = "FakeClassTest";

        /**
         * The name of test class.
         */
        private final String name;

        /**
         * All cases.
         */
        private final Collection<? extends TestCase> all;

        /**
         * All suppressed rules.
         */
        private final List<String> suppressed;

        /**
         * Test class characteristics.
         */
        private final TestClassCharacteristics props;

        /**
         * Primary ctor.
         */
        public Fake() {
            this(Fake.DEFAULT_NAME, Collections.emptyList(), Collections.emptyList());
        }

        /**
         * Constructor.
         * @param all All cases
         */
        public Fake(final TestCase... all) {
            this(Fake.DEFAULT_NAME, all);
        }

        /**
         * Primary ctor.
         * @param extension Is JUnit extension
         */
        public Fake(final boolean extension) {
            this(
                Fake.DEFAULT_NAME,
                Collections.emptyList(),
                Collections.emptyList(),
                new TestClassCharacteristics.Fake(extension)
            );
        }

        /**
         * Ctor.
         * @param name Class name
         * @param all All cases
         */
        public Fake(final String name, final TestCase... all) {
            this(name, Arrays.asList(all), Collections.emptyList());
        }

        /**
         * Create fake test class with suppressed rules.
         * @param suppressed Suppressed rules
         */
        public Fake(final List<String> suppressed) {
            this(Fake.DEFAULT_NAME, Collections.emptyList(), suppressed);
        }

        /**
         * Create fake test class with suppressed rules.
         * @param suppressed Suppressed rules
         * @param all All cases
         */
        public Fake(final List<String> suppressed, final TestCase... all) {
            this(Fake.DEFAULT_NAME, Arrays.asList(all), suppressed);
        }

        /**
         * Constructor.
         * @param props Test class characteristics
         */
        public Fake(final TestClassCharacteristics props) {
            this(
                Fake.DEFAULT_NAME,
                Collections.emptyList(),
                Collections.emptyList(),
                props
            );
        }

        /**
         * Constructor.
         * @param name The name of test class
         * @param all All cases
         * @param suppressed All suppressed rules
         */
        Fake(
            final String name,
            final Collection<? extends TestCase> all,
            final List<String> suppressed
        ) {
            this(name, all, suppressed, new TestClassCharacteristics.Fake(false));
        }

        /**
         * Constructor.
         * @param parent The parent class name
         */
        Fake(final String parent) {
            this(
                Fake.DEFAULT_NAME,
                Collections.emptyList(),
                Collections.emptyList(),
                new TestClassCharacteristics.Fake(parent)
            );
        }

        /**
         * Main ctor.
         * @param name The name of test class
         * @param all All cases
         * @param suppressed All suppressed rules
         * @param props Test class characteristics
         */
        Fake(
            final String name,
            final Collection<? extends TestCase> all,
            final List<String> suppressed,
            final TestClassCharacteristics props
        ) {
            this.name = name;
            this.all = all;
            this.suppressed = suppressed;
            this.props = props;
        }

        @Override
        public Collection<Field> fields() {
            return Collections.emptyList();
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public Collection<TestCase> all() {
            return Collections.unmodifiableCollection(this.all);
        }

        @Override
        public Path path() {
            return Paths.get(this.name);
        }

        @Override
        public Collection<String> suppressed() {
            return Collections.unmodifiableList(this.suppressed);
        }

        @Override
        public TestClassCharacteristics characteristics() {
            return this.props;
        }
    }

    /**
     * The test class with fields.
     * @since 1.4
     */
    final class WithFields implements TestClass {

        /**
         * Fields.
         */
        private final List<Field> fields;

        /**
         * Origin test class.
         */
        private final TestClass origin;

        /**
         * Constructor.
         * @param fields Fields
         */
        public WithFields(final Field... fields) {
            this(Arrays.asList(fields));
        }

        /**
         * Constructor.
         * @param fields Fields.
         */
        WithFields(final List<Field> fields) {
            this(fields, new Fake());
        }

        /**
         * Constructor.
         * @param fields Fields list.
         * @param origin Origin test class.
         */
        WithFields(final List<Field> fields, final TestClass origin) {
            this.fields = fields;
            this.origin = origin;
        }

        @Override
        public Collection<Field> fields() {
            return Collections.unmodifiableList(this.fields);
        }

        @Override
        public String name() {
            return this.origin.name();
        }

        @Override
        public Collection<TestCase> all() {
            return this.origin.all();
        }

        @Override
        public Path path() {
            return this.origin.path();
        }

        @Override
        public Collection<String> suppressed() {
            return this.origin.suppressed();
        }

        @Override
        public TestClassCharacteristics characteristics() {
            return this.origin.characteristics();
        }
    }
}
