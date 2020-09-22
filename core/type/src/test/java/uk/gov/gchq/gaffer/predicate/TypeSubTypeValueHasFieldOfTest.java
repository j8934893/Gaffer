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

public class TypeSubTypeValueHasFieldOfTest extends PredicateTest {

    @Test
    public void shouldAcceptValidValue() {
        // Given
        final TypeSubTypeValueHasFieldOf filter = new TypeSubTypeValueHasFieldOf(TSV.VALUE, "testValue");

        // When
        boolean accepted = filter.testFilter(new TypeSubTypeValue("testType", "testSubType", "testValue"));

        // Then
        assertTrue(accepted);
    }

    @Test
    public void shouldAcceptValidType() {
        // Given
        final TypeSubTypeValueHasFieldOf filter = new TypeSubTypeValueHasFieldOf(TSV.TYPE, "testType");

        // When
        boolean accepted = filter.testFilter(new TypeSubTypeValue("testType", "testSubType", "testValue"));

        // Then
        assertTrue(accepted);
    }

    @Test
    public void shouldAcceptValidSubType() {
        // Given
        final TypeSubTypeValueHasFieldOf filter = new TypeSubTypeValueHasFieldOf(TSV.SUBTYPE, "testSubType");

        // When
        boolean accepted = filter.testFilter(new TypeSubTypeValue("testType", "testSubType", "testValue"));

        // Then
        assertTrue(accepted);
    }

    @Test
    public void shouldRejectInvalidValue() {
        // Given
        final TypeSubTypeValueHasFieldOf filter = new TypeSubTypeValueHasFieldOf(TSV.VALUE, "failValue");

        // When
        boolean accepted = filter.testFilter(new TypeSubTypeValue("testType", "testSubType", "testValue"));

        // Then
        assertFalse(accepted);
    }

    @Test
    public void shouldRejectInvalidType() {
        // Given
        final TypeSubTypeValueHasFieldOf filter = new TypeSubTypeValueHasFieldOf(TSV.TYPE, "failType");

        // When
        boolean accepted = filter.testFilter(new TypeSubTypeValue("testType", "testSubType", "testValue"));

        // Then
        assertFalse(accepted);
    }

    @Test
    public void shouldRejectInvalidSubType() {
        // Given
        final TypeSubTypeValueHasFieldOf filter = new TypeSubTypeValueHasFieldOf(TSV.SUBTYPE, "failSubType");

        //When
        boolean accepted = filter.testFilter(new TypeSubTypeValue("testType", "testSubType", "testValue"));

        // Then
        assertFalse(accepted);
    }

    @Test
    public void shouldJsonSerialiseAndDeserialise() throws IOException {
        // Given
        final TypeSubTypeValueHasFieldOf filter = getInstance();

        // When
        final String json = JsonSerialiser.serialise(filter);

        // Then
        JsonSerialiser.assertEquals(String.format("{%n" +
                "  \"class\" : \"uk.gov.gchq.gaffer.predicate.TypeSubTypeValueHasFieldOf\",%n" +
                "  \"field\" : \"VALUE\",%n" +
                "  \"value\" : \"test\"%n"
                + "  }%n" +
                "}"), json);

        // When 2
        final TypeSubTypeValueHasFieldOf deserialisedFilter = JsonSerialiser.deserialise(json, TypeSubTypeValueHasFieldOf.class);

        // Then 2
        assertEquals(filter.getValue(), deserialisedFilter.getValue());
        assertNotNull(deserialisedFilter);
    }

    @Override
    protected TypeSubTypeValueHasFieldOf getInstance() {
        return new TypeSubTypeValueHasFieldOf(TSV.VALUE, "test");
    }

    @Override
    protected Class<TypeSubTypeValueHasFieldOf> getPredicateClass() {
        return TypeSubTypeValueHasFieldOf.class;
    }
}