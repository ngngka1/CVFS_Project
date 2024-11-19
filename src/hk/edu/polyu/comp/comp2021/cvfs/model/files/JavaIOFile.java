package hk.edu.polyu.comp.comp2021.cvfs.model.files;
import java.io.File;

public class JavaIOFile extends File {
    public JavaIOFile(String fileBaseName, String content) {
        super(fileBaseName, content);
    }

    public JavaIOFile(String fileBaseName) {
        super(fileBaseName);
    }
}
