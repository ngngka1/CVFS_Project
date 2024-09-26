import java.util.ArrayList;
import java.util.List;

public class System {
    private static System systemInstance; // private so singleton instance doesnt get directly accessed
    List<Disk> disks;
    int selectedDiskIndex;
    String workingDirectoryAbsolutePath; // expected value example: /dir1/dir2
    Directory workingDirectory;
    boolean isRunning;

    static final int DEFAULTDIRECTORYSIZE = 40;
    static final int DEFAULTDOCUMENTSIZE = 40;

    private System() {
        isRunning = true;
        selectedDiskIndex = 0;
        disks = new ArrayList<>();
        workingDirectoryAbsolutePath = "/";
        workingDirectory = null;
    }
    private static void createInstance() {
        if (System.systemInstance == null) {
            System.systemInstance = new System();
        }
    }
    public static System getInstance() {
        if (System.systemInstance == null) {
            createInstance();
        }
        return System.systemInstance;
    }

    // maybe change the function name later
    public static Directory traverseDirectoriesRecursive(String targetedPath, Directory workingDirectory) {
        // expected cases for targetedPath:
        // "/dir1": invalid input (this function cant handle absolute path)
        // "./dir1": valid input (relative path)
        // "dir1": valid input (relative path)
        if (targetedPath.isEmpty()) {
            return workingDirectory;
        }
        if (targetedPath.startsWith("/")) {

            return null;
        }
        if (!targetedPath.endsWith("/")) {
            targetedPath += "/";
        }
        int delimiterIndex = targetedPath.indexOf('/');
        String targetedDirectoryName = targetedPath.substring(0, delimiterIndex);
        for (Directory directory : workingDirectory.directories) {
            if (directory.name.equals(targetedDirectoryName)) {
                return traverseDirectoriesRecursive(targetedPath.substring(delimiterIndex + 1), directory);
            }
        }
        return null;
    }

    public static Disk getWorkingDisk() {
        if (!systemInstance.disks.isEmpty()) {
            return systemInstance.disks.get(systemInstance.selectedDiskIndex);
        } else {
            return null;
        }
    }

    // WIP
    public static Directory getWorkingDirectory() {
        final Disk workingDisk = getWorkingDisk();
        if (workingDisk == null) {
            throw new IllegalArgumentException("Unexpected behavior");
        }

        if (systemInstance.workingDirectoryAbsolutePath.equals("/")) {
            return workingDisk.rootDirectory;
        }
        return traverseDirectoriesRecursive(systemInstance.workingDirectoryAbsolutePath, workingDisk.rootDirectory);
    }

    public static void newDisk(int diskSize) {
        systemInstance.disks.add(new Disk(diskSize));
    }

    public static void newDirectory(String dirName) {
        getWorkingDirectory().directories.add(new Directory(dirName));
        handleDiskSizeChange(DEFAULTDIRECTORYSIZE);
    }

    public static void newDocument(String docName, String docType, String docContent) {
        getWorkingDirectory().files.add(new Document(docName, docType, docContent));
        handleDiskSizeChange(DEFAULTDOCUMENTSIZE);
    }

    public static void deleteFile(String fileName) {
        List<Document> files = getWorkingDirectory().files;
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).name.equals(fileName)) {
                int sizeChange = files.get(i).size();
                handleDiskSizeChange(sizeChange);
                files.remove(i);
                return;
            }
        }
//        throw whatever exception idk might do this later
    }

    public static void renameFile(String oldFileName, String newFileName) {
        List<Document> files = getWorkingDirectory().files;
        for (Document file : files) {
            if (file.name.equals(oldFileName)) {
                file.name = newFileName;
                return;
            }
        }
//        throw whatever exception idk might do this later
    }

    public static void changeDirectory(String newDirectoryName) {
        while (newDirectoryName.startsWith("..")) {
            navigateParentDirectory();
            int delimiterIndex = newDirectoryName.indexOf('/');
            newDirectoryName = newDirectoryName.substring(delimiterIndex + 1);
        }
        Directory newDirectory = traverseDirectoriesRecursive(newDirectoryName, systemInstance.workingDirectory);
        if (newDirectory != null) {
            systemInstance.workingDirectory = newDirectory;
            systemInstance.workingDirectoryAbsolutePath += "/" + newDirectoryName;
        } else {
            java.lang.System.out.println("Targeted folder cannot be found!");
        }
    }

    private static void navigateParentDirectory() {
        if (systemInstance.workingDirectoryAbsolutePath.equals("/")) {
            java.lang.System.out.println("Base directory is already reached!");
            return;
        }
        int delimiterIndex = systemInstance.workingDirectoryAbsolutePath.lastIndexOf('/');
        systemInstance.workingDirectoryAbsolutePath = systemInstance.workingDirectoryAbsolutePath.substring(0, delimiterIndex);
    }

    private static void handleDiskSizeChange(int sizeChange) {
        final Disk workingDisk = getWorkingDisk();
        if (workingDisk == null) {
            throw new IllegalArgumentException("Unexpected behavior");
        }
        if (workingDisk.currentSize + sizeChange >= workingDisk.maxSize) {
            java.lang.System.out.println("Disk size exceeded!");
        } else {
            workingDisk.currentSize += sizeChange;
        }
    }

    public static void terminate() {
        systemInstance.isRunning = false;
    }

//    public static List<> listFiles() {
//
//    }
}

//main {
//    Hero.heroA
//}