public class Main {
    public static void main(String[] args) {
        System systemInstance = System.getInstance();
        while (systemInstance.isRunning) {
            CLI.renderCLI();
        }
    }
}