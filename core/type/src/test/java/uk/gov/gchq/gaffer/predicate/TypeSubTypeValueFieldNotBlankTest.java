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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import uk.gov.gchq.gaffer.types.TypeSubTypeValue;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TypeSubTypeValueFieldNotBlankTest {
    private TypeSubTypeValueFieldNotBlank predicate;

    @Before
    public void setup() throws Exception {
        predicate = new TypeSubTypeValueFieldNotBlank();
    }

    @Test
    public void shouldRejectNullInput() {
        // irrespective of field
        for(TypeSubTypeValueField field :TypeSubTypeValueField.values()) {
            predicate.setField(field);
            assertThat(predicate.test(null), is(false));
        }
    }

    @Test
    public void shouldRejectNullValue() {
        predicate.setField(TypeSubTypeValueField.VALUE);
        assertThat(predicate.test(new TypeSubTypeValue("type", "subtype", null)), is(false));
    }

    @Test
    public void shouldRejectEmptyValue() {
        predicate.setField(TypeSubTypeValueField.VALUE);
        assertThat(predicate.test(new TypeSubTypeValue("type", "subtype", "")), is(false));
    }

    @Test
    public void shouldAcceptPopulatedValue() {
        predicate.setField(TypeSubTypeValueField.VALUE);
        assertThat(predicate.test(new TypeSubTypeValue(null, null, "test")), is(true));
    }

    @Test
    public void shouldBeJsonSerializable() throws Exception{
        final ObjectMapper mapper = new ObjectMapper();
        predicate.setField(TypeSubTypeValueField.SUBTYPE);

        // convert to JSON
        String jsonStr = mapper.writeValueAsString(predicate);
        assertThat(jsonStr, equalTo("{\"class\":\"uk.gov.mod.gaffer.predicate.TypeSubTypeValueFieldNotBlank\",\"field\":\"SUBTYPE\"}"));

        // convert back to a DTO
        TypeSubTypeValueFieldNotBlank reread = mapper.readValue(jsonStr, TypeSubTypeValueFieldNotBlank.class);
        assertThat(reread.getField(), equalTo(TypeSubTypeValueField.SUBTYPE));
    }
}