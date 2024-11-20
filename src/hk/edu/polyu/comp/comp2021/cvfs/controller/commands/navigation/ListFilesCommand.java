package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.navigation;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.Command;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.File;

public class ListFilesCommand extends Command {
    private final boolean isRecursive;
    public ListFilesCommand() {this(false);}
    public ListFilesCommand(boolean isRecursive) {this.isRecursive = isRecursive;};

    private static void listFilesHelper(boolean isRecursive) {
        if (System.getWorkingDisk() == null) throw new IllegalArgumentException("Please first initialize the working disk");
        java.lang.System.out.println("name type size");

        int[] fileCountAndSize = listFilesHelper(System.getWorkingDirectory(), "", isRecursive);

        java.lang.System.out.println("total number of files: " + fileCountAndSize[0]);
        java.lang.System.out.println("total size of files: " + fileCountAndSize[1]);
    }

    private static int[] listFilesHelper(Directory currentDirectory, String indentation, boolean isRecursive) {
        int fileCount = 0;
        int fileSize = 0;
        for (File file : currentDirectory.getFiles()) {
            int s = file.size();
            java.lang.System.out.println(indentation + file.toString());
            if ((file instanceof Directory) && isRecursive) {
                int[] fileCountAndSize = listFilesHelper((Directory) file, indentation + "  ", isRecursive);
                fileCount += fileCountAndSize[0];
//                s = fileCountAndSize[1];
            }
            fileSize += s;
            fileCount++;
        }
        return new int[] {fileCount, fileSize};
    }

    @Override
    public void run() {
        listFilesHelper(isRecursive);
    }
}
