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
package com.github.lombrozo.testnames.bytecode;

import com.github.lombrozo.testnames.ProductionClass;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.ClassFile;

/**
 * Utility class for parsing bytecode.
 *
 * @since 0.1.17
 */
final class BytecodeClass {

    /**
     * Path to class file.
     */
    private final Path path;

    /**
     * Constructor.
     * @param bytecode Path to class file.
     */
    BytecodeClass(final Path bytecode) {
        this.path = bytecode;
    }

    /**
     * Check if file is class file.
     * @return True if file is class file.
     */
    boolean isClass() {
        return this.path.getFileName().toString().endsWith(".class");
    }

    /**
     * Convert to test class.
     * @return Test class.
     */
    BytecodeTestClass toTest() {
        return new BytecodeTestClass(this.path, this.parse());
    }

    /**
     * Convert to production class.
     * @return Production class.
     */
    ProductionClass toProductionClass() {
        return new BytecodeProductionClass(this.parse());
    }

    /**
     * Parse class file.
     * @return Class.
     */
    private CtClass parse() {
        try {
            return ClassPool.getDefault()
                .makeClass(
                    new ClassFile(
                        new DataInputStream(
                            Files.newInputStream(this.path.toFile().toPath())
                        )
                    )
                );
        } catch (final IOException ex) {
            throw new IllegalStateException(
                String.format("Can't parse class %s", this.path),
                ex
            );
        }
    }
}
