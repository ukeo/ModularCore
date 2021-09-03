package eu.imposdev.modularcore.util;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

@Getter
@Setter
public class Message {

    private String key;
    private String value;

    /**
     *
     * Create a message with a key and a value
     *
     * @param key The key to authorize
     * @param value The value
     */

    public Message(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     *
     * Translate any message into another message file
     *
     * @param arguments enter variables you want to translate
     * @return Translated message
     */

    public String translate(Variable... arguments) {
        String finalMessage = value;
        for (Variable argument : arguments) {
            finalMessage = finalMessage.replace("%" + argument.getVariableName() + "%", String.valueOf(argument.getValue()));
        }
        return ChatColor.translateAlternateColorCodes('&', finalMessage);
    }

}
