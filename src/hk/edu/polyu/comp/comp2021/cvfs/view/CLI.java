package hk.edu.polyu.comp.comp2021.cvfs.view;

import java.util.Scanner;
import hk.edu.polyu.comp.comp2021.cvfs.controller.CommandHandler;
import hk.edu.polyu.comp.comp2021.cvfs.model.exception.SystemTerminatedException;

public class CLI {
    private static final Scanner scannerObj = new Scanner(java.lang.System.in);
    public static void renderCLI() throws SystemTerminatedException {
        String input = CLI.scannerObj.nextLine();
        CommandHandler.handleInput(input.trim());
    }

    public static void renderCLI(String input) throws SystemTerminatedException {
        CommandHandler.handleInput(input);
    }
}
