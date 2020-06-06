# Changelog
All notable changes to this project will be documented in this file.

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
