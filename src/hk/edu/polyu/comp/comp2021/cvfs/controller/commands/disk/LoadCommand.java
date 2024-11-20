package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.Command;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.StatefulCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.disk.Disk;

public class LoadCommand extends StatefulCommand {
    Disk previous;
    String path;
    public LoadCommand(String path) {
        if (!PathValidator.isValid(path)) {
            throw new IllegalArgumentException("Invalid path!");
        }
        if (path.contains("\\")) path = path.replace("\\", "/");
        if (!path.endsWith("/")) path += "/";
//        if (path.endsWith("/")) path = path.substring(0, path.length() - 1);
        this.path = path;
        previous = System.getWorkingDisk();
    }

    @Override
    public void run() {
        System.load(path);
    }

    @Override
    public void undo() {
        System.setWorkingDisk(previous);
    }

    @Override
    public void redo() {
        run();
    }
}
