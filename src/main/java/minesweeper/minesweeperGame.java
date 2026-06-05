package minesweeper;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class minesweeperGame {
    private minesweeperBoard board;
    private String name; 
    private LocalTime startTime; 
    private LocalTime stopTime; 
    private int safeCells;
    private int safeCellsOpened;
    private boolean isGameFinished;
    private String gameTime;
    private String outcome;
    private List<ArrayList<String>> leaderboard;
    private final minesweeperFileHandler fileHandler;

    public minesweeperGame(String name, String difficulty) {
        this.name = name;
        
        this.board = new minesweeperBoard(difficulty);
        this.fileHandler = new minesweeperFileHandler(difficulty);
        this.leaderboard = fileHandler.readFromFile();
        this.startTime = LocalTime.now();
        this.safeCells = board.getRow() * board.getColumn() - board.getAmountOfMines();
    }

    public minesweeperGame(String name, String difficulty, String filepath) {
        this.name = name;
        
        this.board = new minesweeperBoard(difficulty);
        this.fileHandler = new minesweeperFileHandler(filepath);
        this.leaderboard = fileHandler.readFromFile();
        this.startTime = LocalTime.now();
        this.safeCells = board.getRow() * board.getColumn() - board.getAmountOfMines();
    }

    public void openCell(minesweeperCell cell) {
        cell.open();

        if (cell.isMine()) {
            finishGame("lost");
        }

        else {
            addSafeCellsOpened();
            checkIfGameWin();
        }
        
    }

    public void checkIfGameWin() {
        if (getSafeCells() == getSafeCellsOpened()) {
            finishGame("win");
        }
        
        else {
            return;
        }
    }

    public void finishGame(String outcome) {
        this.isGameFinished = true;
        this.outcome = outcome;
        setStopTime();
        
        if (outcome.equals("win")) {
            ArrayList<String> result = new ArrayList<>();
            result.add(getName());
            result.add(getGameTime(getStartTime(), getStopTime()));
            this.leaderboard.add(result);

            this.leaderboard = getSortedLeaderboard();
            fileHandler.writeToFile(this.leaderboard);
        }
    }

    public boolean isGameFinished() {
        return this.isGameFinished;
    }

    public void clearLeaderboard() {
        this.leaderboard.clear();
        fileHandler.writeToFile(leaderboard);
    }

    public String getGameTime(LocalTime startTime, LocalTime stopTime) {
        long minutes = ChronoUnit.MINUTES.between(startTime, stopTime);
        long seconds = ChronoUnit.SECONDS.between(startTime, stopTime);
        
        this.gameTime = minutes + ":" + seconds;

        return this.gameTime;
    }

    public minesweeperBoard getBoard() {
        return this.board;
    }

    public String getName() {
        return name;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getStopTime() {
        return this.stopTime;
    }

    public void setStopTime() {
        this.stopTime = LocalTime.now();
    }

    public int getSafeCells() {
        return this.safeCells;
    }

    public void addSafeCellsOpened() {
        this.safeCellsOpened++;
    }

    public int getSafeCellsOpened() {
        return this.safeCellsOpened;
    }

    public List<ArrayList<String>> getSortedLeaderboard() {
        List<ArrayList<String>> sortedLeaderboard = this.leaderboard.stream()
                .sorted((t1, t2) -> t1.getLast().compareTo(t2.getLast()))
                .toList();
        return Collections.unmodifiableList(sortedLeaderboard);
    }

    public List<ArrayList<String>> getLeaderboard() {
        return Collections.unmodifiableList(this.leaderboard);
    }

    public List<ArrayList<String>> getTop10() {
        List<ArrayList<String>> top10 = this.leaderboard.stream().limit(9).toList();
        return Collections.unmodifiableList(top10);
    }

    public String getGameResult() {
        return getName() + ": " + getGameTime(startTime, stopTime);
    }

    public String getOutcome() {
        return this.outcome;
    }
    
    public static void main(String[] args) {
        minesweeperGame ms = new minesweeperGame("hannah", "easy", "easyLeaderboard.txt");
        // ms.openCell(0, 0);
        // ms.openCell(5, 6);
        // ms.openCell(7, 7);
        // ms.openCell(2, 2);
        // ms.openCell(9, 9);
        // ms.openCell(5, 6);
        // // ms.clearLeaderboard();
        // ms.finishGame();
        System.out.println(ms.board.getMinesweeperCell(0, 0).getSurroundingMines());
    }
    
}
