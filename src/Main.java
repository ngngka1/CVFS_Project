public class Main {
    public static void main(String[] args) {
        final System systemInstance = System.getInstance();
        while (systemInstance.isRunning()) {
            CLI.renderCLI();
        }
    }
}
//newDisk 999
//newDir test1
//changeDir $:test1
//newDir test2
//changeDir $:test2
//changeDir $:..:..
//changeDir $:test1:..:test1:..:test1:test2:..:..:test1
