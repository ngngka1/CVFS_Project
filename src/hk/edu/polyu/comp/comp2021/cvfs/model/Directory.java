package hk.edu.polyu.comp.comp2021.cvfs.model;

public class Directory extends StorableFile {
    // add a stack for undo/redo action
    public static final int DEFAULT_SIZE = 40;

    public Directory(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "hk.edu.polyu.comp.comp2021.cvfs.model.Directory";
    }

    @Override
    public int size() {
        int totalSize = DEFAULT_SIZE;
        for (File file : files) {
            totalSize += file.size();
        }
        return totalSize;
    }

    @Override
    public void createDir(String dirName) {
        Directory newDirectory = new Directory(dirName);
        add(newDirectory);
    }

    @Override
    public void createDoc(String docName, String docType, String docContent) {
        Document newDocument = new Document(docName, docType, docContent);
        add(newDocument);
    }

}
