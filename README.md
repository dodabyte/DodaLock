# DodaLock v1.0
[![en](https://img.shields.io/badge/lang-en-red.svg)](README.md)
[![ru](https://img.shields.io/badge/lang-ru-blue.svg)](README.ru.md)

Spigot plugin adds locks and code locks for doors, trapdoors, barrels and chests.

## Features

* Close your doors, hatches, barrels and chests from other players.
* The ability to use both ordinary and code locks.
* A code lock, an ordinary lock, a key to it and a bunch of keys has
  their own craft.
* Compatible with new Minecraft 1.20
  items (even with cherry tree items version 1.19).
* There is localization into english and russian languages.
* Open-source (GNU GPL v3 license).

## How to use it?

Holding a code or ordinary lock in your hands, press the RMB in sneaking
on the door, trapdoor, barrel or chest,
so you will put it.

In the case of a code lock, you must enter the password. Now that
you are logged in, you will be able to open this object.

In the case of an ordinary lock, it is necessary, holding the key in your hands,
to press the object on which the lock hangs in the squat of the RMB
to create a key for this lock. Now that you
have the key to this object, you can open it using
the key to the lock of this object.

To remove any lock, press the LMB in sneaking. In the case of an
ordinary lock, it is necessary to hold the key in your hands.

## Video

_Coming soon._

A video with instructions for use is on my YouTube channel 
at this link.

If you have made a video about my plugin, 
then I ask you to send it to me.

## Documentation

This is documentation for DodaLock v1.0 plugin.

### Crafting recipes

_Coming soon._

### Commands

To work with the plugin in the game, you need to enter the command 
`/dodalock` or `/dl`

`/dodalock help` - Calling help to the plugin.

`/dodalock list [all | codelocks | locks]` - Getting a list of installed locks.

`/dodalock remove [codelock | lock] [number | location]` - Removing the lock.

`/dodalock clear [all | codelock | lock]` - Removing all locks.

`/dodalock clone` - Clone the key that is in the main hand.

`/dodalock give [codelock | lock | key | masterkey | bunchofkeys]` - Give one of the custom plugin items.

`/dodalock reload` - Reloading the plugin.

### Server Compatibility

This plugin is compatible with Spigot. 
Using CraftBukkit will not work. 
The versions of Minecraft that are currently supported are 1.19.
