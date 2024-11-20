package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.navigation;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.Command;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.Criteria;
import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.base.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Document;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.File;

import java.util.ArrayList;
import java.util.List;

public class SearchFilesCommand extends Command {
    private final boolean isRecursive;
    private final String criName;
    public SearchFilesCommand(String criName) {this(false, criName);}
    public SearchFilesCommand(boolean isRecursive, String criName) {this.isRecursive = isRecursive; this.criName = criName;};

    private static void searchFilesHelper(boolean isRecursive, Criterion criterion) {
        List<String> output = new ArrayList<>();
        int[] fileCountAndSize = searchFilesHelper(output, System.getWorkingDirectory(), "", isRecursive, criterion);
        for (String line : output) {
            java.lang.System.out.println(line);
        }
        java.lang.System.out.println("number of matched files: " + fileCountAndSize[0]);
        java.lang.System.out.println("total size of matched files: " + fileCountAndSize[1]);
    }

    private static int[] searchFilesHelper(List<String> output, Directory currentDirectory, String indentation, boolean isRecursive, Criterion criterion) {
        int fileCount = 0;
        int fileSize = 0;
        for (File file : currentDirectory.getFiles()) {

            if (file instanceof Document) {
                if (criterion == null || criterion.check(file)) {
                    output.add(indentation + file.toString());
                    fileCount++;
                    fileSize += file.size();
                }
            } else {
                if (criterion == null || criterion.check(file)) {
                    output.add(indentation + file.toString() + " (matched)");
                    fileCount++;
                    if (isRecursive) {
                        int[] fileCountAndSize = searchFilesHelper(output, (Directory) file, indentation + "  ", isRecursive, criterion);
                        fileCount += fileCountAndSize[0];
//                        fileSize += fileCountAndSize[1] + file.getDefaultSize();
                    }
                    fileSize += file.size();
                } else if (isRecursive) {
                    output.add(indentation + file.toString());
                    int[] fileCountAndSize = searchFilesHelper(output, (Directory) file, indentation + "  ", isRecursive, criterion);
                    if (fileCountAndSize[0] == 0) {
                        output.remove(output.size() - 1);
                    } else {
                        fileCount += fileCountAndSize[0];
                        fileSize += fileCountAndSize[1];
                    }
                }
            }
        }
        return new int[] {fileCount, fileSize};
    }

    @Override
    public void run() {
        Criterion criterion = Criteria.get(criName);
        if (criterion == null) throw new IllegalArgumentException("Criterion not found!");
        searchFilesHelper(isRecursive, criterion);
    }
}
