import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DN10 {

    public static void main(String[] args) {
        preberi(args[0]);
        System.out.printf("%.2f", ploscina());
    }

    static ArrayList<Lik> seznamLikov = new ArrayList<>();

    static void preberi(String dat) {
        try {
            Scanner sc = new Scanner(new File(dat));
            while (sc.hasNextLine()) {
                String[] vrstica = sc.nextLine().split(":");
                switch (vrstica[0]) {
                    case "pravokotnik":
                        Pravokotnik l1 = new Pravokotnik(Double.parseDouble(vrstica[1]), Double.parseDouble(vrstica[2]));
                        seznamLikov.add(l1);
                        break;
                    case "krog":
                        Krog l2 = new Krog(Double.parseDouble(vrstica[1]));
                        seznamLikov.add(l2);
                        break;
                    case "kvadrat":
                        Kvadrat l3 = new Kvadrat(Double.parseDouble(vrstica[1]));
                        seznamLikov.add(l3);
                        break;
                }
            }
            sc.close();
        } catch (Exception ignored) {}
    }

    static double ploscina() {
        double vsotaPloscin = 0;
        for (Lik l : seznamLikov) {
            vsotaPloscin += l.ploscina();
        }
        return vsotaPloscin;
    }

    interface Lik {
        double ploscina();
    }

    static class Pravokotnik implements Lik{
        double a;
        double b;
        double ploscina;

        Pravokotnik(double a, double b) {
            this.a = a;
            this.b = b;
        }

        public double ploscina() {
            return ploscina = a * b;
        }
    }

    static class Krog implements Lik {
        double r;
        double ploscina;

        Krog(double r) {
            this.r = r;
        }

        public double ploscina() {
            return ploscina = Math.PI * r * r;
        }
    }

    static class Kvadrat implements Lik {
        double a;
        double ploscina;

        Kvadrat(double a) {
            this.a = a;
        }

        public double ploscina() {
            return ploscina = a * a;
        }
    }

}
