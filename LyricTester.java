public class LyricTester
{
   public static void main(String[] args)
   {
      Song solo = new Solo("a", 'S', 1, 3);
      Lyric[] lyrics = new Lyric[3];

      lyrics[0] = new Lyric("Hello, ", 1.3);
      lyrics[1] = new Lyric("World", 0.2);
      lyrics[2] = new Lyric("!", 2.3);

      for(int i = 0; i < 3; i++)
      {
         try
         {
            lyrics[i].display();
         }
         catch(InterruptedException e)
         {
            System.out.println("Lyric cannot be displayed");
         }
      }

      solo.loadFirstSinger("Hello,", 1.3); solo.loadFirstSinger("World", 0.2); solo.loadFirstSinger("!", 2.3);

      solo.displaySong();
   }
}

