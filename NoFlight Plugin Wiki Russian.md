# Вики Плагина NoFlight

## Описание

**NoFlight** - плагин для Minecraft (Bukkit/Paper 1.20.1), с помощью которого можно запрещать летать игрокам с элитрами, когда они получают определённый тип урона. Эффект применяется на продолжительность времени, указанную в конфиге, отключая возможность полёта. Плагин поддерживает все типы урона с настраемыми параметрами длительности, включения/исключения сущностей.

### Формат конфигурации

Конфигфигурация находится в файле `config.yml` под `allowed-damage-types`. Указать тип урона можно таким образом:

- **Строка**: Просто тип урона (т.е., `LAVA`). С дефолтными параметрами:
  - `duration: 10`
  - `mode: include`
  - `entities: []`
- **Конфигурация**: Более детальное описание с опциональными параметрами (т.е., `LAVA: {duration: 15}`).
  - `duration`: Длительность эффекта (должна быть не отрицательной, по дефолту: 10).
  - `mode`: Либо `include` (только сущности в списке), либо `exclude` (все, кроме сущностей в списке). По дефолту: `include`.
  - `entities`: Список из `EntityType` (т.е., `PLAYER`, `ZOMBIE`). По дефолту: пустое.

**Пример**:
```yaml
allowed-damage-types:
  - LAVA
  - ENTITY_ATTACK:
      mode: include
      entities:
        - PLAYER
      duration: 10
```

### Типы урона

Все типы урона могут использовать `duration`, `mode` и `entities`. Поле `entities` может включать любой из этих типов существ:

**Возможные сущности**:
- **Враждебные**: `BLAZE`, `CREEPER`, `DROWNED`, `ELDER_GUARDIAN`, `ENDER_DRAGON`, `ENDERMAN`, `EVOKER`, `GHAST`, `GUARDIAN`, `HOGLIN`, `HUSK`, `ILLUSIONER`, `MAGMA_CUBE`, `PHANTOM`, `PIGLIN`, `PIGLIN_BRUTE`, `PILLAGER`, `RAVAGER`, `SHULKER`, `SKELETON`, `SLIME`, `SPIDER`, `STRAY`, `VEX`, `VINDICATOR`, `WARDEN`, `WITCH`, `WITHER`, `WITHER_SKELETON`, `ZOGLIN`, `ZOMBIE`, `ZOMBIE_VILLAGER`
- **Нейтральные**: `BEE`, `CAVE_SPIDER`, `DOLPHIN`, `ENDERMITE`, `GOAT`, `IRON_GOLEM`, `LLAMA`, `PANDA`, `POLAR_BEAR`, `PUFFERFISH`, `SILVERFISH`, `WOLF`, `ZOMBIFIED_PIGLIN`
- **Пассивные**: `CHICKEN`, `COW`, `FOX`, `MOOSHROOM`, `PIG`, `RABBIT`, `SHEEP`, `SNOW_GOLEM`, `VILLAGER`
- **Игроки**: `PLAYER`
- **Проджектайлы**: `ARROW`, `DRAGON_FIREBALL`, `EGG`, `ENDER_PEARL`, `FIREBALL`, `LLAMA_SPIT`, `SHULKER_BULLET`, `SNOWBALL`, `SPECTRAL_ARROW`, `TRIDENT`, `WITHER_SKULL`
- **Другое**: `ARMOR_STAND`, `FISHING_HOOK`, `LIGHTNING`, `TNT`, `UNKNOWN`

| Тип урона           | Описание                                                                                      |
|---------------------|-----------------------------------------------------------------------------------------------|
| KILL                | Урон от команды `/kill`                                                                       |
| WORLD_BORDER        | Урон от барьера                                                                               |
| CONTACT             | Урон от контакта с блоком таким, как кактус, капельник или куст с ягодами                     |
| SUFFOCATION         | Урон от застревания в блоках                                                                  |
| FALL                | Урон от падения сущности с высоты больше чем 3 блока                                          |
| FIRE                | Урон от прямого взаимодействия с огнём                                                        |
| FIRE_TICK           | Урон от горения, вызванный огнём                                                              |
| MELTING             | Урон от таяния (по дефолту он действует только на снеговика)                                  |
| LAVA                | Урон от прямого взаимодействия с лавой                                                        |
| DROWNING            | Урон от нехватки воздуха в воде                                                               |
| BLOCK_EXPLOSION     | Урон от взрыва блока, находящегося рядом с сущностью                                          |
| VOID                | Урон от падения в пустоту                                                                     |
| LIGHTNING           | Урон от удара молнии                                                                          |
| SUICIDE             | Урон от самоубийства (по дефолту командой `minecraft:/kill`)                                  |
| STARVATION          | Урон от голодания из-за пустой шкалы голода                                                   |
| POISON              | Урон от эффекта отравления                                                                    |
| FALLING_BLOCK       | Урон от падающего блока на сущность                                                           |
| CUSTOM              | Кастомный урон                                                                                |
| FLY_INTO_WALL       | Урон от столкновения сущности со стеной                                                       |
| HOT_FLOOR           | Урон от нахождения на магма блоке                                                             |
| CRAMMING            | Урон от столкновения с слишком большим количеством сущностей из-за gamerule maxEntityCramming |
| FREEZE              | Урон от замерзания                                                                            |
| ENTITY_ATTACK       | Урон от атаки сущности другой сущностью                                                       |
| ENTITY_SWEEP_ATTACK | Урон от атаки сущности другой сущностью с помощью sweep атаки                                 |
| PROJECTILE          | Урон от атаки проджектайлом                                                                   |
| ENTITY_EXPLOSION    | Урон от взрыва сущности, таких как крипер                                                     |
| THORNS              | Урон от зачарования шипов                                                                     |
| MAGIC               | Урон от воздействия зелья и заклинания                                                        |
| WITHER              | Урон от эффекта иссушения                                                                     |
| DRAGON_BREATH       | Урон от дыхания дракона                                                                       |
| SONIC_BOOM          | Урон от звукового удара Вардена                                                               |