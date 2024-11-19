package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.StatefulCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.File;

public class NewFileCommand extends StatefulCommand {
    private File createdFile;
    private final Directory workingDirectory = System.getWorkingDirectory();
    protected void setCreatedFile(File file) {
        createdFile = file;
    }

    @Override
    public void run() {
        workingDirectory.add(createdFile);
    }

    @Override
    public void undo() {
        workingDirectory.delete(createdFile.getName());
    }

    @Override
    public void redo() {
        run();
    }
}
