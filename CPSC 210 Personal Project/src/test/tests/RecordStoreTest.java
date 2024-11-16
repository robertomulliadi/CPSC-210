package tests;

import model.Album;
//import model.AlbumLiner;
import model.RecordStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


public class RecordStoreTest {
    private RecordStore testRecordStore;
    private Album a1;
    private Album a2;
    private Album a3;

    @BeforeEach
    void setUp() {
        testRecordStore = new RecordStore("My Record Store");
        a1 = new Album("1", "After Hours", "The Weeknd", "120");
        a2 = new Album("2", "beerbongs & bentleys", "Post Malone", "100");
        a3 = new Album("1", "MOONCHILD", "NIKI", "110");
    }

    @Test
    void testConstructor() {
        assertEquals("My Record Store", testRecordStore.getName());
        assertEquals(0, testRecordStore.numAlbums());
    }

    @Test
    void testAddAlbumDistinctId() {
        testRecordStore.addAlbum(a1);
        assertEquals(1, testRecordStore.numAlbums());
        testRecordStore.addAlbum(a2);
        assertEquals(2, testRecordStore.numAlbums());
    }

    @Test
    void testAddAlbumDuplicateId() {
        testRecordStore.addAlbum(a1);
        testRecordStore.addAlbum(a3);
        assertEquals(1, testRecordStore.numAlbums());
    }

    @Test
    void testAlbumCanAdd() {
        testRecordStore.addAlbum(a1);
        assertTrue(testRecordStore.albumCanAdd(a2));
        assertFalse(testRecordStore.albumCanAdd(a3));
    }

    @Test
    void testDeleteAlbum() {
        testRecordStore.addAlbum(a1);
        testRecordStore.addAlbum(a2);
        testRecordStore.deleteAlbum(a1);
        assertEquals(1, testRecordStore.numAlbums());
    }
}
