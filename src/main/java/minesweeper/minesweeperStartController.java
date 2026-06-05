package minesweeper;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class minesweeperStartController implements Initializable {
    private String difficulty;
    private String usernameString;
    private minesweeperGame game;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private FXMLLoader loader;
    private List<String> difficultyList = List.of("Easy", "Medium", "Hard");
    
    @FXML private TextField username;
    @FXML private Button startGameButton;
    @FXML private ChoiceBox<String> difficultyChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        difficultyChoiceBox.getItems().addAll(difficultyList);
    }

    @FXML 
    public void switchToScene(ActionEvent event) throws IOException{
        this.usernameString = this.username.getText();
        this.difficulty = difficultyChoiceBox.getValue();

        if (usernameString.isEmpty() || usernameString == null || this.difficulty.isEmpty() || this.difficulty == null) {
            System.out.println("Must set name and choose difficulty level!");
            invalidStartInputsAlert();
            return;
        }

        this.game = new minesweeperGame(this.usernameString, this.difficulty);

        if (this.difficulty.equals("Easy")) {
            this.loader = new FXMLLoader(getClass().getResource("minesweeperEasyGame.fxml"));
            this.root = this.loader.load();
            minesweeperEasyGameController controller = loader.getController();
            controller.setEasyGame(this.game);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else if (this.difficulty.equals("Medium")) {
            this.loader = new FXMLLoader(getClass().getResource("minesweeperMediumGame.fxml"));
            this.root = this.loader.load();
            minesweeperMediumGameController controller = loader.getController();
            controller.setMediumGame(this.game);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if (this.difficulty.equals("Hard")) {
            this.loader = new FXMLLoader(getClass().getResource("minesweeperHardGame.fxml"));
            this.root = this.loader.load();
            minesweeperHardGameController controller = loader.getController();
            controller.setHardGame(this.game);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void invalidStartInputsAlert() {
        Alert startAlert = new Alert(AlertType.ERROR);
        startAlert.setTitle("ERROR :(");
        startAlert.setContentText("Please choose difficulty level and write username!");

        startAlert.show();
    }
}
