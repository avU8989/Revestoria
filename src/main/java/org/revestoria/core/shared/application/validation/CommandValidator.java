package org.revestoria.core.shared.application.validation;

import org.revestoria.core.shared.application.Command;

import java.util.List;

public interface CommandValidator<C extends Command<?>>{
    List<ValidationError> validate(C command);
    Class<C>  commandType(); // so we can check at runtime which type the command belongs
}
