package hk.edu.polyu.comp.comp2021.cvfs.model.files;

import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.File;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.JavaIOFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class Document extends File {
    private static final int DEFAULT_SIZE = 40;
    private static final HashSet<String> ALLOWED_TYPE = new HashSet<>(Arrays.asList("txt", "java", "html", "css"));
    String type;
    String content;
    // remember to add a stack for undo/redo action

    public Document(String name, String type) {
        super(name);
        if (!isSupportedType(type)) {
            throw new IllegalArgumentException("Unsupported document type!");
        }
        this.type = type;
        this.content = "";
    }

    public static int getDefaultSize() {
        return DEFAULT_SIZE;
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
        return getName() + " " + getType() + " " + size();
    }

    public static boolean isSupportedType(String type) {
        return ALLOWED_TYPE.contains(type);
    }

    public void save(String path) {
//        new JavaIOFile(path, );
        String filename = getName() + "." + getType();
        if (path.contains("\\")) path = path.replace("\\", "/");
        if (!path.endsWith("/")) path += "/";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + filename))) {
            writer.write(content); // Write content to the file
        } catch (IOException e) {
            throw new RuntimeException("Document creation failed");
        }

    }

}
