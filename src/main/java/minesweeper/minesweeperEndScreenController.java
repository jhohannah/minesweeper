package minesweeper;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class minesweeperEndScreenController {
    private minesweeperGame game;
    private String outcome;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private FXMLLoader loader;

    @FXML Label outcomeLabel;
    @FXML Label difficultyLeaderboardLabel;
    @FXML Label yourResultLabel;

    @FXML ListView<String> leaderboardListView;
    @FXML Button playAgainButton;
    @FXML Button clearLeaderboardButton;

    public void setEndscreen(minesweeperGame game) {
        this.game = game;
        this.outcome = this.game.getOutcome();
        

        if (this.outcome.equals("win")) {
            outcomeLabel.setText("Congratulations!");
            difficultyLeaderboardLabel.setText(game.getBoard().getDifficulty());
            yourResultLabel.setText(this.game.getGameResult());
        }
        else {
            outcomeLabel.setText("Game Over!");
            difficultyLeaderboardLabel.setText(game.getBoard().getDifficulty());  
            yourResultLabel.setText("You lost :(");
        }

        leaderboardListView.getItems().clear();
        for (ArrayList<String> leaderboardResult : this.game.getLeaderboard()) {
            leaderboardListView.getItems().add(String.join(": ", leaderboardResult));
        }
    }

    @FXML
    public void switchToStartScreen(ActionEvent event) throws IOException {
        this.loader = new FXMLLoader(getClass().getResource("minesweeperStartScreen.fxml"));
        this.root = this.loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void clearLeaderboard(ActionEvent event) throws IOException {
        this.game.clearLeaderboard();
        leaderboardListView.getItems().clear();
    }
}
