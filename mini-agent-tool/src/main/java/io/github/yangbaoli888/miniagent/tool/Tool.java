package io.github.yangbaoli888.miniagent.tool;

import io.github.yangbaoli888.miniagent.core.AgentIdentity;
import io.github.yangbaoli888.miniagent.model.ToolDefinition;
import java.util.Map;

public interface Tool {
    ToolDefinition definition();
    String execute(AgentIdentity identity, Map<String, Object> arguments);
}