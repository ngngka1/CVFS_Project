package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk.NewDiskCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.DeleteFileCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.NewDirectoryCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.NewDocumentCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.RenameFileCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.navigation.ChangeDirectoryCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Document;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class SystemTest {

    @BeforeEach
    void setUp() {
        System.getInstance();
        System.run(new NewDiskCommand(1000));
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
        doc = (Document) System.getWorkingDirectory().getFiles().get(0);
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

}