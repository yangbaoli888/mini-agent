package io.github.yangbaoli888.miniagent.core;

import java.util.List;

public record AgentResult(AgentStatus status, String output, List<String> steps, Throwable error) {
    public static AgentResult completed(String output, List<String> steps) {
        return new AgentResult(AgentStatus.COMPLETED, output, List.copyOf(steps), null);
    }

    public static AgentResult failed(Throwable error, List<String> steps) {
        return new AgentResult(AgentStatus.FAILED, null, List.copyOf(steps), error);
    }
}
