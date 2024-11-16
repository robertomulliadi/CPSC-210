# Roberto's Record Store App

## Roberto Mulliadi's CPSC 210 Personal Project

**Project Proposal**

- *Why is this project of interest to me?*
- *Who will use it?*
- *What will the application do?*

While thinking of a project to work on, I thought of everything I enjoy in life, and one of these things is music.
So, I decided to create an online record store application that will allow me to easily manage the shop's products.
For now, music albums are the only type of product in the store, and later on it would be easy to add other types
vinyl, cassettes and more by creating a super class. This application will be used by the store's manager, since
it will allow the user to manage all the products in the store, including its price, unique ID, album descriptions
and more. 

It can also be further developed into a shop if I add a Shopping Cart class and such, for customers to use.
For now, the main use of the application will be explained briefly through user stories found below.
### User Stories

**In the context of a record store application:**
- As a user, I want to be able to add a new album
- As a user, I want to be able to delete an album from the store
- As a user, I want to be able to view all the albums in the store
- As a user, I want to be able to search for an album in the store easily

**Phase 2 User Stories:**
- As a user, I want to be able to save my RecordStore to file
- As a user, I want to be able to load my RecordStore from a file


### Instructions for Grader

1. Click on the Add Album button to add a new album to the store. You need to input an ID, title, artist name, and
   price of the new album. These values cannot be an empty string.
2. To delete an album, click on the delete album button and input the details of the album you wish to remove from
   the record store.
3. To view the albums in the store so far, click on the display all albums button in the gui. A scroll pane should pop
   up with a list of album titles that can be found in the store.
4. To see whether an album is for sale, click on the search for album button and input the album details. If the
   album is in store, a message dialog will pop up, otherwise it would do nothing.
5. The visual component in the GUI can be found by clicking on the bestselling album button, which will show the album
   cover and title of the best-selling album of the year.
6. You can load data from file by clicking on the load data button.
7. You can save the record store to file by clicking on the save data button.

### Phase 4: Task 2
The following is an example of a list of events that are outputted in the console when the application is closed:

- Certified Lover Boy added to the record store
- Smithereens added to the record store
- All 4 Nothing added to the record store
- Divide added to the record store
- Continuum added to the record store
- Mr.Morale & The Big Steppers added to the record store
- Dawn FM added to the record store
- Harry's House added to the record store
- Midnights added to the record store
- SOUR added to the record store
- title added to the record store
- title was deleted from the record store

### Phase 4: Task 3

The following are potential future improvements I would make in this project:

- I can refactor some methods in the RecordStoreApp such as the albumCanAdd method to have a separate method that
  returns boolean to replace "albumExists". This can then be used in deleteAlbum as well since there is a similar
  structure in the deleteAlbum method and the albumCanAdd method which makes use of albumExists.
- Add a release date to each album and allow users to search by year
- Have the list of albums in the store be arranged more conveniently (Alphabetical order, sort by year,
  sort by artist) so that navigation is easier when there is significantly more albums in the store
- Allow the user to edit album details after selecting the album
- Have more album cover images, so that it does not look plain
- I can add stock of each album, so that the manager would know how many of each album is available for purchase
- Only use a single frame for the application 
- Have more colours and a better design for the GUI
- I can add a different product, such as vinyl, to the record store, making use of a superclass-subclass relationship
