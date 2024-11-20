package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.Command;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;

public class SaveCommand extends Command {
    String path;
    public SaveCommand(String path) {
        if (!PathValidator.isValid(path)) {
            throw new IllegalArgumentException("Invalid path!");
        }
        if (path.contains("\\")) path = path.replace("\\", "/");
        if (!path.endsWith("/")) path += "/";
        path += "virtual_disk";
        this.path = path;
    }

    @Override
    public void run() {
        System.save(path);
    }
}
