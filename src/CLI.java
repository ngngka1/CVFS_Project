import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI {
    private static Scanner scannerObj = new Scanner(java.lang.System.in);
    public static void renderCLI() {
        java.lang.System.out.println();
        final Disk workingDisk = System.getWorkingDisk();
        if (workingDisk == null) {
            promptCreateDisk();
            promptLoadDisk();
        }
        handleInput();
    }

    private static void promptCreateDisk() {
        java.lang.System.out.println("No disks are found currently, Input \"newDisk diskSize\" to create a new virtual disk with a specified disk size.");
    }

    private static void promptLoadDisk() {
        java.lang.System.out.println("To load a local file in the virtual disk, Input \"load path\" to load a file in your local file system as the virtual disk.");
    }

    private static void handleInput() {
        java.lang.System.out.print(System.getInstance().workingDirectoryAbsolutePath + "> ");
        String input = CLI.scannerObj.nextLine();
        parseInput(input);
    }

    private static void parseInput(String input) {
        String[] inputList = input.split("\\s+");
        String command = inputList[0];
        try {
            switch (command) {
                case "newDisk": {
                    int newDiskSize = Integer.parseInt(inputList[1]);
                    System.newDisk(newDiskSize);
                    return;
                }
                case "newDoc": {
                    String docName = inputList[1];
                    String docType = inputList[2];
                    String docContent = "";
                    if (inputList.length >= 4) {
                        docContent = inputList[3];
                    }
                    System.newDocument(docName, docType, docContent);
                    return;
                }
                case "newDir": {
                    String dirName = inputList[1];
                    System.newDirectory(dirName);
                    return;
                }
                case "delete": {
                    String fileName = inputList[1];
                    System.deleteFile(fileName);
                    return;
                }
                case "rename": {
                    String fileName = inputList[1];
                    String newFileName = inputList[2];
                    System.renameFile(fileName, newFileName);
                    return;
                }
                case "changeDir": {
                    String dirName = inputList[1];
                    System.changeDirectory(dirName);
                    return;
                }
                case "list": {
                    System.listFiles();
                    return;
                }
                case "rList": {
                    System.recursiveListFiles();
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
            java.lang.System.out.println("Inadequate arguments, please make sure necessary arguments are provided!");
            java.lang.System.out.println(e.toString());
        } catch (IllegalArgumentException e) {
            java.lang.System.out.println(e.toString());
        }
    }
}
