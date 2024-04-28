# Changelog
All notable changes to this project will be documented in this file.

## [1.19.4-5.0.2.298] - 2024-04-28
### Changed
 - Update to latest gradle changes

## [1.19.4-5.0.2.291] - 2024-04-19
### Changed
 - Fixed crash because of invalid mixin config

## [1.19.4-5.0.2.288] - 2024-03-29
### Changed
 - Fix publishing
 - Artifact id has changed to reflect modloader

## [1.19.4-5.0.2.279] - 2024-03-29
### Changed
 - Update to latest gradle & gradle files version

## [1.19.4-5.0.2.261] - 2023-06-12
### Changed
 - Update build scripts

## [1.19.4-5.0.2.250] - 2023-04-27
### Changed
 - Fixed [crash](https://github.com/MC-U-Team/U-Team-Core/pull/256) in UShapedRecipeSerializer because of wrong serialization. Thanks DJtheRedstoner

## [1.19.4-5.0.1.249] - 2023-04-26
### Changed
 - Move UScreen to common module (and include it in fabric jar as well)

## [1.19.4-5.0.0.248] - 2023-04-12
### Changed
 - Add jei again and readd jei plugin
 - Cleanup buildscript and dependencies in poms

## [1.19.4-5.0.0.247-SNAPSHOT] - 2023-03-27
### Changed
 - Finally fix curseforge upload order

## [1.19.4-5.0.0.246-SNAPSHOT] - 2023-03-27
### Changed
 - Fix curseforge task order

## [1.19.4-5.0.0.245-SNAPSHOT] - 2023-03-27
### Changed
 - Fix fabric module publishing wrong artifact to curseforge

## [1.19.4-5.0.0.244-SNAPSHOT] - 2023-03-26
### Changed
 - Use multi loader project now
 - Forge and fabric version are now built at the same time and using the same version number
 - Fabric version only includes the gui and render stuff that it used to include as in 1.19.3
 
## [1.19.4-4.6.0.242] - 2023-03-23
### Changed
 - Rework ugui elements
 - Update and fix shaped recipe serializer

## [1.19.4-4.5.4.241-SNAPSHOT] - 2023-03-17
### Changed
 - Port to 1.19.4
 - Beta as there will be changes releated to gui later on