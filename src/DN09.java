import java.io.File;
import java.util.Locale;
import java.util.Scanner;

public class DN09 {

    public static void main(String[] args) {
        String[] iskani = args[1].split("\\+");

        double vsota = 0;
        Planet[] tabela = preberi(args[0]);
        for (Planet p : tabela) {
            for (String s : iskani) {
                if (p.ime.toUpperCase(Locale.ROOT).equals(s.toUpperCase(Locale.ROOT)))
                    vsota += p.povrsina();
            }
        }
        System.out.printf("Povrsina planetov \"%s\" je %.0f milijonov km2", args[1], vsota / 1000000);
    }

    static Planet[] preberi(String imeDatoteke) {
        Planet[] tabela = new Planet[8];

        try {
            Scanner sc = new Scanner(new File(imeDatoteke));

            int i = 0;
            while (sc.hasNextLine()) {
                String[] vrstica = sc.nextLine().trim().split(":");
                tabela[i++] = new Planet(vrstica[0], Integer.parseInt(vrstica[1]));
            }
            sc.close();
        } catch (Exception ignored) {}
        return tabela;
    }
}


class Planet {
    String ime;
    int radij;

    Planet(String ime, int radij) {
        this.ime = ime;
        this.radij = radij;
    }

    double povrsina() { return 4 * Math.PI * Math.pow(this.radij, 2); }
}