package org.revestoria.core.shared.infrastructure.mediator;

import org.revestoria.core.shared.application.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringCommandBus implements CommandBus {
    private final List<CommandHandler<?, ?>> handlers;
    private final List<PipelineBehaviour<?, ?>> behaviours;

    public SpringCommandBus(List<CommandHandler<?, ?>> handlers, List<PipelineBehaviour<?, ?>> behaviours) {
        this.handlers = handlers;
        this.behaviours = behaviours;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R send (Command<R> command){
        CommandHandler<Command<R>, R> handler = (CommandHandler<Command<R>, R>) findHandler(command);

        PipelineNext<R> handlerInvocation = () -> handler.handle(command);

        PipelineNext<R> pipeline = buildPipeline(command, handlerInvocation);

        return pipeline.invoke();
    }

    @SuppressWarnings("unchecked")
    public <R> PipelineNext<R> buildPipeline(Command<R> command, PipelineNext<R> finalHandler){
        PipelineNext<R> next = finalHandler;

        for (int i = behaviours.size() - 1; i >= 0; i--) {
            PipelineBehaviour<Command<R>, R> behaviour = (PipelineBehaviour<Command<R>, R>) behaviours.get(i);
            PipelineNext<R> currentNext = next;
            next = () -> behaviour.handle(command, currentNext);
        }

        return next;
    }

    private CommandHandler<?, ?> findHandler(Command<?> command){
        return handlers.stream()
                .filter(handler -> handler.commandType().equals(command.getClass()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No handler found for command: " + command.getClass().getName()));
    }
}
