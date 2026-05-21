package org.revestoria.core.shared.infrastructure.mediator;

import org.revestoria.core.shared.application.Command;
import org.revestoria.core.shared.application.PipelineBehaviour;
import org.revestoria.core.shared.application.PipelineNext;
import org.revestoria.core.shared.application.validation.CommandValidationException;
import org.revestoria.core.shared.application.validation.CommandValidator;

import java.util.List;

//Validation Behaviour is a generic pipeline that can validate any command c and then return the command's response type r
public class ValidationBehaviour<C extends Command<R>, R> implements PipelineBehaviour<C, R> {
    private final List<CommandValidator<?>> validators;

    public ValidationBehaviour(List<CommandValidator<?>> validators){
        this.validators = validators;
    }

    @Override
    @SuppressWarnings("unchecked")
    public R handle(C command, PipelineNext<R> next) {

        List<String> errors = validators.stream()
                .filter(validator -> validator.commandType().equals(command.getClass()))
                .flatMap(validator -> ((CommandValidator<C>) validator).validate(command).stream())
                .toList();

        if(!errors.isEmpty()){
            throw new CommandValidationException(errors);
        }

        return next.invoke();
    }
}
