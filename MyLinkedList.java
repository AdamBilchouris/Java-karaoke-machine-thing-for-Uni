//Adapted from Absolute Java (6th edition), Walter Savitch

public class MyLinkedList
{
   private class Node
   {
      //=================================================== Attributes =======================================================================\\
      private Song song;   //Song object
      private Node next;   //next Node in the list

      //=============================================== Constructor and Methods ==============================================================\\
      public Node()
      {
         song = null;
         next = null;
      }

      //Node constructor, the temporary song is created in the driver
      public Node(Song parameterSong)
      {
         song = parameterSong;
         next = null;  
      }

      public Song getSong()
      {
         return song;
      }

      public Node getNext()
      {
         return next;
      }

      public void setNext(Node next)
      {
         this.next = next;
      }

      //=========================================== Getters for the Song objects =============================================================\\

      public String getName()
      {
         return song.getName();
      }

      public char getType()
      {
         return song.getType();
      }

      public int getSerialNumber()
      {
         return song.getSerialNumber();
      }

      public void displaySong()
      {
         song.displaySong();
      }

      //Not used, now inside of Solo/Duet class
      public void addToSinger1(Lyric[] lyricArrayParameter)
      {
         song.loadFirstSinger(lyricArrayParameter);
      }

      //Not used, now done inside of the Solo/Duet class
      public void addToSinger2(Lyric[] lyricArrayParameter)
      {
         song.loadSecondSinger(lyricArrayParameter);
      }

      //retuns a string for a song object to write to the file
      public String stringForFileWriting()
      {
         return song.stringForFileWriting();
      }
   }

   //=================================================== Start of LinkedList class ===========================================================\\

   private Node head;   //first Node
   private int size;    //the size of the list

   public MyLinkedList()
   {
      head = null;   //Creates an empty linked list
      size = 0;      //the size of the empty list is 0
   }

   //inserts a Node at the start, used if the head of the list is null, as seen in LyricDisplayer.java
   //passes a song by parameter
   public void insertAtStart(Song songParameter)   
   {
      Node newNode = new Node(songParameter);
      newNode.setNext(head);
      head = newNode;
      size++;
   }

   //inserts a a Node at the end, used if the head is not null
   //passes a Song by parameter
   public void insertAtEnd(Song songParameter)
   {
      Node newNode = new Node(songParameter);   //The node to insert

      if(head == null)  //If the head is null, it makes the node that is being added the head
      {
         head = newNode;
         size++;
      }
      else              //If the head isn't null, it adds it to the end
      {
         Node p = head;

         while(p.getNext() != null)
         {
            p = p.getNext();        //makes p equal to the next node, does so until the next node is pointing to null
         }

         p.setNext(newNode);        //Sets the next node of the last node to the new node
         size++;
      }
   }

   public boolean insertSongBehind(int choice, Song songParameter)
   {
      Node newSong = new Node(songParameter);   //Creates a node to be inserted

      if(choice == 0)
      {
         return false;
      }

      Node p = head;                  //First node
      Node q = null;                 //Previous node
      if(choice > 0)
      {
         for(int i = 0; i < choice; i++)           //Loop that keeps iterating until it reaches the specified index
         {
            q = p;                                 //makes a temporary node q equal to the node after the head node
            p = p.getNext();
            if(q == null)
            {
               break;      //Breaks the loop once it is null (end of loop)
            }
         }
         newSong.setNext(p);     //inserts the node before p
         q.setNext(newSong);     //makes q's next node be the new song inserted
         size += 1;
         return true;
      }
      return false;
   }

   public boolean removeFromFront()
   {
      //Empty list
      if(head == null)
      {
         return false;
      }

      //Only one node in the list
      else
      {
         head = head.getNext();
         size--;
         return true;
      }
   }

   public boolean removeFromEnd()
   {
      Node p;

      //Empty list
      if(head == null)
      {
         return false;
      }

      //Only one node in the list
      else if(head.getNext() == null)
      {
         head = null;
         size--;
         return true;
      }

      //More than one node in the list
      else
      {
         p = head;

         //While loop looks ahead two nodes for the node it is at (CURRENT -> NEXT -> NEXT_NEXT)
         while(p.getNext().getNext() != null)
         {
            p = p.getNext();
         }

         p.setNext(null);
         size--;
         return true;
      }
   }

   //Removes a song specified in the menu (keeps it stored as int and double
   //checks if that int is larger than the amount of songs in the list)
   public boolean removeSpecificSong(int choice)
   {
      Node p = head;

      int toChoice = 0;

      if(head == null)  //Empty list
      {  
         return false; 
      }

      if(choice == 0)   //For the first Node
      {
         head = p.getNext();
         return true;
      }

      else if(choice > 0)
      {
         for(int i = 0; i < choice -1; i++)
         {
            p = p.getNext();  //keeps redefining p to the next node until it reaches the next element
         }
         p.next = p.getNext().getNext();
         return true;
      }

      else
      {
         return false;
      }
   }

   //checks whether the node is empty
   public boolean isEmpty()
   {
      if(head == null)
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   //returns the size of the linked list, used in the driver
   public int sizeOfList()
   {
      int counter = 0;
      Node p = head;

      //keeps increasing the counter while a temporary node p isnt null
      while(p != null)
      {
         counter += 1;
         p = p.getNext();
      }

      return counter;
   }

   //Method that displays the songs 
   public void displayMenu()
   {
      Node p = head;

      int menuOption = 1;

      while(p != null)
      {
         System.out.println(menuOption+ ". " + p.getName());
         menuOption++;
         p = p.getNext();
      }
   }

   //displays a specific song at a certain index, similar to
   //removeSpecificSong(int choice)
   public void displaySong(int choice)
   {
      Node p = head;
      int toChoice = 0;

      while(toChoice < choice)
      {
         p = p.getNext();
         toChoice += 1;
      }

      p.displaySong();

   }

   public boolean doesThisSongExist(String choiceString)
   {
      Node p = head;
      
      while(p != null)
      {
         if(choiceString.equalsIgnoreCase(p.getName()))
         {
            return true;
         }
         else
         {
            p = p.getNext();
         }
      }
      return false;
   }

   //Similar to displaySong(int choice) and removeSpecificSong(int choice)
   public void displaySongOptions(int choice)
   {
      int toChoice = 0;
      Node p = head;

      //loop increments until the node you want is reached (specified by the
      //parameter integer)
      while(toChoice < choice)
      {
         p = p.getNext();
         toChoice += 1;
      }

      System.out.print("\f"); //clears the screen
      System.out.println((choice + 1) + ". " + p.getName());
      System.out.println();
      System.out.println("\tA. Play it\n\tB. Delete it from the menu and the album\n\tC. Add a new song behind it\n\tQ. Return to the main menu");
   }

   //assigns the song-id integer here, as well as the name of the song
   //calls the node class method for file writing which calls the song one
   public String stringForFileWriting()
   {
      String toReturn = "";

      Node p = head;

      int counter = 1;

      while(p != null)
      {
         toReturn += p.getType() + " " + counter + " " + p.getName() + "\n";
         toReturn += p.stringForFileWriting();
         toReturn += "\n";
         p = p.getNext();
         counter += 1;
      }

      return toReturn;
   }
}
