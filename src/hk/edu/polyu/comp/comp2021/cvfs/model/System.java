package hk.edu.polyu.comp.comp2021.cvfs.model;

import hk.edu.polyu.comp.comp2021.cvfs.exception.SystemTerminatedException;

public class System {
    private static System instance; // lazy init singleton
    private Disk disk;
    public String workingDirectoryAbsolutePath; // expected value example: /dir1/dir2
    private Directory workingDirectory;
    private boolean isRunning;

    private System() {
        isRunning = true;
        disk = null;
        workingDirectoryAbsolutePath = "";
        workingDirectory = null;
    }

    public static System getInstance() {
        if (System.instance == null) {
            System.instance = new System();
        }
        return System.instance;
    }


    public static Directory traverseDirectoriesRecursive(String targetPath, Directory workingDirectory) {
        // note:
        // 1. this function doesn't support parent directory navigation
        // so this function can be applied in different situations
        // 2. this function defaults to relative pathing (to the workingDirectory argument)

        if (targetPath.isEmpty()) {
            return workingDirectory;
        }
        if (targetPath.endsWith(":")) {
            // we dont mind trailing :, just like in shell we don't mind
            // cd ./randomFolder/
            targetPath = targetPath.substring(0, targetPath.length() - 1);
        }
        int delimiterIndex = targetPath.indexOf(':');
        String targetedDirectoryName;
        if (delimiterIndex != -1) {
            targetedDirectoryName = targetPath.substring(0, delimiterIndex);
        } else {
            targetedDirectoryName = targetPath;
        }

        for (Directory directory : workingDirectory.getDirectories()) {
            if (directory.getName().equals(targetedDirectoryName)) {
                if (delimiterIndex != -1) {
                    return traverseDirectoriesRecursive(targetPath.substring(delimiterIndex + 1), directory);
                }
                return directory;
            }
        }
        return null; // no such directory found
    }

    public static Disk getWorkingDisk() {
        return instance.disk;
    }

    public static Directory getWorkingDirectory() {
        if (getWorkingDisk() == null) throw new IllegalArgumentException("Please first initialize the working disk");
        final Disk workingDisk = getWorkingDisk();
        return instance.workingDirectory;
    }

    public static void newDisk(int diskSize) {
        instance.disk = new Disk(diskSize);
        instance.workingDirectory = instance.disk.getRootDirectory();
    }

    public static void newDirectory(String dirName) {
        if (getWorkingDisk() == null) throw new IllegalArgumentException("Please first initialize the working disk");
        getWorkingDirectory().createDir(dirName);
    }

    public static void newDocument(String docName, String docType, String docContent) {
        if (getWorkingDisk() == null) throw new IllegalArgumentException("Please first initialize the working disk");
        getWorkingDirectory().createDoc(docName, docType, docContent);
    }

    public static void deleteFile(String fileName) {
        if (getWorkingDisk() == null) throw new IllegalArgumentException("Please first initialize the working disk");
        getWorkingDirectory().delete(fileName);
    }

    public static void renameFile(String oldFileName, String newFileName) {
        if (getWorkingDisk() == null) throw new IllegalArgumentException("Please first initialize the working disk");
        getWorkingDirectory().rename(oldFileName, newFileName);
    }

