package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files;

import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;

public class NewDirectoryCommand extends NewFileCommand {
    public NewDirectoryCommand(String newDirName) {
        setCreatedFile(new Directory(newDirName));
    }
}
