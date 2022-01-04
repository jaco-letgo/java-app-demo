package com.letgo.shared.infrastructure.bus.command;

import com.letgo.shared.application.bus.command.Command;
import com.letgo.shared.application.bus.command.CommandBus;
import com.letgo.shared.application.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryCommandBus implements CommandBus {
    private final Map<Class<? extends Command>, CommandHandler<? extends Command>> handlers = new HashMap<>();

    public InMemoryCommandBus(List<CommandHandler<? extends Command>> handlers) {
        handlers.forEach(commandHandler -> this.handlers.put(getCommandClass(commandHandler), commandHandler));
    }

    @Override
    public void dispatch(Command command) throws Exception {
        if (!handlers.containsKey(command.getClass())) {
            throw new Exception(String.format("No handler found for %s", command.getClass().getName()));
        }
        CommandHandler<Command> commandHandler = (CommandHandler<Command>) handlers.get(command.getClass());
        commandHandler.handle(command);
    }

    private Class<? extends Command> getCommandClass(CommandHandler<? extends Command> handler) {
        return (Class<? extends Command>) actualTypeArgument(handler);
    }

    private Type actualTypeArgument(CommandHandler<? extends Command> handler) {
        return ((ParameterizedType) handler.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }
}
