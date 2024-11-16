package ui;

import model.Album;
import model.Event;
import model.EventLog;
import model.RecordStore;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

// This class represents the graphical user interface of the application
public class Screen {
    private Album album;
    private RecordStore recordStore;
    private static final String JSON_STORE = "./data/recordStore.json";
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);
    private String albumCover = "./data/midnightsAlbumCover.jpeg";

    private JFrame mainFrame;
    private JPanel mainPanel;
    private JButton loadDataButton;
    private JButton saveDataButton;
    private JButton exitButton;
    private JButton addAlbumButton;
    private JButton searchAlbumButton;
    private JButton deleteAlbumButton;
    private JButton showAllAlbumsButton;
    private JLabel recordStoreLabel;
    private JLabel viewAlbumsLabel;
    private JFrame viewAlbumsFrame;
    private JScrollPane albumsScrollPane;

    private JList<String> listAlbums;
    private DefaultListModel<String> listOfAlbums;

    private JFrame imageFrame;
    private JLabel imageLabel;
    private ImageIcon displayedImage;
    private File imageFile;
    private JButton imageButton;
    private JLabel imageDescription;
    private BufferedImage bufferedImage;

    private JButton selectAlbumButton;
    private JFrame viewDetailsFrame;
    private DefaultListModel<String> listOfDetails;
    private JList<String> listDetails;
    private JScrollPane detailsScrollPane;



    // Initializes the user interface
    public Screen() throws IOException {
        recordStore = new RecordStore("My Record Store");
        createRecordStore();
    }

    // EFFECTS: creates a gui for the record store app
    public void createRecordStore() throws IOException {
        createMainFrame();
        createMainPanel();
        createButtons();
        createLabel();

        showAllAlbums();
        addNewAlbum();
        deleteAlbum();
        searchForAlbum();
        loadData();
        saveData();
        displayImage();
        addToMainFrame();

        printEventLog();
    }

    // EFFECTS: creates the record store app mainframe
    private void createMainFrame() {
        mainFrame = new JFrame("Record Store App");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.setSize(450, 450);
    }

    // EFFECTS: creates the record store's main panel
    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBounds(100, 100, 250, 220);
        mainPanel.setBackground(Color.gray);
    }

    // EFFECTS: creates buttons found in the main menu interface
    private void createButtons() {
        showAllAlbumsButton = new JButton("Display All Albums");
        showAllAlbumsButton.setBackground(Color.blue);

        addAlbumButton = new JButton("Add New Album");
        addAlbumButton.setBackground(Color.blue);

        searchAlbumButton = new JButton("Search for Album");
        searchAlbumButton.setBackground(Color.blue);

        deleteAlbumButton = new JButton("Delete Album");
        deleteAlbumButton.setBackground(Color.blue);

        loadDataButton = new JButton("Load Data");
        loadDataButton.setBackground(Color.blue);

        saveDataButton = new JButton("Save Data");
        saveDataButton.setBackground(Color.blue);

        exitButton = new JButton("Exit");
        exitButton.setBackground(Color.blue);

        imageButton = new JButton("Bestselling Album");
        imageButton.setBackground(Color.blue);
    }

    // EFFECTS: creates the record store label
    private void createLabel() {
        recordStoreLabel = new JLabel("Record Store Application");
        recordStoreLabel.setBounds(155, 25, 250, 50);
    }

    // EFFECTS: displays all the album titles in the record store in a scroll panel
    private void showAllAlbums() {
        showAllAlbumsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createViewAlbumsFrame();
                selectAlbum();
            }
        });
    }

    // EFFECTS: allows the user to select an album in the view albums frame
    private void selectAlbum() {
        selectAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = "";
                if (listAlbums.getSelectedIndex() != -1) {
                    createViewAlbumsDetailsFrame();
                }
                viewAlbumsLabel.setText(data);
            }
        });
    }

    // EFFECTS: creates the frame for the list of albums in the store
    private void createViewAlbumsFrame() {
        viewAlbumsFrame = new JFrame();
        viewAlbumsLabel = new JLabel("Albums in Store");
        viewAlbumsLabel.setBounds(130, 20, 100, 15);
        selectAlbumButton = new JButton("Select");
        selectAlbumButton.setBounds(135, 230, 75, 25);
        listOfAlbums = new DefaultListModel<>();
        for (Album a : recordStore.getAlbums()) {
            listOfAlbums.addElement(a.getTitle());
        }
        listAlbums = new JList<>(listOfAlbums);
        listAlbums.setBounds(80, 80, 80, 80);
        albumsScrollPane = new JScrollPane();
        albumsScrollPane.setViewportView(listAlbums);
        listAlbums.setLayoutOrientation(JList.VERTICAL);
        albumsScrollPane.setBounds(65, 40, 220, 175);
        viewAlbumsFrame.add(albumsScrollPane);
        viewAlbumsFrame.add(selectAlbumButton);
        viewAlbumsFrame.add(viewAlbumsLabel);
        viewAlbumsFrame.setSize(350, 320);
        viewAlbumsFrame.setLayout(null);
        viewAlbumsFrame.setVisible(true);
    }

    // EFFECTS: creates a frame for albums' details
    private void createViewAlbumsDetailsFrame() {
        viewDetailsFrame = new JFrame();
        listOfDetails = new DefaultListModel<>();
        album = recordStore.getAlbums().get(listAlbums.getSelectedIndex());
        for (Album a : recordStore.getAlbums()) {
            if (album == a) {
                listOfDetails.addElement("Title: " + a.getTitle());
                listOfDetails.addElement("ID: " + a.getId());
                listOfDetails.addElement("Artist: " + a.getArtistName());
                listOfDetails.addElement("Price: " + a.getPrice() + " CAD");
            }

        }
        listDetails = new JList<>(listOfDetails);
        listDetails.setBounds(80, 80, 80, 80);

        detailsScrollPane = new JScrollPane();
        detailsScrollPane.setViewportView(listDetails);
        listDetails.setLayoutOrientation(JList.VERTICAL);
        detailsScrollPane.setBounds(60, 50, 170, 120);

        addToViewDetailsFrame();
    }

    // EFFECTS: adds elements of the album details frame
    private void addToViewDetailsFrame() {
        viewDetailsFrame.add(detailsScrollPane);
        viewDetailsFrame.setSize(300, 250);
        viewDetailsFrame.setLayout(null);
        viewDetailsFrame.setVisible(true);
    }

    // MODIFIES: albums
    // EFFECTS: adds a new album to the record store
    private void addNewAlbum() {
        addAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog(mainFrame, "Enter album's ID");
                String title = JOptionPane.showInputDialog(mainFrame, "Enter album's title");
                String artist = JOptionPane.showInputDialog(mainFrame, "Enter album's artist");
                String price = JOptionPane.showInputDialog(mainFrame, "Enter album's price");
                album = new Album(id, title, artist, price);
                if (album.getId().isEmpty() || album.getTitle().isEmpty()
                        || album.getArtistName().isEmpty() || album.getPrice().isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "Invalid Input/Missing Values");
                } else {
                    inputAlbum();
                }
            }
        });
    }

