import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DN11 {
    public static void main(String[] args) {
        String ukaz = args[0];
        switch (ukaz) {
            case "mejniki":
                Kataster k1 = new Kataster();
                k1.uvozPodatkov(args[1]);
                System.out.println(k1);
                break;
            case "razdalja":
                Kataster k2 = new Kataster();
                k2.uvozPodatkov(args[1]);
                k2.izpisiRazdaljo(args[2], args[3]);
        }
    }
}

class Mejnik implements Comparable<Mejnik> {
    private String ime;
    private double zSirina;
    private double zDolzina;

    public String getIme() {
        return ime;
    }

    public Mejnik(String ime, double zSirina, double zDolzina) {
        this.ime = ime;
        this.zSirina = zSirina;
        this.zDolzina = zDolzina;
    }

    static double razdalja(Mejnik m1, Mejnik m2) {
        return Math.sqrt(Math.pow((m1.zSirina - m2.zSirina) * 1852 * 60, 2) + Math.pow((m1.zDolzina - m2.zDolzina) * 1290 * 60, 2));
    }

    @Override
    public int compareTo(Mejnik o) {
        return this.ime.compareTo(o.getIme());
    }

    @Override
    public String toString() {
        Locale.setDefault(Locale.US);
        String fSirina = pretvoriDecimalno(zSirina);
        String fDolzina = pretvoriDecimalno(zDolzina);
        char poloblaSirina = zSirina < 0 ? 'S' : 'N';
        char poloblaVisina = zDolzina < 0 ? 'W' : 'E';
        return String.format("Mejnik %s (%s%c %s%c)", ime, fSirina, poloblaSirina, fDolzina, poloblaVisina);
    }

    private String pretvoriDecimalno(double zemljepisna) {
        double zacasno = Math.abs(zemljepisna);
        int stopinje = (int) Math.floor(zacasno);
        zacasno -= stopinje;
        int minute = (int) Math.floor(zacasno * 60);
        zacasno = (zacasno * 60) - minute;
        double sekunde = zacasno * 60;
        return String.format("%d*%02d'%04.1f\"", stopinje, minute, sekunde);
    }
}

class Kataster {
    private ArrayList<Mejnik> seznamMejnikov = new ArrayList<>();
    private TreeSet<String> imena = new TreeSet<>();

    public void uvozPodatkov(String datoteka) {
        try {
            Scanner sc = new Scanner(new File(datoteka));
            while (sc.hasNextLine()) {
                String[] vrstica = sc.nextLine().split(" ");
                if (!imena.contains(vrstica[0])) {
                    seznamMejnikov.add(new Mejnik(vrstica[0],
                            Double.parseDouble(vrstica[1]), Double.parseDouble(vrstica[2])));
                    imena.add(vrstica[0]);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Collections.sort(seznamMejnikov);
    }

    void izpisiRazdaljo(String m1, String m2) {
        if (!imena.contains(m1) || !imena.contains(m2)) {
            System.out.println("NAPAKA: mejnika ni v katastru.");
            return;
        }
        Mejnik m3 = najdiMejnik(m1);
        Mejnik m4 = najdiMejnik(m2);
        System.out.printf("Razdalja med %s in %s je %.1f metrov.", m1, m2, Mejnik.razdalja(m3, m4));
    }

    private Mejnik najdiMejnik(String ime) {
        for (Mejnik m : seznamMejnikov) {
            if (m.getIme().equals(ime))
                return m;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Kataster zajema naslednje mejnike:");
        for (Mejnik m : seznamMejnikov) {
            sb.append("\n").append(m.toString());
        }
        return sb.toString();
    }
}