package hk.edu.polyu.comp.comp2021.cvfs.model.files;

import java.util.Arrays;
import java.util.HashSet;

public class Document extends File {
    public static final int DEFAULT_SIZE = 40;
    private static final HashSet<String> ALLOWED_TYPE = new HashSet<>(Arrays.asList("txt", "java", "html", "css"));
    String type;
    String content;
    // remember to add a stack for undo/redo action

    public Document(String name, String type) {
        super(name);
        if (!ALLOWED_TYPE.contains(type)) {
            throw new IllegalArgumentException("Unsupported document type!");
        }
        this.type = type;
        this.content = "";
    }

    public String getType() {
        return this.type;
    }

    public Document(String name, String type, String content) {
        this(name, type);
        this.content = content;
    }

    public int size() {
        return DEFAULT_SIZE + content.length() * 2;
    }

    public String toString() {
        return getName() + "  " + getType() + "  " + size();
    }

}
