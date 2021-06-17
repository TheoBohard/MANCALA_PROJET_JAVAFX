package ensi.controller;

import ensi.communication.Communication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private Circle CircleTwelve;
    @FXML
    private Text scorePlayer1;
    @FXML
    private Text scorePlayer2;

    @FXML
    private Text turnInfoText;
    @FXML
    private Text roundTextInfo;
    @FXML
    private Text roundScoreInfo;
    @FXML
    private MenuItem loadGame;
    @FXML
    private MenuItem saveGame;
    @FXML
    private MenuItem surrenderGame;
    @FXML
    private MenuItem newMatch;
    @FXML
    private MenuItem stopMatch;
    @FXML
    private MenuItem cancelMove;
    @FXML
    private MenuItem seeRules;
    @FXML
    private MenuItem options;
    @FXML
    private MenuItem about;

    private Communication com;
    private String passWord;
    private String port;

    private ArrayList<?> tabSeed;

    private static boolean CLIENT_NUMBER_SEED = true;
    private static boolean CLIENT_BOARD_STATE = true;
    private static boolean CLIENT_SOUND_EFFECT = true;
    private static boolean CLIENT_MUSIC = true;

    private static String CURRENT_MUSIC;
    private static String CURRENT_SOUND;

    private static MediaPlayer mediaPlayerEffect;
    private static MediaPlayer mediaPlayerMusic;

    private String fxmlFileLoaded = "";


    public void setPort(String port) {
        this.port = port;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFxmlFileLoaded() {
        return fxmlFileLoaded;
    }

    public void setFxmlFileLoaded(String fxmlFileLoaded) {
        this.fxmlFileLoaded = fxmlFileLoaded;
    }

    static public boolean isCLIENT_NUMBER_SEED() {
        return CLIENT_NUMBER_SEED;
    }

    static public void setCLIENT_NUMBER_SEED(boolean IS_NUMBER_SEED) {
        CLIENT_NUMBER_SEED = IS_NUMBER_SEED;
    }

    static public boolean isCLIENT_BOARD_STATE() {
        return CLIENT_BOARD_STATE;
    }

    static public void setCLIENT_BOARD_STATE(boolean IS_BOARD_STATE) {
        CLIENT_BOARD_STATE = IS_BOARD_STATE;
    }

    static public boolean isCLIENT_SOUND_EFFECT() {
        return CLIENT_SOUND_EFFECT;
    }

    static public void setCLIENT_SOUND_EFFECT(boolean IS_SOUND_EFFECT) {
        CLIENT_SOUND_EFFECT = IS_SOUND_EFFECT;
        playSoundEffect();
    }

    static public boolean isCLIENT_MUSIC() {
        return CLIENT_MUSIC;
    }

    static public void setCLIENT_MUSIC(boolean IS_MUSIC) {
        CLIENT_MUSIC = IS_MUSIC;
        playMusic();
    }

    private final ArrayList<GridPane> GridPaneArray = new ArrayList<>();
    private final ArrayList<Circle> CircleArray = new ArrayList<>();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        com = new Communication();
    }

    public void init(ArrayList<Integer> seedTab) {
        this.tabSeed = seedTab;
        this.GridPaneArray.addAll(Arrays.asList(GridPane_1, GridPane_2, GridPane_3, GridPane_4, GridPane_5,
                GridPane_6, GridPane_7, GridPane_8, GridPane_9, GridPane_10, GridPane_11, GridPane_12));

        this.CircleArray.addAll(Arrays.asList(CircleOne, CircleTwo, CircleThree, CircleFour, CircleFive,
                CircleSix, CircleSeven, CircleEight, CircleNine, CircleTen, CircleEleven, CircleTwelve));

        Iterator<GridPane> gridPaneIterator = GridPaneArray.iterator();
        Iterator<Circle> circleIterator = CircleArray.iterator();

        int counter = 0;

        while (gridPaneIterator.hasNext() && circleIterator.hasNext()) {
            GridPane actualGridPane = gridPaneIterator.next();
            Circle actualCircle = circleIterator.next();

            int finalCounter = counter;
            
            actualGridPane.setOnMouseEntered(e -> displayTooltip(actualGridPane,
                    tabSeed.get(finalCounter) + " Graines"));
            actualCircle.setOnMouseEntered(e -> displayTooltip(actualGridPane,
                    tabSeed.get(finalCounter) + " Graines"));

            counter++;
        }

        counter = 0;

        for (GridPane gridpane : this.GridPaneArray) {
            try {
                populateGridPane(seedTab.get(counter), gridpane);
                counter++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        updateScoreGame(tabSeed);


        CURRENT_MUSIC = "blue.mp3";
        playMusic();

        options.setOnAction(e -> {
            try {
                displayOptionsMenu();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        surrenderGame.setOnAction(e -> com.sendMessage("ACTION,SURRENDER_GAME," + this.passWord));

        cancelMove.setOnAction(e -> com.sendMessage("ACTION,CANCEL_MOVE," + this.passWord));

        about.setOnAction(e -> displayAbout());
    }

    private void displayAbout() {
        String msg = "Mancala - Copyright 2021 \nRéalisé par Théo BOHARD & Thomas FILLION \nENSICAEN";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UNIFIED);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.getDialogPane().setContentText(msg);
        alert.getDialogPane().setHeaderText("A propos");
        alert.setTitle("A propos");
        alert.show();
    }

    @FXML
    void displayOptionsMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/optionsMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Options");
        stage.show();
    }

    private void updateScoreGame(ArrayList<?> tabSeed) {
        roundTextInfo.setText("Round numéro : " + ((Integer) tabSeed.get(tabSeed.size()-1) + 1));

        if (fxmlFileLoaded.equals("../view/gamePlayer1.fxml")) {
            roundScoreInfo.setText("Score : " + tabSeed.get(tabSeed.size()-5) + "-" + tabSeed.get(tabSeed.size()-4));
        } else if (fxmlFileLoaded.equals("../view/gamePlayer2.fxml")) {
            roundScoreInfo.setText("Score : " + tabSeed.get(tabSeed.size()-4) + "-" + tabSeed.get(tabSeed.size()-5));
        }
    }

    private void displayTooltip(GridPane gridPane, String message) {
        if(CLIENT_NUMBER_SEED) {
            Tooltip tooltip = new Tooltip();
            tooltip.setPrefSize(200, 100);
            tooltip.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            Tooltip.install(gridPane, makeBubble(new Tooltip(message)));
        }
    }

    private Tooltip makeBubble(Tooltip tooltip) {
        tooltip.setStyle("-fx-font-size: 16px; -fx-shape: \"" + "M24 1h-24v16.981h4v5.019l7-5.019h13z" + "\";");
        tooltip.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);

        return tooltip;
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

    public void updateView(ArrayList<?> tabSeed) {
        setTurnInfo("C'est votre tour !");
        Platform.runLater(() -> {
            this.tabSeed = tabSeed;
            int compteur = 0;
            System.out.println("Array list : " + tabSeed);

            cleanGridPane(GridPaneArray);

            updateScore((Integer) tabSeed.get(tabSeed.size()-2), (Integer) tabSeed.get(tabSeed.size()-3));

            for (GridPane gridpane : this.GridPaneArray) {
                try {
                    populateGridPane((Integer) tabSeed.get(compteur), gridpane);
                    compteur++;
                    System.out.println("Entered in updaterView");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            updateScoreGame(tabSeed);

            }
        );

    }

    private void updateScore(int score1, int score2) {
        scorePlayer1.setText(Integer.toString(score1));
        scorePlayer2.setText(Integer.toString(score2));
    }

    public void askServerToMove(MouseEvent mouseEvent) {
        CURRENT_SOUND = "sardoche_detruire.mp3";
        playSoundEffect();
        GridPane gridpaneClicked = ((GridPane) mouseEvent.getSource());
        String id = gridpaneClicked.getId().split("_")[1];
        Object serverReponse = com.sendMessage(id.concat(",").concat(this.passWord));
        System.out.println("Hello = " + serverReponse);

        if (serverReponse instanceof ArrayList) {
            System.out.println("Object is array list");
            System.out.println(serverReponse);
            updateView((ArrayList<?>) serverReponse);

            setTurnInfo("Ce n'est pas votre tour !");

            ExecutorService threadpool = Executors.newCachedThreadPool();
            threadpool.submit(() -> {
                ArrayList<?> wholesList = null;
                try {
                    wholesList = (ArrayList<?>) listenToServer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    updateView(wholesList);
                }
            });

        } else if (serverReponse instanceof String) {
            System.out.println("Object is String");

            if(serverReponse == "PARTIE TERMINE") {
                System.out.println("La partie est terminée");
                //TODO : Go to menu
            }

        }

    }

    public Object listenToServer() throws InterruptedException {
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

    private static void playSoundEffect() {
        if(CLIENT_SOUND_EFFECT) {
            String musicFile = "Client/src/ensi/assets/".concat(CURRENT_SOUND);
            Media sound = new Media(new File(musicFile).toURI().toString());
            mediaPlayerEffect = new MediaPlayer(sound);
            Platform.runLater(() -> mediaPlayerEffect.play());
        }
    }

    static void playMusic() {
        System.out.println("CLIENT MUSIC = " + CLIENT_MUSIC);
        if(CLIENT_MUSIC) {
            String musicFile = "Client/src/ensi/assets/".concat(CURRENT_MUSIC);
            Media sound = new Media(new File(musicFile).toURI().toString());
            mediaPlayerMusic = new MediaPlayer(sound);
            mediaPlayerMusic.setVolume(0.1);
            mediaPlayerMusic.setCycleCount(MediaPlayer.INDEFINITE);
            Platform.runLater(() -> mediaPlayerMusic.play());
        } else {
            mediaPlayerMusic.stop();
        }
    }

    public void setTurnInfo(String turnInfo) {
        turnInfoText.setText(turnInfo);
    }
}