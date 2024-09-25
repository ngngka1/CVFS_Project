import java.lang.System;
import java.util.ArrayList;
import java.util.List;

public class Directory {
    String name; // only digits and letters; at most 10 chars
    List<Document> files; // only of type [txt, java, html, css]
    List<Directory> directories; // sub directories
    // add a stack for undo/redo action

    public Directory(String name) {
        if (name != null && name.matches("[A-Za-z0-9]+") && name.length() <= 10) { // fyi: https://www.w3schools.com/python/python_regex.asp
            this.name = name;
        } else {
            System.out.println("Invalid directory name!");
        }
        files = new ArrayList<>();
        directories = new ArrayList<>();
    }

    public int size() {
        int totalSize = 0;
        for (Document file : files) {
            totalSize += file.size();
        }
        for (Directory directory : directories) {
            totalSize += directory.size();
        }
        return totalSize;
    }
}
