package hk.edu.polyu.comp.comp2021.cvfs.model;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.Command;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.StatefulCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.exception.SystemTerminatedException;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.disk.Disk;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Document;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.File;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.JavaIOFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        getWorkingDisk().save(path);
    }

    public static void load(String path) {
        JavaIOFile directory = new JavaIOFile(path);
        if (!directory.exists() || !directory.isDirectory()) throw new IllegalArgumentException("target virtual disk not found!");
        JavaIOFile[] files = directory.listFiles();
        setWorkingDisk(new Disk());
        Directory rootDirectory = getWorkingDisk().getRootDirectory();
        recursiveLoadHelper(rootDirectory, files, path);
        setWorkingDirectory(rootDirectory);

    }

    private static void recursiveLoadHelper(Directory directory, JavaIOFile[] files, String path) {
        if (files == null) return; //
        for (JavaIOFile file : files) {
            File current = null;
            String fileBaseName = file.getName();
            String fileName = fileBaseName;
            if (file.isFile()) {
                fileName = fileBaseName.substring(0, fileBaseName.lastIndexOf("."));
                String fileType = fileBaseName.substring(fileName.length() + 1);
                try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
                    StringBuilder content = new StringBuilder();
                    String line;
                    // Read the file line by line
                    while ((line = reader.readLine()) != null) {
                        content.append(line);
                    }
                    if (!Document.isSupportedType(fileType)) {
                        java.lang.System.out.println("Note: " + fileBaseName + " is of unsupported file type, disk load operation will skip this file.");
                        continue;
                    }
                        current = new Document(fileName, fileType, content.toString());
                } catch (IOException e) {
                    throw new IllegalArgumentException("error while reading document: " + e.toString());
                }
            } else if (file.isDirectory()) {
                current = new Directory(fileName);
                recursiveLoadHelper((Directory) current, file.listFiles(), path + fileBaseName + "/");
            } else {
                throw new IllegalArgumentException("no permission to read file " + fileBaseName);
            }
            directory.add(current);
        }
    }

    public static void terminate() throws SystemTerminatedException {
        throw new SystemTerminatedException("System Terminated.");
    }
}