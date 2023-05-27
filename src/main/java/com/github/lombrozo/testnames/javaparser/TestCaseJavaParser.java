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

package com.github.lombrozo.testnames.javaparser;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.VarType;
import com.github.lombrozo.testnames.Assertion;
import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.TestClass;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;

/**
 * Parser for test case.
 *
 * @since 0.1.0
 * @todo #67:90min Continue implementation of TestCaseJavaParser#assertions.
 *  We have to implement the method assertions() in TestCaseJavaParser.
 *  The method should return a list of assertions from different libraries like
 *  Hamcrest, AssertJ, JUnit, etc. Also we have to have a list of tests that check new
 *  functionality.
 */
@Data
final class TestCaseJavaParser implements TestCase {

    /**
     * Method declaration.
     */
    private final MethodDeclaration method;

    /**
     * Parent test class.
     */
    private final TestClass parent;

    /**
     * Ctor.
     *
     * @param name Java method name.
     */
    TestCaseJavaParser(final String name) {
        this(new MethodDeclaration(new NodeList<>(), new VarType(), name));
    }

    /**
     * Ctor.
     *
     * @param method Java method
     * @checkstyle ParameterNameCheck (6 lines)
     */
    TestCaseJavaParser(final MethodDeclaration method) {
        this(method, new TestClass.Fake());
    }

    /**
     * Ctor.
     * @param method Java method
     * @param parent Parent test class
     */
    TestCaseJavaParser(
        final MethodDeclaration method,
        final TestClass parent
    ) {
        this.method = method;
        this.parent = parent;
    }

    @Override
    public String name() {
        return this.method.getNameAsString();
    }

    @Override
    public Collection<String> suppressed() {
        return Stream.concat(
            this.parent.suppressed().stream(),
            new SuppressedAnnotations(this.method).suppressed()
        ).collect(Collectors.toSet());
    }

    @Override
    public Collection<Assertion> assertions() {
        return Collections.emptyList();
    }
}
