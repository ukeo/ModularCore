package eu.imposdev.modularcore;

import eu.imposdev.modularcore.commands.ModuleCommand;
import eu.imposdev.modularcore.module.Module;
import eu.imposdev.modularcore.module.ModuleLoader;
import eu.imposdev.modularcore.util.Metrics;
import eu.imposdev.modularcore.util.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class ModularCore extends JavaPlugin {

    private static ModularCore instance;

    private ModuleLoader moduleLoader;
    private CommandMap commandMap;
    private UpdateChecker updateChecker;

    private String version;

    protected final int pluginId = 12685;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Starting module loader...");
        updateChecker = new UpdateChecker();
        updateChecker.check();
        if (updateChecker.isAvailable()) {
            getLogger().warning("There is an update available! Gop and check out the spigot page! https://www.spigotmc.org/resources/modularcore-advanced-coding-utility-module-system.95935/");
        } else {
            getLogger().info("You are running the latest version of ModularCore!");
        }
        version = this.getDescription().getVersion();
        moduleLoader = new ModuleLoader(getDataFolder().getAbsolutePath() + "/modules/");
        final Field bukkitCommandMap;
        try {
            bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            getLogger().info("Failed init of ModuleCommand:");
            exception.printStackTrace();
        }
        registerCommand(new ModuleCommand());
        loadMetrics();
    }

    @Override
    public void onDisable() {
        for ( Module module : moduleLoader.getLoadedModules() ) {
            module.onDisable();
        }
    }

    protected void loadMetrics() {
        Metrics metrics = new Metrics(this, pluginId);
    }

    protected void registerCommand( Command command ) {
        commandMap.register( command.getName(), command );
    }

    public static ModularCore getInstance() {
        return instance;
    }

    protected ModuleLoader getModuleLoader() {
        return moduleLoader;
    }

    protected CommandMap getCommandMap() {
        return commandMap;
    }

    public String getVersion() {
        return version;
    }
}
