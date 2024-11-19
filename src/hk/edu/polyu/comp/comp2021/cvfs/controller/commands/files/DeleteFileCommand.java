package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.StatefulCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.File;

public class DeleteFileCommand extends StatefulCommand {
    private final Directory workingDirectory;
    private final File deletedFile;

    public DeleteFileCommand(String fileName) {
        workingDirectory = System.getWorkingDirectory();
        for (File file : workingDirectory.getFiles()) {
            if (file.getName().equals(fileName)) {
                deletedFile = file;
                return;
            }
        }
        throw new IllegalArgumentException("target file not found!");
    }

    @Override
    public void run() {
        workingDirectory.delete(deletedFile.getName());
    }

    @Override
    public void undo() {
        workingDirectory.add(deletedFile);
    }
    @Override
    public void redo() {
        run();
    }
}
