package com.example.makeasquare;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.example.makeasquare.MakeASquareAlgorithm.*;
public class HelloController {
    @FXML
    private GridPane matrixGrid1;

    @FXML
    private GridPane matrixGrid2;
    private final Random random = new Random();
    private final ExecutorService executor = Executors.newFixedThreadPool(2); // Two threads for the two grids
    private final Color[] colors = {Color.WHITE, Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.BLACK};
    MakeASquareAlgorithm squareOne, squareTwo;
    public void initialize() {
        squareOne = new MakeASquareAlgorithm();
        squareOne.initialize();
        squareOne.allInputs();
        squareTwo = new MakeASquareAlgorithm();
        squareTwo.initialize();
        squareTwo.allInputs();
//        for (int i = 0 ; i < MakeASquareAlgorithm.n; i++)
//        {
//            int rows, columns;
//            rows = MakeASquareAlgorithm.in.nextInt();
//            columns = MakeASquareAlgorithm.in.nextInt();
//            List<List<Integer>> currentPuzzle = MakeASquareAlgorithm.takeOnePuzzle(rows, columns, i+1);
//            MakeASquareAlgorithm.puzzles.set(i, MakeASquareAlgorithm.handleThisPuzzle(currentPuzzle));
//            MakeASquareAlgorithm.go(MakeASquareAlgorithm.n, 0);
//        }
        // Initialize both GridPanes
        initializeGrid(matrixGrid1);
        initializeGrid(matrixGrid2);

        // Start threads to update the GridPanes
        startUpdatingGrid(matrixGrid1, 3000, squareOne.goodGrids); // Update every 200ms
        startUpdatingGrid(matrixGrid2, 3000, squareTwo.goodGrids); // Update every 200ms
    }

    private void initializeGrid(GridPane gridPane) {
        int cellSize = 50;
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    Rectangle rectangle = new Rectangle(cellSize, cellSize);
                    rectangle.setFill(Color.WHITE); // Assign a random color
                    gridPane.add(rectangle, col, row);
                }
            }
    }

    private void startUpdatingGrid(GridPane gridPane, int interval, Set<List<List<Integer>>> setOfGrids) {
//        executor.submit(() -> {
//            while (true) {
//                try {
//                    Thread.sleep(interval); // Pause for the given interval
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                    break;
//                }
//
//                // Update the grid on the JavaFX Application Thread
//                Platform.runLater(() -> updateGrid(gridPane));
//            }
//        });
        executor.submit(() -> {

             for (List<List<Integer>> matrix : setOfGrids)
             {
                try {
                    Thread.sleep(interval); // Pause for the given interval
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                // Update the grid on the JavaFX Application Thread
                Platform.runLater(() -> updateGrid(gridPane, matrix));
            }
        });

    }

    private void updateGrid(GridPane gridPane, List<List<Integer>> matrix) {
        // Update each rectangle in the GridPane with a random color
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Rectangle rectangle = (Rectangle) getNodeByRowColumnIndex(row, col, gridPane);
                if (rectangle != null) {
                    rectangle.setFill(colors[matrix.get(row).get(col)]);
                }
            }
        }
    }

    private Color randomColor() {
        return Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    private javafx.scene.Node getNodeByRowColumnIndex(int row, int col, GridPane gridPane) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return node;
            }
        }
        return null;
    }

}
