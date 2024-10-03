import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class System {
    private static System instance; // lazy init singleton
    private Disk disk;
    public String workingDirectoryAbsolutePath; // expected value example: /dir1/dir2
    private Directory workingDirectory;
    private boolean isRunning;

    public static final int defaultDirectorySize = 40;
    public static final int defaultDocumentSize = 40;

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

    // maybe change the function name later
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

        for (Directory directory : workingDirectory.directories) {
            if (directory.name.equals(targetedDirectoryName)) {
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

    // WIP
    public static Directory getWorkingDirectory() {
        final Disk workingDisk = getWorkingDisk();
        if (workingDisk == null) {
            throw new IllegalArgumentException("Unexpected behavior");
        }
        return instance.workingDirectory;
    }

    public static void newDisk(int diskSize) {
        instance.disk = new Disk(diskSize);
        instance.workingDirectory = instance.disk.rootDirectory;
    }

    public static void newDirectory(String dirName) {
        Directory newDirectory = new Directory(dirName);
        getWorkingDisk().handleSizeChange(newDirectory.size());
        getWorkingDirectory().directories.add(newDirectory);
    }

    public static void newDocument(String docName, String docType, String docContent) {
        Document newDocument = new Document(docName, docType, docContent);
        getWorkingDisk().handleSizeChange(newDocument.size());
        getWorkingDirectory().documents.add(newDocument);
    }

    // only delete doc now, later change
    public static void deleteFile(String fileName) {
        List<Document> documents = getWorkingDirectory().documents;
        for (int i = 0; i < documents.size(); i++) {
            if (documents.get(i).name.equals(fileName)) {
                int sizeChange = documents.get(i).size();
                getWorkingDisk().handleSizeChange(- sizeChange);
                documents.remove(i);
                return;
            }
        }
        List<Directory> directories = getWorkingDirectory().directories;
        for (int i = 0; i < directories.size(); i++) {
            if (directories.get(i).name.equals(fileName)) {
                int sizeChange = directories.get(i).size();
                getWorkingDisk().handleSizeChange(- sizeChange);
                directories.remove(i);
                return;
            }
        }
        throw new IllegalArgumentException("Targeted file not found!");
    }

    public static void renameFile(String oldFileName, String newFileName) {
        List<Document> documents = getWorkingDirectory().documents;
        for (Document document : documents) {
            if (document.name.equals(oldFileName)) {
                document.name = newFileName;
                return;
            }
        }
        List<Directory> directories = getWorkingDirectory().directories;
        for (Directory directory : directories) {
            if (directory.name.equals(oldFileName)) {
                directory.name = newFileName;
                return;
            }
        }
        throw new IllegalArgumentException("Targeted file not found!");
    }

    public static void changeDirectory(String targetPath) {
        targetPath = parsePath(targetPath);
        if (targetPath.isEmpty()) {
            return;
        }
        // Please read parsePath() to understand why this works
        Directory newDirectory = instance.workingDirectory;
        String newDirectoryAbsolutePath = instance.workingDirectoryAbsolutePath;
        while (targetPath.startsWith("..")) {
            if (newDirectoryAbsolutePath.isEmpty()) {
                throw new IllegalArgumentException("Root directory is already reached!");
            }
            int absolutePathDelimiterIndex = newDirectoryAbsolutePath.lastIndexOf(":");
            if (absolutePathDelimiterIndex != -1) {
                newDirectoryAbsolutePath = newDirectoryAbsolutePath.substring(0, absolutePathDelimiterIndex);
            } else {
                newDirectoryAbsolutePath = "";
            }
            newDirectory = traverseDirectoriesRecursive(newDirectoryAbsolutePath, instance.disk.rootDirectory);
            int targetPathDelimiterIndex = targetPath.indexOf(":");
            if (targetPathDelimiterIndex != -1) {
                targetPath = targetPath.substring(targetPathDelimiterIndex + 1);
            } else {
                targetPath = "";
            }
        }
        newDirectory = traverseDirectoriesRecursive(targetPath, newDirectory);
        if (!targetPath.isEmpty()) {
            if (!newDirectoryAbsolutePath.isEmpty()) {
                newDirectoryAbsolutePath += ":" + targetPath;
            } else {
                newDirectoryAbsolutePath += targetPath;

            }
        }
        if (newDirectory != null) {
            instance.workingDirectory = newDirectory;
            instance.workingDirectoryAbsolutePath = newDirectoryAbsolutePath;
        } else {
            throw new IllegalArgumentException("Targeted directory cannot be found!");
        }
    }

    private static String parsePath(String targetPath) {
        if (!targetPath.startsWith("$:")) {
            throw new IllegalArgumentException("Invalid path!");
        }
        // ** add a regular expression to check if the path is in correct format here
        targetPath = targetPath.substring(2);

        // optimization for parent directory navigation
        List<String> optimizedFileNames = new ArrayList<String>();
        StringBuilder optimizedPathBuilder = new StringBuilder();
        while (!targetPath.isEmpty()) {
            int delimiterIndex = targetPath.indexOf(':');
            String directoryName;
            if (delimiterIndex != -1) {
                directoryName = targetPath.substring(0, delimiterIndex);
            } else {
                directoryName = targetPath;
                targetPath = "";
            }
            if (optimizedFileNames.isEmpty()) {
                optimizedFileNames.add(directoryName);
            } else if (directoryName.equals("..") && !optimizedFileNames.getLast().equals("..")) {
                optimizedFileNames.removeLast();
            } else {
                optimizedFileNames.add(directoryName);
            }
            targetPath = targetPath.substring(delimiterIndex + 1);
        }
        for (int i = 0; i < optimizedFileNames.size(); i += 1) {
            optimizedPathBuilder.append(optimizedFileNames.get(i));
            if (i != optimizedFileNames.size() - 1) {
                optimizedPathBuilder.append(":");
            }
        }
        return optimizedPathBuilder.isEmpty() ? "" : optimizedPathBuilder.toString();
    }

    public static void listFiles() {
        Directory workingDirectory = getWorkingDirectory();
        java.lang.System.out.println("name  type  size");
        int fileCount = listFiles(workingDirectory, "", false);
        java.lang.System.out.println("total number of files: " + fileCount);
        java.lang.System.out.println("total size of files: " + workingDirectory.size());
    }

    public static void recursiveListFiles() {
        Directory workingDirectory = getWorkingDirectory();
        java.lang.System.out.println("name  type  size");
        int fileCount = listFiles(workingDirectory, "", true);
        java.lang.System.out.println("total number of files: " + fileCount);
        java.lang.System.out.println("total size of files: " + workingDirectory.size());
    }

    public static int listFiles(Directory currentDirectory, String indentation, boolean isRecursive) {
        int fileCount = currentDirectory.documents.size() + currentDirectory.directories.size();
        for (Document document : currentDirectory.documents) {
            java.lang.System.out.print(indentation);
            java.lang.System.out.print(document.name + "  ");
            java.lang.System.out.print(document.type + "  ");
            int docSize = document.size();
            java.lang.System.out.print(docSize + "\n");
        }
        for (Directory directory : currentDirectory.directories) {
            java.lang.System.out.print(indentation);
            java.lang.System.out.print(directory.name + "  ");
            java.lang.System.out.print("directory  ");
            int dirSize = directory.size();
            java.lang.System.out.print(dirSize + "\n");
            if (isRecursive) {
                fileCount += listFiles(directory, indentation + "  ", isRecursive);
            }
        }
        return fileCount;
    }

    public static void terminate() {
        instance.isRunning = false;
    }

    public static boolean isRunning() {
        return instance.isRunning;
    }

//    public static List<> listFiles() {
//
//    }
}

//main {
//    Hero.heroA
//}