import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bot {
    public static String convertCoordsToString(int x, int y) {
        return "" + (char) (y + 'a') + (char) (7 - x + '1');
    }

    public static String makeTurn(char c) {
        ArrayList<Integer> numbers = new ArrayList<Integer>(8); //{0, 1, 2, 3, 4, 5, 6, 7};
        ArrayList<Integer> numbers2 = new ArrayList<Integer>(8);
        for (int i = 0; i < 8; i++) {
            numbers.add(i);
            numbers2.add(i);
        }
        Collections.shuffle(numbers);
        Collections.shuffle(numbers2);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Game.isItCorrectTurn(numbers.get(i), numbers2.get(j), c)) {
                    // System.out.printf("%d~%d \n", numbers.get(i), numbers2.get(j));
                    System.out.println("Bot ходит: " + convertCoordsToString(numbers.get(i), numbers2.get(j)));
                    return convertCoordsToString(numbers.get(i), numbers2.get(j));
                }
            }
        }
        return "a1";
    }
}
