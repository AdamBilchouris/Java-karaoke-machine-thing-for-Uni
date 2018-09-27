import java.util.Scanner;

public class LinkedListTester
{
   public static Scanner kb = new Scanner(System.in);

   public static void main(String[] args)
   {
      Album testAlbum = new Album("Songs");
      MyLinkedList testList = new MyLinkedList();

      //testAlbum.insertAtEnd(testSong1);
      //testAlbum.insertAtEnd(testSong2);

      Lyric[] hello = new Lyric[2];
      hello[0] = new Lyric("Test", 0.01);
      hello[1] = new Lyric("Word", 2);

      Lyric[] foo = new Lyric[2];
      foo[0] = new Lyric("Foo", 1);
      foo[1] = new Lyric("Bar", 2);

      Lyric[] bar = new Lyric[2];
      bar[0] = new Lyric("Bar", 2);
      bar[1] = new Lyric("Foo", 1); 


      Song testSong1 = new Solo("Mary had a little lamb", 'S', 1, 2, hello);
      testAlbum.insertAtEnd(testSong1);

      Song testSong2 = new Duet("Eensy weensy spider", 'D', 2, 2, 2, foo, bar);
      testAlbum.insertAtEnd(testSong2);

      Song testSong3 = new Solo("Twinkle Twinkle Little Star", 'S', 3, 2, foo);
      testAlbum.insertAtEnd(testSong3);

      Song testSong4 = new Solo("Baa baa black sheep", 'S', 4, 2, bar);
      testAlbum.insertAtEnd(testSong4);

      int choice = 0;

      System.out.println(testAlbum.stringForFileWriting());
      System.out.println("\n\n\n");

      /*do
      {
         testAlbum.displayMenuFromLinkedList();
         System.out.println("Enter a number to select a song or 0 to exit the system: ");
         choice = kb.nextInt(); kb.nextLine();
         if(choice == 0)
         {
            return;
         }
         testAlbum.displaySong(choice - 1);
         System.out.println("\f");
         testAlbum.displayMenuFromLinkedList();
         System.out.println("Enter a song to delete: ");
         choice = kb.nextInt(); kb.nextLine();
         testAlbum.removeSpecificSong(choice - 1);

      } while(choice != 0);
      
      if(choice == 0)
      {
         System.exit(0);
      }*/
   }
}

