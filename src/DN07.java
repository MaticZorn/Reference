import java.io.File;
import java.util.Scanner;

public class DN07 {

    public static void main(String[] args) {
        if (args.length != 2) System.exit(0);

        String imeDatoteke = args[0];
        int n = Integer.parseInt(args[1]);

        int[] tabela = ustvariTabelo(imeDatoteke);
        poisciNajvecjega(tabela, n);
    }

    /**
     * Metoda vrne tabelo stevil prebranih iz datoteke.
     * @param imeDatoteke ime datoteke, ki jo odpremo
     * @return tabela (int[]), ki vsebuje vsa stevila v datoteki
     */
    static int[] ustvariTabelo(String imeDatoteke) {
        int maxVelikost = 100;
        int i = 0;
        int[] prebrano = new int[maxVelikost];

        try {
            Scanner sc = new Scanner(new File(imeDatoteke));
            while (sc.hasNext()) {
                prebrano[i++] = Integer.parseInt(sc.next());
            }
            sc.close();
            return prebrano;
        } catch (Exception e) {
            System.out.println("Napaka: " + e);
            return null;
        }
    }

    /**
     * Metoda izpise -n- najvecjih stevil v datoteki.
     * @param tabela tabela (int[]), ki vsebuje vsa stevila v datoteki
     * @param n      stevilo najvecjih stevil, ki jih iscemo
     */
    static void poisciNajvecjega(int[] tabela, int n) {
        for (int i = n; i > 0; i--) {
            int max = 0;
            int pozicija = 0;
            int stevec = 0;

            for (int e : tabela) {
                if (e > max) {
                    max = e;
                    pozicija = stevec;
                }
                stevec++;
            }
            System.out.println(max);
            tabela[pozicija] = -1;
        }
    }
}
