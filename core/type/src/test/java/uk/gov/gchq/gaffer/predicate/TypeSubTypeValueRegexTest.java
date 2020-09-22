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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import uk.gov.gchq.gaffer.predicate.TSV;
import uk.gov.gchq.gaffer.types.TypeSubTypeValue;
import uk.gov.gchq.koryphe.predicate.PredicateTest;
import uk.gov.gchq.koryphe.util.JsonSerialiser;

public class TypeSubTypeValueRegexTest extends PredicateTest {

    @Test
    public void shouldAccepValidValue() {
        // Given
        final TypeSubTypeValueRegex filter = new TypeSubTypeValueRegex(TSV.VALUE, "te[a-d]{3}st");

        // When
        boolean accepted = filter.testFilter(new TypeSubTypeValue("test", "test", "teaadst"));

        // Then
        assertTrue(accepted);
    }

    @Test
    public void shouldRejectInvalidValue() {
        // Given
        final TypeSubTypeValueRegex filter = new TypeSubTypeValueRegex(TSV.VALUE, "fa[a-d]{3}il");

        // When
        boolean accepted = filter.testFilter(new TypeSubTypeValue("test", "test","falndil"));

        // Then
        assertFalse(accepted);
    }

    @Test
    public void shouldJsonSerialiseAndDeserialise() throws IOException {
        // Given
        final TypeSubTypeValueRegex filter = new TypeSubTypeValueRegex(TSV.VALUE,"test");

        // When
        final String json = JsonSerialiser.serialise(filter);

        // Then
        JsonSerialiser.assertEquals(String.format("{%n" +
                "  \"class\" : \"uk.gov.gchq.gaffer.predicate.TypeSubTypeValueRegex\",%n" +
                "  \"field\" : \"VALUE\",%n" +
                "  \"regex\" : \"test\"%n"
                + "  }%n" +
                "}"), json);

        // When 2
        final TypeSubTypeValueRegex deserialisedFilter = JsonSerialiser.deserialise(json, TypeSubTypeValueRegex.class);

        // Then 2
        assertEquals(filter.getPattern().toString(), deserialisedFilter.getPattern().toString());
        assertNotNull(deserialisedFilter);
    }

    @Override
    protected TypeSubTypeValueRegex getInstance() {
        return new TypeSubTypeValueRegex(TSV.VALUE, "[a-zA-Z]{1,12}");
    }

    @Override
    protected Class<TypeSubTypeValueRegex> getPredicateClass() {
        return TypeSubTypeValueRegex.class;
    }
}