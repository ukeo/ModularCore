package eu.imposdev.modularcore.language;

import eu.imposdev.modularcore.module.Module;
import eu.imposdev.modularcore.util.Variable;
import lombok.Getter;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Getter
public class LanguageHandler {

    private Map<String, Language> languageMap = new HashMap<>();
    private Language defaultLanguage;

    /**
     *
     * Create a LanguageHandler for a certain module
     *
     * @param module The module
     */

    public LanguageHandler(Module module) {
        for (File locale : getResourceFolderFiles("locale")) {
            Language language = new Language(locale.getName(), locale);
            if (locale.getName().equals("en-EN")) {
                defaultLanguage = language;
            }
            languageMap.put(locale.getName(), language);
        }
    }

    /**
     *
     * Get the message for a certain locale
     *
     * @param locale The locale
     * @param value The value
     * @param variables The variable(s)
     * @return Message as String
     */

    public String getMessage(String locale, String value, Variable... variables) {
        Language language = languageMap.getOrDefault(locale, defaultLanguage);
        return language.getMessages().get(value).translate(variables);
    }

    /**
     *
     * Get all resources in a certain folder
     *
     * @param folder The folders path
     * @return FileArray
     */

    private static File[] getResourceFolderFiles(String folder) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(folder);
        String path = url.getPath();
        return new File(path).listFiles();
    }

}
