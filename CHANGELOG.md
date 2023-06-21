# Changelog

### v.1.0 - 19.06.2023

_Update #3._

### Changed

- _Resource pack preview._
- _Link to the resource pack._
- _Readme files._
- _Key craft._

### v1.0 - 21.06.2023

_Update #3._

### Added

- _Video guide (review) on the plugin._

### Fixed

- _You cannot put a key in a bunch of keys that has been linked 
to coordinates with a negative number._

### v1.0 - 18.06.2023

_Update #2._

### Added

- _The give team now has the opportunity to choose a 
player who will be given an item._

### Changed

- _One of the checks has been made into a separate method._
- _Update readme files._
- _List of commands._

### Fixed

- _Configuration file._
- _Language files._
- _Removing all ordinary keys when cloning instead of one ordinary key._

## v1.0 - 17.06.2023

_Release update. Whoopee!!!_

### Added

- _Permission for crafting a master key._
- _Commands for getting items._

### Changed

- _Some checks were moved to a separate method
because they were often repeated._

### Fixed

- _Incorrect error display when using extra arguments 
in the command._

## v1.0 - 16.06.2023

_Prerelease update. On the way to the first release._

### Added

- _A master key for administrators that allows you to open 
all locked doors._
- _A ban on the use of keys and a bunch of keys in the workbench 
in crafting, in the furnace as fuel and in the enchantment table._

### Changed

- _Changed the structure of "if" checks._

### Fixed

- _Removing the locks and the code locks on the left click 
did not work quite right._

## v1.0 - 15.06.2023

_Config updates and code refinement._

### Added

- _The ability to change the recipe of items in config._
- _The ability to enable and disable the resource pack in 
the config, as well as setting up a link to the resource pack._
- _The ability to enable and disable taking damage with a preset 
number of attempts to open the combination lock_.
- _The ability to select the display of the ent with the coordinates 
of the lock for keys._
- _Comments on each line of the configuration file._

### Fixed

- _The door with the new lock and key was opened with 
the key from the old lock_.
- _When installing a regular lock and a key for the lock, 
the door opened._
- _The interfaces of some blocks (for example, the anvil, etc.) 
did not open._

## v1.0 - 12.06.2023 - 14.06.2023

_Additions and corrections._

### Added

- _Plugin commands: help, list, remove, clear, clone, reload._
- _Cloning a key._

### Changed

- _The material of the keys has been changed to a wooden hoe._
- _Changed the structure of "if" checks in the code of the 
LocksListener class._

### Fixed

- _When transferring a stack of keys to the inventory of a 
bunch of keys, the entire stack is transferred to one cell._
- _Now, when using keys, the hoe functions do not work._


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