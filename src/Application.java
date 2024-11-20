import hk.edu.polyu.comp.comp2021.cvfs.model.exception.SystemTerminatedException;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.view.CLI;

public class Application {
    public static void main(String[] args) {
        System.getInstance();
        try {
            CLI.renderCLI("");
            while (true) {
                CLI.renderCLI();
            }
        } catch (SystemTerminatedException e) {}
    }


}
// printAllCriteria
// newSimpleCri ge name contains "123"

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
