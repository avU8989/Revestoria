package org.revestoria.core.shared.application;

//handle one specific command type C and returns type R
public interface CommandHandler<C extends Command<R>, R>{
    R handle(C command);
    Class<C> commandType();
}
