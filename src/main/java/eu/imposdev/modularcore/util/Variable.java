package eu.imposdev.modularcore.util;

import lombok.Getter;

@Getter
public class Variable {

    private final String variableName;
    private final Object value;

    /**
     *
     * Create a variable with name an value
     *
     * @param variableName Name for the variable
     * @param value Value of the variable (Could be anything)
     */

    public Variable(String variableName, Object value) {
        this.variableName = variableName;
        this.value = value;
    }

}
