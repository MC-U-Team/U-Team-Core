# Changelog
All notable changes to this project will be documented in this file.

## [1.16.1-3.1.2.167] - 2020-08-03
### Changed
 - Remove the tag validation in the generators for existing tags. We need to include forge tags in our tags and that will not work with this change. Invalid tags can now be included, but there is no easy fix.

### Added
 - Added integration system that loads a class based on an annotation and a call in the main mod constructor

## [1.16.1-3.1.1.166] - 2020-07-29
### Changed
 - Run data generators to update the jsons files

## [1.16.1-3.1.1.165] - 2020-07-29
### Changed
 - Updated to newer forge versions
 - Add license in mods.toml
 - First 1.16.1 release

## [1.16.1-3.1.1.163-SNAPSHOT] - 2020-07-17
### Added
 - Added jei plugin with info for dyeable items
 - Add utility methods

## [1.16.1-3.1.0.162-SNAPSHOT] - 2020-07-16
### Changed
 - Fixed crash #41 (Fixes [#41](https://github.com/MC-U-Team/U-Team-Core/issues/41))
 - Updated mappings names

## [1.16.1-3.1.0.161-SNAPSHOT] - 2020-07-07
### Changed
 - Port to 1.16.1 (without dimension support)
