# ModularCore

Advanced coding utility with the ability to create own modules.

## Getting Started

The core is designed as a modular system. You can write your own modules based on bukkit and the core itself. You do not longer have to use 'JavaPlugin'. Also you do not have
thousands of plugins on your server. Just a single one.

### Prerequisites

The core is for Minecraft Version 1.8.x - 1.17.x
The core is built with Java 16

## Installation

Donwload the plugin [HERE](https://imposdev.eu/repo/eu/imposdev/modularcore/1.0.0/modualrcore-1.0.0.jar) and put it into your plugins folder.
Restart your server.
Open up your plugins folder and you will find a ModularCore folder with a subfolder called modules. There you have to put in your modules.

## Maven repository

```maven
<repository>
    <id>modularcore</id>
    <url>https://imposdev.eu/repo</url>
</repository>
```

```maven
<dependency>
    <groupId>eu.imposdev</groupId>
    <artifactId>modularcore</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Module development

The module development is very simple.
To start you have to code your main class like this:

IMPORTANT: You also have to import spigot as a repository. The core doesn't have its own listener and event system.

```java
package eu.imposdev.coretest;

import eu.imposdev.coretest.listener.PlayerJoinListener;
import eu.imposdev.modularcore.ModularCore;
import eu.imposdev.modularcore.module.Module;

public class TestModule extends Module {

    private static TestModule instance;

    private ModularCore coreInstance;

    @Override
    public void onLoad() {
        instance = this;
        coreInstance = this.getCorePlugin();
        this.getLogger().info("onLoad called!");
        registerListener(new PlayerJoinListener());
    }

    @Override
    public void onEnable() {
        this.getLogger().info("Module loaded successfully!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Disabling plugin...");
    }

    @Override
    protected String getName() {
        return "TestModule";
    }

    public static TestModule getInstance() {
        return instance;
    }

    public ModularCore getSkyBlockInstance() {
        return coreInstance;
    }
```

If your main class is ready you simply and just write your listeners, api's, etc.

To register a Listener use the following method provided by the Core:
```java
registerListener(Listener listener);
```

To register a Command use the following method provided by the Core:
```java
registerCommand(Command command);
```

To create a command you have to do it like this:
```java
package eu.imposdev.modularcore.commands;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ModuleCommand extends Command {

    public static void addSubCommand(SubCommand subCommand) {
        subCommands.add(subCommand);
    }

    private static List<SubCommand> subCommands = new ArrayList<>();

    public ModuleCommand() {
        super("module");
        setPermission("command.module");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(strings.length > 0) {
            for (SubCommand subCommand : subCommands) {
                for (String command : subCommand.getSubCommands()) {
                    if (command.equalsIgnoreCase(strings[0])) {
                        subCommand.onCommand(commandSender, null, (String[]) ArrayUtils.subarray(strings, 0, 1));
                        return true;
                    }
                }
            }
        }
        for (SubCommand command : subCommands) {
            commandSender.sendMessage("/module " + command.getSubCommands()[0]);
        }
        return false;
    }

}
```

To let the Core know how to handle your module you have to create a module.yml instaed of a plugin.yml.
The module.yml should look lie this:
```yaml
name: TestModule
version: 1.0
author: Espen
main: eu.imposdev.coretest.TestModule
dependencies: []
```

You now can compile your module and put it into /plugins/ModualrCore/modules/ .

## JavaDocs

You can find the JavaDocs for the Core [HERE](https://javadocs.pixelplays.net/modularcore/)

## Authors

* **Espen** - *SimpleCloud-NPC* - [Espen](https://github.com/EhreGetaken)
