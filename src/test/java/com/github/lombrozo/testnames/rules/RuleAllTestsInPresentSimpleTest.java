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

package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.TestClass;
import java.util.Collections;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link RuleAllTestsInPresentSimple}.
 *
 * @since 0.1.0
 */
final class RuleAllTestsInPresentSimpleTest {

    @Test
    void validatesAllWithoutExceptions() {
        MatcherAssert.assertThat(
            new RuleAllTestsInPresentSimple(
                new TestClass.Fake(
                    new TestCase.Fake("removes"),
                    new TestCase.Fake("creates")
                )
            ).complaints(),
            Matchers.empty()
        );
    }

    @Test
    void validatesAllWithExceptions() {
        MatcherAssert.assertThat(
            new RuleAllTestsInPresentSimple(
                new TestClass.Fake(
                    new TestCase.Fake("remove"),
                    new TestCase.Fake("create")
                )
            ).complaints(),
            Matchers.allOf(Matchers.hasSize(1))
        );
    }

    @Test
    void skipsSomeSuppressedChecksOnCaseLevel() {
        MatcherAssert.assertThat(
            new RuleAllTestsInPresentSimple(
                new TestClass.Fake(
                    new TestCase.Fake(
                        "remove",
                        Collections.singletonList("RulePresentTense")
                    ),
                    new TestCase.Fake(
                        "create",
                        Collections.singletonList("RulePresentTense")
                    )
                )
            ).complaints(),
            Matchers.empty()
        );
    }

    @Test
    void skipsSomeSuppressedChecksOnClassLevel() {
        final TestClass.Fake klass = new TestClass.Fake(
            Collections.singletonList("RuleAllTestsInPresentSimple"),
            new TestCase.Fake("remove"),
            new TestCase.Fake("create")
        );
        MatcherAssert.assertThat(
            new RuleSuppressed(new RuleAllTestsInPresentSimple(klass), klass).complaints(),
            Matchers.empty()
        );
    }
}
