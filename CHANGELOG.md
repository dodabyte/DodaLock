# Changelog

## v1.0 - 07.06.2023 - 09.06.2023

_The third changelog entry._

### Added

- _Resourcepack for plugin._
- _Textures and items of the code lock interface._

### Changed

- _Config file structure: now the configuration config is 
located separately from the config with lock data._
- _Verification of items in the hands of a key, 
a code lock, a regular lock and a bunch of keys._
- _Classes and methods of creating custom items._

### Fixed

- _The work of checking configs with a bunch of keys inventories._
- _You can only put keys in the inventory of the bunch of keys._


## v1.0 - 06.06.2023

_The second changelog entry._

### Added

- _Clearing the config with the contents of the 
bunch of keys inventory after a certain time._

### Changed

- _The creation of the config._


## v1.0 - 30.05.2023

_The first changelog entry._

### Added

- _Unique identifier for each bunch of keys._
- _The ability to open the door with a bunch of keys._

### Changed

- _The config with the items in the bunch of keys inventory 
has been slightly changed._

### Fixed

- _Renaming of all items in the inventory when the key is pressed 
on the door either without a lock or with a lock 
unrelated to this key._
- _Error checking the presence of doors by coordinates in the 
config when reloading (command "reload") the server._