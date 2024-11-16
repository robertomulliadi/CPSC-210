package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// This class represents the record store in the application
public class RecordStore implements Writable {
    private String name;
    private List<Album> albums;

    // EFFECTS: constructs a record store with a name and an empty list of albums
    public RecordStore(String name) {
        this.name = name;
        albums = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addAlbum(Album album) {
        albums.add(album);
        EventLog.getInstance().logEvent(new Event(album.getTitle() + " added to the record store"));
    }

    public void deleteAlbum(Album album) {
        albums.remove(album);
        EventLog.getInstance().logEvent(new Event((album.getTitle() + " was deleted from the record store")));
    }

    // MODIFIES: this
    // EFFECTS: adds an album to the record store
    public boolean albumCanAdd(Album album) {
        for (Album a: albums) {
            if (a.getId().equals(album.getId())) {
                return false;
            }
        }
        albums.add(album);
        EventLog.getInstance().logEvent(new Event(album.getTitle() + " added to the record store"));
        return true;
    }

    // EFFECTS: returns the list of albums in the record store
    public List<Album> getAlbums() {
        return Collections.unmodifiableList(albums);
    }

    // EFFECTS: returns the number of albums in the record store
    public int numAlbums() {
        return albums.size();
    }

//    public boolean deleteAlbum(Album album) {
//        for (Album a: albums) {
//            if (a.getId().equals(album.getId())) {
//                return false;
//            }
//        }
//        albums.remove(album);
//        return true;
//    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("albums", albumsToJson());
        return json;
    }

    // EFFECTS: returns list of albums as a JSON array
    private JSONArray albumsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Album a : albums) {
            jsonArray.put(a.toJson());
        }
        return jsonArray;
    }
}
