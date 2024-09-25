import java.util.ArrayList;
import java.util.Scanner;

public class CLI {
    public static void renderCLI() {
        final Disk workingDisk = System.getWorkingDisk();
        if (System.getInstance().disks.isEmpty()) {
            promptCreateDisk();
        } else if (workingDisk == null) {
            promptLoadDisk();
        } else {
            java.lang.System.out.print(System.getInstance().workingDirectoryAbsolutePath);
        }
        handleInput();
    }

    private static void promptCreateDisk() {
        java.lang.System.out.println("No disks are found currently, Input \"newDisk diskSize\" to create a new virtual disk with a specified disk size.");
    }

    private static void promptLoadDisk() {
        java.lang.System.out.println("You have not selected a disk yet, Input \"load path\" to load a file in your local file system as the virtual disk.");
    }

    private static void handleInput() {
        java.lang.System.out.print(System.getInstance().workingDirectoryAbsolutePath + "> ");
        Scanner scannerObj = new Scanner(System.in);
        String input = scannerObj.nextLine();
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
                }
                case "newDoc": {
                    String docName = inputList[1];
                    String docType = inputList[2];
                    String doc
                }
            }
        } catch (IndexOutOfBoundsException e) {
            java.lang.System.out.println("Inadequate arguments, please make sure necessary arguments are provided!");
        }
    }
}
