package hk.edu.polyu.comp.comp2021.cvfs.model.disk;

import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class Disk {
    private Directory rootDirectory;
    private final HashSet<String> fileNameManager = new HashSet<>();
    // add a stack for undo/redo action
    private static final int DEFAULT_MAX_SIZE = Integer.MAX_VALUE;
    private int maxSize;
    public int currentSize;

    public void save(String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Disk folder creation failed");
        }
        for (File file : getRootDirectory().getFiles()) {
            file.save(path);
        }
    }

    public Disk(int diskSize) {
        maxSize = diskSize;
    }

    public Disk() {this(DEFAULT_MAX_SIZE);}

    public void setRootDirectory(Directory rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public Directory getRootDirectory() {
        if (rootDirectory == null) {
            setRootDirectory(new Directory(""));
        }
        return rootDirectory;
    }

    public void handleSizeChange(int sizeChange) {
        if (currentSize + sizeChange >= maxSize) {
            throw new IllegalArgumentException("Disk size exceeded!");
        } else {
            currentSize += sizeChange;
        }
    }

    private HashSet<String> getFileNameManager() {
        return fileNameManager;
    }
//    public HashSet<String> getFileNameManager() {return fileNameManager;}
    public void addUniqueFileName(String newFileName) {getFileNameManager().add(newFileName);}
    public void deleteUniqueFileName(String oldFileName) {getFileNameManager().remove(oldFileName);}
    public void renameUniqueFileName(String oldFileName, String newFileName) {deleteUniqueFileName(oldFileName); addUniqueFileName(newFileName);}


    public boolean hasFileName(String x) {
        return getFileNameManager().contains(x);
    }

}
