/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.graphql.types;

import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.format;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class DateScalarType extends GraphQLScalarType {

    private final static Logger logger = LoggerFactory.getLogger(DateScalarType.class);

    private static SimpleDateFormat createIsoDateFormat() {
        return new SimpleDateFormat("yyyy/MM/dd");
    }

    @Override
    public String getDescription() {
        return "A Type representing a date (without time, only a day)";
    }

    public DateScalarType() {
        super("Date", "A Date type", new Coercing<Date, String>() {

            @Override
            public String serialize(Object input) {
                if (input instanceof Date) {
                    return createIsoDateFormat().format((Date) input);
                }
                return null;
            }

            @Override
            public Date parseValue(Object input) {
                if (input instanceof Date) {
                    return (Date) input;
                } else if (input instanceof String) {
                    try {
                        Date date = createIsoDateFormat().parse((String) input);
                        return date;
                    } catch (ParseException e) {
                        logger.error(format("Could not parse date from String '%s': %s", input, e.getLocalizedMessage()), e);
                    }
                }
                return null;
            }

            @Override
            public Date parseLiteral(Object input) {
                throw new UnsupportedOperationException("ParseLiteral in DateScalarType not implemented yet");
            }
        });
    }
}
