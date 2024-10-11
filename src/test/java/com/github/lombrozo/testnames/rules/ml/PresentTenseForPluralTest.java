/*
 * MIT License
 *
 * Copyright (c) 2022-2024 Volodya Lombrozo
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
package com.github.lombrozo.testnames.rules.ml;

import java.util.List;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests for {@link PresentTenseForPlural}.
 *
 * @since 1.4.1
 */
final class PresentTenseForPluralTest {

    @MethodSource("presentTenseWithPlural")
    @ParameterizedTest
    void passesOnPresentTenseForPlural(final List<Tag> tags) throws Exception {
        MatcherAssert.assertThat(
            String.format(
                "Tags: %s should form present tense for plural",
                tags
            ),
            new PresentTenseForPlural(tags).value(),
            new IsEqual<>(true)
        );
    }

    @MethodSource("shouldBeRejected")
    @ParameterizedTest
    void rejectsOnNonPresentTenseForPlural(final List<Tag> tags) throws Exception {
        MatcherAssert.assertThat(
            String.format(
                "Tags: %s should not form present tense for plural",
                tags
            ),
            new PresentTenseForPlural(tags).value(),
            new IsEqual<>(false)
        );
    }

    private static List<List<Tag>> presentTenseWithPlural() {
        return new ListOf<>(
            new ListOf<>(Tag.PRP, Tag.PRP, Tag.VBP),
            new ListOf<>(Tag.PRP, Tag.PRP, Tag.VB),
            new ListOf<>(Tag.PRP, Tag.NNS, Tag.VBP),
            new ListOf<>(Tag.PRP, Tag.NNS, Tag.VB)
        );
    }

    private static List<List<Tag>> shouldBeRejected() {
        return new ListOf<>(
            new ListOf<>(Tag.NNS, Tag.PRP, Tag.VBP),
            new ListOf<>(Tag.NNS, Tag.PRP, Tag.VB),
            new ListOf<>(Tag.NNS, Tag.NNS, Tag.VBP),
            new ListOf<>(Tag.NNS, Tag.NNS, Tag.VB),
            new ListOf<>(Tag.PRP, Tag.PRP, Tag.VBZ),
            new ListOf<>(Tag.PRP, Tag.PRP, Tag.VV),
            new ListOf<>(Tag.PRP, Tag.NNS, Tag.VHP),
            new ListOf<>(Tag.PRP, Tag.NN, Tag.VBP),
            new ListOf<>(Tag.PRP, Tag.PRP, Tag.UNKNOWN),
            new ListOf<>(Tag.PRP, Tag.PRP, Tag.JJ)
        );
    }
}
