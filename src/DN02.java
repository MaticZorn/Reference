public class DN02 {
    public static void main(String[] args) {
        int stArgumentov = args.length - 1;

        if (args[0].equals("1")) {
            for (int i = 1; i <= stArgumentov; i++) {
                System.out.print(args[i]);
                if (i < stArgumentov) {
                    System.out.print(", ");
                }
            }
        }
        else {
            String stavek = "";

            for (int i = 1; i <= stArgumentov; i++) {
                stavek += args[i];
                if (i < stArgumentov) {
                    stavek += " ";
                }
            }
            for (int j = 0; j < stavek.length() + 4; j++) {
                System.out.print("*");
            }
            System.out.print("\n* " + stavek + " *\n");
            for (int j = 0; j < stavek.length() + 4; j++) {
                System.out.print("*");
            }
        }
    }
}
