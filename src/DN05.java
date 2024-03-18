public class DN05 {
    public static void main(String[] args) {
        prestej(sestavi(args));
    }


    static String sestavi(String[] argumenti) {
        StringBuilder stavek = new StringBuilder();

        for (String s : argumenti) {
            stavek.append(s).append(" ");
        }
        stavek.deleteCharAt(stavek.length() - 1);
        return stavek.toString();
    }

    static void prestej(String stavek) {
        int[] abeceda = new int[26];
        boolean vsebuje = false;

        // Stetje pojava crk
        for (int i = 0; i < stavek.length(); i++) {
            char c = stavek.charAt(i);
            if ( c >= 97 && c <= 122) {
                abeceda[c - 97] += 1;
                vsebuje = true;
            }
        }
        // Izpis crk
        if (!vsebuje) System.out.printf("V nizu '%s' ni malih crk angleske abecede.", stavek);
        else {
            System.out.printf("V nizu '%s' se pojavijo crke ", stavek);
            boolean prva = true;
            for (int i = 0; i < 26; i++) {
                if (abeceda[i] > 0) {
                    if (prva) {
                        System.out.printf("%c(%d)", (char) (i + 97), abeceda[i]);
                        prva = false;
                    }
                    else System.out.printf(", %c(%d)", (char) (i + 97), abeceda[i]);
                }
            }
            System.out.print(".");
        }
    }
}
