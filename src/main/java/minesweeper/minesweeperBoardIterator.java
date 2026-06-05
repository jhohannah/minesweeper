package minesweeper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class minesweeperBoardIterator implements Iterator<Optional<minesweeperCell>>{
    private ArrayList<ArrayList<minesweeperCell>> board;
    private int boardRowIndex;
    private int boardColumnIndex;
    private int rowIndex;
    private int columnIndex;
    private int rowBefore;
    private int rowAfter;
    private int columnBefore;
    private int columnAfter;
    private int iteration;

    public minesweeperBoardIterator(minesweeperBoard minesweeperBoard, int rowIndex, int columnIndex) {
        this.board = minesweeperBoard.getMinesweeperBoard();
        this.boardRowIndex = minesweeperBoard.getRow() - 1;
        this.boardColumnIndex = minesweeperBoard.getColumn() - 1;

        this.rowIndex = rowIndex;
        this.rowBefore = rowIndex - 1;
        this.rowAfter = rowIndex + 1;

        this.columnIndex = columnIndex;
        this.columnBefore = columnIndex - 1;
        this.columnAfter = columnIndex + 1;

        this.iteration = 1;
    }

    @Override
    public boolean hasNext() {
        if (iteration <= 8) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public Optional<minesweeperCell> next() {
        if ((iteration == 1) && (rowBefore >= 0) && (columnBefore >= 0)) {
                iteration++;
                return Optional.of(board.get(rowIndex - 1).get(columnIndex - 1));
            }

            else if ((iteration == 2) && (rowBefore >= 0)) {
                iteration++;
                return Optional.of(board.get(rowIndex - 1).get(columnIndex));
            }

            else if ((iteration == 3) && (rowBefore >= 0) && (columnAfter <= boardColumnIndex)) {
                iteration++;
                return Optional.of(board.get(rowIndex - 1).get(columnIndex + 1));
            }

            else if ((iteration == 4) && (columnBefore >= 0)) {
                iteration++;
                return Optional.of(board.get(rowIndex).get(columnIndex - 1));
            }

            else if ((iteration == 5) && (columnAfter <= boardColumnIndex)) {
                iteration++;
                return Optional.of(board.get(rowIndex).get(columnIndex + 1));
            }

            else if ((iteration == 6) && (rowAfter <= boardRowIndex) && (columnBefore >= 0)) {
                iteration++;
                return Optional.of(board.get(rowIndex + 1).get(columnIndex - 1));
            }

            else if ((iteration == 7) && (rowAfter <= boardRowIndex)) {
                iteration++;
                return Optional.of(board.get(rowIndex + 1).get(columnIndex));
            }

            else if ((iteration == 8) && (rowAfter <= boardRowIndex) && (columnAfter <= boardColumnIndex)) {
                iteration++;
                return Optional.of(board.get(rowIndex + 1).get(columnIndex + 1));
            } 
            
            else {
                iteration ++; 
                return Optional.empty();
            }
    }
}
