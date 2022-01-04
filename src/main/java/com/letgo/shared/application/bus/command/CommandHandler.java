package com.letgo.shared.application.bus.command;

public interface CommandHandler<T extends Command> {
    public void handle(T command) throws Exception;
}
