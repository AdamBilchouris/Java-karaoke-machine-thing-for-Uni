public class Lyric
{
   private String lyric;            //rest of line after the double
   private double wait;             //double to wait between each lyric

   public Lyric(String lyric, double wait)
   {
      this.lyric = lyric;
      this.wait = 1000 * wait;   //Converts into milliseconds (for the getWait())
   }

   public String getLyric()
   {
      return lyric;
   }

   public double getWait()
   {
      return wait;
   }
 
   //This is used for the fileWriting, converts it back into seconds
   public double getWaitInSeconds()
   {
      double tempWait = wait;
      tempWait = (tempWait) / (1000);
      return tempWait;
   }
}

