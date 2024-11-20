package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class PathValidator {
    public static boolean isValid(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }
}
