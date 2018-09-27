//For file I/O
import java.io.*;

//Scanner and StringTokenizer
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.StringTokenizer;

public class LyricDisplayer
{
   //Attributes that are used a lot in the program
   private static String fileName = null;
   private static Scanner kb = new Scanner(System.in);
   private static Album album;   //makes a null album object
   private static int counter = 0;
   private static boolean newFileName = false;  //whether a new file should be made (if an exception is caught when reading the file)
                                                //this will determine whether to truncate the file and to make a new one with the name new_FileName

   public static void main(String[] args)
   {
      //if the length of the command line arguement is 0, display an error and exit the program
      if(args.length == 0)
      {
         System.out.println("No file was entered in the commandline arguement, please enter one.\nThe format is: java LyricDisplayer fileName");
         return;
      }
      //else perform these actions
      else
      {
         fileName = args[0];
         loadSongsFromFile(args[0]);     
         setFileName();
         writeSongsToFile(fileName);
         menu();
      }
   }

   //the menu method, basically the main part of the program, it is called a bit
   //by other functions
   private static void menu()
   {
      //doesnt clear the screen so the user can see the errors
      if(newFileName == true)
      {
         newFileName = false;
         System.out.println("\n\n");
      }
      else
      {
         System.out.print("\f");  //clears the screen
         System.out.println();
      }

      int choice; boolean boolChoice = false;

      if(album.isLinkedListEmpty() == true)  //if there are no songs in the album (i.e. deleted them all, it will exit the program)
      {
         System.out.println("There are no songs in the album, leaving the system");
         System.exit(0);
      }

      else
      {
         while(boolChoice == false)
         {
            System.out.println(album.getName());

            album.displayMenuFromLinkedList();

            System.out.print("\nEnter a number to select a song or enter 0 to exit the system: ");

            try
            {
               choice = kb.nextInt(); kb.nextLine();
               if(choice < 0 || choice > album.getSizeOfList())   //if the choice provided is less than 0 or greater than the size of the list, display an error and ask again
               {
                  System.out.println("Please enter a valid number, it must be between (inclusive) 0 and " + album.getSizeOfList());
               }
               else if(choice == 0)
               {
                  System.out.println("Exiting the system");
                  System.exit(0);
               }
               else
               {
                  displaySongOptions(choice);
               }
            }
            catch(InputMismatchException e)  //catches whether the user enters anything but an integer
            {
               System.out.println("Please enter an integer\n\n");
               kb.next();  //makes the input stream have a good bit again, not a bad bit
            }
         }
      }
   }

   //displays the song options (A, B, C, Q), for a specified index in the linked
   //list, passed in from the main menu method
   private static void displaySongOptions(int choice)
   {
      char option;
      boolean boolOption = false;

      do
      {
         album.displaySongOptions(choice - 1); 

         System.out.print("\nSelect a function: ");

         option = kb.next().trim().toUpperCase().charAt(0); kb.nextLine();

         switch(option)
         {
            case 'A':
               displaySong(choice); 
               break;

            case 'B':
               boolean removed = removeSpecificSong(choice);
               if(removed == true)
               {
                  System.out.println("Song successfully removed from the album");
                  writeSongsToFile(fileName);   //once the user deletes a song, it will rewrite to the file
                  menu();  //if the user decides to delete a song, it will return back to the main menu
               }
               else
               {
                  System.out.println("Could not remove song from the album");
               }
               break;

            case 'C':
               addSongBehind(choice);
               break;

            case 'Q':
               menu();  //if the user decides to quit the submenu, it will return back to the main menu
               boolOption = true;
               break;

            default:
               System.out.println("That is not a valid choice");

         } 
      } while(boolOption != true);
   }

   //used to display a specific song, calls and album function which calls a
   //linked list function
   private static void displaySong(int choice)
   {
      album.displaySong(choice - 1);   //it is choice - 1 due to the way indices work
   }

   //as above but it removes a song rather than displaying it 
   private static boolean removeSpecificSong(int choice)
   {
      return album.removeSpecificSong(choice - 1);    //it is choice - 1 due to the way indices work
   }

