---
name: commiter
description: Use it when you're asked to create a commit
---

# Commiter Skill

This skill defines how to perform commits following the **Conventional Commits** standard and the use of **Gitmoji**.

## Commit Structure

Commit messages must follow this structure:

```
<emoji> <type>(<scope>): <short description>

[optional body]

[optional footer]
```

- **emoji**: The emoji corresponding to the type of change.
- **type**: The type of change (feat, fix, etc.).
- **scope** (optional): A brief description of the affected area (e.g., UI, DB, Auth).
- **description**: A concise explanation in the present tense (e.g., "add start button").

## Commit Types and Emojis

| Emoji | Type | Description |
| :--- | :--- | :--- |
| ✨ | `feat` | New feature |
| 🐛 | `fix` | Bug fix |
| 📝 | `docs` | Documentation changes |
| 🎨 | `style` | Formatting, missing semi-colons, etc.; no code change |
| ♻️ | `refactor` | Refactoring production code |
| ⚡️ | `perf` | Performance improvement |
| ✅ | `test` | Adding or updating tests |
| 🏗️ | `build` | Build system or external dependencies changes |
| 👷 | `ci` | CI configuration files and scripts changes |
| 🧹 | `chore` | Other changes that don't modify `src` or `test` files |
| ⏪ | `revert` | Reverts a previous commit |

## Critical Rules

1. **Pre-commit Compilation**: Before every commit, you must compile the code (`gradle_build`) to ensure that changes do not break the project.
2. **No Automatic Push**: Do not perform `git push` unless explicitly instructed.
3. **Language**: Commit messages should be in English by default, unless otherwise specified by the project guidelines.
