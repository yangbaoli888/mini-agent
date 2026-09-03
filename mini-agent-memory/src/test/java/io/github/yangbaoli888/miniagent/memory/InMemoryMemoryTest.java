package io.github.yangbaoli888.miniagent.memory;

import io.github.yangbaoli888.miniagent.core.AgentIdentity;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryMemoryTest {
    @Test
    void shouldIsolateSessionMemory() {
        InMemoryMemory memory = new InMemoryMemory();
        AgentIdentity s1 = new AgentIdentity("t", "u", "s1", "a");
        AgentIdentity s2 = new AgentIdentity("t", "u", "s2", "a");

        memory.remember(new MemoryItem("1", s1, MemoryScope.SESSION, "secret", 1, Instant.now()));

        assertEquals(1, memory.recall(s1, "secret", 10).size());
        assertEquals(0, memory.recall(s2, "secret", 10).size());
    }

    @Test
    void shouldIsolateTenantAndUser() {
        InMemoryMemory memory = new InMemoryMemory();
        AgentIdentity owner = new AgentIdentity("t1", "u1", "s1", "a");
        memory.remember(new MemoryItem("1", owner, MemoryScope.USER, "profile", 1, Instant.now()));

        assertEquals(0, memory.recall(
                new AgentIdentity("t2", "u1", "s1", "a"), "profile", 10).size());
        assertEquals(0, memory.recall(
                new AgentIdentity("t1", "u2", "s1", "a"), "profile", 10).size());
    }
}