    public static void changeDirectory(String targetDir) {
        if (getWorkingDisk() == null) throw new IllegalArgumentException("Please first initialize the working disk");
        if (targetDir.isEmpty()) {
            throw new IllegalArgumentException("target hk.edu.polyu.comp.comp2021.cvfs.model.Directory name cannot be empty");
        }
        String newDirectoryAbsolutePath = instance.workingDirectoryAbsolutePath;
        Directory newDirectory = null;
        if (targetDir.equals("..")) {
            if (newDirectoryAbsolutePath.isEmpty()) {
                throw new IllegalArgumentException("Root directory is already reached!");
            }
            int absolutePathDelimiterIndex = newDirectoryAbsolutePath.lastIndexOf(":");
            if (absolutePathDelimiterIndex != -1) {
                newDirectoryAbsolutePath = newDirectoryAbsolutePath.substring(0, absolutePathDelimiterIndex);
            } else {
                newDirectoryAbsolutePath = "";
            }
            newDirectory = traverseDirectoriesRecursive(newDirectoryAbsolutePath, getWorkingDisk().getRootDirectory());
        }
        else {
            if (newDirectoryAbsolutePath.isEmpty()) {
                newDirectoryAbsolutePath = targetDir;
            } else {
                newDirectoryAbsolutePath = newDirectoryAbsolutePath + ":" + targetDir;
            }
            newDirectory = traverseDirectoriesRecursive(targetDir, getWorkingDirectory());
        }
        if (newDirectory != null) {
            instance.workingDirectory = newDirectory;
            instance.workingDirectoryAbsolutePath = newDirectoryAbsolutePath;
        } else {
            throw new IllegalArgumentException("Targeted directory cannot be found!");
        }
    }

//    public static void changeDirectoryDeprecated(String targetPath) {
//        // Note: targetPath is optimized in hk.edu.polyu.comp.comp2021.cvfs.view.CLI.parsePath();
//        if (targetPath.isEmpty()) {
//            return;
//        }
//        hk.edu.polyu.comp.comp2021.cvfs.model.Directory newDirectory = instance.workingDirectory;
//        String newDirectoryAbsolutePath = instance.workingDirectoryAbsolutePath;
//        while (targetPath.startsWith("..")) {
//            if (newDirectoryAbsolutePath.isEmpty()) {
//                throw new IllegalArgumentException("Root directory is already reached!");
//            }
//            int absolutePathDelimiterIndex = newDirectoryAbsolutePath.lastIndexOf(":");
//            if (absolutePathDelimiterIndex != -1) {
//                newDirectoryAbsolutePath = newDirectoryAbsolutePath.substring(0, absolutePathDelimiterIndex);
//            } else {
//                newDirectoryAbsolutePath = "";
//            }
//            newDirectory = traverseDirectoriesRecursive(newDirectoryAbsolutePath, instance.disk.rootDirectory);
//            int targetPathDelimiterIndex = targetPath.indexOf(":");
//            if (targetPathDelimiterIndex != -1) {
//                targetPath = targetPath.substring(targetPathDelimiterIndex + 1);
//            } else {
//                targetPath = "";
//            }
//        }
//        newDirectory = traverseDirectoriesRecursive(targetPath, newDirectory);
//        if (!targetPath.isEmpty()) {
//            if (!newDirectoryAbsolutePath.isEmpty()) {
//                newDirectoryAbsolutePath += ":" + targetPath;
//            } else {
//                newDirectoryAbsolutePath += targetPath;
//
//            }
//        }
//        if (newDirectory != null) {
//            instance.workingDirectory = newDirectory;
//            instance.workingDirectoryAbsolutePath = newDirectoryAbsolutePath;
//        } else {
//            throw new IllegalArgumentException("Targeted directory cannot be found!");
//        }
//    }

    public static void listFiles(boolean isRecursive) {
        if (getWorkingDisk() == null) throw new IllegalArgumentException("Please first initialize the working disk");
        java.lang.System.out.println("name  type  size");
        int[] fileCountAndSize = listFiles(getWorkingDirectory(), "", isRecursive);

        java.lang.System.out.println("total number of files: " + fileCountAndSize[0]);
        java.lang.System.out.println("total size of files: " + fileCountAndSize[1]);
    }

    private static void printDirectory(String name, int size) {
        java.lang.System.out.print(name + "           ");
        java.lang.System.out.print(size + "\n");
    }

    private static void printDocument(String name, String type, int size) {
        java.lang.System.out.print(name + "  ");
        java.lang.System.out.print(type + "  ");
        java.lang.System.out.print(size + "\n");
    }

    private static int[] listFiles(Directory currentDirectory, String indentation, boolean isRecursive) {
        int fileCount = 0;
        int fileSize = 0;
        for (File file : currentDirectory.getFiles()) {
            String n = file.getName();
            String t = file.getType();
            int s = file.size();
            java.lang.System.out.print(indentation);
            if (file instanceof Document) {
                printDocument(n, t, s);
            } else {
                printDirectory(n, s);
                if (isRecursive) {
                    fileCount += listFiles((Directory) file, indentation + "  ", isRecursive)[0];
                }
            }
            fileSize += s;
            fileCount++;
        }
        return new int[] {fileCount, fileSize};
    }

    public static void terminate() throws SystemTerminatedException {
        instance.isRunning = false;
        throw new SystemTerminatedException("System Terminated.");
    }

    public static boolean isRunning() {
        return instance.isRunning;
    }
}