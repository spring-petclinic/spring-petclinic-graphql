package org.springframework.samples.petclinic.model;

public class InvalidVetDataException extends Exception {
    public InvalidVetDataException(String msg, Object... args) {
        super(String.format(msg, args));
    }
}
