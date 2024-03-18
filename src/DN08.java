import java.io.File;
import java.util.Locale;
import java.util.Scanner;

public class DN08 {

    private static final String[] tipiParcel = {"TRAVNATA POVRSINA", "GOZDNA POVRSINA", "OBDELOVALNA POVRSINA", "BIVALNO POSLOPJE", "INDUSTRIJSKO POSLOPJE"};
    private static final double[] vrednostiTipovParcel = {500.0, 750.0, 1000.0, 10000.0, 50000.0};

    public static void main(String[] args) {
        Locale.setDefault(Locale.GERMAN);

        switch (args[0]) {
            case "analiza": //args -> ukaz (analiza), datoteka terena (visina terena)
                int[][] teren = preberiTeren(args[1]);
                izrisTerena(teren);

                int[] visina = prestejVisine(teren);
                for (int i = 0; i < visina.length; i++) {
                    System.out.printf("Visina %d: %dx\n", i, visina[i]);
                }
                System.out.printf("Povprecna visina: %.2f", povprecnaVisina(teren));
                break;

            case "izrisi_poplavo": //args -> ukaz (izrisi_poplavo), tip poplave (visinska), datoteka terena (visina terena), visina poplave (2.1)
                int[][] teren1 = preberiTeren(args[2]);
                if (args[1].equals("visinska")) {
                    izrisiPoplavo(teren1, visinskaPoplava(teren1, Double.parseDouble(args[3])));
                }
                else {
                    izrisiPoplavo(teren1, kolicinskaPoplava(teren1, Double.parseDouble(args[3])));
                }
                break;


            case "porocilo_skode": //args -> ukaz (izrisi_poplavo), tip poplave (visinska), datoteka terena (visina terena),
                int[][] teren2 = preberiTeren(args[2]);       //datoteka kategorij (kategorija parcel), visina poplave (2.1)
                if (args[1].equals("visinska")) {
                    porociloSkode(teren2, preberiTipParcel(args[3]), visinskaPoplava(teren2, Double.parseDouble(args[4])));
                }
                else {
                    porociloSkode(teren2, preberiTipParcel(args[3]), kolicinskaPoplava(teren2, Double.parseDouble(args[4])));
                }
                break;

            case "nacrt_pobega":
                int[][] teren3 = preberiTeren(args[2]);
                if (args[1].equals("visinska")) {
                    nacrtPobega(teren3, preberiTipParcel(args[3]), visinskaPoplava(teren3,Double.parseDouble(args[4])));
                }
                else {
                    nacrtPobega(teren3, preberiTipParcel(args[3]), kolicinskaPoplava(teren3,Double.parseDouble(args[4])));
                }
                break;
        }
    }


