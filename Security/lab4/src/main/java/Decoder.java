import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Decoder {
    private List<Cryptogram> cryptograms;
    private List<Integer> keys = new ArrayList<>();
    private Map<Integer, Integer> lettersFrequency;

    public Decoder() {
        cryptograms = getCryptogramsFromFile();
        lettersFrequency = getLettersFrequency();
    }

    private List<Cryptogram> getCryptogramsFromFile() {
        List<Cryptogram> cryptograms = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream("data.txt"), StandardCharsets.UTF_8));

            String line;
            while ((line = br.readLine()) != null) {
                cryptograms.add(new Cryptogram(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cryptograms;
    }

    private List<Integer> findPossibleKeys() {
        if (!keys.isEmpty()) {
            System.out.println(keys);
            return keys;
        }

        Set<Integer> candidateKeys = new HashSet<>();
        List<Cryptogram> activeCryptograms;

        int positionKey;
        int max;
        int counter;
        int possible;
        int mostPossible;
        int longestCryptogramSize = getLongestCryptogramSize();

        for(int i = 0; i < longestCryptogramSize; i++){
            activeCryptograms = getActiveCryptograms(i);

            for(Cryptogram cryptogram : activeCryptograms){
                for(Integer letterAsciiCode : lettersFrequency.keySet()){
                    candidateKeys.add(letterAsciiCode ^ cryptogram.getCharacterAsciiCode(i));
                }
            }

            positionKey = 32;
            max = 0;
            mostPossible = 0;

            for(int key : candidateKeys) {
                counter = 0;
                possible = 0;

                for(Cryptogram cryptogram : activeCryptograms) {
                    int letterAsciiCode = cryptogram.getCharacterAsciiCode(i) ^ key;
                    if(lettersFrequency.containsKey(letterAsciiCode)) {
                        counter++;
                        possible += lettersFrequency.get(letterAsciiCode);
                    }
                }

                if(counter == max) {
                    if (possible > mostPossible) {
                        positionKey = key;
                        mostPossible = possible;
                    }
                } else if(counter > max){
                    positionKey = key;
                    mostPossible = possible;
                    max = counter;
                }
            }
            keys.add(positionKey);
        }
        return keys;
    }

    public List<Integer> getKeys(){
        return findPossibleKeys();
    }

    public void printAllCryptograms() {
        for(Cryptogram cryptogram : cryptograms) {
            for(int i = 0; i < cryptogram.getCryptogramSize(); i++) {
                System.out.print((char) (cryptogram.getCharacterAsciiCode(i) ^ keys.get(i)));
            }
            System.out.println(Cryptogram.WHITESPACE);
        }
    }

    private List<Cryptogram> getActiveCryptograms(int pos) {
        List<Cryptogram> activeCryptograms = new ArrayList<>();

        for(Cryptogram cryptogram : cryptograms) {
            if(pos < cryptogram.getCryptogramSize()) {
                activeCryptograms.add(cryptogram);
            }
        }
        return activeCryptograms;
    }

    private int getLongestCryptogramSize(){
        Optional<Cryptogram> longest = cryptograms.stream()
                .max(Comparator.comparing(Cryptogram::getCryptogramSize));

        return longest.map(Cryptogram::getCryptogramSize).orElse(0);
    }

    private Map<Integer, Integer> getLettersFrequency(){
        HashMap<Integer, Integer> lettersFrequency = new HashMap<>();

        for (int i = 48; i <= 57; i++) {
            lettersFrequency.put(i, 10);
        }

        for (int i = 65; i < 91; i++) {
            lettersFrequency.put(i, 20);
        }

        lettersFrequency.put((int) ' ', 900 );
        lettersFrequency.put((int) 'a', 990 );
        lettersFrequency.put((int) 'e', 877 );
        lettersFrequency.put((int) 'o', 860 );
        lettersFrequency.put((int) 'i', 821 );
        lettersFrequency.put((int) 'z', 647 );
        lettersFrequency.put((int) 'n', 572 );
        lettersFrequency.put((int) 's', 498 );
        lettersFrequency.put((int) 'w', 465 );
        lettersFrequency.put((int) 'r', 469 );
        lettersFrequency.put((int) 'c', 436 );
        lettersFrequency.put((int) 't', 398 );
        lettersFrequency.put((int) 'y', 376 );
        lettersFrequency.put((int) 'l', 392 );
        lettersFrequency.put((int) 'k', 351 );
        lettersFrequency.put((int) 'd', 325 );
        lettersFrequency.put((int) 'p', 313 );
        lettersFrequency.put((int) 'm', 280 );
        lettersFrequency.put((int) 'j', 228 );
        lettersFrequency.put((int) 'u', 250 );
        lettersFrequency.put((int) 'b', 147 );
        lettersFrequency.put((int) 'g', 142 );
        lettersFrequency.put((int) 'h', 108 );
        lettersFrequency.put((int) 'f', 31 );
        lettersFrequency.put((int) 'x', 8 );
        lettersFrequency.put((int) 'v', 4 );
        lettersFrequency.put((int) ',', 149 );
        lettersFrequency.put((int) '.', 84 );
        lettersFrequency.put((int) '?', 22 );
        lettersFrequency.put((int) '!', 2 );
        lettersFrequency.put((int) ':', 1 );
        lettersFrequency.put((int) ';', 1 );
        lettersFrequency.put((int) '(', 1 );
        lettersFrequency.put((int) ')', 1 );
        lettersFrequency.put((int) '"', 1 );
        lettersFrequency.put((int) '-', 1 );
        lettersFrequency.put((int) '_', 1 );

        return lettersFrequency;
    }
}