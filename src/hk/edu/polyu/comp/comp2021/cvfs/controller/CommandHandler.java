package hk.edu.polyu.comp.comp2021.cvfs.controller;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria.NewBinaryCriterionCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria.NewNegationCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria.NewSimpleCriterionCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria.PrintAllCriteriaCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk.LoadCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk.NewDiskCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk.SaveCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.DeleteFileCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.NewDirectoryCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.NewDocumentCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.RenameFileCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.navigation.ChangeDirectoryCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.navigation.ListFilesCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.navigation.SearchFilesCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.exception.SystemTerminatedException;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;

public class CommandHandler {
    public static void handleInput(String input) throws SystemTerminatedException {
        if (!input.isEmpty())
            parseInput(input);
        if (System.getWorkingDisk() == null) {
            promptCreateDisk();
            promptLoadDisk();
        }
        java.lang.System.out.print(System.getWorkingDirectoryPath() + "> ");
    }
    private static void promptCreateDisk() {
        java.lang.System.out.println("No disks are found currently, Input \"newDisk diskSize\" to create a new virtual disk with a specified disk size.");
    }

    private static void promptLoadDisk() {
        java.lang.System.out.println("To load a local file in the virtual disk, Input \"load path\" to load a file in your local file system as the virtual disk.");
    }

    private static void parseInput(String input) throws SystemTerminatedException {
        String[] inputList = input.split("\\s+");
        String command = inputList[0];
        try {
            switch (command) {
                case "newDisk": {
                    int newDiskSize = Integer.parseInt(inputList[1]);
                    System.run(new NewDiskCommand(newDiskSize));
                    return;
                }
                case "newDoc": {
                    String docName = inputList[1];
                    String docType = inputList[2];
                    StringBuilder docContent = new StringBuilder();
                    if (inputList.length >= 4) {
                        int i = 3;
                        do {
                            docContent.append(inputList[i++]);
                            if (i < inputList.length) docContent.append(" ");
                        } while (i < inputList.length);
                    }
                    System.run(new NewDocumentCommand(docName, docType, docContent.toString()));
                    return;
                }
                case "newDir": {
                    String dirName = inputList[1];
                    System.run(new NewDirectoryCommand(dirName));
                    return;
                }
                case "delete": {
                    String fileName = inputList[1];
                    System.run(new DeleteFileCommand(fileName));
                    return;
                }
                case "rename": {
                    String fileName = inputList[1];
                    String newFileName = inputList[2];
                    System.run(new RenameFileCommand(fileName, newFileName));
                    return;
                }
                case "changeDir": {
                    String dirName = inputList[1];
                    System.run(new ChangeDirectoryCommand(dirName));
                    return;
                }
                case "list": {
                    System.run(new ListFilesCommand());
                    return;
                }
                case "rList": {
                    System.run(new ListFilesCommand(true));
                    return;
                }
                case "newSimpleCri": {
                    String criName = inputList[1];
                    String attrName = inputList[2];
                    String op = inputList[3];
                    StringBuilder val = new StringBuilder();
                    int i = 4;
                    do {
                        val.append(inputList[i++]);
                    } while (i < inputList.length);
                    System.run(new NewSimpleCriterionCommand(criName, attrName, op, val.toString()));
                    return;
                }
                case "newNegation": {
                    String criName1 = inputList[1];
                    String criName2 = inputList[2];
                    System.run(new NewNegationCommand(criName1, criName2));
                    return;
                }
                case "newBinaryCri": {
                    String criName1 = inputList[1];
                    String criName3 = inputList[2];
                    String logicOp = inputList[3];
                    String criName4 = inputList[4];
                    System.run(new NewBinaryCriterionCommand(criName1, criName3, logicOp, criName4));
                    return;
                }
                case "printAllCriteria": {
                    System.run(new PrintAllCriteriaCommand());
                    return;
                }
                case "search": {
                    String criName = inputList[1];
                    System.run(new SearchFilesCommand(criName));
                    return;
                }
                case "rSearch": {
                    String criName = inputList[1];
                    System.run(new SearchFilesCommand(true, criName));
                    return;
                }
                case "save": {
                    String path = inputList[1];
                    System.run(new SaveCommand(path));
                    return;
                }
                case "load": {
                    String path = inputList[1];
                    System.run(new LoadCommand(path));
                    return;
                }
                case "undo": {
                    System.undo();
                    return;
                }
                case "redo": {
                    System.redo();
                    return;
                }
                case "quit": {
                    System.terminate();
                    return;
                }
                default: {
                    java.lang.System.out.println("Invalid command! (Commands are case-sensitive)");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            java.lang.System.out.println("Inadequate arguments, please make sure necessary arguments are provided!" + e);
        } catch (IllegalArgumentException e) {
            java.lang.System.out.println(e.getMessage());
        }
    }

//    private static String parsePathDeprecated(String targetPath) {
//        if (!targetPath.startsWith("$:")) {
//            throw new IllegalArgumentException("Please specify current working directory by $:path !");
//        }
//        targetPath = targetPath.substring(2);
//        String regex = "^([a-zA-Z0-9]+|[.]{2})(:[a-zA-Z0-9]+|:[.]{2})*$";
//        if (!targetPath.matches(regex)) {
//            throw new IllegalArgumentException("Invalid path!");
//
//        }
//
//        // optimization for parent directory navigation
//        List<String> optimizedFileNames = new ArrayList<String>();
//        StringBuilder optimizedPathBuilder = new StringBuilder();
//        while (!targetPath.isEmpty()) {
//            int delimiterIndex = targetPath.indexOf(':');
//            String directoryName;
//            if (delimiterIndex != -1) {
//                directoryName = targetPath.substring(0, delimiterIndex);
//            } else {
//                directoryName = targetPath;
//                targetPath = "";
//            }
//            if (optimizedFileNames.isEmpty()) {
//                optimizedFileNames.add(directoryName);
//            } else if (directoryName.equals("..") && !optimizedFileNames.getLast().equals("..")) {
//                optimizedFileNames.removeLast();
//            } else {
//                optimizedFileNames.add(directoryName);
//            }
//            targetPath = targetPath.substring(delimiterIndex + 1);
//        }
//        for (int i = 0; i < optimizedFileNames.size(); i += 1) {
//            optimizedPathBuilder.append(optimizedFileNames.get(i));
//            if (i != optimizedFileNames.size() - 1) {
//                optimizedPathBuilder.append(":");
//            }
//        }
//        return optimizedPathBuilder.isEmpty() ? "" : optimizedPathBuilder.toString();
//    }
}
