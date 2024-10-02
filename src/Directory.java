import java.util.ArrayList;
import java.util.List;

public class Directory {
    String name; // only digits and letters; at most 10 chars
    List<Document> documents; // only of type [txt, java, html, css]
    List<Directory> directories; // sub directories
    // add a stack for undo/redo action

    public Directory(String name, boolean isRootDirectory) {
        if ((isRootDirectory) || (name != null && name.matches("[A-Za-z0-9]+") && name.length() <= 10)) { // fyi: https://www.w3schools.com/python/python_regex.asp
            this.name = name;
        } else {
            throw new IllegalArgumentException("Invalid directory name!");
        }
        documents = new ArrayList<>();
        directories = new ArrayList<>();
    }

    public Directory(String name) {
        this(name, false);
    }

    public int size() {
        int totalSize = System.defaultDirectorySize;
        for (Document document : documents) {
            totalSize += document.size();
        }
        for (Directory directory : directories) {
            totalSize += directory.size();
        }
        return totalSize;
    }
}
