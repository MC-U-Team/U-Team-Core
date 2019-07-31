# Changelog
All notable changes to this project will be documented in this file.

## [1.14.3-2.7.0.128] - 2019-07-31
### Changed
- Backported changes to tools from 1.14.4
- Changed spade to shovel in code, because thats now the name that mcp/minecraft uses.
- Made the additional damage for tools a float value, because some use floats. When a float cannot be used like for swords then the float is casted to an integer

### Added
- Gson and config util to create easy configs. Mainly used in useful resources

## [1.14.3-2.6.1.125] - 2019-07-26
### Added
- Data generation system (extended support for tags)

## [1.14.3-2.6.0.122] - 2019-07-24
### Changed
- Renamed tool and armor classes to match new convention (major change)

## [1.14.3-2.5.5.119] - 2019-07-23
### Changed
- Fix maven repository url

### Added
- Copy new block properties in BlockProperties class (harvest level and tool)

## [1.14.3-2.5.5.117] - 2019-06-27
### Changed
- Update to 1.14.3
