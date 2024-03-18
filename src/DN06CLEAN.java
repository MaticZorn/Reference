import static java.lang.Math.*;

public class DN06CLEAN {
    private static int x = -1;
    private static int y = -1;

    public static void main(String[] args) {
        double koren = sqrt(args.length);

        preveriVeljavnost(koren);

        String[][] povrsina = ustvariPovrsino(koren, args);
        int vsota = ciljnaVsota(koren);
        int vX = vsotaX(povrsina, x, (int) koren);
        int vY = vsotaY(povrsina, y, (int) koren);
        if (vY != vX) {
            System.out.println("Naloge se ne da resiti.");
        }
        else System.out.printf("%d", vsota - vX);
    }


    static void preveriVeljavnost(double koren) {
        if (koren < ceil(koren)) {
            System.out.println("Napacno stevilo argumentov.");
            System.exit(0);
        }
    }

    static String[][] ustvariPovrsino(double koren, String[] args) {
        int argument = 0;
        String[][] povrsina = new String[(int) koren][(int) koren];
        for (int i = 0; i < koren; i++) {
            for (int j = 0; j < koren; j++) {
                if (args[argument].equals("0")) {
                    x = j; y = i;
                }
                povrsina[i][j] = args[argument];
                argument++;
            }
        }
        // ce sta x in y = -1 ni nicle notr
        return povrsina;
    }

    static int ciljnaVsota(double koren) {
        int n = 0;
        while (koren > 0)
            n += koren--;
        return n;
    }

    static int vsotaY(String[][] povrsina, int y, int vsota) {
        int sestevek = 0;
        for (int i = 0; i < vsota; i++) {
            sestevek += Integer.parseInt(povrsina[y][i]);
        }
        return sestevek;
    }

    static int vsotaX(String[][] povrsina, int x, int vsota) {
        int sestevek = 0;
        for (int i = 0; i < vsota; i++) {
            sestevek += Integer.parseInt(povrsina[i][x]);
        }
        return sestevek;
    }
}
