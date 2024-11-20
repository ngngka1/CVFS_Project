package hk.edu.polyu.comp.comp2021.cvfs.model.files;


import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.File;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.StorableFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Directory extends StorableFile {
    // add a stack for undo/redo action
    private static final int DEFAULT_SIZE = 40;

    public Directory(String name) {
        super(name);
    }


    public static int getDefaultSize() {
        return DEFAULT_SIZE;
    }

    @Override
    public String getType() {
        return "directory";
    }

    @Override
    public int size() {
        int totalSize = DEFAULT_SIZE;
        for (File file : getFiles()) {
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

    public String toString() {
        return getName() + " " + size();
    }

    @Override
    public void save(String path) {
        if (path.contains("\\")) path = path.replace("\\", "/");
        if (!path.endsWith("/")) path += "/";
        path += getName();
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Directory creation failed");
        }
        for (File file : getFiles()) {
            file.save(path);
        }
    }
}
