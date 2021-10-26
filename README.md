# U-Team-Core

[
![Curseforge Downloads](http://cf.way2muchnoise.eu/u-team-core.svg)
![Curseforge Versions](http://cf.way2muchnoise.eu/versions/u-team-core.svg)
](https://www.curseforge.com/minecraft/mc-mods/u-team-core)
[
![Discord](https://img.shields.io/discord/297104769649213441?label=Discord)
](https://discord.u-team.info)

### This is an opensource api and library for u-team's mods.

- Please ask questions in our [discord server](https://discord.u-team.info).
- Download on [curseforge](https://www.curseforge.com/minecraft/mc-mods/u-team-core).  
- Find more information on our [website](https://u-team.info/mods/uteamcore).
- Updates can be found in the [changelog](CHANGELOG.md).

### How to build this mod

#### How to clone
- For a fesh clone use ``git clone --recursive https://github.com/MC-U-Team/U-Team-Core``
- For already cloned repository use ``git submodule update --init --recursive`` to initialise the submodules.

#### Setup Eclipse
- ``./gradlew genEclipseRuns``
- Import as gradle project

#### Setup IntelliJ IDEA
- ``./gradlew genIntellijRuns``
- Import as gradle project

#### Build a jar
- ``./gradlew build``

### How to include this mod

- Repository: [repo.u-team.info](https://repo.u-team.info)
- Artifact: **info.u-team:u_team_core-${mcversion}:${modversion}** 
- *{mcversion}* is the minecraft version.
- *{modversion}* is the uteamcore version.

#### Using in Forge Gradle 5:
```gradle
repositories {
	maven { url = "https://repo.u-team.info" }
}

dependencies {
	implementation fg.deobf("info.u-team:u_team_core-${mcversion}:${modversion}")
}
```

### License

- This mod is licensed under apache 2 license. For more information see [here](LICENSE).  
- This mod can be packed in any curseforge modpack you like.

### Issues

- Please report issues [here](../../issues).