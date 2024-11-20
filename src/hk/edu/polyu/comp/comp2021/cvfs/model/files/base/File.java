package hk.edu.polyu.comp.comp2021.cvfs.model.files.base;

import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.disk.Disk;

import java.io.Serializable;


public abstract class File {
    private String name;
    abstract public void save(String path);
    abstract public int size();
    abstract public String getType();

    public static int getDefaultSize() {return 0;};

    protected File(String name) {
        if (name == null) {
            throw new IllegalArgumentException("File name cannot be empty!");
        }
        Disk workingDisk = System.getWorkingDisk();
        if (!(name.isEmpty() && !workingDisk.hasFileName(""))) {
            if (workingDisk.hasFileName(name)) {
                throw new IllegalArgumentException("File with the same name already exists!");
            }
            if (!(name.matches("[A-Za-z0-9]+") && name.length() <= 10)) {
                throw new IllegalArgumentException("Invalid file name!");
            }
        }
        setName(name);
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    abstract public String toString();
}
