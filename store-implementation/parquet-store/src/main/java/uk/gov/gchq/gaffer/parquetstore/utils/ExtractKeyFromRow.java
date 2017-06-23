/*
 * Copyright 2017. Crown Copyright
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

package uk.gov.gchq.gaffer.parquetstore.utils;

import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Row;
import scala.collection.Seq;
import scala.collection.Seq$;
import scala.collection.mutable.Builder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExtractKeyFromRow implements Function<Row, Seq<Object>>, Serializable {

    private static final long serialVersionUID = -5811180619204002981L;
    private final Set<String> groupByColumns;

    public ExtractKeyFromRow(final Set<String> groupByColumns, final Map<String, String[]> columnToPaths, final boolean isEntity, final Map<String, String> propertyToAggregatorMap) {
        this.groupByColumns = new HashSet<>();
        if (isEntity) {
            addGroupByColumns(columnToPaths, ParquetStoreConstants.VERTEX);
        } else {
            addGroupByColumns(columnToPaths, ParquetStoreConstants.SOURCE);
            addGroupByColumns(columnToPaths, ParquetStoreConstants.DESTINATION);
            this.groupByColumns.add(ParquetStoreConstants.DIRECTED);
        }
        final Set<String> propertiesWithAggregators = propertyToAggregatorMap.keySet();
        for (final String col : columnToPaths.keySet()) {
            if (groupByColumns.contains(col)
                    || !propertiesWithAggregators.contains(col)
                    && !ParquetStoreConstants.VERTEX.equals(col)
                    && !ParquetStoreConstants.SOURCE.equals(col)
                    && !ParquetStoreConstants.DESTINATION.equals(col)
                    && !ParquetStoreConstants.DIRECTED.equals(col)) {
                addGroupByColumns(columnToPaths, col);
            }
        }
    }

    private void addGroupByColumns(final Map<String, String[]> columnToPaths, final String col) {
        final String[] paths = columnToPaths.get(col);
        if (paths != null) {
            Collections.addAll(groupByColumns, paths);
        }
    }

    @Override
    public Seq<Object> call(final Row row) throws Exception {
        final Builder<Object, Seq<Object>> key = Seq$.MODULE$.newBuilder();
        for (final String column : groupByColumns) {
            final Object columnValue = row.getAs(column);
            if (columnValue instanceof byte[]) {
                key.$plus$eq(Arrays.toString((byte[]) columnValue));
            } else {
                key.$plus$eq(columnValue);
            }
        }
        return key.result();
    }
}
