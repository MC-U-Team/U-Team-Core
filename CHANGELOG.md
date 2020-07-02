# Changelog
All notable changes to this project will be documented in this file.

## [1.15.2-3.0.0.160] - 2020-07-02
### Changed
 - Updated to forge 31.2.30
 - Updated mappings to 20200702-1.15.1
 - Release after uteamcore 3.0.0 update

## [1.15.2-3.0.0.159-SNAPSHOT] - 2020-07-02
### Changed
 - Use deferred register all over now
 - Do not init registry entries before they are registered
 - Therefore the whole IURegistryEntry system is gone
 - Base RegistryUtil is removed
 - Fixed a bug in the UFluidHandler
 - Make the logo file smaller
 
### Removed
 - Remove some UTypes as they were only used for the IURegistryEntry
 
### Added
 - Added custom deferred registers
 - Added some new systems to make registering easy

## [1.15.2-2.12.1.158] - 2020-06-16
### Added
 - Added getPlaneDistance to math util
 - Added NoPlaceUBucketItem
 - Added [#39](https://github.com/MC-U-Team/U-Team-Core/issues/39) to make the inventory adder better (for fluid inventory, and item inventory)
 - Added uteamcore subcommand locatebiome
 - Added uteamcore subcommand locatestructure
 
### Changed
 - Fixed [#38](https://github.com/MC-U-Team/U-Team-Core/issues/38) to expose the fluid capabilities for the u bucket item

## [1.15.2-2.12.0.157-SNAPSHOT] - 2020-06-15
### Added
 - Added valueInRange methods to math util
 - Added randomNumberInRange method and deprecate the old random methods
 - Added item ingredient that takes amount of items (Can only be used in custom machines, because vanilla machines cannot handle the amount of input ingredients)
 - Added fluid ingredient that can handle fluids, fluid tags and the amount
 - Added serialize util for item stacks and fluid stacks as well as item ingredients and fluid ingredients
 - Added float and double buffer holder
 - Added new interaction type enum (execute, simulate) for item and fluid stuff
 - Added extended item handler that uses the new interaction type (implemented in the UItemStackHandler)
 - Added interface for the modifiable fluid handler like the item handler modifiable
 - Added extended fluid handler to handle fluids better. (Copied from mekanism)
 - Added fluid container, fluid slot and fluid screen that can handle the whole fluid sync system with fluid slots
 - Added fluid render for slots

### Changed
 - Updated to forge 31.2.19
 - Updated mappings to 20200615-1.15.1
 - Add travis ci for building
 - Fix build script for travis ci
 - Use the right file endings now in all files with git
 - Make extra api source set

## [1.15.2-2.11.0.156] - 2020-03-06
### Added
 - Added generator for fluid and entity type tags
 - Added utility methods for fluid and entity type tags
 - Added UParticleType for easier particle registration
 - Added UFlowingFluid and USourceFluid
 - Added UFluidBlock
 - Added UBucketItem
 - Added SetTileEntityNBT loot function under the name uteamcore:set_tileentity_nbt and add basic method to generate them in the loot tables generator

### Added
 - Duplicate tags error with data generators can now not occur anymore
 - Make some constructors public

## [1.15.2-2.10.6.155] - 2020-02-21
### Added
 - Better method for the world save data
 - Energy change detection in the basic energy storage implementation
 - Energy delegates for acceptor and producer

## [1.15.2-2.10.5.154] - 2020-02-17
### Changed
 - Updated to forge 31.1.12
 - Updated mappings to 20200217-1.15.1
 - Update buildscripts

## [1.15.2-2.10.4.153] - 2020-02-07
### Changed
 - Fixed bug with scaling text renderer for music player mod. Fixed [#34](https://github.com/MC-U-Team/U-Team-Core/issues/34) and fixed [#34](https://github.com/MC-U-Team/Music-Player/issues/34) in music player

## [1.15.2-2.10.3.152] - 2020-02-06
### Changed
 - Fixed [#33](https://github.com/MC-U-Team/U-Team-Core/issues/33) so the signed jar file verifier now checks all files excluding directories and certificate files

## [1.15.2-2.10.2.151] - 2020-02-04
### Changed
 - Updated to forge 31.1.0
 - Updated mappings to 20200203-1.15.1

## [1.15.2-2.10.1.149] - 2020-02-03
### Added
 - Added certificate to sign the jar
 - Added sign verifier to check if the mod classes are signed

## [1.15.2-2.10.0.146] - 2020-01-23
### Changed
 - Update to 1.15.2
