package io.github.yangbaoli888.miniagent.memory;

import io.github.yangbaoli888.miniagent.core.AgentIdentity;
import java.util.List;

public interface Memory {
    List<MemoryItem> recall(AgentIdentity identity, String query, int limit);
    void remember(MemoryItem item);
}
