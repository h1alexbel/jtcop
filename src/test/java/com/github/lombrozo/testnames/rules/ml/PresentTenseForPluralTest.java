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

/**
 * Tests for {@link PresentTenseForPlural}.
 *
 * @since 1.4.1
 */
final class PresentTenseForPluralTest {

    @Test
    void passesOnPresentTenseWithPresentVerb() throws Exception {
        final List<Tag> tags = new ListOf<>(Tag.PRP, Tag.PRP, Tag.VBP);
        MatcherAssert.assertThat(
            String.format(
                "Tags: %s should form present tense for plural",
                tags
            ),
            new PresentTenseForPlural(tags).value(),
            new IsEqual<>(true)
        );
    }

    @Test
    void passesOnPresentTenseWithBaseVerb() throws Exception {
        final List<Tag> tags = new ListOf<>(Tag.PRP, Tag.PRP, Tag.VB);
        MatcherAssert.assertThat(
            String.format(
                "Tags '%s' should form present tense for plural",
                tags
            ),
            new PresentTenseForPlural(tags).value(),
            new IsEqual<>(true)
        );
    }
}