   //asks user for information to create a song
   private static void addSongBehind(int choice)
   {
      boolean songTypeBool = false; boolean linesForSinger1Bool = false; boolean linesForSinger2Bool = false;  //a bunch of booleans for loops if the user keeps entering the wrong data type
      char songType = 'A'; String songName = null; int linesForSinger1 = 0; int linesForSinger2 = 0;           //definition of variables for the method

      //keeps asking until the user enters either an S or D character
      while(songTypeBool != true)
      {
         System.out.print("Enter the song type (either S or D): ");
         try
         {
            songType = kb.next().trim().toUpperCase().charAt(0);  //gets the first character
            kb.nextLine(); //consumes the \n
            if(songType != 'S' && songType != 'D')
            {
               System.out.println("Please enter either S or D for the song type\n\n");
            }
            else
            {
               songTypeBool = true;
            }
         }
         catch(InputMismatchException e)
         {
            System.out.println("Please enter a single character, either S or D\n"); 
            kb.next();  //consumes the bad-bit
         }
      }

      //gets user input if the song type is S
      if(songType == 'S')
      {
         System.out.print("Enter the name of the song: ");
         songName = kb.nextLine();

         //keeps asking until the user enters an integer with the specified conditions
         while(linesForSinger1Bool != true)
         {
            System.out.print("Enter the amount of lyrics (an integer greater than 0): ");
            try
            {
               linesForSinger1 = kb.nextInt();
               kb.nextLine();
               if(linesForSinger1 <= 0)
               {
                  System.out.println("Please enter an integer that is greater than 0\n");
               }
               else
               {
                  linesForSinger1Bool = true;
               }
            }
            catch(InputMismatchException e)
            {
               System.out.println("Please enter an integer that is greater than 0\n");
               kb.next();  //consumes the bad-bit
            }
         }

         Lyric[] singer1Array = new Lyric[linesForSinger1]; //creates a temporary lyric object for the temporary song object

         System.out.println("You will be asked to enter lyrics for the song");

         //keeps asking for doubles and strings until the arrary is full
         for(int i = 0; i < linesForSinger1; i++)   
         {
            System.out.print("\n");
            boolean waitBool = false;

            double wait = 0; String lyric = null;

            while(waitBool != true)
            {
               System.out.print("Enter the wait (a double that is greater than 0) for line " + (i + 1) + ": ");
               try
               {
                  wait = kb.nextDouble();
                  kb.nextLine(); //consumes \n
                  if(wait <= 0.0)
                  {
                     System.out.println("Please enter an double that is greater than 0\n");
                  }
                  else
                  {
                     waitBool = true;
                  }
               }
               catch(InputMismatchException e)
               {
                  System.out.println("Please enter a double that is greater than 0\n");
                  kb.next();
               }
            }
            System.out.print("Please enter the lyric for line " + (i + 1) + ": ");
            lyric = kb.nextLine();

            singer1Array[i] = new Lyric(lyric, wait); //makes a new lyric object at that index
         }

         Song tempSolo = new Solo(songName, songType, 0, linesForSinger1, singer1Array);  //creates a temporary song object to be added to the linked list
         album.insertSongBehind(choice - 1, tempSolo);   //choice - 1, due to the way indices work
         writeSongsToFile(fileName);                     //writes the linked list to the file again
         displaySongOptions(choice + 1);                 //as the new song is at the index 'choice', adding 1 will return to the original song
         //the java garbage collector will get rid of the orphaned Song object as with the lyric array
      }

      //user input if the user enters D
      else if(songType == 'D')
      {
         System.out.print("Enter the name of the song: ");
         songName = kb.nextLine();

         while(linesForSinger1Bool != true)
         {
            System.out.print("Enter the amount of lyrics for the first singer (an integer greater than 0): ");
            try
            {
               linesForSinger1 = kb.nextInt();
               kb.nextLine(); //consumes \n
               if(linesForSinger1 <= 0)
               {
                  System.out.println("Please enter an integer that is greater than 0\n");
               }
               else
               {
                  linesForSinger1Bool = true;
               }
            }
            catch(InputMismatchException e)
            {
               System.out.println("Please enter an integer that is greater than 0\n");
               kb.next();  //consumes the bad-bit
            }
         }

         Lyric[] singer1Array = new Lyric[linesForSinger1]; //creates a temporary lyric array for the first singer

         while(linesForSinger2Bool != true)
         {
            System.out.print("Enter the amount of lyrics for the second singer (an integer greater than 0): ");
            try
            {
               linesForSinger2 = kb.nextInt();
               kb.nextLine(); //consumes \n

               if(linesForSinger2 <= 0)
               {
                  System.out.println("Please enter an integer that is greater than 0\n");
               }
               else
               {
                  linesForSinger2Bool = true;
               }
            }
            catch(InputMismatchException e)
            {
               System.out.println("Please enter an integer greater than 0\n");
               kb.next();
            }
         }

         Lyric[] singer2Array = new Lyric[linesForSinger2]; //creates a temporary lyric arry for the second singer

         System.out.println("You are adding lyrics for the first singer");
         for(int i = 0; i < linesForSinger1; i++)   
         {
            System.out.print("\n");
            boolean waitBool = false;

            double wait = 0; String lyric = null;

            while(waitBool != true)
            {
               System.out.print("Enter the wait (for the first singer (a double greater than 0)) for line " + (i + 1) + ": ");
               try
               {
                  wait = kb.nextDouble();
                  kb.nextLine(); //consumes \n
                  if(wait <= 0.0)
                  {
                     System.out.println("Please enter a double that is greater than 0\n");
                  }
                  else
                  {
                     waitBool = true;
                  }
               }
               catch(InputMismatchException e)
               {
                  System.out.println("Please enter a double that is greater than 0\n");
                  kb.next();
               }
            }
            System.out.print("\nPlease enter the lyric for line " + (i + 1) + ": ");
            lyric = kb.nextLine();

            singer1Array[i] = new Lyric(lyric, wait); //creates a lyric object at index i for the temporary array
         }

         System.out.println("You are adding lyrics for the second singer");
         for(int i = 0; i < linesForSinger2; i++)   
         {
            System.out.print("\n");
            boolean waitBool = false;

            double wait = 0; String lyric = null;

            while(waitBool != true)
            {
               System.out.print("Enter the wait (for the second singer (a double that is greater than 0)) for line " + (i + 1) + ": ");
               try
               {
                  wait = kb.nextDouble();
                  kb.nextLine(); //consumes \n

                  if(wait <= 0.0)
                  {
                     System.out.println("Please enter a double that is greater than 0\n");
                  }
                  else
                  {
                     waitBool = true;
                  }
               }
               catch(InputMismatchException e)
               {
                  System.out.println("Please enter a double that is greater than 0\n");
                  kb.next();
               }
            }
            System.out.print("\nPlease enter the lyric for line " + (i + 1) + ": ");
            lyric = kb.nextLine();

            singer2Array[i] = new Lyric(lyric, wait); //creates a lyric object at index i for the temporary array

         }

         Song tempDuet = new Duet(songName, songType, 0, linesForSinger1, linesForSinger2, singer1Array, singer2Array); //creates a temporary duet object
         album.insertSongBehind(choice - 1, tempDuet);  //this is the same as above for the solo object, instead it is now with a duet object 

         writeSongsToFile(fileName);                  //writes the linked list to the file again

         displaySongOptions(choice + 1);              //as above with the solo option
      }

      else
      {
         System.out.println("Couldn't add a song");
         return;  //if all else fails, just don't do anything
      }
   }

