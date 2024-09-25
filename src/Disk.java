import java.util.ArrayList;
import java.util.List;

public class Disk {
    List<Document> files;
    List<Directory> directories;
    // add a stack for undo/redo action
    private int maxSize;
    public int currentSize;

    public Disk(int diskSize) {
        maxSize = diskSize;
        files = new ArrayList<>();
        directories = new ArrayList<>();
    }

}
