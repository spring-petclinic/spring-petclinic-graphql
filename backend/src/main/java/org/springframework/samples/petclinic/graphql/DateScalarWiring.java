package org.springframework.samples.petclinic.graphql;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.RuntimeWiring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

@Component
public class DateScalarWiring implements RuntimeWiringConfigurer {
    private final static Logger logger = LoggerFactory.getLogger(DateScalarWiring.class);

    private static DateTimeFormatter createIsoDateFormat() {
        return DateTimeFormatter.ofPattern("yyyy/MM/dd");
    }

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.scalar(GraphQLScalarType.newScalar()
            .name("Date")
            .description("A Type representing a date (without time, only a day)")
            .coercing(new DateCoercing())
            .build());
    }

    private static class DateCoercing implements Coercing<LocalDate, String> {

        @Override
        public String serialize(Object input) {
            if (input instanceof LocalDate) {
                return createIsoDateFormat().format((LocalDate) input);
            }
            return null;
        }

        @Override
        public LocalDate parseValue(Object input) {
            if (input instanceof LocalDate) {
                return (LocalDate) input;
            } else if (input instanceof String) {
                fromString((String)input);
            }
            return null;
        }

        @Override
        public LocalDate parseLiteral(Object input) {
            if (input instanceof StringValue) {
                String value = ((StringValue) input).getValue();
                return fromString(value);
            }
            throw new UnsupportedOperationException("Unsupported input in DateScalarType: " +  input);
        }
    }

    private static LocalDate fromString(String input) {
        try {
            LocalDate date = LocalDate.parse(input, createIsoDateFormat());
            return date;
        } catch (Exception e) {
            throw new IllegalArgumentException(format("Could not parse date from String '%s': %s", input, e.getLocalizedMessage()), e);
        }
    }
}
