package com.letgo.shared.application.bus.command;

public interface CommandBus {
    public void dispatch(Command command) throws Exception;
}
