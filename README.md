# DodaLock v1.0
[![en](https://img.shields.io/badge/lang-en-red.svg)](README.md)
[![ru](https://img.shields.io/badge/lang-ru-blue.svg)](README.ru.md)

Spigot plugin adds locks and code locks for doors, trapdoors, barrels and chests.

## Features

* Close your doors, hatches, barrels and chests from other players.
* The ability to use both ordinary and code locks.
* A code lock, an ordinary lock, a key to it and a bunch of keys has
  their own craft.
* Compatible with new Minecraft 1.20.
* There is localization into english and russian languages.
* Open-source (GNU GPL v3 license).

## How to use it?

Holding a code or ordinary lock in the main hand, press in a sneaking
RMB on the door, trapdoor, barrel or chest, so you put it.

In the case of a code lock, a password must be entered. After
you have entered the password, you will be able to open this object. Players with
a master key will be able to open this object without entering a password.

In the case of an ordinary lock, it is necessary, holding the key in the main hand,
to press the object on which the lock hangs in the squat of the RMB
to create a key for this lock. Now that you
have the key to this object, you can open it using
this key or the master key is for administrators.

To remove any lock, press the LMB in the sneaking. In the case of an
ordinary lock, you need to hold the key in your hands, and in the case of a
code lock, you need to be authorized, that is, enter
the password from it at least once.

Any keys can be stored in a bunch of keys. To put the keys in
a bunch of keys, you need to hold it in the main hand and
press the RMB in a sneaking. Also, a bunch of keys can be used to open
objects on which a lock is installed, if the bunch of keys contains
the key to this lock.

Any key attached to the lock can be cloned. To do this, 
enter the command `/clone`, holding this key in the main hand, 
while the player's inventory must have an ordinary (not
tied to any lock) key.

## Video

A video with instructions for using the plugin is on my YouTube channel at this [link](https://youtu.be/pOgOVg9vnRY).

If you have made a video about my plugin, 
then I ask you to send it to me.

## Documentation

This is documentation for DodaLock v1.0 plugin.

### General information

You will be able to close doors, trapdoors, barrels and chests with an
ordinary or code locks.

An ordinary lock requires the use of a key.

The code lock also requires you to enter a password consisting of four digits,
and the lock remembers the authorized player, and he does not have
to enter the password again.

Any lock can be opened with a master key if this setting
is enabled in the config.

Any keys can be stored in a bunch of keys.

Keys can be cloned by having an ordinary key.

### Crafting recipes

All new items have their own crafting recipe.

The recipe for crafting a lock:

![The recipe for crafting a lock](https://github.com/dodabyte/DodaLock/blob/screenshots/recipe_crafts_images/lock_craft.png?raw=true)

The recipe for crafting a code lock:

![The recipe for crafting a code lock](https://github.com/dodabyte/DodaLock/blob/screenshots/recipe_crafts_images/code_lock_craft.png?raw=true)

The recipe for crafting a key:

![The recipe for crafting a key](https://github.com/dodabyte/DodaLock/blob/screenshots/recipe_crafts_images/key_craft.png?raw=true)

The recipe for crafting a bunch of keys:

![The recipe for crafting a bunch of keys](https://github.com/dodabyte/DodaLock/blob/screenshots/recipe_crafts_images/bunch_of_keys_craft.png?raw=true)

The recipe for crafting a master key:

![The recipe for crafting a master key](https://github.com/dodabyte/DodaLock/blob/screenshots/recipe_crafts_images/master_key_craft.png?raw=true)

### Commands

To work with the plugin in the game, you need to enter the command 
`/dodalock` or `/dl`

`/dodalock help` - Calling help to the plugin.

`/dodalock list [all | codelocks | locks]` - Getting a list of installed locks.

`/dodalock remove [codelock | lock] [number | location]` - Removing the lock.

`/dodalock clear [all | codelock | lock]` - Removing all locks.

`/dodalock clone` - Clone the key that is in the main hand.

`/dodalock give {player} [codelock | lock | key | masterkey | bunchofkeys]` - Give the specified player one of the custom plugin items.

`/dodalock reload` - Reloading the plugin.

### Configuration file

`language` - The language of the plugin interface. Default: en_us (English).

`allow_clear_bunch_of_keys_inventory` - Setting that includes clearing the bunch of keys inventory data once in a while. Default: true.

`verification_period` - Setting the frequency (in hours) of checking the inventory of the bunch of keys for cleaning. Default: 3.

`enable_key_lore` - Setting enable a lore for keys and a bunch of keys, displaying the coordinates of the lock that the key or keys are associated with. Default: true.

`enable_damage` - Setting enable damage from the code lock during incorrect input password entry attempts. Default: true.

`max_attempts_to_take_damage` - Setting the maximum number of attempts in which damage from incorrect password entry is not inflicted. Default: 3.

`enable_master_key` - The setting allows you to use a universal key for administrators. Default: true.

`enable_resourcepack` - Setting enable a custom resource pack for the plugin. Setting enable a custom resource pack for the plugin. Enter false if you want to play without it or embed the resource pack into your server locally. Default: true.

`resourcepack_url` - Setting responsible for the link to the resource pack. Default: http://resourcepack.host/dl/CBdqxUZ66Q7b6HOV8OoVYM7pJjclsUEF/DodaLock.zip.

`items` - Setting up recipes for crafting custom items.

`shape` - The items crafts shape. Max. three lines and three characters by line, where symbol '-' is none. Example: #$#, -#-, #$#.

`shape_materials` - Materials intended for crafting. Example: #: IRON_INGOT, $: STICK

`items.code_lock.shape` - Default: $$$, &*%, $$$.

`items.code_lock.shape_materials` - Default: '$: IRON_INGOT, *: REDSTONE_BLOCK, &: STONE_BUTTON, %: GLASS_PANE'.

`items.lock.shape` - Default: -$-, %%%, %%%.

`items.lock.shape_materials` - Default: '$: STRING, %: IRON_INGOT'.

`items.key.shape` - Default: '%, $'

`items.key.shape_materials` - Default: '%: IRON_INGOT, $: LEVER'

`items.master_key.shape` - Default:  '%, $'

`items.master_key.shape_materials` - Default: '%: IRON_INGOT, $: COMMAND_BLOCK'

`items.bunch_of_keys.shape` - Default: -$-, $%$, -$-

`items.bunch_of_keys.shape_materials` - Default: '%: IRON_INGOT, $: STRING'

### Permissions

Permissions for administrators:

`dodalock.craft.masterkey` - Permission to craft a master key.

`dodalock.give.key` - Permission to give a key.

`dodalock.give.masterkey` - Permission to give a master key.

`dodalock.give.lock` - Permission to give a lock.

`dodalock.give.codelock` - Permission to give a code lock.

`dodalock.give.bunchofkeys` - Permission to give a bunch of keys.

### Server Compatibility

This plugin is compatible with Spigot. 
Using CraftBukkit will not work. 
The versions of Minecraft that are currently supported are 1.19 - 1.20.1.
