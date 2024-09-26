import java.util.ArrayList;
import java.util.List;

public class Disk {
    Directory rootDirectory;
    // add a stack for undo/redo action
    public final int maxSize;
    public int currentSize;

    public Disk(int diskSize) {
        maxSize = diskSize;
        rootDirectory = new Directory("", true);
    }

}
