package io.github.yangbaoli888.miniagent.memory;

import io.github.yangbaoli888.miniagent.core.AgentIdentity;
import java.time.Instant;
import java.util.Objects;

public record MemoryItem(String id, AgentIdentity identity, MemoryScope scope, String content, double importance, Instant createdAt) {
    public MemoryItem {
        Objects.requireNonNull(identity, "identity must not be null");
        Objects.requireNonNull(scope, "scope must not be null");
        Objects.requireNonNull(content, "content must not be null");
        createdAt = createdAt == null ? Instant.now() : createdAt;
    }
}
