package ensi.controller;

import ensi.communication.Communication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
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

    private final ArrayList<GridPane> GridPaneArray = new ArrayList<>();
    private final ArrayList<Circle> CircleArray = new ArrayList<>();


    /**
     * This function permit to set the port value
     * @param port The port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * This function permit to set the password value
     * @param passWord The password
     */

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * This function permit to cet the current fxml file used
     * @param fxmlFileLoaded The fxml file path
     */
    public void setFxmlFileLoaded(String fxmlFileLoaded) {
        this.fxmlFileLoaded = fxmlFileLoaded;
    }

    /**
     * This function permit to get the CLIENT_NUMBER_SEED
     * @return The CLIENT_NUMBER_SEED value
     */
    static public boolean isCLIENT_NUMBER_SEED() {
        return CLIENT_NUMBER_SEED;
    }

    /**
     * This function permit to set the CLIENT_NUMBER_SEED
     * @param IS_NUMBER_SEED The value to set
     */
    static public void setCLIENT_NUMBER_SEED(boolean IS_NUMBER_SEED) {
        CLIENT_NUMBER_SEED = IS_NUMBER_SEED;
    }

    /**
     * This function permit to get the CLIENT_BOARD_STATE
     * @return The CLIENT_BOARD_STATE value
     */
    static public boolean isCLIENT_BOARD_STATE() {
        return CLIENT_BOARD_STATE;
    }

    /**
     * This function permit to set the CLIENT_BOARD_STATE
     * @param IS_BOARD_STATE The value to set
     */
    static public void setCLIENT_BOARD_STATE(boolean IS_BOARD_STATE) {
        CLIENT_BOARD_STATE = IS_BOARD_STATE;
    }

    /**
     * This function permit to get the CLIENT_SOUND_EFFECT
     * @return The CLIENT_SOUND_EFFECT value
     */
    static public boolean isCLIENT_SOUND_EFFECT() {
        return CLIENT_SOUND_EFFECT;
    }

    /**
     * This function permit to set the CLIENT_SOUND_EFFECT
     * @param IS_SOUND_EFFECT The value to set
     */
    static public void setCLIENT_SOUND_EFFECT(boolean IS_SOUND_EFFECT) {
        CLIENT_SOUND_EFFECT = IS_SOUND_EFFECT;
        playSoundEffect();
    }

    /**
     * This function permit to get the CLIENT_MUSIC
     * @return The CLIENT_MUSIC value
     */
    static public boolean isCLIENT_MUSIC() {
        return CLIENT_MUSIC;
    }

    /**
     * This function permit to set the CLIENT_MUSIC
     * @param IS_MUSIC The value to set
     */
    static public void setCLIENT_MUSIC(boolean IS_MUSIC) {
        CLIENT_MUSIC = IS_MUSIC;
        playMusic();
    }

    /**
     * Initialization function called when the view is initialized
     * @param url The URL
     * @param resourceBundle the RessourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        com = new Communication();
    }

    /**
     * This function permit to init all what we need to make a party
     * @param seedTab The default array of seed value and game info
     */
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


        CURRENT_MUSIC = "Mancala_song.mp3";
        playMusic();

        options.setOnAction(e -> {
            try {
                displayOptionsMenu();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        surrenderGame.setOnAction(e -> {
            Object response;

            response = com.sendMessage("ACTION,SURRENDER_GAME," + this.passWord);

            if (response instanceof String) {
                if (response == "ABANDON ?") {
                    boolean wantToSurrender = askUserToSurrender("L'adversaire vous propose un abandon !\nVoulez vous l'accepter");
                    if (wantToSurrender) {
                        System.out.println("Abandon : " + wantToSurrender);
                        com.sendMessage("ABANDONYES");
                    } else {
                        com.sendMessage("ABANDONNO");
                    }
                }
                else if (response == "Deplacement impossible") {
                    System.out.println("Abandon impossible");
                }
            }
        }
        );

        cancelMove.setOnAction(e -> {
            Object response;

            response = com.sendMessage("ACTION,CANCEL_MOVE," + this.passWord);

            if(response instanceof ArrayList) {
                updateView((ArrayList<?>) response);
            } else if (response instanceof String) {
                if (response == "Deplacement impossible") {
                    System.out.println("Ce n'est pas possible");
                }
            }

        });

        about.setOnAction(e -> displayAbout());

        seeRules.setOnAction(e -> displayRules());
    }

    /**
     * This function permit to display the rules
     */
    void displayRules() {
        String msg = "Règle 1 : Seulement deux joueurs peuvent s'affronter.\n" +
                "\n" +
                "Règle 2 : On répartit quarante-huit graines dans les douze trous à raison de quatre graines par trou.\n" +
                "\n" +
                "Règle 3 : Chaque joueur joue à tour de rôle, celui qui joue en premier est tiré au hasard. Le joueur va prendre l'ensemble des graines dans l'un des trous de son territoire et les distribuer, un par trou, dans le sens inverse des aiguilles d'une montre.\n" +
                "\n" +
                "Règle 4 : Si sa dernière graine tombe dans un trou du camp adverse et qu'il y a maintenant deux ou trois graines dans ce trou, le joueur récupère ces deux ou trois graines et les met de côté. Ensuite il regarde la case précédente : si elle est dans le camp adverse et contient deux ou trois graines, il récupère ces graines, et ainsi de suite jusqu'à ce qu'il arrive à son camp ou jusqu'à ce qu'il y ait un nombre de graines différent de deux ou trois.\n" +
                "\n" +
                "Règle 5 : On ne saute pas de case lorsqu'on égrène sauf lorsqu'on a douze graines ou plus, c'est-à-dire qu'on fait un tour complet : on ne remplit pas la case dans laquelle on vient de prendre les graines.\n" +
                "\n" +
                "Règle 6 : Il faut « nourrir » l'adversaire, c'est-à-dire que, quand celui-ci n'a plus de graines, il faut absolument jouer un coup qui lui permette de rejouer ensuite. Si ce n'est pas possible, la partie s'arrête et le joueur qui allait jouer capture les graines restantes.\n" +
                "\n" +
                "Règle 7 : Si un coup devait prendre toutes les graines adverses, alors le coup peut être joué, mais aucune capture n'est faite : il ne faut pas « affamer » l'adversaire.\n" +
                "\n" +
                "Règle 8 : La partie s'arrête quand : ➢ Un des joueurs a capturé au moins 25 graines, et est désigné gagnant (mode moyen) ➢ Ou qu'il ne reste qu'au plus 6 graines en jeu et que l’un des joueurs n’a plus de graines dans son camp et que son adversaire n’a pas eu l’opportunité de lui en redonner (mode débutant)\n" +
                "\n" +
                "Règle 9 : Quand il ne reste qu'au plus 10 graines sur le plateau, le joueur qui a la main peut proposer l'abandon de la partie. Si il est accepté les deux joueurs se partagent les graines restantes. Si le total des graines du plateau est inférieur à 6, sans qu'aucun des joueurs n'a un total de graines supérieur à 24. La partie est nulle.";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMinSize(600, 700);
        alert.getDialogPane().setContentText(msg);
        alert.getDialogPane().setHeaderText("Règles ");
        alert.setTitle("Règles ");
        alert.show();
    }

    /**
     * This function permit to ask to the use to surrender or not
     *
     * @param message The message we want to display
     * @return If he want to surrender
     */
    boolean askUserToSurrender(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        alert.setContentText(message);
        Button noButton = (Button) alert.getDialogPane().lookupButton(ButtonType.NO);
        Button yesButton = (Button) alert.getDialogPane().lookupButton(ButtonType.YES);
        noButton.setDefaultButton(false);
        yesButton.setDefaultButton(true);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }

    /**
     * This function permit to display about windows
     */
    private void displayAbout() {
        String msg = "Mancala - Copyright 2021 \nRéalisé par Théo BOHARD & Thomas FILLION \nENSICAEN";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setContentText(msg);
        alert.getDialogPane().setHeaderText("A propos");
        alert.setTitle("A propos");
        alert.show();
    }

    /**
     * This function permit to display the option menu to change options
     *
     * @throws IOException IOException
     */
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

    /**
     * This funciton permit to update the score for each player
     * @param tabSeed The score Array
     */
    private void updateScoreGame(ArrayList<?> tabSeed) {
        System.out.println("Array list : " + tabSeed);
        roundTextInfo.setText("Round numéro : " + ((Integer) tabSeed.get(tabSeed.size()-1) + 1));

        if (fxmlFileLoaded.equals("../view/gamePlayer1.fxml")) {
            roundScoreInfo.setText("Score : " + tabSeed.get(tabSeed.size()-5) + "-" + tabSeed.get(tabSeed.size()-4));
        } else if (fxmlFileLoaded.equals("../view/gamePlayer2.fxml")) {
            roundScoreInfo.setText("Score : " + tabSeed.get(tabSeed.size()-4) + "-" + tabSeed.get(tabSeed.size()-5));
        }
    }

    /**
     * This function permit to display a Tooltip
     * @param gridPane The GridPane concerned
     * @param message The message we want to display
     */
    private void displayTooltip(GridPane gridPane, String message) {
        if(CLIENT_NUMBER_SEED) {
            Tooltip tooltip = new Tooltip();
            tooltip.setPrefSize(200, 100);
            tooltip.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            Tooltip.install(gridPane, makeBubble(new Tooltip(message)));
        }
    }

    /**
     * This function permit to display a bubble on a tooltip
     * @param tooltip The tooltip
     * @return The tooltip
     */
    private Tooltip makeBubble(Tooltip tooltip) {
        tooltip.setStyle("-fx-font-size: 16px; -fx-shape: \"" + "M24 1h-24v16.981h4v5.019l7-5.019h13z" + "\";");
        tooltip.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);

        return tooltip;
    }

    /**
     * This function permit to clear the GridPanes
     * @param gridPanes The GridPanes
     */
    private void cleanGridPane(ArrayList<GridPane> gridPanes) {
        for (GridPane grid : gridPanes) {
            grid.getChildren().clear();
        }
    }

    /**
     * This function permit to populate a GridPane
     *
     * @param numberSeed The number of seed
     * @param grid       The GridPane concerned
     * @throws IOException IOException
     */
    private void populateGridPane(int numberSeed, GridPane grid) throws IOException {

        for (int i = 0; i < numberSeed; i++) {
            grid.add(new Ellipse(10, 10), i % 4, i / 4);
        }
    }

    /**
     * This function permit to update the view with the game and seed informations
     * @param tabSeed The seed/game informations
     */
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

    /**
     * This function permit to update the score of both player
     * @param score1 The score of the player 1
     * @param score2 The score of the player 2
     */
    private void updateScore(int score1, int score2) {
        scorePlayer1.setText(Integer.toString(score1));
        scorePlayer2.setText(Integer.toString(score2));
    }

    /**
     * This function permit to ask the server to move the seed when we click in a hole
     * @param mouseEvent The event of click
     */
    public void askServerToMove(MouseEvent mouseEvent) {
        CURRENT_SOUND = "notif.wav";
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
                ArrayList<?> holesList = null;
                try {
                    holesList = (ArrayList<?>) listenToServer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    updateView(holesList);
                }
            });

        } else if (serverReponse instanceof String) {
            System.out.println("Object is String");

            if(serverReponse == "PARTIE TERMINE") {
                System.out.println("La partie est terminée");
                this.setTurnInfo("PARTIE TERMINE");
            }

        }

    }

    /**
     * This function permit to listen the server messages
     *
     * @return The response
     * @throws InterruptedException InterruptedException
     */
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

            Socket socket = new Socket(InetAddress.getByName(ControllerMenu.ip), Integer.parseInt(this.port) + 2);

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

    /**
     * This function permit to play a sound effect
     */
    private static void playSoundEffect() {
        if(CLIENT_SOUND_EFFECT) {
            String musicFile = "Client/src/ensi/assets/".concat(CURRENT_SOUND);
            Media sound = new Media(new File(musicFile).toURI().toString());
            mediaPlayerEffect = new MediaPlayer(sound);
            Platform.runLater(() -> mediaPlayerEffect.play());
        }
    }

    /**
     * This function permit to play a music
     */
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

    /**
     * This function permit to set the turn information
     * @param turnInfo The turn information
     */
    public void setTurnInfo(String turnInfo) {
        turnInfoText.setText(turnInfo);
    }
}