# Changelog

## v1.0 - 10.06.2023

_Basic and important corrections, as well as improvements._

### Added

- _In the getLocations method, when getting the coordinates of 
the lower part of the door, a check is added to ensure that 
the click occurs on the same door._
- _Checking for the uniqueness of the identifiers of keys 
and a bunch of keys._
- _Texts of the results of performing any actions 
(for example, errors when trying to open a closed door, etc)._

### Changed

- _The material of the key is now a book, and the material of 
the bunch of keys is a wooden hoe._
- _The maximum stack of the slot in the keychain inventory is 
now equal to 1._
- _Instead of the usual variable with the bunch of keys menu, a 
Map has been added, which stores the bunch of keys menu by the 
Player key. This is necessary so that there are no problems 
with the operation of several menus for different players._

### Fixed

- _Italic display names of items after localization._
- _Transfer items from hotbar to an empty bunch of keys 
inventory slot._
- _Double call Listener at the PlayerInteractEvent event._

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