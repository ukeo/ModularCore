package eu.imposdev.modularcore;

import eu.imposdev.modularcore.commands.ModuleCommand;
import eu.imposdev.modularcore.module.Module;
import eu.imposdev.modularcore.module.ModuleLoader;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class ModularCore extends JavaPlugin {

    private static ModularCore instance;

    private ModuleLoader moduleLoader;
    private CommandMap commandMap;

    protected final int pluginId = 12685;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Starting module loader...");
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

    public static ModularCore getInstance() {
        return instance;
    }

    protected ModuleLoader getModuleLoader() {
        return moduleLoader;
    }

    protected CommandMap getCommandMap() {
        return commandMap;
    }

    protected void registerCommand( Command command ) {
        commandMap.register( command.getName(), command );
    }
}
