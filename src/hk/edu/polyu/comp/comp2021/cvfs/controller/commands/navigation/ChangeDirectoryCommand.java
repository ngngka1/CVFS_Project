package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.navigation;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.StatefulCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;

public class ChangeDirectoryCommand extends StatefulCommand {
    Directory original;
    Directory target;
    String originalPath;
    String targetPath;
    public ChangeDirectoryCommand(String targetDir) {
        if (System.getWorkingDisk() == null) throw new IllegalArgumentException("Please first initialize the working disk");
        if (targetDir.isEmpty()) {
            throw new IllegalArgumentException("target directory name cannot be empty");
        }
        originalPath = System.getWorkingDirectoryPath();
        if (targetDir.equals("..")) {
            if (originalPath.isEmpty()) {
                throw new IllegalArgumentException("Root directory is already reached!");
            }
            targetPath = originalPath.contains(":") ? originalPath.substring(0, originalPath.lastIndexOf(":")) : "";
            traverseDirectoriesRecursive(targetPath, System.getWorkingDisk().getRootDirectory());
        }
        else {
            if (originalPath.isEmpty()) {
                targetPath = targetDir;
            } else {
                targetPath = originalPath + ":" + targetDir;
            }
            traverseDirectoriesRecursive(targetDir, System.getWorkingDirectory());
        }
    }

    private void traverseDirectoriesRecursive(String targetPath, Directory workingDirectory) {
        // note:
        // 1. this function doesn't support parent directory navigation
        // so this function can be applied in different situations
        // 2. this function defaults to relative pathing (to the workingDirectory argument)

        if (targetPath.isEmpty()) {
            target = workingDirectory;
            return;
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
                    traverseDirectoriesRecursive(targetPath.substring(delimiterIndex + 1), directory);
                }
                else {
                    original = workingDirectory;
                    target = directory;
                }
            }
        }
        // no such directory found
    }

    @Override
    public void run() {
        if (target == null) {
            throw new IllegalArgumentException("Target directory cannot be found!");
        }
        System.setWorkingDirectory(target);
        System.setWorkingDirectoryPath(targetPath);
    }

    @Override
    public void undo() {
        System.setWorkingDirectory(original);
        System.setWorkingDirectoryPath(originalPath);
    }

    @Override
    public void redo() {
        run();
    }
}
