package org.revestoria.core.shared.infrastructure.mediator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.revestoria.core.shared.application.Command;
import org.revestoria.core.shared.application.PipelineBehaviour;
import org.revestoria.core.shared.application.PipelineNext;
import org.revestoria.core.shared.application.validation.CommandValidationException;
import org.revestoria.core.shared.application.validation.CommandValidator;
import org.revestoria.core.shared.application.validation.ValidationError;

import java.util.List;
import java.util.Set;

//Validation Behaviour is a generic pipeline that can validate any command c and then return the command's response type r
public class ValidationBehaviour<C extends Command<R>, R> implements PipelineBehaviour<C, R> {
    private final Validator validator;

    public ValidationBehaviour(Validator validator) {
        this.validator = validator;
    }
    @Override
    public R handle(C command, PipelineNext<R> next) {

        //validate jakarta validation annotation

        Set<ConstraintViolation<C>> violations = validator.validate(command);
        if(!violations.isEmpty()){
            List<ValidationError> errors = violations.stream()
                    .map(v -> new ValidationError(
                            v.getPropertyPath().toString(),
                            "VALIDATION_ERROR",
                            v.getMessage()
                    ))
                    .toList();

            throw new CommandValidationException(errors);
        }

        return next.invoke();
    }
}
