import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class DN03 {

    public static void main(String[] args) throws Exception {

        //Manjka if(): preveri ce je stevilo argumentov pravilno...

        Scanner datoteka = new Scanner(new File(args[0]));
        int dolzinaTabele = Integer.parseInt(datoteka.next());
        String [] besede = new String [dolzinaTabele];
        int i = 0;

        while (datoteka.hasNext()) {
            besede[i] = datoteka.next();
            i++;
        }

        Random rnd = new Random(Integer.parseInt(args[2]));
        int dolzina = Integer.parseInt(args[1]);
        String geslo = "";
        char crka;

        for (int j = 0; j < dolzina; j++) {
            int izbiraTabele = rnd.nextInt(dolzinaTabele);
            int izbiraCrke   = rnd.nextInt(besede[izbiraTabele].length());
            crka = besede[izbiraTabele].charAt(izbiraCrke);
            geslo += crka;
        }
        System.out.println(geslo);
        datoteka.close();
    }
}
