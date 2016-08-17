#Lobby
This is a spigot plugin intended to be used on the default server of a bungeecord setup. This is currently work in progress, so don't expect it to work or not to crash your server.

## Features
### No Damage
* Players don't get any damage
* Players don't get hungry
* If players fall into the void they get ported back
* Players cannot push each other around

### Inventory Menu
* Configurable items in the player inventory trigger custom actions
* Submenus in additional inventories

### Pets
* Players can have pets that follow them around

## Setup
### Prerequisites
* Maven
* Spigot [BuildTools.jar](https://hub.spigotmc.org/jenkins/job/BuildTools/)

### Add craftbukkit to your local maven repository
1. run `java -jar BuildTools.jar`
2. cd into `Spigot/CraftBukkit` and run `mvn install`

### Open the project in your IDE (that has maven support, preferably intellij)
Maven downloads dependencies automatically.