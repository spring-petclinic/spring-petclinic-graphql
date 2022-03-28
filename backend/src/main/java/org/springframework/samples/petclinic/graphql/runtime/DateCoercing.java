package org.springframework.samples.petclinic.graphql.runtime;

import graphql.language.StringValue;
import graphql.schema.Coercing;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

/**
 * Implements own date format (yyyy/MM/dd) for our own "Date" scalar type
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class DateCoercing implements Coercing<LocalDate, String> {
    private static DateTimeFormatter createIsoDateFormat() {
        return DateTimeFormatter.ofPattern("yyyy/MM/dd");
    }

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
            return fromString((String) input);
        }
        return null;
    }

    @Override
    public LocalDate parseLiteral(Object input) {
        if (input instanceof StringValue) {
            String value = ((StringValue) input).getValue();
            return fromString(value);
        }
        throw new UnsupportedOperationException("Unsupported input in DateScalarType: " + input);
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
