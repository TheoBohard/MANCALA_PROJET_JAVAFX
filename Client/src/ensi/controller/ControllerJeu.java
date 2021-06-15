package ensi.controller;

import ensi.communication.Communication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

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
    private Circle CircleFour;
    @FXML
    private Circle CircleFive;
    @FXML
    private Circle CircleSix;
    @FXML
    private Circle CircleSeven;
    @FXML
    private Circle CircleEight;
    @FXML
    private Circle CircleNine;
    @FXML
    private Circle CircleTen;
    @FXML
    private Circle CircleEleven;
    @FXML
    private Text scorePlayer1;
    @FXML
    private Text scorePlayer2;
    @FXML
    private Circle CircleTwelve;
    private Communication com;
    private String passWord;
    private String port;


    public void setPort(String port) {
        this.port = port;
    }

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

        for (GridPane gridpane : this.GridPaneArray) {
            try {
                populateGridPane(tab_seed.get(compteur), gridpane);
                compteur++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void cleanGridPane(ArrayList<GridPane> gridPanes) {
        for (GridPane grid : gridPanes) {
            grid.getChildren().clear();
        }
    }

    private void populateGridPane(int numberSeed, GridPane grid) throws IOException {

        for (int i = 0; i < numberSeed; i++) {
            grid.add(new Ellipse(10, 10), i % 4, i / 4);
        }
    }

    public void circleMouseCliked(MouseEvent mouseEvent) {
        System.out.println("Mouse Clicked");
    }

    public boolean updateView(ArrayList<?> tabSeed) {
        Platform.runLater(() -> {
            int compteur = 0;
            System.out.println("Array list : " + tabSeed);

            cleanGridPane(GridPaneArray);

            update_score((Integer) tabSeed.get(tabSeed.size()-1), (Integer) tabSeed.get(tabSeed.size()-2));

            for (GridPane gridpane : this.GridPaneArray) {
                try {
                    populateGridPane((Integer) tabSeed.get(compteur), gridpane);
                    compteur++;
                    System.out.println("Entered in updaterView");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        );

        return true;
    }

    private void update_score(int score1, int score2) {
        scorePlayer1.setText(Integer.toString(score1));
        scorePlayer2.setText(Integer.toString(score2));
    }

    public void ask_server_to_move(MouseEvent mouseEvent) {
        GridPane gridpaneClicked = ((GridPane) mouseEvent.getSource());
        String id = gridpaneClicked.getId().split("_")[1];
        Object serverReponse = com.sendMessage(id.concat(",").concat(this.passWord));
        System.out.println("Hello = " + serverReponse);

        if (serverReponse instanceof ArrayList) {
            System.out.println("Object is array list");
            System.out.println(serverReponse);
            updateView((ArrayList<?>) serverReponse);

            ExecutorService threadpool = Executors.newCachedThreadPool();
            threadpool.submit(() -> {
                ArrayList<?> wholesList = null;
                try {
                    wholesList = (ArrayList<?>) listen_to_server();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    updateView(wholesList);
                }
            });


        } else if (serverReponse instanceof String) {
            System.out.println("Object is String");
        }

    }

    public Object listen_to_server() throws InterruptedException {
        System.out.println("ENTERED LISTEN");
        ServerSocket serverSocket;
        Socket inputSocket;
        Object request = null;

        try {
            System.out.println("Port = " + this.port);
            System.out.println(this.port);
            serverSocket = new ServerSocket(Integer.parseInt(this.port)+1);
            System.out.println("Le client est à l'écoute du port " + serverSocket.getLocalPort());
            inputSocket = serverSocket.accept();
            System.out.println("Le socket a ete accepte");

            Socket socket = new Socket(InetAddress.getLocalHost(), Integer.parseInt(this.port) + 2);

            System.out.println("Hello on essaye de recevoir l'array list");
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

            request = ois.readObject();

            System.out.println("Request received ECOUTE");
            System.out.println(request);

            inputSocket.close();
            serverSocket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("EXITED LISTEN");

        return request;
    }
}