    public static int[][] preberiTeren(String datoteka) {
        try {
            Scanner sc = new Scanner(new File(datoteka));

            int h = sc.nextInt();
            int w = sc.nextInt();
            int[][] teren = new int[h][w];

            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    teren[y][x] = sc.nextInt();
                }
            }
            return teren;
        } catch (Exception e) {
            System.out.println("Napaka " + e);
            return null;
        }
    }

    public static void izrisTerena(int[][] teren) {
        char[] simboli = new char[]{' ', '.', ':', '-', '=', '+', '*', '#', '%', '@'};
        for (int y = 0; y < teren.length; y++) {
            for (int x = 0; x < teren.length; x++) {
                System.out.print(simboli[teren[y][x]]);
            }
            System.out.println();
        }
    }

    public static double povprecnaVisina(int[][] teren) {
        double povprecnaV = 0;
        int stElementov = 0;

        for (int y = 0; y < teren.length; y++) {
            for (int x = 0; x < teren.length; x++) {
                povprecnaV += teren[y][x];
                stElementov++;
            }
        }
        povprecnaV = povprecnaV / stElementov;
        return povprecnaV;
    }

    public static int[] prestejVisine(int[][] teren) {
        int[] stVisin = new int[10];

        for (int y = 0; y < teren.length; y++) {
            for (int x = 0; x < teren.length; x++) {
                stVisin[teren[x][y]]++;
            }
        }
        return stVisin;
    }

    public static int[][] preberiTipParcel(String datoteka) {
        try {
            Scanner sc = new Scanner(new File(datoteka));

            int h = sc.nextInt();
            int w = sc.nextInt();
            int[][] tipParcele = new int[h][w];

            for (int y = 0; y < h; y++) {
                String beseda = sc.next();
                for (int x = 0; x < w; x++) {
                    for (int i = 0; i < tipiParcel.length; i++) {
                        if (tipiParcel[i].charAt(0) == beseda.charAt(x)) {
                            tipParcele[y][x] = i;
                            break;
                        }
                    }
                }
            }
            return tipParcele;
        } catch (Exception e) {
            System.out.println("Napaka " + e);
            return null;
        }
    }

    public static boolean[][] visinskaPoplava(int[][] teren, double visinaPoplave) {
        boolean[][] tabelaPoplavljenih = new boolean[teren.length][teren.length];
        for (int y = 0; y < teren.length; y++) {
            for (int x = 0; x < teren.length; x++) {
                tabelaPoplavljenih[y][x] = visinaPoplave > teren[y][x];
            }
        }
        return tabelaPoplavljenih;
    }

    public static void izrisiPoplavo(int[][] teren, boolean[][] poplava) {
        char[] simboli = new char[]{' ', '.', ':', '-', '=', '+', '*', '#', '%', '@'};

        for (int y = 0; y < poplava.length; y++) {
            for (int x = 0; x < poplava.length; x++) {
                if (poplava[y][x])
                    System.out.print('~');
                else {
                    System.out.print(simboli[teren[y][x]]);
                }
            }
            System.out.println();
        }
    }

    public static void porociloSkode(int[][] teren, int[][] tipParcele, boolean[][] poplava) {
        double[] denarnaSkoda     = new double[5];
        int[] steviloPoplavljenih = new int[5];

        for (int y = 0; y < poplava.length; y++) {
            for (int x = 0; x < poplava.length; x++) {
                if (poplava[y][x]) {
                    denarnaSkoda[tipParcele[y][x]] += vrednostiTipovParcel[tipParcele[y][x]];
                    steviloPoplavljenih[tipParcele[y][x]]++;
                }
            }
        }
        int skupnoPoplavljenih = 0;
        double skupnaSkoda     = 0;
        for (int i = 0; i < denarnaSkoda.length; i++) {
            skupnoPoplavljenih += steviloPoplavljenih[i];
            skupnaSkoda += denarnaSkoda[i];
        }

        System.out.print( "          Tip parcele    Stevilo       Ocenjena skoda\n" +
                          "-----------------------------------------------------\n");
        for (int i = 0; i < tipiParcel.length; i++) {
            System.out.printf("%21s %10d %,16.2f EUR\n", tipiParcel[i], steviloPoplavljenih[i], denarnaSkoda[i]);
        }
        System.out.print( "-----------------------------------------------------\n");
        System.out.printf("%21s %10d %,16.2f EUR", "SKUPAJ", skupnoPoplavljenih, skupnaSkoda);
    }

    public static boolean[][] kolicinskaPoplava(int[][] teren, double kolicina) {
        int[] visine = prestejVisine(teren);
        int zePoplavljena = 0;
        int visinaPoplave = 0;

        for (int i : visine) {
            if (i <= kolicina) {
                zePoplavljena += i;
                kolicina -= zePoplavljena;
                visinaPoplave++;
            }
        }
        return visinskaPoplava(teren, visinaPoplave);
    }

    public static void nacrtPobega(int[][] teren, int[][] tipParcele, boolean[][] poplava) {
        int varneHise = 0;
        int pobeg = 0;
        int pomoc = 0;
        
        for (int y = 0; y < teren.length; y++) {
            for (int x = 0; x < teren.length; x++) {
                if (!poplava[y][x] && tipParcele[y][x] == 3)
                    varneHise++;
                if (poplava[y][x] && tipParcele[y][x] == 3) {
                    if (lahkoPobegne(teren, poplava, y, x))
                        pobeg++;
                    else pomoc++;
                }
            }
        }
        System.out.printf("Varne hise: %d\nLahko pobegnejo: %d\nPotrebujejo pomoc: %d", varneHise, pobeg, pomoc);
    }
    
    public static boolean lahkoPobegne(int[][] teren, boolean[][] poplava, int y, int x) {
        boolean premik = true;

        while (premik) {
            premik = false;
            if (y - 1 >= 0 && teren[y - 1][x] > teren[y][x]) {
                y--;
                premik = true;
            }
            if (!premik && x + 1 < poplava.length && teren[y][x + 1] > teren[y][x]) {
                x++;
                premik = true;
            }
            if (!premik && y + 1 < poplava.length && teren[y + 1][x] > teren[y][x]) {
                y++;
                premik = true;
            }
            if (!premik && x - 1 >= 0 && teren[y][x - 1] > teren[y][x]) {
                x--;
            }
        }
        return !poplava[y][x];
    }
}
