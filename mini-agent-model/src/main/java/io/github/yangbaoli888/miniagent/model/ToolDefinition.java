package io.github.yangbaoli888.miniagent.model;

import java.util.Map;

public record ToolDefinition(String name, String description, Map<String, Object> inputSchema) {
    public ToolDefinition { inputSchema = inputSchema == null ? Map.of() : Map.copyOf(inputSchema); }
}
