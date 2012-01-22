/*
 * Copyright (C) 2011 Benoit GUEROUT <bguerout at gmail dot com> and Yves AMSELLEM <amsellem dot yves at gmail dot com>
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

package com.jongo;

import com.jongo.Query.Builder;
import com.jongo.jackson.EntityProcessor;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class FindOne {

    private final EntityProcessor processor;
    private final DBCollection collection;
    private final Builder queryBuilder;

    FindOne(EntityProcessor processor, DBCollection collection, String query) {
        this.processor = processor;
        this.collection = collection;
        this.queryBuilder = new Builder(query);
    }

    FindOne(EntityProcessor processor, DBCollection collection, String query, Object... parameters) {
        this.processor = processor;
        this.collection = collection;
        this.queryBuilder = new Builder(query).parameters(parameters);
    }

    public FindOne on(String fields) {
        this.queryBuilder.fields(fields);
        return this;
    }

    public <T> T as(Class<T> clazz) {
        return map(processor.createEntityMapper(clazz));
    }

    public <T> T map(DBObjectMapper<T> mapper) {
        DBObject result = collection.findOne(queryBuilder.build().toDBObject());
        return result == null ? null : mapper.map(result);
    }
}