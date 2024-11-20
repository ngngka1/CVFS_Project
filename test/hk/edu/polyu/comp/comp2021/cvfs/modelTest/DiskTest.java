package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk.NewDiskCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk.SaveCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.NewDocumentCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.NewDirectoryCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.navigation.ChangeDirectoryCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.JavaIOFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import static org.junit.jupiter.api.Assertions.*;

class DiskTest {

    @BeforeEach
    void setUp() {
        System.getInstance();
        System.run(new NewDiskCommand(1000));
    }

    @Test
    void testSetRootDirectory() {
        assertEquals("", System.getWorkingDirectory().getName());
    }

    @Test
    void testHandleSizeChangeIncrease() {
        assertEquals(0, System.getWorkingDisk().currentSize);
        System.getWorkingDisk().handleSizeChange(200);
        assertEquals(200, System.getWorkingDisk().currentSize);
    }

    @Test
    void testHandleSizeChangeDecrease() {
        System.getWorkingDisk().handleSizeChange(300);
        assertEquals(300, System.getWorkingDisk().currentSize);
        System.getWorkingDisk().handleSizeChange(-100);
        assertEquals(200, System.getWorkingDisk().currentSize);
    }

    @Test
    void testHandleSizeChangeExceedMaxSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            System.getWorkingDisk().handleSizeChange(1001);
        });
    }

    @Test
    void testAddUniqueFileName() {
        System.getWorkingDisk().addUniqueFileName("file1");
        assertTrue(System.getWorkingDisk().hasFileName("file1"));
        assertFalse(System.getWorkingDisk().hasFileName("file2"));
    }

    @Test
    void testDeleteUniqueFileName() {
        System.getWorkingDisk().addUniqueFileName("file1");
        System.getWorkingDisk().deleteUniqueFileName("file1");
        assertFalse(System.getWorkingDisk().hasFileName("file1"));
    }

    @Test
    void testRenameUniqueFileName() {
        System.getWorkingDisk().addUniqueFileName("file1");
        System.getWorkingDisk().renameUniqueFileName("file1", "file2");
        assertFalse(System.getWorkingDisk().hasFileName("file1"));
        assertTrue(System.getWorkingDisk().hasFileName("file2"));
    }

    @Test
    void testSaveDisk() throws IOException {
        System.run(new NewDocumentCommand("doc1", "txt", "Content of doc1"));
        System.run(new NewDocumentCommand("doc2", "txt", "Content of doc2"));
        System.run(new NewDirectoryCommand("test"));
        System.run(new ChangeDirectoryCommand("test"));
        System.run(new NewDocumentCommand("doc12", "txt", "Content of doc1"));
        System.run(new NewDocumentCommand("doc22", "txt", "Content of doc2"));
//        System.run(new ChangeDirectoryCommand(".."));

        // Save the System.getWorkingDisk() to a temporary directory
        JavaIOFile curDir = new JavaIOFile("");
        String currentAbsolutePath = curDir.getAbsolutePath();
        System.run(new SaveCommand(currentAbsolutePath));
        String tempDir = currentAbsolutePath + "\\virtual_disk";

        // Verify that the files have been created
        JavaIOFile savedFile1 = new JavaIOFile(tempDir + "\\doc1.txt");
        JavaIOFile savedFile2 = new JavaIOFile(tempDir + "\\doc2.txt");
        JavaIOFile savedFile3 = new JavaIOFile(tempDir + "\\test");
        JavaIOFile savedFile4 = new JavaIOFile(tempDir + "\\test\\doc12.txt");
        JavaIOFile savedFile5 = new JavaIOFile(tempDir + "\\test\\doc22.txt");
//        java.lang.System.out.println("savedFile1 path: " + savedFile1.toPath().toString());
//        java.lang.System.out.println("savedFile1.exists(): " + savedFile1.exists());
//        java.lang.System.out.println("savedFile1.canRead(): " + savedFile1.canRead());
//        try {
//            BasicFileAttributes attr = Files.readAttributes(Paths.get(savedFile1.getPath()), BasicFileAttributes.class);
//        } catch (Exception e) {
//
//        }

        assertTrue(savedFile1.exists());
        assertTrue(savedFile2.exists());
        assertTrue(savedFile3.exists());
        assertTrue(savedFile4.exists());

        // Clean up created files
        Files.deleteIfExists(savedFile5.toPath());
        Files.deleteIfExists(savedFile4.toPath());
        Files.deleteIfExists(savedFile3.toPath());
        Files.deleteIfExists(savedFile2.toPath());
        Files.deleteIfExists(savedFile1.toPath());
        Files.deleteIfExists(new JavaIOFile(tempDir).toPath());
    }

    @Test
    void testSaveDiskFolderCreationFailure() {
        assertThrows(IllegalArgumentException.class, () -> {
            System.run(new SaveCommand("asd.c.xsc.asx'qws$////"));
        });
    }

    @Test
    void testGetRootDirectoryDefault() {
        System.run(new NewDiskCommand(1000));
        assertNotNull(System.getWorkingDirectory());
    }
}