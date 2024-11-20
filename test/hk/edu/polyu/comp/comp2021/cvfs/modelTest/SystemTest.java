package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk.LoadCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk.NewDiskCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.DeleteFileCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.NewDirectoryCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.NewDocumentCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.RenameFileCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.navigation.ChangeDirectoryCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.navigation.ListFilesCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.exception.SystemTerminatedException;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Document;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.JavaIOFile;
import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class SystemTest {

    @BeforeEach
    void setUp() {
        System.getInstance();
        System.run(new NewDiskCommand(10000000));
    }

    @Test
    void testNewDirectory() {
        System.run(new NewDirectoryCommand("dir1"));
        Directory workingDir = System.getWorkingDirectory();
        assertEquals(1, workingDir.getDirectories().size());
        assertEquals("dir1", workingDir.getDirectories().get(0).getName());
    }

    @Test
    void testNewDocument() {
        System.run(new NewDirectoryCommand("dir1"));
        System.run(new NewDocumentCommand("doc1", "txt", "TEXT"));
        Document doc;
        doc = (Document) System.getWorkingDirectory().getFiles().get(1);
        assertEquals("doc1", doc.getName());
        assertEquals("txt", doc.getType());
    }

    @Test
    void testDeleteFile() {
        System.run(new NewDirectoryCommand("dir1"));
        System.run(new NewDocumentCommand("doc1", "txt", "TEXT"));
        System.run(new DeleteFileCommand("doc1"));
        assertThrows(IllegalArgumentException.class, () -> System.getWorkingDirectory().delete("doc1"));
    }

    @Test
    void testRenameFile() {
        System.run(new NewDirectoryCommand("dir1"));
        System.run(new NewDocumentCommand("doc1", "txt", "TEXT"));
        System.run(new RenameFileCommand("doc1", "doc2"));
        assertThrows(IllegalArgumentException.class, () -> System.getWorkingDirectory().delete("doc1"));
        assertNotNull(System.getWorkingDirectory().getFiles().stream().filter(f -> f.getName().equals("doc2")).findFirst().orElse(null));
    }

    @Test
    void testChangeDirectory() {
        System.run(new NewDirectoryCommand("dir1"));
        System.run(new ChangeDirectoryCommand("dir1"));
        assertEquals("dir1", System.getWorkingDirectory().getName());
    }

    @Test
    void UndoRedoTest() {
        System.run(new ListFilesCommand());
        System.run(new NewDirectoryCommand("dir1"));
        System.run(new NewDirectoryCommand("dir12"));
        System.run(new NewDirectoryCommand("dir13"));
        System.run(new NewDirectoryCommand("dir14"));
        System.run(new ChangeDirectoryCommand("dir1"));

        System.run(new NewDirectoryCommand("dir2"));
        System.run(new NewDirectoryCommand("dir22"));
        System.run(new NewDirectoryCommand("dir23"));
        System.run(new NewDirectoryCommand("dir24"));
        System.run(new NewDirectoryCommand("dir25"));
        System.run(new ChangeDirectoryCommand("dir2"));

        System.run(new NewDirectoryCommand("dir3"));
        System.run(new NewDirectoryCommand("dir32"));
        System.run(new NewDirectoryCommand("dir33"));
        System.run(new NewDirectoryCommand("dir34"));
        System.run(new NewDirectoryCommand("dir35"));
        System.run(new NewDirectoryCommand("dir36"));
        System.run(new ChangeDirectoryCommand("dir3"));

        System.run(new NewDirectoryCommand("dir4"));
        System.run(new NewDirectoryCommand("dir42"));
        System.run(new NewDirectoryCommand("dir43"));
        System.run(new NewDirectoryCommand("dir44"));
        System.run(new NewDirectoryCommand("dir45"));
        System.run(new NewDirectoryCommand("dir46"));
        System.run(new NewDirectoryCommand("dir47"));
        System.run(new ChangeDirectoryCommand("dir4"));

        System.undo();
        System.undo();
        System.undo();
        assertThrows(IllegalArgumentException.class, () -> System.run(new ChangeDirectoryCommand("dir46")));
        System.redo();
        assertDoesNotThrow(() -> System.run(new ChangeDirectoryCommand("dir46")));
    }

    @Test
    void loadDiskTest() throws IOException {
        JavaIOFile curDir = new JavaIOFile("");
        JavaIOFile diskDir = new JavaIOFile(curDir.getAbsolutePath() + "/testDisk");
        JavaIOFile subDir123 = new JavaIOFile(diskDir.getAbsolutePath() + "/123");
        JavaIOFile subDirTest = new JavaIOFile(diskDir.getAbsolutePath() + "/test");
        JavaIOFile subDirTestDoc = new JavaIOFile(subDirTest.getAbsolutePath() + "/ab.txt");
//        java.lang.System.out.println(subDirTestDoc.isFile());

        if (!(diskDir.mkdir() && subDir123.mkdir() && subDirTest.mkdir())) {
            throw new IllegalArgumentException("asdasd");
        }
        try (FileWriter writer = new FileWriter(subDirTestDoc.getAbsolutePath())) {
            writer.write("asda\n" +
                    "whatasdf");
        } catch (IOException e ) {java.lang.System.out.println("cant write");}

        String currentAbsolutePath = curDir.getAbsolutePath();
        System.run(new LoadCommand(currentAbsolutePath + "\\" + "testDisk"));
        assertDoesNotThrow(() -> System.run(new ChangeDirectoryCommand("test")));
        assertTrue(subDirTestDoc.exists());
        assertTrue(subDirTest.exists());
        assertTrue(subDir123.exists());
        assertTrue(diskDir.exists());
        assertEquals("ab", System.getWorkingDirectory().getFiles().get(0).getName());

        Files.deleteIfExists(subDirTestDoc.toPath());
        Files.deleteIfExists(subDirTest.toPath());
        Files.deleteIfExists(subDir123.toPath());
        Files.deleteIfExists(diskDir.toPath());
    }

    @Test
    void loadDiskTest2() throws IOException {
        JavaIOFile curDir = new JavaIOFile("");
        JavaIOFile diskDir = new JavaIOFile(curDir.getAbsolutePath() + "/testDisk");
        JavaIOFile subDir123 = new JavaIOFile(diskDir.getAbsolutePath() + "/123");
        JavaIOFile subDirTest = new JavaIOFile(diskDir.getAbsolutePath() + "/test");
        JavaIOFile subDirTestDoc = new JavaIOFile(subDirTest.getAbsolutePath() + "/ab.cpp");
//        java.lang.System.out.println(subDirTestDoc.isFile());

        if (!(diskDir.mkdir() && subDir123.mkdir() && subDirTest.mkdir())) {
            throw new IllegalArgumentException("asdasd");
        }
        try (FileWriter writer = new FileWriter(subDirTestDoc.getAbsolutePath())) {
            writer.write("asda\n" +
                    "whatasdf");
        } catch (IOException e ) {java.lang.System.out.println("cant write");}

        String currentAbsolutePath = curDir.getAbsolutePath();
        System.run(new LoadCommand(currentAbsolutePath + "\\" + "testDisk"));
        assertDoesNotThrow(() -> System.run(new ChangeDirectoryCommand("test")));
        assertTrue(subDirTestDoc.exists());
        assertTrue(subDirTest.exists());
        assertTrue(subDir123.exists());
        assertTrue(diskDir.exists());
//        assertEquals("ab", System.getWorkingDirectory().getFiles().get(0).getName());

        Files.deleteIfExists(subDirTestDoc.toPath());
        Files.deleteIfExists(subDirTest.toPath());
        Files.deleteIfExists(subDir123.toPath());
        Files.deleteIfExists(diskDir.toPath());
    }

    @Test
    void TerminateTest(){
        assertThrows(SystemTerminatedException.class, System::terminate);
    }

}