package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.HashSet;

public class Disk {
    private Directory rootDirectory;
    private final HashSet<String> fileNameManager = new HashSet<>();
    // add a stack for undo/redo action
    public final int maxSize;
    public int currentSize;

    public Disk(int diskSize) {
        maxSize = diskSize;
    }

    public Directory getRootDirectory() {
        if (rootDirectory == null) {
            rootDirectory = new Directory("");
        }
        return rootDirectory;
    }

    public void handleSizeChange(int sizeChange) {
        if (currentSize + sizeChange >= maxSize) {
            throw new IllegalArgumentException("hk.edu.polyu.comp.comp2021.cvfs.model.Disk size exceeded!");
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
