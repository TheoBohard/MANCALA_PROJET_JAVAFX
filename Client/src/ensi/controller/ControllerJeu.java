package ensi.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ControllerJeu implements Initializable {

    @FXML
    GridPane gripane1;
    @FXML
    GridPane gripane2;
    @FXML
    GridPane gripane3;
    @FXML
    GridPane gripane4;
    @FXML
    GridPane gripane5;
    @FXML
    GridPane gripane6;
    @FXML
    GridPane gripane7;
    @FXML
    GridPane gripane8;
    @FXML
    GridPane gripane9;
    @FXML
    GridPane gripane10;
    @FXML
    GridPane gripane11;
    @FXML
    GridPane gripane12;

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
    private Circle CIrcleEleven;

    @FXML
    private Circle CircleTwelve;

    private ArrayList<GridPane> GridPaneArray = new ArrayList<>();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        GridPaneArray.add(gripane1);
        GridPaneArray.add(gripane2);
        GridPaneArray.add(gripane3);
        GridPaneArray.add(gripane4);
        GridPaneArray.add(gripane5);
        GridPaneArray.add(gripane6);
        GridPaneArray.add(gripane7);
        GridPaneArray.add(gripane8);
        GridPaneArray.add(gripane9);
        GridPaneArray.add(gripane10);
        GridPaneArray.add(gripane11);
        GridPaneArray.add(gripane12);

        System.out.println("Hello");
        //TODO : Add action on click gridpane and circle
    }

    public void init(ArrayList<Integer> tab_seed) {

        int compteur = 0;
        for (Integer nb_seed : tab_seed) {
            GridPaneArray.get(compteur);
            compteur++;
        }
    }

    private void populateGridPane(GridPane gridPane, int numberSeed) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/graine.fxml"));
        Ellipse foo = (Ellipse)loader.getNamespace().get("seedTemplate");
        for(int i = 0; i < numberSeed; i++) {
            //gridPane.add(foo);
            // TODO : Add the seed on the gridpane
        }
    }

    public void circleMouseCliked(MouseEvent mouseEvent) {
        System.out.println("Mouse Clicked");
    }
}
