package io.github.yangbaoli888.miniagent.memory;

import io.github.yangbaoli888.miniagent.core.AgentIdentity;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class InMemoryMemory implements Memory {
    private final CopyOnWriteArrayList<MemoryItem> items = new CopyOnWriteArrayList<>();

    @Override
    public List<MemoryItem> recall(AgentIdentity identity, String query, int limit) {
        String normalized = query == null ? "" : query.toLowerCase();
        return items.stream()
                .filter(item -> visibleTo(identity, item.identity(), item.scope()))
                .filter(item -> normalized.isBlank() || item.content().toLowerCase().contains(normalized))
                .sorted(Comparator.comparingDouble(MemoryItem::importance).reversed()
                        .thenComparing(MemoryItem::createdAt).reversed())
                .limit(limit)
                .toList();
    }

    @Override
    public void remember(MemoryItem item) { items.add(item); }

    private boolean visibleTo(AgentIdentity current, AgentIdentity owner, MemoryScope scope) {
        if (!safeEquals(current.tenantId(), owner.tenantId()) || !current.userId().equals(owner.userId())) return false;
        return switch (scope) {
            case SESSION -> current.sessionId().equals(owner.sessionId());
            case AGENT -> safeEquals(current.agentId(), owner.agentId());
            default -> true;
        };
    }

    private boolean safeEquals(Object a, Object b) { return java.util.Objects.equals(a, b); }
}
