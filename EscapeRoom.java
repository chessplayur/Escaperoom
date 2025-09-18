import java.util.Scanner;

public class EscapeRoom {

    public static void main(String[] args) {
        // Welcome message
        System.out.println("Welcome to EscapeRoom!");
        System.out.println("Goal: Reach the far right side of the board, avoid traps and walls, and collect prizes.");
        System.out.println("Type 'help' to see all commands.\n");

        GameGUI game = new GameGUI();
        game.createBoard();

        Scanner in = new Scanner(System.in);
        int score = 0;
        boolean play = true;

        while (play) {
            System.out.print("\nEnter command: ");
            String input = in.nextLine().trim().toLowerCase();

            switch (input) {
                case "right":
                case "r":
                    score += game.movePlayer(60, 0); break;
                case "left":
                case "l":
                    score += game.movePlayer(-60, 0); break;
                case "up":
                case "u":
                    score += game.movePlayer(0, -60); break;
                case "down":
                case "d":
                    score += game.movePlayer(0, 60); break;

                case "jump":
                case "jr":
                    score += game.movePlayer(120, 0); break;
                case "jumpleft":
                case "jl":
                    score += game.movePlayer(-120, 0); break;
                case "jumpup":
                case "ju":
                    score += game.movePlayer(0, -120); break;
                case "jumpdown":
                case "jd":
                    score += game.movePlayer(0, 120); break;

                case "pickup":
                case "p":
                    score += game.pickupPrize(); break;

                case "spring":
                case "s":
                    // try all 4 adjacent directions for traps
                    boolean trapFound = false;
                    int[] dx = {60, -60, 0, 0};
                    int[] dy = {0, 0, -60, 60};
                    for (int i = 0; i < 4; i++) {
                        if (game.isTrap(dx[i], dy[i])) {
                            score += game.springTrap(dx[i], dy[i]);
                            trapFound = true;
                            break;
                        }
                    }
                    if (!trapFound) {
                        System.out.println("No traps nearby to spring.");
                        score -= 5;
                    }
                    break;

                case "replay":
                    score += game.replay();
                    System.out.println("Game restarted."); break;

                case "quit":
                case "q":
                    System.out.print("Are you sure you want to quit? (y/n): ");
                    String confirm = in.nextLine().trim().toLowerCase();
                    if (confirm.equals("y")) {
                        play = false;
                    }
                    break;

                case "help":
                case "?":
                    System.out.println("""
                    Commands:
                    - right / r
                    - left / l
                    - up / u
                    - down / d
                    - jump / jr (right), jl (left), ju (up), jd (down)
                    - pickup / p
                    - spring / s
                    - replay
                    - quit / q
                    - help / ?
                    """);
                    break;

                default:
                    System.out.println("Unknown command. Type 'help' to see valid commands.");
                    score -= 1;
            }
        }

        score += game.endGame();

        System.out.println("\nGame Over!");
        System.out.println("Final Score: " + score);
        System.out.println("Steps Taken: " + game.getSteps());
    }
}
