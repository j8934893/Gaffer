/*
 * Copyright 2016 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gaffer.elasticsearchstore.serialisation;

import gaffer.data.element.Element;
import gaffer.exception.SerialisationException;
import gaffer.jsonserialisation.JSONSerialiser;
import gaffer.serialisation.Serialisation;

public class SimpleElasticElementSerialiser implements Serialisation {

    private JSONSerialiser jsonSerialiser;

    public SimpleElasticElementSerialiser(){
        jsonSerialiser = new JSONSerialiser();
    }

    @Override
    public boolean canHandle(Class clazz) {
        return Element.class.equals(clazz);
    }

    @Override
    public byte[] serialise(Object object) throws SerialisationException {
        Element element = (Element) object;
        return jsonSerialiser.serialise(element);
    }

    @Override
    public Object deserialise(byte[] bytes) throws SerialisationException {
        return null;
    }
}
