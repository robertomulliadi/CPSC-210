package persistence;

import model.Album;
import model.RecordStore;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/fileThatDoesNotExist.json");
        try {
            RecordStore rs = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    public void testReaderEmptyRecordStore() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRecordStore.json");
        try {
            RecordStore rs = reader.read();
            assertEquals("My Record Store", rs.getName());
            assertEquals(0, rs.numAlbums());
        } catch (IOException e) {
            fail("Unable to read from file");
        }
    }

    @Test
    public void testReaderGeneralRecordStore() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRecordStore.json");
        try {
            RecordStore rs = reader.read();
            assertEquals("Rob's Record Store", rs.getName());
            List<Album> albums = rs.getAlbums();
            assertEquals(2, albums.size());
            checkAlbum("1", "Certified Lover Boy", "Drake","120", albums.get(0));
            checkAlbum("2", "Smithereens", "Joji","90", albums.get(1));
        } catch (IOException e) {
            fail("Unable to read from file");
        }
    }
}
