public class Cifrado {
    public static String cifrado(String text, int shift){
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            ch = (char) (ch + shift);

            result.append(ch);
        }

        return result.toString();
    }
}
