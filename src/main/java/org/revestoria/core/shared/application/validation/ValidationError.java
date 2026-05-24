package org.revestoria.core.shared.application.validation;

public record ValidationError (
        String field,
        String code,
        String message
){
}
