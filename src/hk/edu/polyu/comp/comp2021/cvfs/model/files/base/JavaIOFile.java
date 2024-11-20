package hk.edu.polyu.comp.comp2021.cvfs.model.files.base;
import java.io.File;

public class JavaIOFile extends File {
    String absolutePath;
//    public JavaIOFile(String fileBaseName, String content) {
//        super(fileBaseName, content);
//    }

    public JavaIOFile(String filePath) {
        super(filePath);
        absolutePath = filePath;
    }

//    public JavaIOFile(File file) {
//        super(new File("").getAbsoluteFile() + "/" + file.getName());
//    }

    @Override
    public JavaIOFile[] listFiles() {
//        if (!super.isDirectory())
        File[] x = super.listFiles();
        if (x == null) return null;
        JavaIOFile[] arr = new JavaIOFile[x.length];
        for (int i = 0; i < x.length; i++) {
//            System.out.println(x[i] + " asdfasdfsd");
            arr[i] = new JavaIOFile(x[i].getAbsolutePath());
        }
        return arr;
    }
}
