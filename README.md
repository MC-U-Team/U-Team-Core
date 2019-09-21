# U-Team-Core

[
![](http://cf.way2muchnoise.eu/u-team-core.svg)
![](http://cf.way2muchnoise.eu/versions/u-team-core.svg)
](https://www.curseforge.com/minecraft/mc-mods/u-team-core)

### This is an opensource api and library for u-team's mods.

- Download on [curseforge](https://www.curseforge.com/minecraft/mc-mods/u-team-core).  
- Find more information on our [website](https://u-team.info/mods/uteamcore).
- See the [changelog](CHANGELOG.md) for updates.

### How to include this mod

- Repository: [repo.u-team.info](https://repo.u-team.info)
- Artifact: **info.u-team:u_team_core-${config.forge.mcversion}:${config.uteamcore.version}** 
- *{config.forge.mcversion}* is the minecraft version.
- *{config.uteamcore.version}* is the uteamcore version.

#### Using in Forge Gradle 3:
```gradle
repositories {
    maven { url = "https://repo.u-team.info" }
}

dependencies {
  compileOnly fg.deobf("info.u-team:u_team_core-${config.forge.mcversion}:${config.uteamcore.version}")
}
```

### License

- This mod is licensed under apache 2 license. For more information see [here](LICENSE).  
- This mod can be packed in any curseforge modpack you like.

### Issues

- Please report issues to the [github issues](../../issues).
- Include your minecraft version, forge version and mod version.
- Upload your log on [gist](https://gist.github.com) or [pastebin](https://pastebin.com) and include link in your report.
