# Changelog
All notable changes to this project will be documented in this file.

## [1.14.4-2.7.3.132] - 2019-09-25
### Changed
 - Fixed all java docs to compile properly
 - Update readme to be more user friendly
 - Add issue templates
 - Build script now accept system properties as inputs for secrets and also compile if no secrets are available
 - Format all files and include formatter in repository
 - Add some missing side annotations
 - Expand config util a bit with more useful functions

### Added
 - Add ModelUtil to add custom state maps for models (Useful for states that should not be present in the model)

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
