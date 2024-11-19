package hk.edu.polyu.comp.comp2021.cvfs.model;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.Command;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.StatefulCommand;
import hk.edu.polyu.comp.comp2021.cvfs.exception.SystemTerminatedException;
import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.*;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.disk.Disk;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Document;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class System {
    private static System instance; // lazy init singleton
    private Disk disk;
    private String workingDirectoryAbsolutePath; // expected value example: /dir1/dir2
    private Directory workingDirectory;

    private static final Stack<StatefulCommand> undoStack = new Stack<>();
    private static final Stack<StatefulCommand> redoStack = new Stack<>();

    private System() {
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

//    public static Directory traverseDirectoriesRecursive(String targetPath, Directory workingDirectory) {
//        // note:
//        // 1. this function doesn't support parent directory navigation
//        // so this function can be applied in different situations
//        // 2. this function defaults to relative pathing (to the workingDirectory argument)
//
//        if (targetPath.isEmpty()) {
//            return workingDirectory;
//        }
//        if (targetPath.endsWith(":")) {
//            // we dont mind trailing :, just like in shell we don't mind
//            // cd ./randomFolder/
//            targetPath = targetPath.substring(0, targetPath.length() - 1);
//        }
//        int delimiterIndex = targetPath.indexOf(':');
//        String targetedDirectoryName;
//        if (delimiterIndex != -1) {
//            targetedDirectoryName = targetPath.substring(0, delimiterIndex);
//        } else {
//            targetedDirectoryName = targetPath;
//        }
//
//        for (Directory directory : workingDirectory.getDirectories()) {
//            if (directory.getName().equals(targetedDirectoryName)) {
//                if (delimiterIndex != -1) {
//                    return traverseDirectoriesRecursive(targetPath.substring(delimiterIndex + 1), directory);
//                }
//                return directory;
//            }
//        }
//        return null; // no such directory found
//    }
    public static String getWorkingDirectoryPath() {
        return instance.workingDirectoryAbsolutePath;
    }

    public static void setWorkingDisk(Disk disk) {
        instance.disk = disk;
    }

    public static Disk getWorkingDisk() {
        return instance.disk;
    }

    public static void setWorkingDirectory(Directory directory) {
        instance.workingDirectory = directory;
    }

    public static void setWorkingDirectoryPath(String path) {
        instance.workingDirectoryAbsolutePath = path;
    }

    public static Directory getWorkingDirectory() {
        if (getWorkingDisk() == null) throw new IllegalArgumentException("Please first initialize the working disk");
        final Disk workingDisk = getWorkingDisk();
        return instance.workingDirectory;
    }

    public static void run(Command command) {
        if (command instanceof StatefulCommand) {
            undoStack.push((StatefulCommand) command);
            command.run();
            redoStack.clear();
        } else {
            command.run();
        }
    }

//    public static void newDisk(int diskSize) {
//        setWorkingDisk(new Disk(diskSize));
//        instance.workingDirectory = instance.disk.getRootDirectory();
//    }

//    public static void newDirectory(String dirName) {
//        if (getWorkingDisk() == null) throw new IllegalArgumentException("Please first initialize the working disk");
//        getWorkingDirectory().createDir(dirName);
//        getWorkingDisk().addUniqueFileName(dirName);
//    }

//    public static void newDocument(String docName, String docType, String docContent) {
//        if (getWorkingDisk() == null) throw new IllegalArgumentException("Please first initialize the working disk");
//        getWorkingDirectory().createDoc(docName, docType, docContent);
//        getWorkingDisk().addUniqueFileName(docName);
//    }

//    public static void deleteFile(String fileName) {
//        if (getWorkingDisk() == null) throw new IllegalArgumentException("Please first initialize the working disk");
//        getWorkingDirectory().delete(fileName);
//        getWorkingDisk().deleteUniqueFileName(fileName);
//    }
//
//    public static void renameFile(String oldFileName, String newFileName) {
//        if (getWorkingDisk() == null) throw new IllegalArgumentException("Please first initialize the working disk");
//        getWorkingDirectory().rename(oldFileName, newFileName);
//        Disk workingDisk = getWorkingDisk();
//        workingDisk.deleteUniqueFileName(oldFileName);
//        workingDisk.addUniqueFileName(newFileName);
//    }

//    public static void newSimpleCri(String criName, String attrName, String op, String val) {
//        Criteria.add(new SimpleCriterion(criName, attrName, op, val));
//    }

//    public static void newNegation(String criName1, String criName2) {
//        Criterion orig = Criteria.get(criName2);
//        if (orig == null) throw new IllegalArgumentException("Target criteria not found");
//        Criteria.add(new NegatedCriterion(criName1, orig));
//    }

//    public static void newBinaryCri(String criName1, String criName3, String logicOp, String criName4) {
//        Criterion criterion1 = Criteria.get(criName3);
//        Criterion criterion2 = Criteria.get(criName4);
//        if (criterion1 == null || criterion2 == null) throw new IllegalArgumentException("Target criteria not found");
//        Criteria.add(new BinaryCriterion(criName1, criterion1, logicOp, criterion2));
//    }

//    public static void printAllCriteria() {
//        Criterion[] criteria = Criteria.getAll();
//        for (Criterion criterion : criteria) {
//            java.lang.System.out.print(criterion.getName() + ": ");
//            java.lang.System.out.print(criterion);
//            java.lang.System.out.println();
//        }
//    }

//    public static void changeDirectory(String targetDir) {
//        if (getWorkingDisk() == null) throw new IllegalArgumentException("Please first initialize the working disk");
//        if (targetDir.isEmpty()) {
//            throw new IllegalArgumentException("target directory name cannot be empty");
//        }
//        String newDirectoryAbsolutePath = instance.workingDirectoryAbsolutePath;
//        Directory newDirectory = null;
//        if (targetDir.equals("..")) {
//            if (newDirectoryAbsolutePath.isEmpty()) {
//                throw new IllegalArgumentException("Root directory is already reached!");
//            }
//            int absolutePathDelimiterIndex = newDirectoryAbsolutePath.lastIndexOf(":");
//            if (absolutePathDelimiterIndex != -1) {
//                newDirectoryAbsolutePath = newDirectoryAbsolutePath.substring(0, absolutePathDelimiterIndex);
//            } else {
//                newDirectoryAbsolutePath = "";
//            }
//            newDirectory = traverseDirectoriesRecursive(newDirectoryAbsolutePath, getWorkingDisk().getRootDirectory());
//        }
//        else {
//            if (newDirectoryAbsolutePath.isEmpty()) {
//                newDirectoryAbsolutePath = targetDir;
//            } else {
//                newDirectoryAbsolutePath = newDirectoryAbsolutePath + ":" + targetDir;
//            }
//            newDirectory = traverseDirectoriesRecursive(targetDir, getWorkingDirectory());
//        }
//        if (newDirectory != null) {
//            instance.workingDirectory = newDirectory;
//            instance.workingDirectoryAbsolutePath = newDirectoryAbsolutePath;
//        } else {
//            throw new IllegalArgumentException("Targeted directory cannot be found!");
//        }
//    }

//    public static void listFiles() {
//        listFilesHelper(false);
//    }

//    public static void rListFiles() {
//        listFilesHelper(true);
//    }

//    public static void search(String criName) {
//        Criterion criterion = Criteria.get(criName);
//        searchFilesHelper(false, criterion);
//    }
//
//    public static void rSearch(String criName) {
//        Criterion criterion = Criteria.get(criName);
//        searchFilesHelper(true, criterion);
//    }

//    private static void listFilesHelper(boolean isRecursive) {
//        if (getWorkingDisk() == null) throw new IllegalArgumentException("Please first initialize the working disk");
//        java.lang.System.out.println("name  type  size");
//
//        int[] fileCountAndSize = listFilesHelper(getWorkingDirectory(), "", isRecursive);
//
//        java.lang.System.out.println("total number of files: " + fileCountAndSize[0]);
//        java.lang.System.out.println("total size of files: " + fileCountAndSize[1]);
//    }

//    private static void searchFilesHelper(boolean isRecursive, Criterion criterion) {
//        List<String> output = new ArrayList<>();
//        int[] fileCountAndSize = searchFilesHelper(output, getWorkingDirectory(), "", isRecursive, criterion);
//        for (String line : output) {
//            java.lang.System.out.println(line);
//        }
//        java.lang.System.out.println("number of matched files: " + fileCountAndSize[0]);
//        java.lang.System.out.println("total size of matched files: " + fileCountAndSize[1]);
//    }
//
//    private static int[] searchFilesHelper(List<String> output, Directory currentDirectory, String indentation, boolean isRecursive, Criterion criterion) {
//        int fileCount = 0;
//        int fileSize = 0;
//        for (File file : currentDirectory.getFiles()) {
//            int s = file.size();
//
//            if (file instanceof Document) {
//                if (criterion == null || criterion.check(file)) {
//                    output.add(indentation + file.toDisplayString());
//                    fileCount++;
//                    fileSize += s;
//                }
//            } else {
//                int[] fileCountAndSize = new int[] {0, 0};
//                if (criterion == null || criterion.check(file)) {
//                    output.add(indentation + file.toDisplayString());
//                    if (isRecursive) {
//                        fileCountAndSize = searchFilesHelper(output, (Directory) file, indentation + "  ", isRecursive, null);
//                    }
//                    fileCount++;
//                    // note: fileSize will be added later outside if block
//                } else if (isRecursive) {
//                    output.add(indentation + file.toDisplayString());
//                    fileCountAndSize = searchFilesHelper(output, (Directory) file, indentation + "  ", isRecursive, criterion);
//                    if (fileCountAndSize[0] == 0) {
//                        output.remove(output.size() - 1);
//                    }
//                }
//                fileCount += fileCountAndSize[0];
//                fileSize += fileCountAndSize[1];
//            }
//        }
//        return new int[] {fileCount, fileSize};
//    }

//    private static int[] listFilesHelper(Directory currentDirectory, String indentation, boolean isRecursive) {
//        int fileCount = 0;
//        int fileSize = 0;
//        for (File file : currentDirectory.getFiles()) {
//            int s = file.size();
//            java.lang.System.out.println(indentation + file);
//            if ((file instanceof Directory) && isRecursive) {
//                int[] fileCountAndSize = listFilesHelper((Directory) file, indentation + "  ", isRecursive);
//                fileCount += fileCountAndSize[0];
////                s = fileCountAndSize[1];
//            }
//            fileSize += s;
//            fileCount++;
//        }
//        return new int[] {fileCount, fileSize};
//    }

    public static void undo() {
        if (undoStack.isEmpty()) throw new IllegalArgumentException("No undo actions available!");
        StatefulCommand x = undoStack.pop();
        x.undo();
        redoStack.push(x);
    }

    public static void redo() {
        if (redoStack.isEmpty()) throw new IllegalArgumentException("No redo actions available!");
        StatefulCommand x = redoStack.pop();
        x.redo();
        undoStack.push(x);
    }

    public static void save(String path) {
        getWorkingDisk().getRootDirectory().save(path);
    }

    public static void terminate() throws SystemTerminatedException {
        throw new SystemTerminatedException("System Terminated.");
    }

}