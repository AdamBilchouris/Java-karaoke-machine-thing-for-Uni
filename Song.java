//This class is virtual as I do not want to create a song object directly, I
//only need to create a Solo or Duet class


public abstract class Song
{
   private String name;          //name of the song
   private char type;            //solo or duet

   private int serialNumber = 0;    //serial number of song
   private int numberOfLines = 0;    //how many lines are in the song
   private int numberOfLines2 = 0;   //number of lines for the second singer

   public Song(String name, char type, int serialNumber, int numberOfLines)                          //overloaded constructor for solo
   {
      this.name = name;
      this.type = type;
      this.serialNumber = serialNumber;
      this.numberOfLines = numberOfLines;
   }

   public Song(String name, char type, int serialNumber, int numberOfLines, int numberOfLines2)      //overloaded constructor for duet
   {
      this.name = name;
      this.type = type;
      this.serialNumber = serialNumber;
      this.numberOfLines = numberOfLines;
      this.numberOfLines2 = numberOfLines2;
   }

   //============== Getters ===========//
   public String getName()
   {
      return name;
   }

   public char getType()
   {
      return type;
   }

   public int getNumberOfLines()
   {
      return numberOfLines;
   }

   public int getNumberOfLines2()
   {
      return numberOfLines2;
   }

   //============= Abstract Methods ========== //
   public abstract void displaySong();
   public abstract void loadFirstSinger(Lyric[] lyricArrayParameter);
   public abstract void loadSecondSinger(Lyric[] lyricArrayParameter);

   public abstract String stringForFileWriting();
}
