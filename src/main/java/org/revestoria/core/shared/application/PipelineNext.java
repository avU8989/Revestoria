package org.revestoria.core.shared.application;

public interface PipelineNext<R> {
    R invoke();
}