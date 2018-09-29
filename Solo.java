public class Solo extends Song
{
   //Attributes
   private Lyric[] lyricArr;
   private int MAX_LYRICS;

   public Solo(String name, char type, int serialNumber, int numberOfLines, Lyric[] singer1)
   {
      super(name, type, serialNumber, numberOfLines); //Calls the first constructor of the Song class
      lyricArr = new Lyric[numberOfLines];   //Assigns memory to for the array
      MAX_LYRICS = numberOfLines;
      loadFirstSinger(singer1);
   }

   //Assigns values to each index of the array (passed in from the constructor)
   public void loadFirstSinger(Lyric[] lyricArrayParameter)
   {
      for(int i = 0; i < MAX_LYRICS; i++)
      {
         //Assings lyricArr at index i to the parameter array at index i
         lyricArr[i] = lyricArrayParameter[i];
      }
   }

   //Needed to implement this from the abstract class (Song), doesn't do anything
   public void loadSecondSinger(Lyric[] lyricArrayParemeter) {}
   
   //The display method, as it as a solo, nothing fancy needs to be done
   public void displaySong()
   {
      for(int i = 0; i < MAX_LYRICS; i++)
      {
         try
         {
            System.out.print("\f");                      //Clears the screen
            System.out.println(lyricArr[i].getLyric());  //Gets the lyric at index i
            Thread.sleep((long)lyricArr[i].getWait());   //Waits a specified amount in ms (gotten from the Lyric class, had to type-cast to a long as the API specified that
         }
        catch(InterruptedException e) //Exception for Thread.sleep
         {
            System.out.println("InterruptedException caught in solo display method");
         }
         catch(NullPointerException e) //Exception for a lyric array index being null
         {
            System.out.println("NullPointerException caught in solo display method");
            System.out.println("Some array index is out of bounds: " + i);
         }
      }
   }

   //Makes a string with the number of lyrics, doubles and the indivudal lyrics
   public String stringForFileWriting()
   {
      String toReturn = null;

      //First line of the string is the max lyrics
      toReturn = MAX_LYRICS + "\n";
      
      //For loop that adds to the string
      for(int i = 0; i < MAX_LYRICS; i++)
      {
         if(i == MAX_LYRICS - 1) //Does not create a new line for the very last double and string pair
         {
            toReturn += lyricArr[i].getWaitInSeconds() + " " + lyricArr[i].getLyric();
         }
         //For every other index it does create a new line
         else
         {
            toReturn += lyricArr[i].getWaitInSeconds() + " " + lyricArr[i].getLyric() + "\n";
         }
      }
      return toReturn;
   }
}
