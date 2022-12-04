import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> highscore_names = new ArrayList<>(100);
        ArrayList<Integer> highscore_scores = new ArrayList<>(100);
        int turnBOT = -1;
        System.out.println("""
                Выберите режим:
                0 - игра против лёгкого компьютера, начинает компьютер
                1 - игра против лёгкого компьютера, начинает игрок
                2 - игра против игрока
                3 - выход
                """);
        String command = reader.readLine();
        if (command.equals("0")) {
            turnBOT = 0;
        }
        if (command.equals("1")) {
            turnBOT = 1;
        }
        while (!command.equals("3")) {
            Player First = new Player('X');
            Player Second = new Player('#');
            Player[] players = {First, Second};
            int turnNow = 0;
            Game b = new Game(First, Second);
            b.printField(players[turnNow].symbol);

            int result = 0;
            do {
                String coordinates;
                if (turnNow == turnBOT) {
                    coordinates = Bot.makeTurn(players[turnNow].symbol);
                } else {
                    System.out.println("Введите ваш ход в формате буквы + цифра слитно, например a1");
                    coordinates = reader.readLine();
                    if (coordinates.length() != 2) {
                        System.out.println("Ошибка: повторите ввод. Формат: буква и цифра, например c4");
                        continue;
                    }
                }
                result = players[turnNow].makeTurn(coordinates.charAt(0), coordinates.charAt(1));
                if (result == 0) {
                    turnNow = (turnNow + 1) % 2;
                }
                if (result == 1 || result == 2) {
                    break;
                }
                if (Game.noPlace(players[turnNow].symbol)) {
                    if (Game.noPlace(players[(turnNow + 1) % 2].symbol)) {
                        System.out.print("Обоим игрокам некуда ходить!\n");
                        break;
                    } else {
                        System.out.printf("Ход %c пропущен, так как некуда ходить.\n", players[turnNow].symbol);
                        turnNow = (turnNow + 1) % 2;
                    }
                }
                System.out.printf("Ход: %c\n", players[turnNow].symbol);
                b.printField(players[turnNow].symbol);
            } while (result < 1);

            b.printField(players[turnNow].symbol);
            result = Game.whoIsWinner(players[0].symbol, players[1].symbol);
            int playerWin;
            if (result > 0) {
                playerWin = 1;
            } else {
                result = -result;
                playerWin = 2;
            }
            if (result == 0) {
                System.out.print("Ничья!\n");
            } else {
                System.out.printf("Победа игрока номер %d!\n", playerWin);
                System.out.printf("Количество очков: %d\n\n", result);
                if (playerWin != turnBOT) {
                    System.out.println("Введите ваше имя, чтобы сохранить результат!");
                    String name = reader.readLine();
                    highscore_scores.add(result);
                    highscore_names.add(name);
                }
            }
            do {
                System.out.println("""
                        Выберите режим:
                        0 - игра против лёгкого компьютера, начинает компьютер
                        1 - игра против лёгкого компьютера, начинает игрок
                        2 - игра против игрока
                        3 - выход
                        4 - рекорды
                        """);
                command = reader.readLine();
                if (command.equals("4")){
                    for (int i = 0; i < highscore_names.size(); i++){
                        System.out.printf("%s : %d\n", highscore_names.get(i), highscore_scores.get(i));
                    }
                }
            } while (command.equals("4"));
            turnBOT = switch (command) {
                case "0" -> 0;
                case "1" -> 1;
                case "2" -> -1;
                default -> turnBOT;
            };
        }
    }
}