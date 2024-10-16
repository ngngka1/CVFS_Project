public class Main {
    public static void main(String[] args) {
        final System systemInstance = System.getInstance();
        while (systemInstance.isRunning()) {
            CLI.renderCLI();
        }
    }
}
//Hello
//newDisk 999
//newDir test1
//changeDir $:test1
//newDir test2
//changeDir $:test2
//changeDir $:..:..
//changeDir $:test1:..:test1:..:test1:test2:..:..:test1

//newDisk 9999
//newDir test1
//newDir test2
//newDir test3
//newDir test4
//newDir test5
//newDir test6
//changeDir $:test2
//newDoc testCss css abcdeCss
//newDoc testTxt txt abcdeTxt
//newDoc testHtml html abcdeHtml
//newDir test21
//changeDir $:..:test4
//newDoc testCss css abcdeCss
//newDoc testTxt txt abcdeTxt
//newDoc testHtml html abcdeHtml
