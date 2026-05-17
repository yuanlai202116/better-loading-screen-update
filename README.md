# Better Loading Screen Update

A mod to improve the Minecraft loading screen, imitating the old Forge 1.12.2 style. Supports Fabric.

## Build

### Prerequisites
- JDK 21

### Compile
```bash
# Unix
./gradlew :fabric:build

# Windows
gradlew.bat :fabric:build
```

Output: `fabric/build/libs/`

## Known Issues

- **Text rendering as boxes (1.20+ / 1.19.x)** — During loading phase 3, text may appear as empty boxes because font textures haven't been loaded yet.
- **Sodium 0.5.x conflict** — Pre-loading screen is disabled when Sodium 0.5.x is detected, resulting in no custom loading UI.

## License
MIT - see LICENSE file.

## Credits
- [shedaniel](https://github.com/shedaniel) - Original author