   //===================================== FILE I/O =============================================//

   //sets the fileName static variable based upon whether there is an error in fileReading
   private static void setFileName()
   {
      if(newFileName == true)
      {
         fileName = "new_" + fileName;      
         File file = new File(fileName);
         try
         {
            file.createNewFile();   //tries to create a new file, it can't it will through an exception
         }
         catch(IOException e)
         {
            System.out.println("Cannot create a new file to append the mistakes from the given input file (truncating from the error down");
            return;
         }
      }
   }     

   //used for file writing
   private static void writeSongsToFile(String fileName)
   {
      File file = new File(fileName);
   
      //this can be used instead of FileNotFoundException
      if(!file.exists())
      {
         System.out.println("The file " + fileName + " does not exist");
         return;
      }

      else
      {
         PrintWriter fout;

         try
         {
            fout = new PrintWriter(fileName);
            if(fout == null)
            {
               System.out.println("The PrintWriter object is null");
               return;
            }

            else
            {
               if(album.isLinkedListEmpty() == true)
               {
                  //if the linked list is empty, don't write to the file
               }
               else
               {
                  fout.write(album.stringForFileWriting());
                  fout.flush();  //flushes the output stream
               }
            }
         }
         catch(FileNotFoundException e)
         {
            System.out.println("Caught a FileNotFoundException in writeSongsToFile method of LyricDisplayer.java");
            return;
         }
         fout.close();  //closes the output stream
      }
   }

