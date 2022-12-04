import java.util.ArrayList;

public class Game {
    static char[][] field;

    Game() {
        generateField();
        field[3][3] = '1';
        field[4][4] = '1';
        field[3][4] = '2';
        field[4][3] = '2';
    }

    Game(Player a, Player b) {
        generateField();
        field[3][3] = a.symbol;
        field[4][4] = a.symbol;
        field[3][4] = b.symbol;
        field[4][3] = b.symbol;
    }

    private void generateField() {
        char[] line = {'·', '·', '·', '·', '·', '·', '·', '·'};
        field = new char[][]{line.clone(), line.clone(), line.clone(), line.clone(),
                line.clone(), line.clone(), line.clone(), line.clone()};
    }

    public static boolean correctRange(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    private static int processDelta(int x, int y, int delta_x, int delta_y, char c, boolean edit) {
        int x_ptr, y_ptr, local_score = 0;
        x_ptr = x + delta_x;
        y_ptr = y + delta_y;
        while (correctRange(x_ptr, y_ptr) && field[x_ptr][y_ptr] != '·' && field[x_ptr][y_ptr] != c) {
            x_ptr += delta_x;
            y_ptr += delta_y;
            local_score++;
        }
        if (correctRange(x_ptr, y_ptr) && field[x_ptr][y_ptr] == c) {
            if (!edit) {
                if (local_score > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
            while (x_ptr != x || y_ptr != y) {
                field[x_ptr][y_ptr] = c;
                x_ptr -= delta_x;
                y_ptr -= delta_y;
            }
            return local_score;
        }
        return 0;
    }

    public static int processTurn(int x, int y, char c) {
        if (field[x][y] != '·') {
            System.out.println("Ошибка: невозможный ход, измените координаты.");
            return -1;
        }
        int score = 0;
        score += processDelta(x, y, -1, -1, c, true);
        score += processDelta(x, y, -1, 0, c, true);
        score += processDelta(x, y, -1, 1, c, true);
        score += processDelta(x, y, 0, -1, c, true);
        score += processDelta(x, y, 0, 1, c, true);
        score += processDelta(x, y, 1, -1, c, true);
        score += processDelta(x, y, 1, 0, c, true);
        score += processDelta(x, y, 1, 1, c, true);
        if (score == 0) {
            System.out.println("Ошибка: невозможный ход, измените координаты.");
            return -1;
        }
        field[x][y] = c;
        return 0;
    }

    public static boolean isItCorrectTurn(int x, int y, char c) {
        if (field[x][y] == '·') {
            return processDelta(x, y, -1, -1, c, false) > 0 ||
                    processDelta(x, y, -1, 0, c, false) > 0 ||
                    processDelta(x, y, -1, 1, c, false) > 0 ||
                    processDelta(x, y, 0, -1, c, false) > 0 ||
                    processDelta(x, y, 0, 1, c, false) > 0 ||
                    processDelta(x, y, 1, -1, c, false) > 0 ||
                    processDelta(x, y, 1, 0, c, false) > 0 ||
                    processDelta(x, y, 1, 1, c, false) > 0;
        }
        return false;
    }

    public static boolean noPlace(char c) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (field[x][y] == '·') {
                    if (processDelta(x, y, -1, -1, c, false) > 0 ||
                            processDelta(x, y, -1, 0, c, false) > 0 ||
                            processDelta(x, y, -1, 1, c, false) > 0 ||
                            processDelta(x, y, 0, -1, c, false) > 0 ||
                            processDelta(x, y, 0, 1, c, false) > 0 ||
                            processDelta(x, y, 1, -1, c, false) > 0 ||
                            processDelta(x, y, 1, 0, c, false) > 0 ||
                            processDelta(x, y, 1, 1, c, false) > 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static int whoIsWinner(char c_x, char c_y) {
        int score_x = 0, score_y = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (field[x][y] == c_x) {
                    score_x++;
                } else if (field[x][y] == c_y) {
                    score_y++;
                }
            }
        }
        return score_x - score_y;
    }

    public void printField(char c) {
        System.out.println("   a  b  c  d  e  f  g  h   ");
        ArrayList<String> hints = new ArrayList<>(65);
        for (int i = 0; i < field.length; i++) {
            System.out.printf("%d  ", 8 - i);
            char[] line = field[i];
            for (int j = 0; j < line.length; j++) {
                if (field[i][j] == '·' && isItCorrectTurn(i, j, c)) {
                    System.out.print("?  ");
                    hints.add(Bot.convertCoordsToString(i, j));
                } else {
                    System.out.printf("%c  ", line[j]);
                }
            }
            System.out.printf("%d", 8 - i);
            System.out.println();
        }
        System.out.println("   a  b  c  d  e  f  g  h   ");
        System.out.println("Подсказка. Доступные ходы: ");
        for (String hint : hints) {
            System.out.printf("%s ", hint);
        }
        System.out.println();
    }
}
