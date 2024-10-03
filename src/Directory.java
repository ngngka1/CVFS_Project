import java.util.ArrayList;
import java.util.List;

public class Directory {
    String name; // only digits and letters; at most 10 chars
    List<Document> documents; // only of type [txt, java, html, css]
    List<Directory> directories; // sub directories
    // add a stack for undo/redo action

    public Directory(String name, boolean isRootDirectory) {
        if ((isRootDirectory) || (name != null && name.matches("[A-Za-z0-9]+") && name.length() <= 10)) { // fyi: https://www.w3schools.com/python/python_regex.asp
            this.name = name;
        } else {
            throw new IllegalArgumentException("Invalid directory name!");
        }
        documents = new ArrayList<>();
        directories = new ArrayList<>();
    }

    public Directory(String name) {
        this(name, false);
    }

    public int size() {
        int totalSize = System.defaultDirectorySize;
        for (Document document : documents) {
            totalSize += document.size();
        }
        for (Directory directory : directories) {
            totalSize += directory.size();
        }
        return totalSize;
    }

    public void newDirectory(String dirName) {
        Directory newDirectory = new Directory(dirName);
        System.getWorkingDisk().handleSizeChange(newDirectory.size());
        this.directories.add(newDirectory);
    }

    public void newDocument(String docName, String docType, String docContent) {
        Document newDocument = new Document(docName, docType, docContent);
        System.getWorkingDisk().handleSizeChange(newDocument.size());
        this.documents.add(newDocument);
    }

    public void deleteFile(String fileName) {
        List<Document> documents = this.documents;
        for (int i = 0; i < documents.size(); i++) {
            if (documents.get(i).name.equals(fileName)) {
                int sizeChange = documents.get(i).size();
                System.getWorkingDisk().handleSizeChange(- sizeChange);
                documents.remove(i);
                return;
            }
        }
        List<Directory> directories = this.directories;
        for (int i = 0; i < directories.size(); i++) {
            if (directories.get(i).name.equals(fileName)) {
                int sizeChange = directories.get(i).size();
                System.getWorkingDisk().handleSizeChange(- sizeChange);
                directories.remove(i);
                return;
            }
        }
        throw new IllegalArgumentException("Targeted file not found!");
    }

    public void removeFile(String oldFileName, String newFileName) {
        List<Document> documents = this.documents;
        for (Document document : documents) {
            if (document.name.equals(oldFileName)) {
                document.name = newFileName;
                return;
            }
        }
        List<Directory> directories = this.directories;
        for (Directory directory : directories) {
            if (directory.name.equals(oldFileName)) {
                directory.name = newFileName;
                return;
            }
        }
        throw new IllegalArgumentException("Targeted file not found!");
    }

}
