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
    private GridPane GridPane1;
    @FXML
    private GridPane GridPane2;
    @FXML
    private GridPane GridPane3;
    @FXML
    private GridPane GridPane4;
    @FXML
    private GridPane GridPane5;
    @FXML
    private GridPane GridPane6;
    @FXML
    private GridPane GridPane7;
    @FXML
    private GridPane GridPane8;
    @FXML
    private GridPane GridPane9;
    @FXML
    private GridPane GridPane10;
    @FXML
    private GridPane GridPane11;
    @FXML
    private GridPane GridPane12;

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

    private final ArrayList<GridPane> GridPaneArray = new ArrayList<>();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        GridPaneArray.addAll(Arrays.asList(GridPane1, GridPane2, GridPane3, GridPane4, GridPane5,
                GridPane6, GridPane7, GridPane8, GridPane9, GridPane10, GridPane11, GridPane12));

        System.out.println("Hello");
        try {
            populateGridPane(10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO : Add action on click GridPane and circle
    }

    public void init(ArrayList<Integer> tab_seed) {
        GridPaneArray.addAll(Arrays.asList(GridPane1, GridPane2, GridPane3, GridPane4, GridPane5,
                GridPane6, GridPane7, GridPane8, GridPane9, GridPane10, GridPane11, GridPane12));
        int compteur = 0;
        for (int nb_seed : tab_seed) {
//            GridPaneArray.get(compteur);
            compteur++;
        }
    }

    private void populateGridPane(int numberSeed) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/graine.fxml"));
        Scene scene = new Scene(root);

        AnchorPane foo = (AnchorPane) scene.lookup("#seedTemplate");

        for (int i = 0; i < numberSeed; i++) {
            GridPane1.add(new Ellipse(10,10), i%4 ,i/4 );
            // TODO : Add the seed on the GridPane
        }
    }

    public void circleMouseCliked(MouseEvent mouseEvent) {
        System.out.println("Mouse Clicked");
    }
}
