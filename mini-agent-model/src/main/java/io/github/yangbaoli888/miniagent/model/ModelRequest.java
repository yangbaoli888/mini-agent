package io.github.yangbaoli888.miniagent.model;

import io.github.yangbaoli888.miniagent.core.Message;
import java.util.List;

public record ModelRequest(List<Message> messages, List<ToolDefinition> tools) {
    public ModelRequest {
        messages = List.copyOf(messages);
        tools = tools == null ? List.of() : List.copyOf(tools);
    }
}
