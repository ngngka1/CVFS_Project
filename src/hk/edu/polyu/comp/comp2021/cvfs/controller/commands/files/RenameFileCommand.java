package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.StatefulCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;

public class RenameFileCommand extends StatefulCommand {
    private final Directory workingDirectory;
    private final String oldFileName;
    private final String newFileName;

    public RenameFileCommand(String oldFileName, String newFileName) {
        workingDirectory = System.getWorkingDirectory();
        this.oldFileName = oldFileName;
        this.newFileName = newFileName;
    }

    @Override
    public void run() {
        workingDirectory.rename(oldFileName, newFileName);
    }

    @Override
    public void undo() {
        workingDirectory.rename(newFileName, oldFileName);
    }
    @Override
    public void redo() {
        run();
    }
}
