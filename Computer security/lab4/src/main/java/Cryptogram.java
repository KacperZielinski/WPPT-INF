import java.util.ArrayList;

public class Cryptogram {
    public static String WHITESPACE = " ";
    public static int WHITESPACE_ASCII = 32;

    private ArrayList<Integer> cryptogramChars = new ArrayList<>();

    public Cryptogram(String cryptogram){
        convertStringToArrayOfAsciiCodes(cryptogram);
    }

    private void convertStringToArrayOfAsciiCodes(String cryptogram) {
        for(String character : cryptogram.split(WHITESPACE)){
            cryptogramChars.add(Integer.parseInt(character, 2));
        }
    }

    public int getCharacterAsciiCode(int pos){
        if(pos >= 0 && pos < getCryptogramSize()) {
            return cryptogramChars.get(pos);
        } else {
            return WHITESPACE_ASCII;
        }
    }

    public int getCryptogramSize(){
        return cryptogramChars.size();
    }
}
