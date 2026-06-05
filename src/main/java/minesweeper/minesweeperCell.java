package minesweeper;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class minesweeperCell {
    private static final List<String> TYPES_LIST = List.of("safe", "mine");
    private String type;
    private boolean isFlagged;
    private boolean isOpen;
    private int surroundingMines;
    private int rowIndex;
    private int columnIndex;
    private Image redFlagImage;
    private ImageView redFlagImageView;

    public minesweeperCell(String type, int rowIndex, int columnIndex) {
        if (!TYPES_LIST.contains(type)) {
            throw new IllegalArgumentException("Invalid cell type!");
        }

        else if (rowIndex < 0 || columnIndex < 0) {
            throw new IllegalArgumentException("Invalid cell index!");
        }

        this.type = type;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.redFlagImage = new Image(getClass().getResourceAsStream("redFlag.png"));
        this.redFlagImageView = new ImageView();
    }

    public void flag() {
        this.isFlagged = true;

        this.redFlagImageView.setImage(this.redFlagImage);
        redFlagImageView.setFitWidth(20);
        redFlagImageView.setFitHeight(20);
    }

    public void unflag() {
        this.isFlagged = false;
        
        this.redFlagImageView.setImage(null);
    }

    public boolean isFlagged() {
        return this.isFlagged;
    }

    public void open() {
        this.isOpen = true;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean isMine() {
        if (getType().equals("mine")) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isSafe() {
        if (getType().equals("safe")) {
            return true;
        }
        else {
            return false;
        }
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        if (!TYPES_LIST.contains(type)) {
            throw new IllegalArgumentException("Invalid cell type!");
        }

        this.type = type;
    }

    public int getSurroundingMines() {
        return this.surroundingMines;
    }

    public void setSurroundingMines(int surroundingMines) {
        this.surroundingMines = surroundingMines;
    }

    public void addOneSurroundingMine() {
        this.surroundingMines++;
    }

    public ImageView getRedFlagImageView() {
        return this.redFlagImageView;
    }
}
