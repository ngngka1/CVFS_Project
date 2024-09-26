import java.util.List;

// unused for now

abstract class Storable {
    List<Document> files;
    List<Directory> directories;
    abstract public int size();
}
