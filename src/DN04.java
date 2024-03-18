public class DN04 {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Napačno število argumentov!");
            System.exit(0);
        }

        String inBinary = args[0];
        StringBuilder message = new StringBuilder();

        for (int i = 0; i < inBinary.length() / 8; i++) {
            //pretvorimo iz binarnega Stringa v decimalno število
            int inDecimal = Integer.parseInt(inBinary.substring(i*8, i*8 + 8), 2);
            message.append((char) inDecimal);
        }
        System.out.println(message);
    }
}
