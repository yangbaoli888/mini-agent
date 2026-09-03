# mini-agent

一个极简、可扩展的 Java Agent 开发框架。

## 设计目标

- Tenant / User / Session 隔离
- Working / Session / Long-Term Memory
- Context 管理与 Token Budget 扩展点
- 统一 Tool 抽象
- MCP 作为 Tool Provider 接入
- Model Provider 解耦
- 极简 Agent Runtime Loop

## 模块

- `mini-agent-core`：核心领域模型
- `mini-agent-memory`：Memory 抽象与内存实现
- `mini-agent-model`：LLM Provider 抽象
- `mini-agent-tool`：Tool Registry / Router
- `mini-agent-runtime`：Agent 执行循环

## 架构

```text
AgentRequest
  -> AgentRuntime
  -> Memory Recall
  -> Context Build
  -> Model
  -> Tool Router (optional loop)
  -> Memory Persist
  -> AgentResult
```
add content
