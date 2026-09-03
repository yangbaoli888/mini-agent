package io.github.yangbaoli888.miniagent.tool;

import io.github.yangbaoli888.miniagent.core.AgentIdentity;
import io.github.yangbaoli888.miniagent.model.ModelResponse;

public final class ToolRouter {
    private final ToolRegistry registry;

    public ToolRouter(ToolRegistry registry) {
        this.registry = registry;
    }

    public String execute(AgentIdentity identity, ModelResponse.ToolCall call) {
        return registry.get(call.name()).execute(identity, call.arguments());
    }
}