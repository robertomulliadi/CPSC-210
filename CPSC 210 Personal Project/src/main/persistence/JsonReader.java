package persistence;


// Code is based on the JsonSerializationDemo project


import model.Album;
import model.RecordStore;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Stream;

// A reader that reads a record store from JSON data stored in a file
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads a record store from a file and returns it;
    //          throws IOException if an error occurs
    public RecordStore read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRecordStore(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses record store from JSON object and returns it
    private RecordStore parseRecordStore(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        RecordStore rs = new RecordStore(name);
        addAlbums(rs, jsonObject);
        return rs;
    }

    // MODIFIES: rs
    // EFFECTS: parses albums from JSON object and adds them to the recordstore
    private void addAlbums(RecordStore rs, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("albums");
        for (Object json : jsonArray) {
            JSONObject nextAlbum = (JSONObject) json;
            addAlbum(rs, nextAlbum);
        }
    }

    // MODIFIES: rs
    // EFFECTS: parses album from JSON object and adds it to the recordstore
    private void addAlbum(RecordStore rs, JSONObject jsonObject) {
        String id = jsonObject.getString("id");
        String title = jsonObject.getString("title");
        String artist = jsonObject.getString("artist");
        String price = jsonObject.getString("price");
        Album album = new Album(id, title, artist, price);
        rs.addAlbum(album);
    }
}
