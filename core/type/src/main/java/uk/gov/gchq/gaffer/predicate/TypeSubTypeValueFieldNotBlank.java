/*
 * Copyright 2016-2020 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.gaffer.predicate;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import uk.gov.gchq.gaffer.types.TypeSubTypeValue;
import uk.gov.gchq.koryphe.predicate.KoryphePredicate;

import java.util.Objects;

/**
 * Koryphe predicate to accept input {@link TypeSubTypeValue}s which have which have a field (i.e. type, subtype, or value)
 * set to a non-null, non-empty, non-blank value.
 */
public class TypeSubTypeValueFieldNotBlank extends KoryphePredicate<TypeSubTypeValue> {
    private TypeSubTypeValueField field;

    public TypeSubTypeValueField getField() {
        return field;
    }

    public void setField(final TypeSubTypeValueField field) {
        this.field = field;
    }

    @Override
    public boolean test(final TypeSubTypeValue input) {
        Objects.requireNonNull(field, "Relevant field must be specified");
        return input != null && StringUtils.isNotBlank(field.getFieldValue(input));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("field", field).toString();
    }
}