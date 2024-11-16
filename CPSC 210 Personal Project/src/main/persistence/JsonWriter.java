package persistence;

import model.RecordStore;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// This class represents a JSON writer whose job is to write record store to file as its JSON representation
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private final String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer;
    //          throws FileNotFoundException if destination file cannot be written on
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of recordstore to file
    public void write(RecordStore rs) {
        JSONObject json = rs.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }




}
