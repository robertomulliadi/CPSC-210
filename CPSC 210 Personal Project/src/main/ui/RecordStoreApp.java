package ui;

import model.Album;
import model.RecordStore;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// This class represents the application in the console UI
public class RecordStoreApp {
    private static final String JSON_STORE = "./data/recordStore.json";
    private RecordStore recordStore;
    private static Scanner scanner;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    // EFFECTS: runs the application (starts the online record store)
    public RecordStoreApp() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        recordStore = new RecordStore("Rob's Record Store");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runRecordStore();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runRecordStore() {
        System.out.println("Welcome to Roberto's Record Store App!");
        mainMenu();
    }

    // EFFECTS: processes user input
    public void mainMenu() {
        System.out.println("Select an option:" + "\n\t1. Display all albums in store" + "\n\t2. Add new album"
                + "\n\t3. Search for album" + "\n\t4. Delete album" + "\n\t5. Load Record Store from file"
                + "\n\t6. Save Record Store to file" + "\n\t7. Exit");
        scanner = new Scanner(System.in).useDelimiter("\n");
        int option = scanner.nextInt();
        chooseOptions(option);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void chooseOptions(int option) {
        switch (option) {
            case 1:
                showAllAlbums();
                break;
            case 2:
                addNewAlbum();
                break;
            case 3:
                searchForAlbum();
                break;
            case 4:
                deleteAlbum();
                break;
            case 5:
                loadRecordStore();
                break;
            case 6:
                saveRecordStore();
                break;
            case 7:
                System.exit(0);
                break;
            default:
                mainMenu();
                break;
        }
    }

    // EFFECTS: processes user input
//    public void mainMenu() {
//        System.out.println("Select an option:" + "\n\t1. Manage Albums" + "\n\t2. Load Record Store from file"
//                + "\n\t3. Save Record Store to file" + "\n\t4. Exit");
//        scanner = new Scanner(System.in).useDelimiter("\n");
//        int option = scanner.nextInt();
//        switch (option) {
//            case 1:
//                manageAlbums();
//                break;
//            case 2:
//                loadRecordStore();
//                break;
//            case 3:
//                saveRecordStore();
//                break;
//            case 4:
//                System.exit(0);
//                break;
//            default:
//                mainMenu();
//                break;
//        }
//    }

    // MODIFIES: this
    // EFFECTS: processes user input
//    private void manageAlbums() {
//        System.out.println("Select an option: " + "\n\t1. Display all albums in store" + "\n\t2. Add a new album"
//                + "\n\t3. Search for an existing album" + "\n\t4. Delete an album" + "\n\t5. Back");
//
//        int option = scanner.nextInt();
//        switch (option) {
//            case 1:
//                showAllAlbums();
//                break;
//            case 2:
//                addNewAlbum();
//                break;
//            case 3:
//                searchForAlbum();
//                break;
//            case 4:
//                deleteAlbum();
//                break;
//            case 5:
//                mainMenu();
//                break;
//            default:
//                manageAlbums();
//                break;
//        }
//    }

    // EFFECTS: prints out the album details
    public void getInformation(Album album) {
        System.out.format("%-10s %21s %23s %23s", album.getId(), album.getTitle(),
                album.getArtistName(), album.getPrice());
        System.out.println("\n");
    }

    // EFFECTS: pints all albums in the store to console
    private void showAllAlbums() {
        List<Album> albums = recordStore.getAlbums();
        System.out.println("Album ID \t\t\t Album Title \t\t\t Album Artist \t\t\t Album Price");
        System.out.println("------------------------------------------------------------------------------------");
        for (Album a : albums) {
            getInformation(a);
        }
        System.out.println("------------------------------------------------------------------------------------");
        mainMenu();
    }

    // MODIFIES: this
    // EFFECTS: adds a new album to the store if none of the inputs are empty strings
    private void addNewAlbum() {
        System.out.println("Adding an album to the collection... " + "\nEnter the album's ID: ");
        String id = scanner.next();
        System.out.println("Enter the album's title: ");
        String title = scanner.next();
        System.out.println("Enter the album's artist: ");
        String artist = scanner.next();
        System.out.println("Enter the album's price: ");
        double price = Double.parseDouble(scanner.next());
        if (title.equals("") || id.equals("") || artist.equals("") || price <= 0) {
            System.out.println("Required information is missing");
            addNewAlbum();
        } else {
            albumCanAdd(id, title, artist, String.valueOf(price));
        }
        mainMenu();
    }

    // REQUIRES: new album ID inputted by user cannot be an empty string
    // MODIFIES: albums list
    // EFFECTS: if new album id already exists in albums, calls addNewAlbum again. Otherwise, adds the new album
    //          into albums
    public void albumCanAdd(String id, String title, String artist, String price) {
        Album album = new Album(id, title, artist, price);
        if (recordStore.albumCanAdd(album)) {
            System.out.println(title + " was added successfully");
        } else {
            System.out.println("An album with id " + id + " already exists in the catalogue");
            addNewAlbum();
        }
        boolean albumExists = false;
        for (Album a : recordStore.getAlbums()) {
            if (a.getId().equals(id)) {
                albumExists = true;
                break;
            }
        }
        if (albumExists) {
            System.out.println("An album with id " + id + " already exists in the catalogue");
            mainMenu();
        } else {
            recordStore.addAlbum(album);
            System.out.println(title + " was added successfully");
        }
    }

    // EFFECTS: saves the record store to a file
    private void saveRecordStore() {
        try {
            jsonWriter.open();
            jsonWriter.write(recordStore);
            jsonWriter.close();
            System.out.println("Saved " + recordStore.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads record store from a file
    private void loadRecordStore() {
        try {
            recordStore = jsonReader.read();
            System.out.println("Loaded " + recordStore.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        mainMenu();
    }


    // MODIFIES: this
    // EFFECTS: allows user to type in title of an album to search for it in the albums list. If found, displays
    //          the album's information, otherwise states that album is not in the store and returns user to the
    //          manageAlbums menu
    public void searchForAlbum() {
        System.out.println("Enter the title of the album: ");
        String title = scanner.next();
        if (title.equals("")) {
            System.out.println("Please enter a valid name: ");
            searchForAlbum();
        } else {
            boolean albumExists = false;
            for (Album a : recordStore.getAlbums()) {
                if (a.getTitle().equals(title)) {
                    albumExists = true;
                    System.out.println("Album ID \t\t\t Album Title \t\t\t Album Artist \t\t\t Album Price");
                    System.out.println("------------------------------------------------------------------"
                            + "------------------");
                    getInformation(a);
                    System.out.println("------------------------------------------------------------------"
                            + "------------------");
                }
            }
            if (!albumExists) {
                System.out.println("Album is not in the store yet");
            }
        }
        mainMenu();
    }

    // MODIFIES: this
    // EFFECTS: allows user to type in the title of an album. If album is found in albums, removes it from albums list
    //          and displays all remaining albums, otherwise state that the album is not in the store and returns
    //          user to the main menu
    public void deleteAlbum() {
        System.out.println("Enter the album title: ");
        String title = scanner.next();
        if (title.equals("")) {
            System.out.println("Please enter a valid title: ");
            deleteAlbum();
        } else {
            boolean albumExists = false;
            for (Album a : recordStore.getAlbums()) {
                if (a.getTitle().equals(title)) {
                    albumExists = true;
                    recordStore.deleteAlbum(a);
                    System.out.println(title + " has been removed successfully");
                    showAllAlbums();
                    mainMenu();
                }
            }
            if (!albumExists) {
                System.out.println("The album is not found in the store");
            }
        }
        mainMenu();
    }
}


// ------------------------------------------------  OLD METHODS ----------------------------------------------------

// MODIFIES: this
// EFFECTS: displays all albums in the record store, and then directs the user back to the manage albums menu
//     public void showAllAlbums() {
//        System.out.println("Album ID \t\t\t Album Title \t\t\t Album Artist \t\t\t Album Price");
//        System.out.println("------------------------------------------------------------------------------------");
//        for (Album a : albums) {
//            System.out.println();
//            a.getInformation();
//        }
//
//        System.out.println("------------------------------------------------------------------------------------");
//
//        manageAlbums();
//    }

// MODIFIES: this
// EFFECTS: directs user to options in the manage notes menu
//    public void manageNotes() {
//        System.out.println("Select an option: "
//                + "\n\t1. Display all descriptions"
//                + "\n\t2. Add a new album description"
//                + "\n\t3. Back");
//
//        int options = scanner.nextInt();
//        switch (options) {
//            case 1:
//                showAllNotes();
//                break;
//            case 2:
//                addNewNote();
//                break;
//            case 3:
//                mainMenu();
//                break;
//            default:
//                manageNotes();
//                break;
//        }
//    }

// MODIFIES: this
// EFFECTS: displays all the notes for each album in store, and then directs user back to the main menu
//    public void showAllNotes() {
//        ArrayList<AlbumLiner> allNotes = new ArrayList<>();
//        for (Album a : albums) {
//            allNotes.addAll(a.getNotes());
//        }
//        if (allNotes.size() > 0) {
//            System.out.println("Album ID \t\t\t\t\t Album Description \t\t\t\t\t Note Code");
//            System.out.println("---------------------------------------------------------------------------------");
//            for (AlbumLiner n : allNotes) {
//                n.getDetails();
//            }
//            System.out.println("---------------------------------------------------------------------------------");
//        } else {
//            System.out.println("Album liner does not exist for any album");
//        }
//        mainMenu();
//    }

// MODIFIES: this
// EFFECTS: allows user to input album ID. If it is an empty string, states that it is an invalid ID and
//          calls addNewNote again, otherwise calls descriptionCanAdd
//    public void addNewNote() {
//        System.out.println("Enter album ID");
//        String id = scanner.next();
//        if (id.equals("")) {
//            System.out.println("Invalid ID");
//            addNewNote();
//        } else {
//            descriptionCanAdd(id);
//        }
//    }

// MODIFIES: this
// EFFECTS: allows user to input new album description. If entered description is an empty string, state that it
//          is invalid and asks user to type description again, otherwise calls addDescription and directs
//          user back to manage notes menu
//    public void descriptionCanAdd(String id) {
//        boolean albumExists = false;
//        for (Album a : albums) {
//            if (a.getId().equals(id)) {
//                albumExists = true;
//                break;
//            }
//        }
//        if (albumExists) {
//            System.out.println("Enter album description");
//            String text = scanner.next();
//            if (text.equals("")) {
//                System.out.println("Invalid description");
//                addNewNote();
//            } else {
//                addDescription(id, text);
//            }
//            manageNotes();
//        }
//        System.out.println("Album not found");
//        manageNotes();
//    }

// MODIFIES: this
// EFFECTS: if album id is found in albums, adds description inputted by user to list of notes,
//          otherwise does nothing
//    public void addDescription(String id, String text) {
//        code++;
//        AlbumLiner newDescription = new AlbumLiner(text, id, code);
//        for (Album a : albums) {
//            if (a.getId().equals(id)) {
//                ArrayList<AlbumLiner> newDescriptions = a.getNotes();
//                newDescriptions.add(newDescription);
//
//                a.setNotes(newDescriptions);
//                albums.remove(a);
//                albums.add(a);
//                System.out.println("Description saved");
//            } else {
//                System.out.println("Album not found");
//            }
//        }
//    }