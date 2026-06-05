package minesweeper;

import java.util.ArrayList;
import java.util.List;

public class minesweeperBoard {
    private final List<String> DIFFICULTY_LIST = List.of("Easy", "Medium", "Hard");
    private int row;
    private int column;
    private ArrayList<ArrayList<minesweeperCell>> board;
    private String difficulty;
    private int amountOfMines;


    public minesweeperBoard(String difficulty) {
        if (!this.DIFFICULTY_LIST.contains(difficulty)) {
            throw new IllegalArgumentException("Difficulty invalid!");
        }

        if (difficulty.equals("Easy")) {
            this.difficulty = difficulty;
            this.row = 10;
            this.column = 10;
            this.amountOfMines = 10;
        }

        else if (difficulty.equals("Medium")) {
            this.difficulty = difficulty;
            this.row = 10;
            this.column = 15;
            this.amountOfMines = 25;
        }

        else {
            this.difficulty = difficulty;
            this.row = 10;
            this.column = 20;
            this.amountOfMines = 40;
        }

        this.board = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < getRow(); rowIndex++) {
            ArrayList<minesweeperCell> rowList = new ArrayList<>();

            for (int columnIndex = 0; columnIndex < getColumn(); columnIndex++) {
                rowList.add(new minesweeperCell("safe", rowIndex, columnIndex));
            }

            this.board.add(rowList);
        }

        int currentAmountOfMines = 0;
        while (currentAmountOfMines < amountOfMines) {
            int randomRow = (int) (Math.random() * getRow());
            int randomColumn = (int) (Math.random() * getColumn());

            if (!this.board.get(randomRow).get(randomColumn).getType().equals("mine")) {
                this.board.get(randomRow).get(randomColumn).setType("mine");
                currentAmountOfMines++;
            }
        }

        for (int rowIndex = 0; rowIndex < this.row; rowIndex++) {
            for (int columnIndex = 0; columnIndex < this.column; columnIndex++) {
                if (this.board.get(rowIndex).get(columnIndex).isMine()) {
                    minesweeperBoardIterator boardIterator = new minesweeperBoardIterator(this, rowIndex, columnIndex);
                    while (boardIterator.hasNext()) {
                        boardIterator.next().ifPresent(cell -> cell.addOneSurroundingMine());
                    }
                }
            }
        }
    }

    public minesweeperCell getMinesweeperCell(int rowIndex, int columnIndex) {
        return this.board.get(rowIndex).get(columnIndex);
    }

    public ArrayList<ArrayList<minesweeperCell>> getMinesweeperBoard() {
        return this.board;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return column;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getAmountOfMines() {
        return this.amountOfMines;
    }
    
}
