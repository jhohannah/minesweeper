package minesweeper;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class minesweeperEasyGameController {
    private minesweeperGame game;
    private minesweeperBoard board;
    private Stage stage;
    private Parent root;
    private FXMLLoader loader;

    @FXML GridPane easyBoard;

    public void setEasyGame(minesweeperGame game) {
        this.game = game;
        this.board = this.game.getBoard();
        createboard();
    }

    public void createboard() {
        for (int row = 0; row < board.getRow(); row++) {
            for (int column = 0; column < board.getColumn(); column++) {
                
                Button cellButton = new Button();
                minesweeperCell cell = this.board.getMinesweeperCell(row, column);
                cellButton.setPrefSize(25,25);
                cellButton.getStyleClass().add("gameCell");

                int rowIndex = row; 
                int columnIndex = column;

                cellButton.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        cellLeftClicked(cell, cellButton);
                    }
                    else if (event.getButton() == MouseButton.SECONDARY) {
                        cellRightClicked(cell, cellButton);
                    }
                });

                easyBoard.add(cellButton, columnIndex, rowIndex);
            }
        }
    }

    public void cellLeftClicked(minesweeperCell cell, Button button) {
        if (cell.isMine()) {
            Image bombImage = new Image(getClass().getResourceAsStream("bombImage.png"));
            ImageView bombImageView = new ImageView(bombImage);
            bombImageView.setFitWidth(25);
            bombImageView.setFitHeight(25);
            
            easyBoard.getChildren().remove(button);
            easyBoard.add(bombImageView, GridPane.getColumnIndex(button), GridPane.getRowIndex(button));
            this.game.openCell(cell);
            
            try {
                switchToEndScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        else {
            Label surroundingMinesLabel = new Label(Integer.toString(cell.getSurroundingMines()));
            surroundingMinesLabel.setPrefSize(25, 25);
            surroundingMinesLabel.setAlignment(Pos.CENTER);

            easyBoard.getChildren().remove(button);
            easyBoard.add(surroundingMinesLabel, GridPane.getColumnIndex(button), GridPane.getRowIndex(button));
            this.game.openCell(cell);
            
        }

        if (this.game.isGameFinished()) {
            try {
                switchToEndScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void cellRightClicked(minesweeperCell cell, Button button) {        
        if (!cell.isFlagged()) {
            cell.flag();
            button.setGraphic(cell.getRedFlagImageView());
            button.setPadding(new Insets(0));
        }
        else {
            cell.unflag();
            button.setGraphic(cell.getRedFlagImageView());
            button.setPadding(new Insets(0));
        }
}

    @FXML
    public void switchToEndScreen() throws IOException {
        this.loader = new FXMLLoader(getClass().getResource("minesweeperEndScreen.fxml"));
        this.root = this.loader.load();
        this.stage = (Stage) easyBoard.getScene().getWindow();
        minesweeperEndScreenController controller = loader.getController();
        controller.setEndscreen(game);
        stage.setScene((new Scene(root)));
        stage.show();
    }
}
