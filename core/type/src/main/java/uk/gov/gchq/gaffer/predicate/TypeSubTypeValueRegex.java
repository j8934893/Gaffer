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

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import uk.gov.gchq.gaffer.predicate.TSV;
import uk.gov.gchq.gaffer.types.TypeSubTypeValue;
import uk.gov.gchq.koryphe.Since;
import uk.gov.gchq.koryphe.predicate.KoryphePredicate;

import java.util.regex.Pattern;

public class TypeSubTypeValueRegex extends KoryphePredicate<TypeSubTypeValue> {

    private TSV field;
    private Pattern regex;

    public TypeSubTypeValueRegex() {
        this.field = TSV.VALUE;
    }

    public TypeSubTypeValueRegex(final TSV field, final String regex) {
        this.regex = Pattern.compile(regex);
        this.field = field;
    }

    public TypeSubTypeValueRegex(final TSV field, final Pattern regex) {
        this.regex = regex;
        this.field = field;
    }

    @JsonIgnore
    public Pattern getPattern() {
        return regex;
    }

    @JsonGetter("regex")
    public String getRegex() {
        return regex == null ? null : regex.pattern().toString();
    }

    @JsonIgnore
    public void setRegex(final Pattern regex) {
        this.regex = regex;
    }

    public void setRegex(final String regex) {
        this.regex = Pattern.compile(regex);
    }

    public TSV getField() {
        return field;
    }

    public void setField(final TSV field) {
        this.field = field;
    }

    @Override
    public boolean testFilter(final TypeSubTypeValue typeSubTypeValue) {
        if (null != typeSubTypeValue) {
            switch (field) {
                case TYPE :
                    return _testFilter(typeSubTypeValue.getType());
                case SUBTYPE :
                    return _testFilter(typeSubTypeValue.getSubType());
                case VALUE :
                    return _testFilter(typeSubTypeValue.getValue());
                default:
                    throw new IllegalArgumentException("field can only be type, subtype or value");
            }
        }
        return false;
    }

    public boolean _testFilter(final String input) {
        return !(null == input)
                && this.regex.matcher(input).matches();
    }
}