package ch.codebulb.jsfrestfulprg.util;

/**
 * A utility class with common String-related helper functionality.
 */
public class Strings {
    private Strings() {}
    
    public static boolean isEmpty(String input) {
        return input == null || input.isEmpty();
    }
}
