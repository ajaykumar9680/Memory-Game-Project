package mindgame.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import mindgame.GameObserver;

public class MemoryModel {

    private int[][] gridNumbers;
    private ArrayList<Integer> numbers;
    private ArrayList<GameObserver> observers;
    private int length;
    private boolean[][] opened;
    private int prevRow;
    private int prevCol;
    private int rows, cols;
    private String level;
    private int score;
    private int wrongAttempts;
    private int maxWrongAttempts;
    private int openedcount;
    private int successful;
    private String highScore;
    private HighScore high;
    protected String highScoreFileName;

    public MemoryModel(int rows, int cols, String level) {
        this.rows = rows;
        this.cols = cols;
        this.level = level;
        this.score = 0;
        this.wrongAttempts = 0;
        this.gridNumbers = new int[rows][cols];
        this.numbers = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.length = (rows * cols) / 2;
        this.opened = new boolean[rows][cols];
        this.prevRow = -1;
        this.prevCol = -1;
        successful = 0;
        this.high = new HighScore();
        this.highScoreFileName = "highscore.ser";
        setmaxWrongAttempts();
        generateRandomGrid();
        loadHighScore();
        highScore = Integer.toString(high.getScores(level));

    }

    public void sethighscore() {
    }

    private void generateRandomGrid() {
        openedcount = 0;
        for (int i = 0; i < length; i++) {
            numbers.add(i + 1);
            numbers.add(i + 1);
        }

        // Shuffle the numbers array
        Collections.shuffle(numbers);

        for (boolean[] row : opened) {
            Arrays.fill(row, true);
        }

        // Populate the grid with pairs of numbers
        int index = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                gridNumbers[row][col] = numbers.get(index);
                opened[row][col] = false;
                index++;
                if (index == 2 * length) {
                    break;
                }
            }
        }
        // for grid reference
        index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(gridNumbers[i][j] + "\t");
                index++;
                if (index == 2 * length) {
                    break;
                }
            }
            System.out.println();
        }
    }

    public int[][] getGridNumbers() {

        return gridNumbers;
    }

    public void register(GameObserver observer) {
        this.observers.add(observer);
    }

    public void unregister(GameObserver observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        for (GameObserver o : observers) {
            o.update();
        }
    }

    public void makeGuess(int row, int col) {
        if (!opened[row][col]) {
            opened[row][col] = true;
            if (prevRow < 0 && prevCol < 0) {
                prevRow = row;
                prevCol = col;
                openedcount++;
            } else {
                if (gridNumbers[prevRow][prevCol] == gridNumbers[row][col]) {
                    opened[row][col] = true;
                    this.score += 2;
                    prevRow = -1;
                    prevCol = -1;
                    openedcount++;

                } else {
                    opened[row][col] = false;
                    this.wrongAttempts += 1;
                }
            }
        }
        updateHighScore();
    }

    public int newGrid() {
        return openedcount;
    }

    public boolean[][] getOpenedArr() {
        return this.opened;
    }

    public boolean isOpened(int row, int col) {
        return this.opened[row][col];

    }

    public int getGridNumber(int row, int col) {
        return gridNumbers[row][col];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getLen() {
        return 2 * length;
    }

    public String getLevel() {
        return level;
    }

    public String getWrongAttempts() {
        return String.valueOf(wrongAttempts);
    }

    public void setWrongAttempts() {
        this.wrongAttempts = 0;
    }

    public String getScore() {
        return String.valueOf(score);

    }

    public void setScore() {
        this.score = 0;
    }

    public int getOpenTime() {
        return rows * (rows - 2);
    }

    public void setmaxWrongAttempts() {
        this.maxWrongAttempts = rows - 3;
    }

    public boolean isGameOver() {
        return wrongAttempts > maxWrongAttempts;
    }

    public int getMaxWrongAttempts() {
        return maxWrongAttempts;
    }

    public void generateNewGrid() {
        successful += 1;
        numbers.clear();
        gridNumbers = new int[rows][cols];
        opened = new boolean[rows][cols];
        System.out.println("Grid: " + (successful + 1));
        generateRandomGrid();
        notifyObservers();
    }

    public int successfulGrids() {
        return successful;
    }

    public void setSuccesfulGrids() {
        successful = -1;
    }

    public void updateHighScore() {
        int currentHighScore = high.getScores(level);
        if (score > currentHighScore) {
            high.setScores(level, score);
            saveHighScore();
        }
    }

    public void saveHighScore() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("highscore.ser"))) {
            oos.writeObject(high);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHighScore() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("highscore.ser"))) {
            high = (HighScore) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // If file doesn't exist or there's an issue reading, create a new instance
            high = new HighScore();
            high.setScores("Easy Mode", 0);
            high.setScores("Medium Mode", 0);
            high.setScores("Hard Mode", 0);
        }
    }

    public String getHighScore() {
        return highScore;
    }

}
