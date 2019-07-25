# Changelog
All notable changes to this project will be documented in this file.

## [1.14.2-2.6.1.124] - 2019-07-26
### Added
- Data generation system (extended support for tags)

## [1.14.2-2.6.0.121] - 2019-07-24
### Changed
- Renamed tool and armor classes to match new convention (major change)

## [1.14.2-2.5.5.118] - 2019-07-23
### Changed
- Fix maven repository url

## [1.14.2-2.5.5.116] - 2019-06-26
### Added
- Basic energy storage
- Energy gui widget

### Changed
- Add default methods for sync methods in ISyncedTileEntity
- Add more methods for slot creation in UContainer

## [1.14.2-2.5.4.115] - 2019-06-23
### Changed
- Updated to forge 26.0.55
- Fixed colored items not working since forge 26.0.51 due to forge changes

## [1.14.2-2.5.3.114] - 2019-06-22
### Added
- Added Scrolling list

### Changed
- Rewritten gui buttons
- Update to forge 26.0.43 and newest mappings
- First stable release

## [1.14.2-2.5.2.113-SNAPSHOT] - 2019-06-20
### Changed
- Fixed UShapedRecipeSerializer where method was not copied correctly
- Repacked IDyeableItem and made the color registry array in api so the api can be split from the implemention

## [1.14.2-2.5.1.112-SNAPSHOT] - 2019-06-20
### Added 
- Added IDyeableItem for easy usage with colored items
- Added custom dye recipe
- Added ColorUtil for wool blocks
- Added UShapedRecipeSerializer that make some methods public from shaped recipe

### Changed
- Fixed server crash with containers
- Apply convention to write static final fields in caps with underscores

## [1.14.2-2.5.0.111-SNAPSHOT] - 2019-06-18
### Changed
- Updated to 1.14.2
- Rewritten registry system
- Rewritten container sync system
- Fixed some bugs
