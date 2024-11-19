package hk.edu.polyu.comp.comp2021.cvfs.model.files;
import hk.edu.polyu.comp.comp2021.cvfs.model.Disk;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
;

public class FileSerializer {

    public void saveVirtualDisk(String path, Disk disk) {
        try (FileOutputStream fileOut = new FileOutputStream(path);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(disk);
        } catch (IOException e) {
            java.lang.System.out.println("Error saving virtual disk: " + e.getMessage());
        }
    }
}
