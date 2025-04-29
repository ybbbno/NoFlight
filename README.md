# NoFlight

NoFlight is a Minecraft plugin for Paper 1.20.1 that adds a NoFlight effect. This effect disables flight for players wearing elytra when they take damage or via a command, displaying the remaining duration in the ActionBar.

## Features

- **No Flight Effect**:
  - Applied to players with elytra when they take damage (excluding fall, void, or collision damage).
  - Disables flight (`setFlying`, `setGliding`, `setAllowFlight`) for a set duration (default: 10 seconds for damage).
  - Resets the effect timer on subsequent damage.
  - Does not apply in `CREATIVE` or `SPECTATOR` game modes.
  - Displays the remaining time in the ActionBar (e.g., "Запрет полёта: 10 сек").

- **Command `/noflight`**:
  - Format: `/noflight <player> <seconds>`
  - Restricted to operators (OP) only.
  - Applies the NoFlight effect to the specified player for the given number of seconds.
  - Validates elytra presence and ensures the seconds are a non-negative number.

- **Damage Exclusions**:
  - Damage from falling (`FALL`), void (`VOID`), and collision (`FLY_INTO_WALL`) does not trigger the effect.

## Requirements

- **Server**: Paper 1.20.1 (or compatible version).