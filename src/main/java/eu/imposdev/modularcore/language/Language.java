package eu.imposdev.modularcore.language;

import eu.imposdev.modularcore.util.Message;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Language {

    private final String name;
    private final File file;
    private final FileConfiguration config;
    private Map<String, Message> messages = new HashMap<>();

    /**
     *
     * Create a language constructor to load a language file
     *
     * @param name Name of the language eg. de_DE
     * @param file The file of the language
     */

    public Language(String name, File file) {
        this.name = name;
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
        config.getValues(true).forEach((s, o) -> messages.put(s, new Message(s, (String) o)));
    }

}