//    private void checkPriceInput(String price) {
//        if (price.isEmpty()) {
//            JOptionPane.showMessageDialog(mainFrame, "Invalid Input");
//        }
//    }
//
//    private void checkArtistInput(String artist) {
//        if (artist.isEmpty()) {
//            JOptionPane.showMessageDialog(mainFrame, "Invalid Input");
//        }
//    }
//
//    private void checkTitleInput(String title) {
//        if (title.isEmpty()) {
//            JOptionPane.showMessageDialog(mainFrame, "Invalid Input");
//        }
//    }
//
//    private void checkIdInput(String id) {
//        if (id.isEmpty()) {
//            JOptionPane.showMessageDialog(mainFrame, "Invalid Input");
//        }
//    }


    // EFFECTS: inserts inputted album into record store
    private void inputAlbum() {
        recordStore. addAlbum(album);
        listOfAlbums.addElement(album.getTitle());
        listAlbums = new JList<>(listOfAlbums);
        listAlbums.setBounds(80, 80, 80, 80);
        albumsScrollPane.setViewportView(listAlbums);
        JOptionPane.showMessageDialog(mainFrame, "Album added successfully");
    }

    // EFFECTS: removes an album from the store
    private void deleteAlbum() {
        deleteAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog(mainFrame, "Enter album's title");
                for (Album a : recordStore.getAlbums()) {
                    if (a.getTitle().equals(title)) {
                        recordStore.deleteAlbum(a);
                        JOptionPane.showMessageDialog(mainFrame, "Album deleted");
                    }
                }
            }
        });
    }


