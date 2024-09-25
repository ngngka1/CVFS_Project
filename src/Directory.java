import java.util.ArrayList;
import java.util.List;

public class Directory {
    String name; // only digits and letters; at most 10 chars
    List<Document> files; // only of type [txt, java, html, css]
    List<Directory> directories; // sub directories
    int size;
    // add a stack for undo/redo action

    public Directory(String name) {
        files = new ArrayList<>();
        directories = new ArrayList<>();
        if (name != null && name.matches("[A-Za-z0-9]+")) { // fyi: https://www.w3schools.com/python/python_regex.asp
            this.name = name;
        } else {
            // throw some exception
        }
    }
}
