package org.revestoria.core.shared.application.validation;

import java.util.List;

public class CommandValidationException extends RuntimeException{
    private final List<ValidationError> errors;

    public CommandValidationException(List<ValidationError> errors) {
        super("Command validation failed");
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
