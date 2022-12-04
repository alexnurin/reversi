public class Player {
    public char symbol;

    Player() {
        symbol = '?';
    }

    Player(char ch) {
        symbol = ch;
    }

    public int makeTurn(char ch_y, char ch_x) {
        int y = ch_y - 'a';
        int x = 7 - (ch_x - '1');
        if (!Game.correctRange(x, y)) {
            System.out.println("Ошибка: повторите ввод. Формат: буква и цифра, например c4");
            return 0;
        }
        return Game.processTurn(x, y, symbol);
    }
}
