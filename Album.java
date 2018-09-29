public class Album
{
   //Attributes
   private String name;
   private MyLinkedList listOfSongs;

   //Constructor
   public Album(String name)
   {
      this.name = name;
      listOfSongs = new MyLinkedList();
   }

   //Getter
   public String getName()
   {
      return name;
   }
   
   //=================== METHODS FOR AND FROM THE LINKED LIST ==========================//
   public void displayMenuFromLinkedList()
   {
      listOfSongs.displayMenu();
   }

   public void insertAtStart(Song parameterSong)
   {
      listOfSongs.insertAtStart(parameterSong);
   }

   public void insertSongBehind(int choice, Song parameterSong)
   {
      listOfSongs.insertSongBehind(choice, parameterSong);
   }

   public void insertAtEnd(Song parameterSong)
   {
      listOfSongs.insertAtEnd(parameterSong);
   }

   public boolean removeSpecificSong(int choice)
   {
      return listOfSongs.removeSpecificSong(choice);
   }

   public boolean isLinkedListEmpty()
   {
      return listOfSongs.isEmpty();
   }

   public void loadFirstSinger(Lyric[] lyricArrayParameter)
   {
      listOfSongs.loadFirstSinger(lyricArrayParameter);
   }

   public void loadSecondSinger(Lyric[] lyricArrayParameter)
   {
      listOfSongs.loadSecondSinger(lyricArrayParameter);
   }

   public void displaySong(int choice)
   {
      listOfSongs.displaySong(choice);
   }

   public void displaySongOptions(int choice)
   {
      listOfSongs.displaySongOptions(choice);
   }

   public int getSizeOfList()    //Returns starting from 1
   {
      return listOfSongs.sizeOfList();
   }

   public boolean doesThisSongExist(String choiceString)
   {
      return listOfSongs.doesThisSongExist(choiceString);
   }

   public String stringForFileWriting()
   {
      String toReturn = name + "\n";
      toReturn += listOfSongs.stringForFileWriting();
      return toReturn;
   }

   //toString() - Not used
   public String toString()
   {
      return "Name of Album: " + name; // add display for songs in album
   }
}
