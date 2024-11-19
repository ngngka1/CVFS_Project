package hk.edu.polyu.comp.comp2021.cvfs.model;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.Command;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.StatefulCommand;
import hk.edu.polyu.comp.comp2021.cvfs.exception.SystemTerminatedException;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.disk.Disk;

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
        getWorkingDisk().getRootDirectory().save(path);
    }

    public static void terminate() throws SystemTerminatedException {
        throw new SystemTerminatedException("System Terminated.");
    }
}