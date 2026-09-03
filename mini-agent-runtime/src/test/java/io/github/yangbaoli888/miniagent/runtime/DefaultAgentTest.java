package io.github.yangbaoli888.miniagent.runtime;

import io.github.yangbaoli888.miniagent.core.*;
import io.github.yangbaoli888.miniagent.memory.InMemoryMemory;
import io.github.yangbaoli888.miniagent.model.*;
import io.github.yangbaoli888.miniagent.tool.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DefaultAgentTest {
    @Test
    void shouldExecuteToolThenReturnAnswer() {
        Model model = request -> request.messages().stream()
                .anyMatch(m -> m.role() == Message.Role.TOOL)
                ? new ModelResponse.FinalAnswer("42")
                : new ModelResponse.ToolCall("calculator", Map.of("expression", "40+2"));

        Tool tool = new Tool() {
            public ToolDefinition definition() {
                return new ToolDefinition("calculator", "calculate", Map.of());
            }
            public String execute(AgentIdentity identity, Map<String, Object> arguments) {
                return "42";
            }
        };

        ToolRegistry registry = new ToolRegistry();
        registry.register(tool);

        Agent agent = new DefaultAgent(model, new InMemoryMemory(), registry, 5);
        AgentResult result = agent.run(AgentRequest.of(
                new AgentIdentity("t1", "u1", "s1", "a1"), "what is 40+2"));

        assertEquals(AgentStatus.COMPLETED, result.status());
        assertEquals("42", result.output());
        assertEquals(3, result.steps().size());
    }
}