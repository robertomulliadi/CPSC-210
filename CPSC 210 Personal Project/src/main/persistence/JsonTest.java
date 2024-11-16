package persistence;

import model.Album;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkAlbum(String id, String title, String artist, String price, Album album) {
        assertEquals(id, album.getId());
        assertEquals(title, album.getTitle());
        assertEquals(artist, album.getArtistName());
        assertEquals(price, album.getPrice());
    }
}
