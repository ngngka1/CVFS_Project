package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.StatefulCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.disk.Disk;

public class NewDiskCommand extends StatefulCommand {
    Disk previous;
    Disk target;
    public NewDiskCommand(int diskSize) {
        previous = System.getWorkingDisk();
        target = new Disk(diskSize);
    }


    @Override
    public void run() {
        System.setWorkingDisk(target);
        System.setWorkingDirectory(target.getRootDirectory());
        System.setWorkingDirectoryPath("");
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
