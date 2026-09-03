package io.github.yangbaoli888.miniagent.core;

import java.util.Map;
import java.util.Objects;

public record AgentRequest(AgentIdentity identity, String input, Map<String, Object> attributes) {
    public AgentRequest {
        Objects.requireNonNull(identity, "identity must not be null");
        Objects.requireNonNull(input, "input must not be null");
        attributes = attributes == null ? Map.of() : Map.copyOf(attributes);
    }

    public static AgentRequest of(AgentIdentity identity, String input) {
        return new AgentRequest(identity, input, Map.of());
    }
}
