package comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.model.Disk;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiskTest {
    private Disk disk;

    @BeforeEach
    void setUp() {
        System.getInstance();
        System.newDisk(1000);
    }

    @Test
    void testInitialRootDirectory() {
        assertNotNull(disk.getRootDirectory());
        assertEquals("", disk.getRootDirectory().getName());
    }

    @Test
    void testHandleSizeChange() {
        disk.handleSizeChange(100);
        assertEquals(100, disk.currentSize);

        disk.handleSizeChange(-50);
        assertEquals(50, disk.currentSize);
    }

    @Test
    void testHandleSizeChangeExceedsMaxSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            disk.handleSizeChange(1001);
        });
    }

    @Test
    void testAddUniqueFileName() {
        disk.addUniqueFileName("file1");
        assertTrue(disk.hasFileName("file1"));
    }

    @Test
    void testDeleteUniqueFileName() {
        disk.addUniqueFileName("file2");
        disk.deleteUniqueFileName("file2");
        assertFalse(disk.hasFileName("file2"));
    }

    @Test
    void testRenameUniqueFileName() {
        disk.addUniqueFileName("file3");
        disk.renameUniqueFileName("file3", "file4");
        assertFalse(disk.hasFileName("file3"));
        assertTrue(disk.hasFileName("file4"));
    }

    @Test
    void testHasFileName() {
        disk.addUniqueFileName("file5");
        assertTrue(disk.hasFileName("file5"));
        assertFalse(disk.hasFileName("noThisFile"));
    }
}