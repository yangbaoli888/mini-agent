package io.github.yangbaoli888.miniagent.runtime;

import io.github.yangbaoli888.miniagent.core.*;
import io.github.yangbaoli888.miniagent.memory.*;
import io.github.yangbaoli888.miniagent.model.*;
import io.github.yangbaoli888.miniagent.tool.*;

import java.time.Instant;
import java.util.*;

/** Minimal model -> tool -> observation -> model execution loop. */
public final class DefaultAgent implements Agent {
    private final Model model;
    private final Memory memory;
    private final ToolRegistry toolRegistry;
    private final ToolRouter toolRouter;
    private final int maxSteps;

    public DefaultAgent(Model model, Memory memory, ToolRegistry toolRegistry, int maxSteps) {
        this.model = Objects.requireNonNull(model);
        this.memory = Objects.requireNonNull(memory);
        this.toolRegistry = Objects.requireNonNull(toolRegistry);
        this.toolRouter = new ToolRouter(toolRegistry);
        this.maxSteps = Math.max(1, maxSteps);
    }

    @Override
    public AgentResult run(AgentRequest request) {
        List<String> steps = new ArrayList<>();
        List<Message> messages = new ArrayList<>();

        memory.recall(request.identity(), request.input(), 5)
                .forEach(item -> messages.add(new Message(
                        Message.Role.SYSTEM, "Relevant memory: " + item.content())));
        messages.add(new Message(Message.Role.USER, request.input()));

        try {
            for (int step = 1; step <= maxSteps; step++) {
                steps.add("model:" + step);
                ModelResponse response =
                        model.generate(new ModelRequest(messages, toolRegistry.definitions()));

                if (response instanceof ModelResponse.FinalAnswer answer) {
                    memory.remember(new MemoryItem(
                            UUID.randomUUID().toString(),
                            request.identity(),
                            MemoryScope.SESSION,
                            "User: " + request.input() + "\nAssistant: " + answer.content(),
                            0.5,
                            Instant.now()));
                    return AgentResult.completed(answer.content(), steps);
                }

                ModelResponse.ToolCall call = (ModelResponse.ToolCall) response;
                steps.add("tool:" + call.name());
                String observation = toolRouter.execute(request.identity(), call);
                messages.add(new Message(Message.Role.TOOL, observation));
            }
            return new AgentResult(AgentStatus.MAX_STEPS_REACHED, null, steps, null);
        } catch (Throwable error) {
            return AgentResult.failed(error, steps);
        }
    }
}