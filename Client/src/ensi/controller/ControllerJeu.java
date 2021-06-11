package ensi.controller;

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
    public GridPane GridPane1;
    @FXML
    public GridPane GridPane2;
    @FXML
    public GridPane GridPane3;
    @FXML
    public GridPane GridPane4;
    @FXML
    public GridPane GridPane5;
    @FXML
    public GridPane GridPane6;
    @FXML
    public GridPane GridPane7;
    @FXML
    public GridPane GridPane8;
    @FXML
    public GridPane GridPane9;
    @FXML
    public GridPane GridPane10;
    @FXML
    public GridPane GridPane11;
    @FXML
    public GridPane GridPane12;
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

    private final ArrayList<GridPane> GridPaneArray = new ArrayList<>();

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void init(ArrayList<Integer> tab_seed) {
        int compteur = 0;
        this.GridPaneArray.addAll(Arrays.asList(this.GridPane1, GridPane2, GridPane3, GridPane4, GridPane5,
                GridPane6, GridPane7, GridPane8, GridPane9, GridPane10, GridPane11, GridPane12));

        for(GridPane gridpane:this.GridPaneArray) {
            try {
                populateGridPane(tab_seed.get(compteur),gridpane);
                compteur++;
            } catch (IOException e) {
                e.printStackTrace();
            }
            //TODO : Add action on click GridPane and circle
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
}
