package minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class minesweeperTest {
    private minesweeperGame easyGame;
    private minesweeperGame mediumGame;
    private minesweeperGame hardGame;

    @BeforeEach
    public void setUp() {
        this.easyGame = new minesweeperGame("hannah", "Easy");
        this.mediumGame = new minesweeperGame("hannah", "Medium");
        this.hardGame = new minesweeperGame("hannah", "Hard");
    }

    @Test
    @DisplayName("Check that displayed surrounding mines are correct")
    public void testSurroundingMines() {
        minesweeperBoard board = hardGame.getBoard();

        for (int row = 0; row < board.getRow(); row++) {
            for (int column = 0; column < board.getColumn(); column++) {
                int actualSurroundingMine = 0;
                
                minesweeperBoardIterator boardIterator = new minesweeperBoardIterator(board, row, column);

                while (boardIterator.hasNext()) {
                    if (boardIterator.next().isEmpty()) {
                        return;
                    }

                    else if (boardIterator.next().get().isMine()) {
                        actualSurroundingMine++;
                    }
                }

                assertEquals(actualSurroundingMine, board.getMinesweeperCell(row, column).getSurroundingMines());
            }
        }
    }

        @Test
    @DisplayName("Check that iterator is correct")
    public void testBoardIterator() {
        minesweeperBoard board = hardGame.getBoard();

        minesweeperBoardIterator iterator = new minesweeperBoardIterator(hardGame.getBoard(), 1, 1);

        minesweeperCell expectedElement1 = board.getMinesweeperCell(0, 0);
        Assertions.assertEquals(expectedElement1, iterator.next().get());

        minesweeperCell expectedElement2 = board.getMinesweeperCell(0, 1);
        Assertions.assertEquals(expectedElement2, iterator.next().get());

        minesweeperCell expectedElement3 = board.getMinesweeperCell(0, 2);
        Assertions.assertEquals(expectedElement3, iterator.next().get());

        minesweeperCell expectedElement4 = board.getMinesweeperCell(1, 0);
        Assertions.assertEquals(expectedElement4, iterator.next().get());
        
        minesweeperCell expectedElement5 = board.getMinesweeperCell(1, 2);
        Assertions.assertEquals(expectedElement5, iterator.next().get());
        
        minesweeperCell expectedElement6 = board.getMinesweeperCell(2, 0);
        Assertions.assertEquals(expectedElement6, iterator.next().get());
        
        minesweeperCell expectedElement7 = board.getMinesweeperCell(2, 1);
        Assertions.assertEquals(expectedElement7, iterator.next().get());
        
        minesweeperCell expectedElement8 = board.getMinesweeperCell(2, 2);
        Assertions.assertEquals(expectedElement8, iterator.next().get());   
    }

    @Test
    @DisplayName("Check if opening all safe cells leads to game finish and win")
    public void testWinGameFinish() {
        minesweeperBoard board = mediumGame.getBoard();

        for (ArrayList<minesweeperCell> row : board.getMinesweeperBoard()) {
            for (minesweeperCell minesweeperCell : row) {
                if (minesweeperCell.isSafe()) {
                    Assertions.assertFalse(mediumGame.isGameFinished());
                    mediumGame.openCell(minesweeperCell);
                }
            }
        }

        Assertions.assertTrue(mediumGame.isGameFinished());
        Assertions.assertEquals("win", mediumGame.getOutcome());
    }

    @Test
    @DisplayName("Check if opening mine cell leads to game finish and lost")
    public void testLostGameFinish() {
        minesweeperBoard board = hardGame.getBoard();

        Assertions.assertFalse(hardGame.isGameFinished());
        for (ArrayList<minesweeperCell> row : board.getMinesweeperBoard()) {
            for (minesweeperCell minesweeperCell : row) {
                if (minesweeperCell.isMine()) {
                    hardGame.openCell(minesweeperCell);
                    Assertions.assertTrue(hardGame.isGameFinished());
                }
            }
        }

        Assertions.assertEquals("lost", hardGame.getOutcome());
    }

    @Test
    @DisplayName("Check if filehandler works")
    public void testFileWriteAndRead() {
        minesweeperFileHandler filehandler = new minesweeperFileHandler("testLeaderboard.txt");

        List<ArrayList<String>> expectedLeaderboard = new ArrayList<>();
        expectedLeaderboard.add(new ArrayList<>(List.of("hannah", "2:100")));
        expectedLeaderboard.add(new ArrayList<>(List.of("hannah", "1:300")));
        expectedLeaderboard.add(new ArrayList<>(List.of("hannah", "4:200")));

        filehandler.writeToFile(expectedLeaderboard);

        List<ArrayList<String>> loadedLeaderboard = filehandler.readFromFile();

        Assertions.assertEquals(loadedLeaderboard, expectedLeaderboard);
    }

    @Test
    @DisplayName("Check if leaderboard gets sorted")
    public void testSortedLeaderboard() {
        minesweeperFileHandler filehandler = new minesweeperFileHandler("Hard");

        List<ArrayList<String>> hardLeaderboard = new ArrayList<>();
        hardLeaderboard.add(new ArrayList<>(List.of("hannah", "2:100")));
        hardLeaderboard.add(new ArrayList<>(List.of("hannah", "1:300")));
        hardLeaderboard.add(new ArrayList<>(List.of("hannah", "4:200")));
        filehandler.writeToFile(hardLeaderboard);


        List<ArrayList<String>> expectedSortedLeaderboard = new ArrayList<>();
        expectedSortedLeaderboard.add(new ArrayList<>(List.of("hannah", "1:300")));
        expectedSortedLeaderboard.add(new ArrayList<>(List.of("hannah", "2:100")));
        expectedSortedLeaderboard.add(new ArrayList<>(List.of("hannah", "4:200")));

        hardGame.finishGame("lost");
        Assertions.assertEquals(expectedSortedLeaderboard, hardGame.getSortedLeaderboard());
    }

    @Test
    @DisplayName("Checks that game information is as spesified easy")
    public void testConstructorEasy() {
        minesweeperBoard board = easyGame.getBoard();
        ArrayList<ArrayList<minesweeperCell>> minesweeperBoard = this.easyGame.getBoard().getMinesweeperBoard();

        int amountOfRows = minesweeperBoard.size();
        Assertions.assertEquals(10, amountOfRows);
        Assertions.assertEquals(10, board.getRow());

        for (ArrayList<minesweeperCell> row : minesweeperBoard) {
            int amountOfColumns = row.size();
            Assertions.assertEquals(10, amountOfColumns);
            Assertions.assertEquals(10, board.getColumn());
        }

        int actualAmountOfMines = 0;
        for (ArrayList<minesweeperCell> row : minesweeperBoard) {
            for (minesweeperCell cell : row) {
                if (cell.getType().equals("mine")) {
                    actualAmountOfMines++;
                }
            }
        }
        Assertions.assertEquals(10, actualAmountOfMines);
        Assertions.assertEquals(10, board.getAmountOfMines());
    }

    @Test
    @DisplayName("Checks that game information is as spesified medium")
    public void testConstructorMedium() {
        minesweeperBoard board = mediumGame.getBoard();
        ArrayList<ArrayList<minesweeperCell>> minesweeperBoard = this.mediumGame.getBoard().getMinesweeperBoard();

        int amountOfRows = minesweeperBoard.size();
        Assertions.assertEquals(10, amountOfRows);
        Assertions.assertEquals(10, board.getRow());

        for (ArrayList<minesweeperCell> row : minesweeperBoard) {
            int amountOfColumns = row.size();
            Assertions.assertEquals(15, amountOfColumns);
            Assertions.assertEquals(15, board.getColumn());
        }

        int actualAmountOfMines = 0;
        for (ArrayList<minesweeperCell> row : minesweeperBoard) {
            for (minesweeperCell cell : row) {
                if (cell.getType().equals("mine")) {
                    actualAmountOfMines++;
                }
            }
        }
        Assertions.assertEquals(25, actualAmountOfMines);
        Assertions.assertEquals(25, board.getAmountOfMines());
    }

    @Test
    @DisplayName("Checks that game information is as spesified hard")
    public void testConstructorHard() {
        minesweeperBoard board = hardGame.getBoard();
        ArrayList<ArrayList<minesweeperCell>> minesweeperBoard = this.hardGame.getBoard().getMinesweeperBoard();

        int amountOfRows = minesweeperBoard.size();
        Assertions.assertEquals(10, amountOfRows);
        Assertions.assertEquals(10, board.getRow());

        for (ArrayList<minesweeperCell> row : minesweeperBoard) {
            int amountOfColumns = row.size();
            Assertions.assertEquals(20, amountOfColumns);
            Assertions.assertEquals(20, board.getColumn());
        }

        int actualAmountOfMines = 0;
        for (ArrayList<minesweeperCell> row : minesweeperBoard) {
            for (minesweeperCell cell : row) {
                if (cell.getType().equals("mine")) {
                    actualAmountOfMines++;
                }
            }
        }
        Assertions.assertEquals(40, actualAmountOfMines);
        Assertions.assertEquals(40, board.getAmountOfMines());
    }
}
