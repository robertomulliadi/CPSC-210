package model;

import org.json.JSONObject;
import persistence.Writable;

// This class represents an album in the record store
public class Album implements Writable {
    private String id;
    private final String title;
    private final String artist;
//    private ArrayList<AlbumLiner> notes;
    private String price;

    // Constructs album with price > 0
//    public Album(String id, String title, String artist, double price) {
//        this.id = id;
//        this.title = title;
//        this.artist = artist;
//       this.notes = new ArrayList<>();
//        this.price = price;
//    }

    // EFFECTS: creates an album with associated id, title, artist and price
    public Album(String id, String title, String artist, String price) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.price = price;
//        this.notes = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getArtistName() {
        return artist;
    }

    public String getId() {
        return id;
    }

//    public ArrayList<AlbumLiner> getNotes() {
//        return notes;
//    }

    public String getPrice() {
        return price;
    }

//    public void getInformation() {
//        System.out.format("%-10s %21s %23s %23s", this.id, this.title, this.artist, this.price);
//        System.out.println("\n");
//    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("title", title);
        json.put("artist", artist);
        json.put("price", price);
        return json;

    }
//    public void setId(String id) { this.id = id; }
//    public void setPrice(double price) { this.price = price; }
//    public void setArtist(String name) { this.artist = name; }
//    public void setTitle(String title) { this.title = title; }








}
