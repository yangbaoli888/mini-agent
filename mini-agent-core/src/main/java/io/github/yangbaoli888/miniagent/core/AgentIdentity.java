package io.github.yangbaoli888.miniagent.core;

import java.util.Objects;

public record AgentIdentity(String tenantId, String userId, String sessionId, String agentId) {
    public AgentIdentity {
        Objects.requireNonNull(userId, "userId must not be null");
        Objects.requireNonNull(sessionId, "sessionId must not be null");
    }
}
