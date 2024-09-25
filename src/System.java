import java.util.ArrayList;
import java.util.List;

public class System {
    private static System systemInstance; // private so singleton instance doesnt get directly accessed
    List<Disk> disks;
    int selectedDiskIndex;
    String workingDirectory; // expected value example: /dir1/dir2

    private System() {
        selectedDiskIndex = 0;
        disks = new ArrayList<>();
    }
    public static System createInstance() {
        if (System.systemInstance == null) {
            System.systemInstance = new System();
        }
        return System.systemInstance;
    }
    public static System getInstance() {
        if (System.systemInstance != null) {
            return System.systemInstance;
        } else {
            return null;
        }
    }
    public static Disk getWorkingDisk() {
        return systemInstance.disks.get(systemInstance.selectedDiskIndex);
    }

    // WIP
    public static Directory getWorkingDirectory() {
        final Disk workingDisk = getWorkingDisk();
        return workingDisk.directories.get(-1);
    }

    public static void newDisk(int diskSize) {
        systemInstance.disks.add(new Disk(diskSize));
    }

    public static void newDirectory(String dirName) {
        getWorkingDirectory().directories.add(new Directory(dirName));
    }

    public static void newDocument(String docName, String doctype, String docContent) {
        getWorkingDirectory().files.add(new Document(docName, doctype, docContent));
    }

    public void deleteFile(String fileName) {
        List<Document> files = getWorkingDirectory().files;
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).name.equals(fileName)) {
                files.remove(i);
                return;
            }
        }
//        throw whatever exception idk might do this later
    }

    public void renameFile(String oldFileName, String newFileName) {
        List<Document> files = getWorkingDirectory().files;
        for (Document file : files) {
            if (file.name.equals(oldFileName)) {
                file.name = newFileName;
                return;
            }
        }
//        throw whatever exception idk might do this later
    }
}

//main {
//    Hero.heroA
//}