//    private void searchForAlbum() {
//        searchAlbumButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String id = JOptionPane.showInputDialog(mainFrame, "Enter Album's ID:");
//                String title = JOptionPane.showInputDialog(mainFrame, "Enter Album's title:");
//                String artist = JOptionPane.showInputDialog(mainFrame, "Enter Artist Name:");
//                String price = JOptionPane.showInputDialog(mainFrame, "Enter Album's Price:");
//                Album album = new Album(id, title, artist, price);
//                if (id == "" || title == "" || artist == "" || price == "") {
//                    JOptionPane.showMessageDialog(mainFrame, "Invalid Input/Missing Values");
//                } else if (recordStore.getAlbums().contains(album)) {
//                    JOptionPane.showMessageDialog(mainFrame, "Album is available for purchase!");
//                }
//                JOptionPane.showMessageDialog(mainFrame, "Album is not in store yet");
//            }
//        });
//    }

    // EFFECTS: searches for an album in the store, shows a message dialog if record is available,
    //          otherwise do nothing.
    private void searchForAlbum() {
        searchAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog(mainFrame, "Enter Album's title:");
                String artist = JOptionPane.showInputDialog(mainFrame, "Enter Artist Name:");
                for (Album a : recordStore.getAlbums()) {
                    if (a.getTitle().equalsIgnoreCase(title) && a.getArtistName().equalsIgnoreCase(artist)) {
                        JOptionPane.showMessageDialog(mainFrame, "Album is available for purchase!");
                    }
                }
            }
        });
    }



    // EFFECTS: creates a frame with the best-selling album cover picture and a short description
    private void displayImage() {
        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageFrame = new JFrame();
                imageFile = new File(albumCover);
                try {
                    bufferedImage = ImageIO.read(imageFile);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
                displayedImage = new ImageIcon(bufferedImage);
                imageLabel = new JLabel(displayedImage);
                Border border = BorderFactory.createLineBorder(Color.BLACK);
                imageLabel.setBorder(border);
                imageDescription = new JLabel("<html><div style = 'text-align: center;'>"
                        + "Best-selling Album of the Year (2022):<br/> Midnights by Taylor Swift</div></html>");
                imageFrame.add(imageLabel);
                imageFrame.add(imageDescription);
                imageFrame.setLayout(new FlowLayout());
                imageFrame.setSize(315, 385);
                imageFrame.setVisible(true);
            }
        });
    }

    // EFFECTS: saves the albums in record store when save data button is pressed
    private void saveData() {
        saveDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveRecordStore();
                JOptionPane.showMessageDialog(mainFrame, "Data Saved");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: loads a record store from a file when this button is pressed
    private void loadData() {
        loadDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadRecordStore();
                JOptionPane.showMessageDialog(mainFrame, "Data Loaded.");
            }
        });
    }

    // EFFECTS: adds all the buttons to the main panel
    private void addToMainFrame() {
        mainPanel.add(showAllAlbumsButton);
        mainPanel.add(addAlbumButton);
        mainPanel.add(deleteAlbumButton);
        mainPanel.add(searchAlbumButton);
        mainPanel.add(loadDataButton);
        mainPanel.add(saveDataButton);
        mainPanel.add(imageButton);
        mainFrame.add(recordStoreLabel);
        mainFrame.add(mainPanel);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: loads the record store from a file
    private void loadRecordStore() {
        try {
            recordStore = jsonReader.read();
            System.out.println("Loaded record store from: " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to load record store from: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the record store to a file
    private void saveRecordStore() {
        try {
            jsonWriter.open();
            jsonWriter.write(recordStore);
            jsonWriter.close();
            System.out.println("Saved record store to: " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save record store to: " + JSON_STORE);
        }
    }

    // EFFECTS: prints out events that occurred when program was run
    private void printEventLog() {
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Iterator<model.Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
                    Event event = it.next();
                    System.out.println(event.getDescription());
                }
                System.exit(0);
            }
        });
    }
}