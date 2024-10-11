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

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;

/**
 * Present tense for plural.
 *
 * @since 1.4.0
 */
public final class PresentTenseForPlural implements Scalar<Boolean> {

    /**
     * Tags.
     */
    private final Collection<Tag> tags;

    /**
     * Constructor.
     * @param tgs Tags
     */
    public PresentTenseForPlural(final Tag... tgs) {
        this(new ListOf<>(tgs));
    }

    /**
     * Primary constructor.
     * @param tgs Tags
     */
    public PresentTenseForPlural(final Collection<Tag> tgs) {
        this.tags = tgs;
    }

    @Override
    public Boolean value() throws Exception {
        System.out.println(this.tags);
        final List<Tag> collected = this.tags.stream()
            .filter(
                tag ->
                    "PRP".equals(tag.name()) || tag.isVerb() ||
                    "NNS".equals(tag.name())
            )
            .collect(Collectors.toList());
        final List<Tag> verbs = collected.stream().filter(
            tag -> tag.isVerb() || "NNS".equals(tag.name())
        ).collect(Collectors.toList());
        final boolean result;
        if (collected.size() > 2) {
            result = this.pluralStartsWithSubject(collected, verbs);
        } else {
            result = false;
        }
        return result;
    }

    private boolean pluralStartsWithSubject(final List<Tag> collected, final List<Tag> verbs) {
        return ("PRP".equals(collected.get(0).name()) && "PRP".equals(collected.get(1).name()) || "PRP".equals(collected.get(0).name()) && "NNS".equals(collected.get(1).name()) || "NNS".equals(collected.get(1).name())) && !verbs.isEmpty();
    }
}
