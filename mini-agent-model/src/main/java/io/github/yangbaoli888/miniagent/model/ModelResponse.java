package io.github.yangbaoli888.miniagent.model;

import java.util.Map;

public sealed interface ModelResponse permits ModelResponse.FinalAnswer, ModelResponse.ToolCall {
    record FinalAnswer(String content) implements ModelResponse {}
    record ToolCall(String name, Map<String, Object> arguments) implements ModelResponse {
        public ToolCall { arguments = arguments == null ? Map.of() : Map.copyOf(arguments); }
    }
}
