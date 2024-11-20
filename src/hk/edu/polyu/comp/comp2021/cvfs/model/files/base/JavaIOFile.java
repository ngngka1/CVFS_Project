package hk.edu.polyu.comp.comp2021.cvfs.model.files.base;
import java.io.File;

public class JavaIOFile extends File {
    public JavaIOFile(String fileBaseName, String content) {
        super(fileBaseName, content);
    }

    public JavaIOFile(String fileBaseName) {
        super(fileBaseName);
    }

    public JavaIOFile(File file) {
        super(file.getName());
    }

    @Override
    public JavaIOFile[] listFiles() {
        File[] x = super.listFiles();
        if (x == null) return null;
        JavaIOFile[] arr = new JavaIOFile[x.length];
        for (int i = 0; i < x.length; i++) {
//            System.out.println(x[i] + " asdfasdfsd");
            arr[i] = new JavaIOFile(x[i]);
        }
        return arr;
    }
}
