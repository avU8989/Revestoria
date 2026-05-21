package org.revestoria.core.shared.application;

public interface CommandBus {
    <R> R send(Command<R> command);
}
