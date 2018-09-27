import java.io.*;
import java.util.*;

public class FileReading
{
   public static void main(String[] args)
   {
      int size;
      Scanner kb = new Scanner(System.in);

      //MyLinkedList songList = new MyLinkedList();

      try
      {
         System.out.print("How many songs in file (for testing only): ");
         size = kb.nextInt();
      }
      catch(InputMismatchException e)
      {
         System.out.println("Please enter an integer");
         return;
      }

      Song[] mySongs;
      try
      {
         mySongs = new Song[size];
      }
      catch(NegativeArraySizeException e)
      {
         System.out.println("The array cannot be negative");
         return;
      }

      int counter = 0;
      BufferedReader fileRead = null;
      try
      {
         File fileIn = new File(args[0]);
         if(fileIn.length() == 0)
         {
            System.out.println("the file is empty");
            return;
         }

         fileRead = new BufferedReader(new FileReader(fileIn));

         String lineIn = null;

         String albumName = fileRead.readLine();
         System.out.println(albumName);

         int linesToRead = 0; int linesToReadSecondSinger = 0;

         while((lineIn = fileRead.readLine()) != null)
         {
            System.out.println("\n\n");
            //System.out.println("lineIn = " + lineIn);
            StringTokenizer songNameTokenizer = new StringTokenizer(lineIn);
            char type = lineIn.charAt(0);
            //System.out.println("type: " + type);

            if(type == 'S' || type == 's' || type == 'D' || type == 'd')
            {
               songNameTokenizer.nextToken();
               int serialNumber = Integer.parseInt(songNameTokenizer.nextToken().trim());

               //System.out.println("serial number: " + serialNumber);

               String songName = songNameTokenizer.nextToken("").trim();

               //System.out.println("song name: " + songName);

               if(type == 'S' || type == 's')
               {
                  String linesToReadString = fileRead.readLine().trim();

                  //System.out.println("lines to read string: " + linesToReadString);

                  linesToRead = Integer.parseInt(linesToReadString);

                  lineIn = fileRead.readLine();
                  StringTokenizer lyricsTokenizerForFirstLyric = new StringTokenizer(lineIn);
                  double waitForFirstLyric = Double.parseDouble(lyricsTokenizerForFirstLyric.nextToken().trim());
                  String firstLyric = lyricsTokenizerForFirstLyric.nextToken("").trim();

                  try
                  {  
                     /*if(MyLinkedList.isEmpty())
                     {
                        Song tempSong = new Solo(songName, type, serialNumber, linesToRead);
                        songList.addToFront(tempSong);
                     }*/

                     mySongs[counter] = new Solo(songName, type, serialNumber, linesToRead);
                     mySongs[counter].loadFirstSinger(firstLyric, waitForFirstLyric);
                  }
                  catch(ArrayIndexOutOfBoundsException e)
                  {
                     System.out.println("The array is out of bounds");
                     break;
                  }

                  for(int i = 1; i < linesToRead; ++i)
                  {
                     lineIn = fileRead.readLine();
                     StringTokenizer lyricsTokenizer = new StringTokenizer(lineIn);
                     double wait = Double.parseDouble(lyricsTokenizer.nextToken().trim());
                     String lyric = lyricsTokenizer.nextToken("").trim();
                     try
                     {
                        mySongs[counter].loadFirstSinger(lyric, wait);
                     }
                     catch(ArrayIndexOutOfBoundsException e)
                     {
                        System.out.println("The array is out of bounds");
                        break;
                     }
                  }

                  //mySongs[counter].displaySong();
                  counter++;
               }

               if(type == 'D' || type == 'd')
               {
                  String linesToReadString = fileRead.readLine();

                  //System.out.println("lines to read string: " + linesToReadString);

                  StringTokenizer duetTokenizer = new StringTokenizer(linesToReadString);
                  String linesToRead_1 = duetTokenizer.nextToken().trim();
                  String linesToRead_2 = duetTokenizer.nextToken().trim();

                  linesToRead = Integer.parseInt(linesToRead_1);
                  linesToReadSecondSinger = Integer.parseInt(linesToRead_2);


                  lineIn = fileRead.readLine();
                  StringTokenizer lyricsTokenizerForFirstLyric = new StringTokenizer(lineIn);
                  double waitForFirstLyric = Double.parseDouble(lyricsTokenizerForFirstLyric.nextToken().trim());
                  String firstLyric = lyricsTokenizerForFirstLyric.nextToken("").trim();
                  try
                  {
                     mySongs[counter] = new Duet(songName, type, serialNumber, linesToRead, linesToReadSecondSinger);
                     mySongs[counter].loadFirstSinger(firstLyric, waitForFirstLyric);
                  }
                  catch(ArrayIndexOutOfBoundsException e)
                  {
                     System.out.println("The array is out of bounds");
                     break;
                  }

                  for(int i = 1; i < linesToRead; ++i)
                  {
                     lineIn = fileRead.readLine();
                     StringTokenizer lyricsTokenizer = new StringTokenizer(lineIn);
                     double wait = Double.parseDouble(lyricsTokenizer.nextToken().trim());
                     String lyric = lyricsTokenizer.nextToken("").trim();
                     try
                     {
                        mySongs[counter].loadFirstSinger(lyric, wait);
                     }
                     catch(ArrayIndexOutOfBoundsException e)
                     {
                        System.out.println("The array is out of bounds");
                        break;
                     }
                  }

                  for(int j = 1; j <= linesToReadSecondSinger; ++j)
                  {
                     lineIn = fileRead.readLine();
                     StringTokenizer lyricsTokenizer = new StringTokenizer(lineIn);
                     double wait = Double.parseDouble(lyricsTokenizer.nextToken().trim());
                     String lyric = lyricsTokenizer.nextToken("").trim();
                     try
                     {
                        mySongs[counter].loadSecondSinger(lyric, wait);
                     }
                     catch(ArrayIndexOutOfBoundsException e)
                     {
                        System.out.println("The array is out of bounds");
                        break;
                     }
                  }
                  //mySongs[counter].displaySong(); 
                  counter++;
               }
            }
            /*else
              {
              StringTokenizer lyricsTokenizerForFirstLyric = new StringTokenizer(lineIn);
              double waitForFirstLyric = Double.parseDouble(lyricsTokenizerForFirstLyric.nextToken().trim());
              String firstLyric = lyricsTokenizerForFirstLyric.nextToken("");
              System.out.println("first lyrics:\n\twait: " + waitForFirstLyric + "\n\tlyric: " + firstLyric);
              for(int i = 1; i < linesToRead; ++i)
              {
              lineIn = fileRead.readLine();
              StringTokenizer lyricsTokenizer = new StringTokenizer(lineIn);
              double wait = Double.parseDouble(lyricsTokenizer.nextToken().trim());
              String lyric = lyricsTokenizer.nextToken("").trim();
              System.out.println("Wait: " + wait + "\n\tLyric: " + lyric);
              }
              fileRead.reset();
              }*/     
         }
      }
      catch(FileNotFoundException e)
      {
         System.out.println("File not found.");
      }
      catch(IOException e)
      {
         System.out.println("Error in reading the file");
      }
      
      for(int i = 0; i < counter; i++)
      {
         try
         {
            System.out.println();
            mySongs[i].displaySong();
            System.out.println();
         }
         catch(ArrayIndexOutOfBoundsException e)
         {
            System.out.println("Array is out of bounds");
            break;
         }
      }
   }
}

