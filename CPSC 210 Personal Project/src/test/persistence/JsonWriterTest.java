package persistence;

import model.Album;
import model.RecordStore;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            RecordStore rs = new RecordStore("Robby's Record Store");
            JsonWriter writer = new JsonWriter("./data/rob\0illegal:filename.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyRecordStore() {
        try {
            RecordStore rs = new RecordStore("My Record Store");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRecordStore.json");
            writer.open();
            writer.write(rs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRecordStore.json");
            rs = reader.read();
            assertEquals("My Record Store", rs.getName());
            assertEquals(0, rs.numAlbums());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralRecordStore() {
        try {
            RecordStore rs = new RecordStore("Rob's Record Store");
            rs.addAlbum(new Album("9", "channel ORANGE", "Frank Ocean", "90"));
            rs.addAlbum(new Album("7", "Continuum", "John Mayer", "100"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRecordStore.json");
            writer.open();
            writer.write(rs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralRecordStore.json");
            rs = reader.read();
            assertEquals("Rob's Record Store", rs.getName());
            List<Album> albums = rs.getAlbums();
            assertEquals(2, albums.size());
            checkAlbum("9", "channel ORANGE", "Frank Ocean", "90", albums.get(0));
            checkAlbum("7", "Continuum", "John Mayer","100", albums.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
