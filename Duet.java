public class Duet extends Song
{
   //Attributes
   private Lyric[] singer1;
   private Lyric[] singer2;

   private int counter_1;
   private int counter_2;
   private int MAX_LYRICS_1;
   private int MAX_LYRICS_2;

   public Duet(String name, char type, int serialNumber, int numberOfLines_1, int numberOfLines_2, Lyric[] firstSinger, Lyric[] secondSinger)
   {
      super(name, type, serialNumber, numberOfLines_1, numberOfLines_2);   //Second constructor of the Song class
      singer1 = new Lyric[numberOfLines_1];  //Assigns  memory 
      singer2 = new Lyric[numberOfLines_2];  //Assigns memory
      counter_1 = 0;
      counter_2 = 0;
      MAX_LYRICS_1 = numberOfLines_1;
      MAX_LYRICS_2 = numberOfLines_2;

      loadFirstSinger(firstSinger);
      loadSecondSinger(secondSinger);
   }

   //Same as the Solo class
   public void loadFirstSinger(Lyric[] lyricArrayParameter)
   {
      for(int i = 0; i < MAX_LYRICS_1; i++)
      {
         singer1[i] = lyricArrayParameter[i];
      }
   }

   //Same as the solo class but with a different array
   public void loadSecondSinger(Lyric[] lyricArrayParameter)
   {
      for(int i = 0; i < MAX_LYRICS_2; i++)
      {
         singer2[i] = lyricArrayParameter[i];
      }
   }

   //Calls the display() method, uses displaySong() as that is the abstract method from the song class
   public void displaySong()
   {
      display();
   }

   //display method, 
   private void display()
   {
      //indices for the two Lyric arrays
      int singer1Counter = 0; int singer2Counter = 0;
      //gets the wait for the singers at index 0
      double initWaitSinger1 = singer1[0].getWait(); double initWaitSinger2 = singer2[0].getWait();
      //sets the difference of the singer's wait and the lowest wait to 1
      //(waitAt_1 - min(wait1, wait2) = 0
      double waitCheckerSinger1 = 1; double waitCheckerSinger2 = -1;
      //sets the waits for the singers to the initial waits
      double waitForSinger1 = initWaitSinger1;  double waitForSinger2 = initWaitSinger2;

     try
     {
         while(singer1Counter < MAX_LYRICS_1 && singer2Counter < MAX_LYRICS_2)
         {
            /*if(singer1Counter == MAX_LYRICS_1 || singer2Counter == MAX_LYRICS_2)
            {
               return;
            }*/

            //if the two waits are equal
            if(waitForSinger1 == waitForSinger2)
            {
               double wait = waitForSinger1; //as the waits are equal, it assigns it to the wait for the first singer
               try
               {
                  System.out.println("\fSinger 1 = " + singer1[singer1Counter].getLyric() + "\nSinger 2 = " + singer2[singer2Counter].getLyric());
                  Thread.sleep((long)wait);
               }
               catch(InterruptedException e)
               {
                  System.out.println("InterruptedException caught in display() method of Duet.java");
               }
               waitCheckerSinger1 = waitForSinger1 - wait; waitCheckerSinger2 = waitForSinger2 - wait;   //this is the difference between the wait and the wait for each singer

               //if the wait difference for the first singer is 0, move onto the
               //next index
               if(waitCheckerSinger1 == 0)
               {
                  singer1Counter += 1;
                  waitForSinger1 = singer1[singer1Counter].getWait();
               }
               //if the wait difference for the second singer is 0, move onto
               //the next index
               if(waitCheckerSinger2 == 0)
               {
                  singer2Counter += 1;
                  waitForSinger2 = singer2[singer2Counter].getWait();
               }
            }

            //if the two waits are different
            if(waitForSinger1 < waitForSinger2)
            {
               double wait = waitForSinger1; //assigns it to the smaller wait
               try
               {
                  System.out.println("\fSinger 1 = " + singer1[singer1Counter].getLyric() + "\nSinger 2 = " + singer2[singer2Counter].getLyric());
                  Thread.sleep((long)wait);
               }
               catch(InterruptedException e)
               {
                  System.out.println("InterruptedException caught in display() method of Duet.java");
               }
               waitCheckerSinger1 = waitForSinger1 - wait; waitCheckerSinger2 = waitForSinger2 - wait;

               //as above, but this this the wait for singer2 now becomes the
               //difference between it and singer1's wait
               if(waitCheckerSinger1 == 0)
               {
                  singer1Counter += 1;
                  waitForSinger1 = singer1[singer1Counter].getWait();
                  waitForSinger2 = waitCheckerSinger2;
               }
               //as above but with respect to the second singer
               if(waitCheckerSinger2 == 0)
               {
                  singer2Counter += 1;
                  waitForSinger2 = singer2[singer2Counter].getWait();
                  waitForSinger1 = waitCheckerSinger1;
               }
            }

            //as above but when the wait for singer1 is larger than singer2
            if(waitForSinger1 > waitForSinger2)
            {
               double wait = waitForSinger2; //assigns it to the smaller wait

               try
               {
                  System.out.println("\fSinger 1 = " + singer1[singer1Counter].getLyric() + "\nSinger 2 = " + singer2[singer2Counter].getLyric());
                  Thread.sleep((long)wait);
               }
               catch(InterruptedException e)
               {
                  System.out.println("InterruptedException caught in display() method of Duet.java");
               }

               waitCheckerSinger1 = waitForSinger1 - wait; waitCheckerSinger2 = waitForSinger2 - wait;

               //the same as the < if statement
               if(waitCheckerSinger1 == 0)
               {
                  singer1Counter += 1;
                  waitForSinger1 = singer1[singer1Counter].getWait();
                  waitForSinger2 = waitCheckerSinger2;
               }
               if(waitCheckerSinger2 == 0)
               {
                  singer2Counter += 1;
                  waitForSinger2 = singer2[singer2Counter].getWait();
                  waitForSinger1 = waitCheckerSinger1;
               }

            }
         }
      }
      //Couldn't think of logic to avoid this so once it reaches the end of an
      //array it will just leave the display, not the smartest thing to do but
      //better than getting errors
      catch(ArrayIndexOutOfBoundsException e)
      {
         return;
      }
   }

   //Similar to the Solo method, except it caters for 2 arrays, this time the
   //first array will create a new for every single pair of doubles and strings
   //but the second one will not for the last pair
   public String stringForFileWriting()
   {
      String toReturn = null;

      toReturn = MAX_LYRICS_1 + " " + MAX_LYRICS_2 + "\n";

      for(int i = 0; i < MAX_LYRICS_1; i++)
      {
         toReturn += singer1[i].getWaitInSeconds() + " " + singer1[i].getLyric() + "\n";
      }

      for(int j = 0; j < MAX_LYRICS_2; j++)
      {
         if(j == MAX_LYRICS_2 - 1)
         {
            toReturn += singer2[j].getWaitInSeconds() + " " + singer2[j].getLyric();
         }
         else
         {
            toReturn += singer2[j].getWaitInSeconds() + " " + singer2[j].getLyric() + "\n";
         }
      } 

      return toReturn;
   }
}
