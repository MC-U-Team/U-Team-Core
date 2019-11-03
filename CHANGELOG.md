# Changelog
All notable changes to this project will be documented in this file.

## [1.14.4-2.7.8.138] - 2019-11-03
### Changed
 - Make data generator much easier to register
 - Breaking change for mod developers depending on this, but not for the players
 
## [1.14.4-2.7.7.137] - 2019-11-02
### Changed
 - Deprecate some methods in BasicEnergyStorage
 - Update test mod to the new sync system + some more improvements
 - Fix loot table provider (data generator)
 - Fix some streams not closed correctly
 
### Added
 - Add languages provider (data generator)
 - Add block state provider / block models provider (data generator)
 - Add item models provider (data generator)
 - Expand BufferHolderReference with some basic methods for int, short, byte sync
 - Add a MessageHolder that will send one message if you mark it that it should sync

## [1.14.4-2.7.6.136] - 2019-10-29
### Changed
 - Make the new sync system way better
 - Deprecate the old sync system (will be removed in further updates)
 - Fix the loot table provider

## [1.14.4-2.7.5.135-SNAPSHOT] - 2019-10-17
### Added
 - Add new way to sync things like the vanilla tracker.
 - Very alpha state (so this is only an alpha snapshot)

## [1.14.4-2.7.4.134] - 2019-10-12
### Changed
 - Update network protocol to 1.14.4
 - Change uteamcore command
 
### Added
 - Add no mirror shaped recipe
 - Add recipe data provider for generating recipes
 - Add loot table data provider for generating loot tables
 - Add dimension teleport methods to world util
 - Add dimension teleport command to uteamcore command (/uteamcore dimteleport ...)
 - Add ping command for uteamcore command (/uteamcore ping ... or /ping ...)
 - Add better button that can scale the display text
 - Add method util with vector rotation functions
 - Add class to easily negate a predicate for method reference usage in lambda expressions
 
### Removed
 - Removed uteamcore command (with spelling mistake, I know 😊)

## [1.14.4-2.7.3.132] - 2019-09-25
### Changed
 - Fixed all java docs to compile properly
 - Update readme to be more user friendly
 - Build script now accept system properties as inputs for secrets and also compile if no secrets are available
 - Format all files and include formatter in repository
 
### Added
 - Add ModelUtil to add custom state maps for models (Useful for states that should not be present in the model)
 - Add some missing side annotations
 - Expand config util a bit with more useful functions
 - Add issue templates

## [1.14.4-2.7.2.131] - 2019-09-09
### Changed
 - Renamed UTileeEntityContainer to UTileEntityContainer (fix spelling mistake)

## [1.14.4-2.7.1.130] - 2019-08-13
### Changed
 - Extract init sync methods for container to extra interface 
 - Add interface for utility methods for opening guis that were in UTileEntityBlock to make it accessible for more blocks that cannot extend UBlockTileEntity e.g. rails in useful railroads

### Added
 - Added new tile entity container with no sync stuff except the init packet sync

## [1.14.4-2.7.0.129] - 2019-07-31
### Changed
 - Changed spade to shovel in code, because thats now the name that mcp/minecraft uses.
 - Made the additional damage for tools a float value, because some use floats. When a float cannot be used like for swords then the float is casted to an integer

### Added
 - Gson and config util to create easy configs. Mainly used in useful resources

## [1.14.4-2.6.1.126] - 2019-07-26
### Added
 - Data generation system (extended support for tags)

## [1.14.4-2.6.0.123] - 2019-07-24
### Changed
 - Renamed tool and armor classes to match new convention (major change)

## [1.14.3-2.5.5.120] - 2019-07-23
### Changed
 - Update to 1.14.4
