package hk.edu.polyu.comp.comp2021.cvfs.model.disk;

import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
;

public class DiskSerializer {

    public static void save(String path, Disk disk) {
        try (FileOutputStream fileOut = new FileOutputStream(path);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(disk);
        } catch (IOException e) {
            java.lang.System.out.println("Error saving virtual disk: " + e.getMessage());
        }
    }

    private static void saveDir(Directory dir) {

    }
}
