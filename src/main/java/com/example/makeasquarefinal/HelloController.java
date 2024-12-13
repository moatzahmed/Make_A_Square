package com.example.makeasquarefinal;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloController {

    public int numberOfPieces = 0;
    List<List<List<Integer>>> piecesOfPuzzle = new ArrayList<>();
    List<List<List<List<Integer>>>> puzzleStates = new ArrayList<>();

    private final List<Color> listOfColors = new ArrayList<>(Arrays.asList(
            Color.WHITE,
            Color.GREEN,
            Color.RED,
            Color.BLUE,
            Color.CYAN,
            Color.YELLOW
    ));
    private boolean[] isColorTaken = new boolean[6];

    Thread t, t1, t2, t3;


    private List<Integer> I = new ArrayList<>();
    private List<Integer> Z = new ArrayList<>();
    private List<Integer> J = new ArrayList<>();
    private List<Integer> T = new ArrayList<>();
    private List<Integer> O = new ArrayList<>();

    @FXML
    private Button addIPieceButton;

    @FXML
    private Button addJPieceButton;

    @FXML
    private Button addTPieceButton;

    @FXML
    private Button addZPieceButton;

    @FXML
    private Button addOPieceButton;

    @FXML
    private Button removeIPieceButton;

    @FXML
    private Button removeJPieceButton;

    @FXML
    private Button removeTPieceButton;

    @FXML
    private Button removeZPieceButton;

    @FXML
    private Button removeOPieceButton;

    @FXML
    private VBox trackingVbox;

    @FXML
    private GridPane gridPane;
    @FXML
    private GridPane gridPane1;
    @FXML
    private GridPane gridPane2;
    @FXML
    private GridPane gridPane3;
    @FXML
    private Text firstGridPaneText;
    @FXML
    private Text secondGridPaneText;
    @FXML
    private Text thirdGridPaneText;
    @FXML
    private Text fourthGridPaneText;


    public void addIPiece() {
        int currentColor = takeColor();
        List<List<Integer>> iPiece = new ArrayList<>();
        iPiece.add(Arrays.asList(currentColor, currentColor, currentColor, currentColor));
        I.add(currentColor);
        piecesOfPuzzle.add(iPiece);
        ImageView iPieceView = new ImageView();
        Image image = new Image(getClass().getResourceAsStream("/puzzle_pieces/Tetris_piece_I.png"));
        iPieceView.setImage(image);
        iPieceView.setFitWidth(125);
        iPieceView.setPreserveRatio(true);
        iPieceView.setSmooth(true);
        trackingVbox.getChildren().add(iPieceView);
        animateNode(iPieceView);
        numberOfPieces++;
        checkIfMaxNumberOfPuzzlesHasBeenReached();
    }

    public void addJPiece() {
        int currentColor = takeColor();
        List<List<Integer>> jPiece = new ArrayList<>();
        jPiece.add(Arrays.asList(currentColor, 0, 0));
        jPiece.add(Arrays.asList(currentColor, currentColor, currentColor));
        J.add(currentColor);
        currentColor++;
        piecesOfPuzzle.add(jPiece);
        ImageView jPieceView = new ImageView();
        Image image = new Image(getClass().getResourceAsStream("/puzzle_pieces/Tetris_piece_J.png"));
        jPieceView.setImage(image);
        jPieceView.setFitWidth(100);
        jPieceView.setPreserveRatio(true);
        jPieceView.setSmooth(true);
        trackingVbox.getChildren().add(jPieceView);
        animateNode(jPieceView);
        numberOfPieces++;
        checkIfMaxNumberOfPuzzlesHasBeenReached();
    }

    public void addTPiece() {
        int currentColor = takeColor();
        List<List<Integer>> tPiece = new ArrayList<>();
        tPiece.add(Arrays.asList(0, currentColor, 0));
        tPiece.add(Arrays.asList(currentColor, currentColor, currentColor));
        T.add(currentColor);
        piecesOfPuzzle.add(tPiece);
        ImageView tPieceView = new ImageView();
        Image image = new Image(getClass().getResourceAsStream("/puzzle_pieces/Tetris_piece_T.png"));
        tPieceView.setImage(image);
        tPieceView.setFitWidth(100);
        tPieceView.setPreserveRatio(true);
        tPieceView.setSmooth(true);
        trackingVbox.getChildren().add(tPieceView);
        animateNode(tPieceView);
        numberOfPieces++;
        checkIfMaxNumberOfPuzzlesHasBeenReached();
    }

    public void addZPiece() {
        int currentColor = takeColor();
        List<List<Integer>> zPiece = new ArrayList<>();
        zPiece.add(Arrays.asList(currentColor, currentColor, 0));
        zPiece.add(Arrays.asList(0, currentColor, currentColor));
        Z.add(currentColor);
        currentColor++;
        piecesOfPuzzle.add(zPiece);
        ImageView zPieceView = new ImageView();
        Image image = new Image(getClass().getResourceAsStream("/puzzle_pieces/Tetris_piece_Z.png"));
        zPieceView.setImage(image);
        zPieceView.setFitWidth(100);
        zPieceView.setPreserveRatio(true);
        zPieceView.setSmooth(true);
        trackingVbox.getChildren().add(zPieceView);
        animateNode(zPieceView);
        numberOfPieces++;
        checkIfMaxNumberOfPuzzlesHasBeenReached();
    }

    public void addOPiece()
    {
        int currentColor = takeColor();
        List<List<Integer>> oPiece = new ArrayList<>();
        oPiece.add(Arrays.asList(currentColor, currentColor));
        oPiece.add(Arrays.asList(currentColor, currentColor));
        O.add(currentColor);
        piecesOfPuzzle.add(oPiece);
        ImageView oPieceView = new ImageView();
        Image image = new Image(getClass().getResourceAsStream("/puzzle_pieces/Tetris_piece_O.png"));
        oPieceView.setImage(image);
        oPieceView.setFitWidth(75);
        oPieceView.setPreserveRatio(true);
        oPieceView.setSmooth(true);
        trackingVbox.getChildren().add(oPieceView);
        animateNode(oPieceView);
        numberOfPieces++;
        checkIfMaxNumberOfPuzzlesHasBeenReached();
    }

    public void removeIPiece() {

        if (I.size() > 0) {
            int removedColor = I.get(I.size() - 1);
            isColorTaken[removedColor] = false;
            int index = -1;
            I.remove(I.size() - 1);
            for (int i = 0; i < piecesOfPuzzle.size(); i++) {
                for (int j = 0; j < piecesOfPuzzle.get(i).size(); j++) {
                    for (int z = 0; z < piecesOfPuzzle.get(i).get(j).size(); z++) {
                        if (piecesOfPuzzle.get(i).get(j).get(z) == removedColor) {
                            index = i;
                        }
                        if (index != -1) {
                            break;
                        }

                    }
                    if (index != -1) {
                        break;
                    }
                }
                if (index != -1) {
                    break;
                }
            }
            piecesOfPuzzle.remove(index);
            numberOfPieces--;
            checkIfMaxNumberOfPuzzlesHasBeenReached();
            if (!trackingVbox.getChildren().isEmpty()) {
                Node node = trackingVbox.getChildren().get(index);
                animateNodeRemoval(node, index);
            }
        }
    }

    public void removeZPiece() {
        if (Z.size() > 0) {
            int removedColor = Z.get(Z.size() - 1);
            isColorTaken[removedColor] = false;
            int index = -1;
            Z.remove(Z.size() - 1);
            for (int i = 0; i < piecesOfPuzzle.size(); i++) {
                for (int j = 0; j < piecesOfPuzzle.get(i).size(); j++) {
                    for (int z = 0; z < piecesOfPuzzle.get(i).get(j).size(); z++) {
                        if (piecesOfPuzzle.get(i).get(j).get(z) == removedColor) {
                            index = i;
                        }
                        if (index != -1) {
                            break;
                        }
                    }
                    if (index != -1) {
                        break;
                    }
                }
                if (index != -1) {
                    break;
                }
            }
            piecesOfPuzzle.remove(index);
            numberOfPieces--;
            checkIfMaxNumberOfPuzzlesHasBeenReached();
            if (!trackingVbox.getChildren().isEmpty()) {
                Node node = trackingVbox.getChildren().get(index);
                animateNodeRemoval(node, index);
            }
        }
    }

    public void removeJPiece() {
        if (J.size() > 0) {
            int removedColor = J.get(J.size() - 1);
            isColorTaken[removedColor] = false;
            int index = -1;
            J.remove(J.size() - 1);
            for (int i = 0; i < piecesOfPuzzle.size(); i++) {
                for (int j = 0; j < piecesOfPuzzle.get(i).size(); j++) {
                    for (int z = 0; z < piecesOfPuzzle.get(i).get(j).size(); z++) {
                        if (piecesOfPuzzle.get(i).get(j).get(z) == removedColor) {
                            index = i;
                        }
                        if (index != -1) {
                            break;
                        }
                    }
                    if (index != -1) {
                        break;
                    }
                }
                if (index != -1) {
                    break;
                }
            }
            piecesOfPuzzle.remove(index);
            numberOfPieces--;
            checkIfMaxNumberOfPuzzlesHasBeenReached();
            if (!trackingVbox.getChildren().isEmpty()) {
                Node node = trackingVbox.getChildren().get(index);
                animateNodeRemoval(node, index);
            }
        }
    }

    public void removeTPiece() {
        if (T.size() > 0) {
            int removedColor = T.get(T.size() - 1);
            isColorTaken[removedColor] = false;
            int index = -1;
            T.remove(T.size() - 1);
            for (int i = 0; i < piecesOfPuzzle.size(); i++) {
                for (int j = 0; j < piecesOfPuzzle.get(i).size(); j++) {
                    for (int z = 0; z < piecesOfPuzzle.get(i).get(j).size(); z++) {
                        if (piecesOfPuzzle.get(i).get(j).get(z) == removedColor) {
                            index = i;
                        }
                        if (index != -1) {
                            break;
                        }

                    }
                    if (index != -1) {
                        break;
                    }
                }
                if (index != -1) {
                    break;
                }
            }
            piecesOfPuzzle.remove(index);
            numberOfPieces--;
            checkIfMaxNumberOfPuzzlesHasBeenReached();
            if (!trackingVbox.getChildren().isEmpty()) {
                Node node = trackingVbox.getChildren().get(index);
                animateNodeRemoval(node, index);
            }
        }
    }

    public void removeOPiece()
    {
        if (O.size() > 0) {
            int removedColor = O.get(O.size() - 1);
            isColorTaken[removedColor] = false;
            int index = -1;
            O.remove(O.size() - 1);
            for (int i = 0; i < piecesOfPuzzle.size(); i++) {
                for (int j = 0; j < piecesOfPuzzle.get(i).size(); j++) {
                    for (int z = 0; z < piecesOfPuzzle.get(i).get(j).size(); z++) {
                        if (piecesOfPuzzle.get(i).get(j).get(z) == removedColor) {
                            index = i;
                        }
                        if (index != -1) {
                            break;
                        }

                    }
                    if (index != -1) {
                        break;
                    }
                }
                if (index != -1) {
                    break;
                }
            }
            piecesOfPuzzle.remove(index);
            numberOfPieces--;
            checkIfMaxNumberOfPuzzlesHasBeenReached();
            if (!trackingVbox.getChildren().isEmpty()) {
                Node node = trackingVbox.getChildren().get(index);
                animateNodeRemoval(node, index);
            }
        }
    }

    public void checkIfMaxNumberOfPuzzlesHasBeenReached() {
        if (numberOfPieces == 5) {
            addIPieceButton.setDisable(true);
            addJPieceButton.setDisable(true);
            addTPieceButton.setDisable(true);
            addZPieceButton.setDisable(true);
            addOPieceButton.setDisable(true);
        } else {
            addIPieceButton.setDisable(false);
            addJPieceButton.setDisable(false);
            addTPieceButton.setDisable(false);
            addZPieceButton.setDisable(false);
            addOPieceButton.setDisable(false);
        }
    }

    public void initialize() {
        initializeGridPane(gridPane);
        initializeGridPane(gridPane1);
        initializeGridPane(gridPane2);
        initializeGridPane(gridPane3);
        firstGridPaneText.setVisible(false);
        secondGridPaneText.setVisible(false);
        thirdGridPaneText.setVisible(false);
        fourthGridPaneText.setVisible(false);
    }

    public void initializeGridPane(GridPane gridPane) {
        int cellSize = 50;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Rectangle rectangle = new Rectangle(cellSize, cellSize);
                rectangle.setFill(Color.WHITE);
                gridPane.add(rectangle, col, row);
            }
        }
        gridPane.setGridLinesVisible(true);
    }

    public List<List<List<Integer>>> achieveAllPuzzleStates(List<List<Integer>> puzzle) {
        List<List<List<Integer>>> onePuzzleStates = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            onePuzzleStates.add(new ArrayList<>());
        }
        onePuzzleStates.set(0, puzzle);
        onePuzzleStates.set(1, flip(puzzle));
        for (int i = 2; i < 8; i += 2) {
            onePuzzleStates.set(i, rotate(onePuzzleStates.get(i - 2)));
            onePuzzleStates.set(i + 1, flip(onePuzzleStates.get(i)));
        }
        return onePuzzleStates;
    }

    public List<List<Integer>> flip(List<List<Integer>> puzzle) {
        List<List<Integer>> flippedPuzzle = new ArrayList<>();
        for (List<Integer> row : puzzle) {
            List<Integer> flippedRow = new ArrayList<>(row);
            Collections.reverse(flippedRow);
            flippedPuzzle.add(flippedRow);
        }
        return flippedPuzzle;
    }

    public List<List<Integer>> rotate(List<List<Integer>> puzzle) {
        int n = puzzle.size();
        int m = puzzle.get(0).size();
        List<List<Integer>> rotatedPuzzle = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            rotatedPuzzle.add(new ArrayList<>(Collections.nCopies(n, 0)));
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                rotatedPuzzle.get(j).set(i, puzzle.get(i).get(j));
            }
        }
        for (int i = 0; i < m; i++) {
            Collections.reverse(rotatedPuzzle.get(i));
        }
        return rotatedPuzzle;
    }

    public void fillPuzzleStates() {
        for (List<List<Integer>> puzzle : piecesOfPuzzle) {
            puzzleStates.add(achieveAllPuzzleStates(puzzle));
        }
    }

    public void printTwoD(List<List<Integer>> matrix) {
        for (List<Integer> row : matrix) {
            for (int number : row) {
                System.out.print(number + " ");
            }
            System.out.println();
        }
        System.out.print("--------------------------------------------");
        System.out.println();
    }

    public void printThreeD(List<List<List<Integer>>> arrayOfMatrices) {
        for (List<List<Integer>> matrix : arrayOfMatrices) {
            printTwoD(matrix);
        }
    }

    public void printFourD(List<List<List<List<Integer>>>> arrayOf3DMatrices) {
        for (List<List<List<Integer>>> threeDMatrix : arrayOf3DMatrices) {
            printThreeD(threeDMatrix);
        }
    }

    @FXML
    Button debugButton;
    boolean flag = false;

    public void myFun() throws InterruptedException {
        fillPuzzleStates();
        MakeASquare MAS = new MakeASquare(gridPane, numberOfPieces, "firstGrid");
        t = new Thread(() -> {
            MAS.go(0,puzzleStates);
        });
        t.start();
        MakeASquare MAS1 = new MakeASquare(gridPane1, numberOfPieces, "secondGrid");
        t1 = new Thread(() -> {
            MAS1.go(0, puzzleStates);
        });
        t1.start();
        MakeASquare MAS2 = new MakeASquare(gridPane2, numberOfPieces, "SecondGrid");
        t2 = new Thread(() -> {MAS2.go(0,puzzleStates);});
        t2.start();
        MakeASquare MAS3 = new MakeASquare(gridPane3, numberOfPieces, "SecondGrid");
        t3 = new Thread(() -> {MAS3.go(0,puzzleStates);});
        t3.start();
        printFourD(puzzleStates);
        Thread waitingThread = new Thread(() -> {
            try {
                t.join();
                t1.join();
                t2.join();
                t3.join();
                System.out.println(MAS.isSolutionFound + " " + MAS1.isSolutionFound + " " + MAS2.isSolutionFound + " " + MAS3.isSolutionFound);
                Platform.runLater(()->{
                    if(MAS.isSolutionFound)
                    {
                        firstGridPaneText.setText("Solution Found");
                        firstGridPaneText.setVisible(true);
                        firstGridPaneText.setFill(Color.GREEN);
                    }
                    else
                    {
                        firstGridPaneText.setText("No solution found");
                        firstGridPaneText.setVisible(true);
                        firstGridPaneText.setFill(Color.RED);
                    }
                    if(MAS1.isSolutionFound)
                    {
                        secondGridPaneText.setText("Solution Found");
                        secondGridPaneText.setVisible(true);
                        secondGridPaneText.setFill(Color.GREEN);
                    }
                    else
                    {
                        secondGridPaneText.setText("No solution found");
                        secondGridPaneText.setVisible(true);
                        secondGridPaneText.setFill(Color.RED);
                    }
                    if(MAS2.isSolutionFound)
                    {
                        thirdGridPaneText.setText("Solution Found");
                        thirdGridPaneText.setVisible(true);
                        thirdGridPaneText.setFill(Color.GREEN);
                    }
                    else
                    {
                        thirdGridPaneText.setText("No solution found");
                        thirdGridPaneText.setVisible(true);
                        thirdGridPaneText.setFill(Color.RED);
                    }
                    if(MAS3.isSolutionFound)
                    {
                        fourthGridPaneText.setText("Solution Found");
                        fourthGridPaneText.setVisible(true);
                        fourthGridPaneText.setFill(Color.GREEN);
                    }
                    else
                    {
                        fourthGridPaneText.setText("No solution found");
                        fourthGridPaneText.setVisible(true);
                        fourthGridPaneText.setFill(Color.RED);
                    }
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        waitingThread.start();
    }
    @FXML
    Button clearGridButton;
    public void clearGrid()
    {
        resetGrid(gridPane);
        resetGrid(gridPane1);
        resetGrid(gridPane2);
        resetGrid(gridPane3);
        puzzleStates.clear();
        firstGridPaneText.setVisible(false);
        secondGridPaneText.setVisible(false);
        thirdGridPaneText.setVisible(false);
        fourthGridPaneText.setVisible(false);
    }
    public void resetGrid(GridPane gridPane) {
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(false);
        initializeGridPane(gridPane);
        gridPane.setGridLinesVisible(true);
    }

    public int takeColor() {
        for (int i = 1; i <= 5; i++) {
            if (!isColorTaken[i]) {
                isColorTaken[i] = true;
                return i;
            }
        }
        return -1;
    }
    private void animateNode(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }
    private void animateNodeRemoval(Node node, int index) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), node);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> {
            trackingVbox.getChildren().remove(index);
        });
        fadeTransition.play();
    }


}
