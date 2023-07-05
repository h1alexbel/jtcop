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

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithMembers;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JavaParser class.
 * The utility class for working with JavaParser library.
 *
 * @since 0.1.15
 */
final class JavaParserClass {

    /**
     * Parsed Java class.
     */
    private final Node klass;

    /**
     * Ctor.
     * @param stream Input stream with java class.
     */
    JavaParserClass(final Path stream) {
        this(JavaParserClass.parse(stream));
    }

    /**
     * Ctor.
     * @param stream Input stream with java class.
     */
    JavaParserClass(final InputStream stream) {
        this(StaticJavaParser.parse(stream));
    }

    /**
     * Ctor.
     * @param unit Compilation unit.
     */
    JavaParserClass(final CompilationUnit unit) {
        this(JavaParserClass.fromCompilation(unit));
    }

    /**
     * Ctor.
     * @param unit Compilation unit.
     */
    private JavaParserClass(final Node unit) {
        this.klass = unit;
    }

    /**
     * Annotations of class.
     * @return Annotations of class.
     */
    SuppressedAnnotations annotations() {
        return new SuppressedAnnotations((Node) this.klass);
    }

    /**
     * Methods of class.
     * @param filters Filters for methods.
     * @return Methods of class.
     */
    @SafeVarargs
    final Stream<JavaParserMethod> methods(final Predicate<MethodDeclaration>... filters) {
        return ((NodeWithMembers<TypeDeclaration<?>>) this.klass).getMethods()
            .stream()
            .filter(method -> Stream.of(filters).allMatch(filter -> filter.test(method)))
            .map(JavaParserMethod::new);
    }

    /**
     * Checks if the class is an annotation.
     * @return True if an annotation
     */
    boolean isAnnotation() {
        return this.klass instanceof AnnotationDeclaration;
    }

    /**
     * Checks if the class is an interface.
     * @return True if an interface
     */
    boolean isInterface() {
        return this.klass instanceof ClassOrInterfaceDeclaration
            && this.cast().isInterface();
    }

    /**
     * Checks if the class is a package-info.java.
     * @return True if a package-info.java
     */
    boolean isPackageInfo() {
        return this.klass instanceof ClassOrInterfaceDeclaration
            && "empty".equals(this.cast().getNameAsString());
    }

    /**
     * Returns all parents of the class.
     *
     * @return All parents of the class.
     */
    Collection<Class<?>> parents() {
        final Collection<String> all = this.imports();
        return this.cast().getImplementedTypes().stream()
            .filter(ClassOrInterfaceType::isClassOrInterfaceType)
            .map(ClassOrInterfaceType::getNameWithScope)
            .map(name -> all.stream().filter(s -> s.contains(name)).findFirst().orElse(name))
            .map(this::load)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    /**
     * Loads class from the classpath.
     * @param name Name of the class.
     * @return Loaded class.
     */
    private Optional<Class<?>> load(final String name) {
        Optional<Class<?>> res;
        try {
            res = Optional.ofNullable(
                Thread.currentThread().getContextClassLoader().loadClass(name)
            );
        } catch (final ClassNotFoundException ex) {
            Logger.getLogger(this.getClass().getName())
                .warning(
                    String.format("Can't find class %s in classpath", name)
                );
            res = Optional.empty();
        }
        return res;
    }

    /**
     * All the imports of the current class.
     * @return All the imports of the current class.
     */
    private Collection<String> imports() {
        return this.cast()
            .getParentNode()
            .map(
                node -> ((CompilationUnit) node)
                    .getImports()
                    .stream()
                    .map(ImportDeclaration::getNameAsString)
                    .collect(Collectors.toList())
            )
            .orElse(Collections.emptyList());
    }

    /**
     * Cast the class to ClassOrInterfaceDeclaration.
     * @return ClassOrInterfaceDeclaration
     */
    private ClassOrInterfaceDeclaration cast() {
        if (this.klass instanceof ClassOrInterfaceDeclaration) {
            return (ClassOrInterfaceDeclaration) this.klass;
        } else {
            throw new IllegalStateException(
                String.format("Can't cast %s to ClassOrInterfaceDeclaration", this.klass)
            );
        }
    }

    /**
     * Prestructor of class.
     * @param unit Compilation unit.
     * @return Node with class.
     * @todo #187:90min Provide refactoring for JavaParserClass and TestClassJavaParser.
     *  The JavaParserClass and TestClassJavaParser classes are very similar. They share some logic
     *  and have similar methods. The refactoring should be provided to make the code more
     *  readable and maintainable. Also we have to count the different cases like records and
     *  package-info classes, inner classes and so on. For each case we must have a test.
     */
    private static Node fromCompilation(final CompilationUnit unit) {
        final Queue<Node> all = unit.getChildNodes()
            .stream()
            .filter(node -> node instanceof TypeDeclaration<?>)
            .collect(Collectors.toCollection(LinkedList::new));
        if (all.isEmpty()) {
            all.add(new ClassOrInterfaceDeclaration());
        }
        return all.element();
    }

    /**
     * Parse java by path.
     * @param path Path to java file
     * @return Compilation unit.
     */
    private static CompilationUnit parse(final Path path) {
        try {
            return StaticJavaParser.parse(path);
        } catch (final IOException ex) {
            throw new IllegalStateException(
                String.format("Can't parse java file: %s", path.toAbsolutePath()),
                ex
            );
        }
    }

}
