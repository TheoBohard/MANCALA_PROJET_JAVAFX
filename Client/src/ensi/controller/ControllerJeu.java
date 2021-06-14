package ensi.controller;

import ensi.communication.Communication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ControllerJeu implements Initializable {

    @FXML
    public GridPane GridPane_1;
    @FXML
    public GridPane GridPane_2;
    @FXML
    public GridPane GridPane_3;
    @FXML
    public GridPane GridPane_4;
    @FXML
    public GridPane GridPane_5;
    @FXML
    public GridPane GridPane_6;
    @FXML
    public GridPane GridPane_7;
    @FXML
    public GridPane GridPane_8;
    @FXML
    public GridPane GridPane_9;
    @FXML
    public GridPane GridPane_10;
    @FXML
    public GridPane GridPane_11;
    @FXML
    public GridPane GridPane_12;
    @FXML
    private Circle CircleOne;
    @FXML
    private Circle CircleTwo;
    @FXML
    private Circle CircleThree;
    @FXML
    private Circle CIrcleFour;
    @FXML
    private Circle CircleFive;
    @FXML
    private Circle CircleSIx;
    @FXML
    private Circle CIrcleSeven;
    @FXML
    private Circle CircleEIght;
    @FXML
    private Circle CircleNIne;
    @FXML
    private Circle CircleTen;
    @FXML
    private Circle CircleEleven;
    @FXML
    private Circle CircleTwelve;
    private Communication com;
    private String passWord;

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    private final ArrayList<GridPane> GridPaneArray = new ArrayList<>();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        com = new Communication();
    }

    public void init(ArrayList<Integer> tab_seed) {
        int compteur = 0;
        this.GridPaneArray.addAll(Arrays.asList(this.GridPane_1, GridPane_2, GridPane_3, GridPane_4, GridPane_5,
                GridPane_6, GridPane_7, GridPane_8, GridPane_9, GridPane_10, GridPane_11, GridPane_12));

        for(GridPane gridpane:this.GridPaneArray) {
            try {
                populateGridPane(tab_seed.get(compteur),gridpane);
                compteur++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void populateGridPane(int numberSeed, GridPane grid) throws IOException {

        for (int i = 0; i < numberSeed; i++) {
            grid.add(new Ellipse(10,10), i%4 ,i/4 );
        }
    }

    public void circleMouseCliked(MouseEvent mouseEvent) {
        System.out.println("Mouse Clicked");
    }

    public void ask_server_to_move(MouseEvent mouseEvent) throws IOException {
        GridPane gridpaneClicked = ((GridPane)mouseEvent.getSource());
        String id = gridpaneClicked.getId().split("_")[1];
        com.sendMessage(id.concat(",").concat(this.passWord));

    }
}
