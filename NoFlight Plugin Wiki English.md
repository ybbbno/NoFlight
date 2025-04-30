# NoFlight Plugin Wiki

## Overview

The **NoFlight** plugin for Minecraft (Bukkit/Paper 1.20.1) prevents players from flying with elytra when they take specific types of damage. The effect is applied for a configurable duration, disabling flight and gliding. The plugin supports all damage types, with customizable settings for duration, entity inclusion/exclusion.

### Configuration Format

The configuration is defined in `config.yml` under the `allowed-damage-types` key. Damage types can be specified as:

- **Simple String**: Just the damage type name (e.g., `LAVA`). Defaults to:
  - `duration: 10`
  - `mode: include`
  - `entities: []`
- **Configuration Map**: A detailed configuration with optional properties (e.g., `LAVA: {duration: 15}`).
  - `duration`: Duration of the no-flight effect in seconds (non-negative, default: 10).
  - `mode`: Either `include` (only listed entities trigger) or `exclude` (all except listed entities trigger). Default: `include`.
  - `entities`: List of `EntityType` values (e.g., `PLAYER`, `ZOMBIE`). Default: empty.

**Example**:
```yaml
allowed-damage-types:
  - LAVA
  - ENTITY_ATTACK:
      mode: include
      entities:
        - PLAYER
      duration: 10
```

### Damage Types

All damage types can use `duration`, `mode`, and `entities`. The `entities` field can include any of these creature types:

**Possible Entities**:
- **Hostile**: `BLAZE`, `CREEPER`, `DROWNED`, `ELDER_GUARDIAN`, `ENDER_DRAGON`, `ENDERMAN`, `EVOKER`, `GHAST`, `GUARDIAN`, `HOGLIN`, `HUSK`, `ILLUSIONER`, `MAGMA_CUBE`, `PHANTOM`, `PIGLIN`, `PIGLIN_BRUTE`, `PILLAGER`, `RAVAGER`, `SHULKER`, `SKELETON`, `SLIME`, `SPIDER`, `STRAY`, `VEX`, `VINDICATOR`, `WARDEN`, `WITCH`, `WITHER`, `WITHER_SKELETON`, `ZOGLIN`, `ZOMBIE`, `ZOMBIE_VILLAGER`
- **Neutral**: `BEE`, `CAVE_SPIDER`, `DOLPHIN`, `ENDERMITE`, `GOAT`, `IRON_GOLEM`, `LLAMA`, `PANDA`, `POLAR_BEAR`, `PUFFERFISH`, `SILVERFISH`, `WOLF`, `ZOMBIFIED_PIGLIN`
- **Passive**: `CHICKEN`, `COW`, `FOX`, `MOOSHROOM`, `PIG`, `RABBIT`, `SHEEP`, `SNOW_GOLEM`, `VILLAGER`
- **Players**: `PLAYER`
- **Projectiles**: `ARROW`, `DRAGON_FIREBALL`, `EGG`, `ENDER_PEARL`, `FIREBALL`, `LLAMA_SPIT`, `SHULKER_BULLET`, `SNOWBALL`, `SPECTRAL_ARROW`, `TRIDENT`, `WITHER_SKULL`
- **Other**: `ARMOR_STAND`, `FISHING_HOOK`, `LIGHTNING`, `TNT`, `UNKNOWN`

| Damage Type         | Description                                                                                             |
|---------------------|---------------------------------------------------------------------------------------------------------|
| KILL                | Damage caused by `/kill` command                                                                        |
| WORLD_BORDER        | Damage caused by the World Border                                                                       |
| CONTACT             | Damage caused when an entity contacts a block such as a Cactus, Dripstone (Stalagmite) or Berry Bush    |
| SUFFOCATION         | Damage caused by being put in a block                                                                   |
| FALL                | Damage caused when an entity falls a distance greater than 3 blocks                                     |
| FIRE                | Damage caused by direct exposure to fire                                                                |
| FIRE_TICK           | Damage caused due to burns caused by fire                                                               |
| MELTING             | Damage caused due to melting (in default it's used when a snowman melting)                              |
| LAVA                | Damage caused by direct exposure to lava                                                                |
| DROWNING            | Damage caused by running out of air while in water                                                      |
| BLOCK_EXPLOSION     | Damage caused by being in the area when a block explodes                                                |
| VOID                | Damage caused by falling into the void                                                                  |
| LIGHTNING           | Damage caused by being struck by lightning                                                              |
| SUICIDE             | Damage caused by committing suicide or like default `/minecraft:kill` command.                          |
| STARVATION          | Damage caused by starving due to having an empty hunger bar                                             |
| POISON              | Damage caused due to an ongoing poison effect                                                           |
| FALLING_BLOCK       | Damage caused by being hit by a falling block which deals damage                                        |
| CUSTOM              | Custom damage                                                                                           |
| FLY_INTO_WALL       | Damage caused when an entity runs into a wall                                                           |
| HOT_FLOOR           | Damage caused when an entity steps on a magma block                                                     |
| CRAMMING            | Damage caused when an entity is colliding with too many entities due to the maxEntityCramming game rule |
| FREEZE              | Damage caused from freezing                                                                             |
| ENTITY_ATTACK       | Damage caused when an entity attacks another entity                                                     |
| ENTITY_SWEEP_ATTACK | Damage caused when an entity attacks another entity in a sweep attack.                                  |
| PROJECTILE          | Damage caused when attacked by a projectile.                                                            |
| ENTITY_EXPLOSION    | Damage caused by being in the area when an entity, such as a creeper explodes.                          |
| THORNS              | Damage caused in retaliation to another attack by the Thorns enchantment.                               |
| MAGIC               | Damage caused by being hit by a damage potion or spell                                                  |
| WITHER              | Damage caused by Wither potion effect                                                                   |
| DRAGON_BREATH       | Damage caused by a dragon breathing fire                                                                |
| SONIC_BOOM          | Damage caused by the Sonic Boom attack from Warden                                                      |