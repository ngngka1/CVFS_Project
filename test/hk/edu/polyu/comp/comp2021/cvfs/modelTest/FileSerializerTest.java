package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.model.Disk;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.FileSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileSerializerTest {
    private FileSerializer fileSerializer;

    @BeforeEach
    void setUp() {
        fileSerializer = new FileSerializer();
    }

    @Test
    void testSaveVirtualDisk() {
        Disk disk = new Disk(1000);
        String path = "testDisk.ser";
        fileSerializer.saveVirtualDisk(path, disk);
        assertTrue(new File(path).exists());
    }
}