   //long inefficient way of reading the input file
   private static void loadSongsFromFile(String fileName)
   {
      int lineCounter = 0;    //integer used to tell the user where the error is in their file (if there is an error)

      BufferedReader fileRead = null;  //creates a null buffered reader object
      File fileIn = new File(fileName); 

      //FileNotFound replacement
      if(!fileIn.exists())
      {
         System.out.println("'" + fileName + "' does not exist");
         System.out.println("Exiting the system");
         System.exit(0);
      }

      //if the input file is blank, display an error
      if(fileIn.length() == 0)
      {
         System.out.println("'" + fileName + "' is an empty file");
         System.out.println("Exiting the system");
         System.exit(0);
      }

      try
      {
         fileRead = new BufferedReader(new FileReader(fileIn));   //assigns memory to the buffered reader object

         String lineIn = null;

         //reads in the album name
         String albumName = fileRead.readLine();
         lineCounter += 1;

         album = new Album(albumName); //reads in the album name and creates the album object

         //just some integers
         int linesToReadCounter = 0;    int linesToReadSecondSingerCounter = 0;
         int linesToRead = 0;           int linesToReadSecondSinger = 0;

         //keeps reading until the end of the file
         while((lineIn = fileRead.readLine()) != null)
         {
            StringTokenizer songNameTokenizer = new StringTokenizer(lineIn);  //cretes string tokenizer for the line being read in
            char type = lineIn.trim().charAt(0); //gets the song type

            lineCounter += 1;

            //if the song type is S or D (also lower-case incase the file is slightly different)
            if(type == 'S' || type == 's' || type == 'D' || type == 'd')
            {
               int serialNumber = 0;
               songNameTokenizer.nextToken();   //reads the serial number

               try
               {
                  serialNumber = Integer.parseInt(songNameTokenizer.nextToken().trim());  //tries to turn the serial number into an integer
               }
               //if it fails, it will print an error and says which line it is
               //on, and what should be on that line
               catch(NumberFormatException e)
               {
                  System.out.println("Error in input file format (filename: " + fileName + ") at line: " + lineCounter);
                  System.out.println("This line should contain a char, an intger and a string");
                  System.out.println("It read in: " + lineIn);
                  System.out.println("Will stop reading the file");
                  newFileName = true;
                  return;
               }

               String songName = songNameTokenizer.nextToken("").trim();   //gets the song line from the remainder of the line

               //if the char is equal to S
               if(type == 'S' || type == 's')
               {
                  linesToReadCounter = 0;  linesToReadSecondSingerCounter = 0;

                  String linesToReadString = fileRead.readLine().trim();
                  lineCounter += 1;

                  try
                  {
                     //tries to convert the number of lines to an integer
                     linesToRead = Integer.parseInt(linesToReadString);
                  }
                  //if it fails, it will print an error and says which line it is
                  //on, and what should be on that line
                  catch(NumberFormatException e)
                  {
                     System.out.println("Error in input file format (filename: " + fileName + ") at line: " + lineCounter);
                     System.out.println("This line should contain a single integer");
                     System.out.println("It read in: " + lineIn);
                     System.out.println("Will stop reading the file");
                     newFileName = true;
                     return;
                  }

                  lineIn = fileRead.readLine();
                  lineCounter += 1;

                  double waitForFirstLyric = 0;

                  //the way it is setup, it reads the first line outside of the for loop
                  StringTokenizer lyricsTokenizerForFirstLyric = new StringTokenizer(lineIn);
                  //tries to parse a double from a string
                  try
                  {
                     waitForFirstLyric = Double.parseDouble(lyricsTokenizerForFirstLyric.nextToken().trim());
                  }
                  //if it fails, it will print a message and says what line the
                  //error is on and what should be on that line
                  catch(NumberFormatException e)
                  {
                     System.out.println("Error in input file format (filename: " + fileName + ") at line: " + lineCounter);
                     System.out.println("This line should contain a double and a string");
                     System.out.println("It read in: " + lineIn);
                     System.out.println("Will stop reading the file");
                     newFileName = true;
                     return;
                  }

                  String firstLyric = lyricsTokenizerForFirstLyric.nextToken("").trim();   

                  //creates a temporary lyric array for the temporary song
                  //object
                  Lyric[] tempLyricArray = new Lyric[linesToRead];
                  tempLyricArray[linesToReadCounter] = new Lyric(firstLyric, waitForFirstLyric);
                  linesToReadCounter++;

                  //reads in the file
                  for(int i = 1; i < linesToRead; ++i)
                  {
                     double wait = 0;  String lyric = null;

                     lineIn = fileRead.readLine();
                     lineCounter += 1;

                     StringTokenizer lyricsTokenizer = new StringTokenizer(lineIn);
                     //tries to parse a double
                     try
                     {
                        wait = Double.parseDouble(lyricsTokenizer.nextToken().trim());
                     }
                     //if it fails it will print a message and says what line
                     //the error is on and what should be on that line
                     catch(NumberFormatException e)
                     {
                        System.out.println("Error in input file format (filename: " + fileName + ") at line: " + lineCounter);
                        System.out.println("This line should contain a double and a string");
                        System.out.println("It read in: " + lineIn);
                        System.out.println("Will not create the object for this song and will stop reading the file");
                        newFileName = true;
                        return;
                     }

                     lyric = lyricsTokenizer.nextToken("").trim();

                     tempLyricArray[linesToReadCounter] = new Lyric(lyric, wait);   //creates a lyric object that specific index
                     linesToReadCounter += 1;
                  }

                  //creates a temporary solo object, it gets orphaned later
                  Song tempSolo = new Solo(songName, type, serialNumber, linesToRead, tempLyricArray);

                  //if the linked list is empty (i.e. the first song in the
                  //file), it will insert it at the front, otherwise at the end of the list
                  if(album.isLinkedListEmpty())
                  {
                     album.insertAtStart(tempSolo);
                  }
                  else
                  {
                     album.insertAtEnd(tempSolo);
                  }

                  //increases the counter
                  counter++;
               }

               //If the type of song is D
               if(type == 'D' || type == 'd')
               {
                  linesToReadCounter = 0;  linesToReadSecondSingerCounter = 0;

                  String linesToReadString = fileRead.readLine();
                  lineCounter += 1;
                  
                  //reads in both number of lyrics
                  StringTokenizer duetTokenizer = new StringTokenizer(linesToReadString);
                  String linesToRead_1 = duetTokenizer.nextToken().trim();
                  String linesToRead_2 = duetTokenizer.nextToken().trim();

                  //tries to parse them
                  try
                  {
                     linesToRead = Integer.parseInt(linesToRead_1);
                     linesToReadSecondSinger = Integer.parseInt(linesToRead_2);
                  }
                  //if it fails it will print a message and says what line the
                  //error is on and what should be on that line
                  catch(NumberFormatException e)
                  {
                     System.out.println("Error in input file, " + fileName + ", at line: " + lineCounter);
                     System.out.println("This line should contain two integers seperated by a white space");
                     System.out.println("It read in: " + lineIn);
                     System.out.println("Will stop reading the file");
                     newFileName = true;
                     return; 
                  }

                  lineIn = fileRead.readLine();
                  lineCounter += 1;

                  double waitForFirstLyric = 0;

                  StringTokenizer lyricsTokenizerForFirstLyric = new StringTokenizer(lineIn);
                  //tries to parse the double
                  try
                  {
                     waitForFirstLyric = Double.parseDouble(lyricsTokenizerForFirstLyric.nextToken().trim());
                  }
                  //if it fails it will print a message and says what line the
                  //error is on and what should be on that line
                  catch(NumberFormatException e)
                  {
                     System.out.println("Error in input file format (filename: " + fileName + ") at line: " + lineCounter);
                     System.out.println("This line should contain a double and a string");
                     System.out.println("It read in: " + lineIn);
                     System.out.println("Will stop reading the file");
                     newFileName = true;
                     return;
                  }

                  String firstLyric = lyricsTokenizerForFirstLyric.nextToken("").trim();

                  Lyric[] tempLyricArrayForSinger1 = new Lyric[linesToRead];     //cretes a temporary lyric object for the first singer
                  tempLyricArrayForSinger1[linesToReadCounter] = new Lyric(firstLyric, waitForFirstLyric);
                  linesToReadCounter += 1;

                  //reads in the lyrics for the first singer
                  for(int i = 1; i < linesToRead; ++i)
                  {
                     double wait = 0; String lyric = null;

                     lineIn = fileRead.readLine();
                     lineCounter += 1;

                     StringTokenizer lyricsTokenizer = new StringTokenizer(lineIn);

                     try
                     {
                        wait = Double.parseDouble(lyricsTokenizer.nextToken().trim());
                     }
                     catch(NumberFormatException e)
                     {
                        System.out.println("Error in input file format (filename: " + fileName + ") at line: " + lineCounter);
                        System.out.println("This line should contain a double and a string");
                        System.out.println("It read in: " + lineIn);
                        System.out.println("Will not create the song object and will stop reading the file");
                        newFileName = true;
                        return;
                     }

                     lyric = lyricsTokenizer.nextToken("").trim();

                     if(linesToReadCounter < linesToRead)
                     {
                        tempLyricArrayForSinger1[linesToReadCounter] = new Lyric(lyric, wait);
                        linesToReadCounter += 1;
                     }
                  }

                  Lyric[] tempLyricArrayForSinger2 = new Lyric[linesToReadSecondSinger];  //cretes a temporary lyric object for the second singer

                  //reads in the lyrics for the second singer
                  for(int j = 1; j <= linesToReadSecondSinger; ++j)
                  {
                     double wait = 0; String lyric = null;

                     lineIn = fileRead.readLine();
                     lineCounter += 1;

                     StringTokenizer lyricsTokenizer = new StringTokenizer(lineIn);
                     try
                     {
                        wait = Double.parseDouble(lyricsTokenizer.nextToken().trim());
                     }
                     catch(NumberFormatException e)
                     {
                        System.out.println("Error in input file format (filename: " + fileName + ") at line: " + lineCounter);
                        System.out.println("This line should contain a double and a string");
                        System.out.println("It read in: " + lineIn);
                        System.out.println("Will not create the song object and will stop reading the file");
                        newFileName = true;
                        return;
                     }

                     lyric = lyricsTokenizer.nextToken("").trim();

                     tempLyricArrayForSinger2[linesToReadSecondSingerCounter] = new Lyric(lyric, wait);
                     linesToReadSecondSingerCounter += 1;
                  }

                  Song tempDuet = new Duet(songName, type, serialNumber, linesToRead, linesToReadSecondSinger, tempLyricArrayForSinger1, tempLyricArrayForSinger2);  //creates a temporary duet object, later orphaned

                  //if the linked list is empty (first song being read), it will
                  //insert it at the front, otherwise at the end
                  if(album.isLinkedListEmpty())
                  {
                     album.insertAtStart(tempDuet);
                  }
                  else
                  {
                     album.insertAtEnd(tempDuet);
                  }

                  counter++;
               }
            }
            //If there is an invalid song type, it will give an error and exit
            //the program
            else
            {
               System.out.println("Invalid song type at line " + lineCounter + ", exiting the program");
               System.exit(0);
            }
         }
      }
      catch(FileNotFoundException e)
      {
         System.out.println("The file '" + fileName + "' was not found. Closing the program");
         System.exit(0);
      }
      catch(IOException e)
      {
         System.out.println("There is an error in reading the file. Closing the program");
         System.exit(0);
      }
   }
}
