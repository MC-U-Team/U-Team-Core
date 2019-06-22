# Changelog
All notable changes to this project will be documented in this file.

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
