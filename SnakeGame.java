import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class SnakeGame {
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final char EMPTY_CELL = '-';
    private static final char SNAKE_CELL = 'O';
    private static final char FOOD_CELL = '*';

    private static char[][] board = new char[ROWS][COLS];
    private static Queue<Point> snake = new LinkedList<>();
    private static Point food;

    public static void main(String[] args) {
        initializeGame();
        displayBoard();

        Scanner scanner = new Scanner(System.in);
        char direction = 'D'; // Default direction: Right

        while (true) {
            System.out.print("Enter direction (W/A/S/D): ");
            char input = scanner.next().toUpperCase().charAt(0);

            if (input == 'W' || input == 'A' || input == 'S' || input == 'D') {
                direction = input;
            }
            scanner.close();
            moveSnake(direction);
            displayBoard();

            if (checkCollision()) {
                System.out.println("Game Over! Your score: " + (snake.size() - 1));
                break;
            }

            if (snake.peek().equals(food)) {
                eatFood();
                placeFood();
            }
        }
    }

    private static void initializeGame() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }

        snake.clear();
        snake.add(new Point(0, 0));
        placeFood();
    }

    private static void displayBoard() {
        System.out.println("Score: " + (snake.size() - 1));
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void moveSnake(char direction) {
        Point head = snake.peek();
        Point newHead;

        switch (direction) {
            case 'W':
                newHead = new Point(head.row - 1, head.col);
                break;
            case 'A':
                newHead = new Point(head.row, head.col - 1);
                break;
            case 'S':
                newHead = new Point(head.row + 1, head.col);
                break;
            case 'D':
                newHead = new Point(head.row, head.col + 1);
                break;
            default:
                return;
        }

        snake.add(newHead);
        if (!newHead.equals(food)) {
            Point tail = snake.poll();
            board[tail.row][tail.col] = EMPTY_CELL;
        }

        if (isValidMove(newHead)) {
            board[newHead.row][newHead.col] = SNAKE_CELL;
        }
    }

    private static boolean isValidMove(Point point) {
        return point.row >= 0 && point.row < ROWS + 1 && point.col >= 0 && point.col < COLS + 1;
    }
    private static boolean checkCollision() {
        Point head = snake.peek();
        return !isValidMove(head);
    }

    private static void placeFood() {
        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(ROWS);
            col = random.nextInt(COLS);
        } while (board[row][col] != EMPTY_CELL && !snake.contains(new Point(row, col)));

        food = new Point(row, col);
        board[row][col] = FOOD_CELL;
    }

    private static void eatFood() {
        snake.add(food);
    }

    private static class Point {
        private int row;
        private int col;

        Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Point point = (Point) obj;
            return row == point.row && col == point.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
    
}
