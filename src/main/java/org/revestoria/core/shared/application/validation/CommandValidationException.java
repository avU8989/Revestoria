package org.revestoria.core.shared.application.validation;

import java.util.List;

public class CommandValidationException extends RuntimeException{
    private final List<String> errors;

    public CommandValidationException(List<String> errors) {
        super("Command validation failed");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
