package org.revestoria.core.shared.application;

//Pipeline behaviour can do something before handler
//then call next.invoke()
public interface PipelineBehaviour<C extends Command<R>, R> {
    R handle(C command, PipelineNext<R> next);
}
