package io.github.yangbaoli888.miniagent.core;

import java.util.Objects;

public record Message(Role role, String content) {
    public Message {
        Objects.requireNonNull(role, "role must not be null");
        Objects.requireNonNull(content, "content must not be null");
    }

    public enum Role { SYSTEM, USER, ASSISTANT, TOOL }
}
