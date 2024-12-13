package com.example.makeasquarefinal;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class MakeASquare {

    private final List<Color> listOfColors = new ArrayList<>(Arrays.asList(
            Color.WHITE,
            Color.GREEN,
            Color.RED,
            Color.BLUE,
            Color.CYAN,
            Color.YELLOW
    ));
    private volatile boolean done;
    private FileWriter fw;

    private int numberOfPieces;
    private int currentColor = 0;
    private GridPane gridPane;

    public boolean isSolutionFound;


    public MakeASquare(GridPane GGPane, int numberOfPieces, String fileName) {
        this.gridPane = GGPane;
        this.numberOfPieces = numberOfPieces;
        try {
            fw = new FileWriter(fileName);
        } catch (Exception e) {
        }
    }

    public  javafx.scene.Node getNodeByRowColumnIndex(int row, int col, GridPane gridPane) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return node;
            }
        }
        return null;
    }

    public void printTwoD(List<List<Integer>> matrix) {
        for (List<Integer> row : matrix) {
            for (int number : row) {
                System.out.print(number + " ");
                try {
                    fw.write(number + " ");
                } catch (Exception e) {
                }
            }
            try {
                fw.write("\n");
            } catch (Exception e) {
            }
            System.out.println();
        }
        try {
            fw.write("--------------------------------------------");
            fw.write("\n");
        } catch (Exception e) {
        }
        System.out.print("--------------------------------------------");
        System.out.println();
    }

    public void printThreeD(List<List<List<Integer>>> arrayOfMatrices) {
        for (List<List<Integer>> matrix : arrayOfMatrices) {
            printTwoD(matrix);
        }
    }

    private synchronized  boolean isDone() {
        return done;
    }
    private synchronized  void setDone(boolean value) {
        done = value;
    }


    //    public void processState(int stateIdx, List<List<List<List<Integer>>>> localStates) {
//
//
//        if (stateIdx >= numberOfPieces) {
//            done = true;
//            return;
//        }
//
//        if (done) return;
//        boolean iPut = false;
//        for (List<List<Integer>> oneState : localStates.get(stateIdx)) {
//            if (done) return;
//            for (int i = 0; i < 4; i++) {
//                if (done) return;
//                for (int j = 0; j < 4; j++) {
//                    if (done) return;
//                    if (isValid(oneState, i, j) && !iPut && !done) {
//                        if (done) return;
//                        iPut = true;
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException IE) {
//                            System.out.println(IE.getMessage());
//                        }
//                        int I = i, J = j;
//                        CountDownLatch latch = new CountDownLatch(1);
//                        Platform.runLater(() -> {
//                            putMe(oneState, I, J);
//                            latch.countDown();
//                            System.out.println(Thread.currentThread().getName()+" UI ");
//                        });
//                        try {
//                            latch.await();
//                        } catch (InterruptedException e) {
//                            System.out.println("Interrupted while waiting for UI update: " + e.getMessage());
//                        }
//                        System.out.println(Thread.currentThread().getName()+" next puzzle");
//                        processState(stateIdx + 1, localStates);
//                    }
//                }
//            }
//        }
//        if (!iPut) {
//            processState(stateIdx + 1, localStates);
//        }
//    }

    private final ReentrantLock recursionLock = new ReentrantLock();
    public synchronized void processState(int stateIdx, List<List<List<List<Integer>>>> localStates) {
        recursionLock.lock();
        try {
            if (stateIdx >= numberOfPieces) {
                setDone(true);
                return;
            }
            if (isDone()) return;
            boolean iPut = false;
            for (List<List<Integer>> oneState : localStates.get(stateIdx)) {
                if (isDone()) return;
                for (int i = 0; i < 4; i++) {
                    if (isDone()) return;
                    for (int j = 0; j < 4; j++) {
                        if (isDone()) return;
                        if (isValid(oneState, i, j) && !iPut) {
                            setDone(false);
                            iPut = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException IE) {
                                System.out.println(IE.getMessage());
                            }
                            CountDownLatch latch = new CountDownLatch(1);
                            final int I = i, J = j;
                            Platform.runLater(() -> {
                                putMe(oneState, I, J);
                                latch.countDown();
                            });

                            try {
                                latch.await();
                            } catch (InterruptedException e) {
                                System.out.println("Interrupted while waiting for UI update: " + e.getMessage());
                            }
                            processState(stateIdx + 1, localStates);
                            if (isDone()) return;
                        }
                    }
                }
            }
            if (!iPut) {
                processState(stateIdx + 1, localStates);
            }
        } finally {
            recursionLock.unlock();
        }
    }

    public synchronized  void go(int stateIdx, List<List<List<List<Integer>>>> puzzlesStates) {
        List<List<List<List<Integer>>>> localPuzzlesStates = new ArrayList<>();

        for (List<List<List<Integer>>> state : puzzlesStates) {
            List<List<List<Integer>>> temp = new ArrayList<>();
            for (List<List<Integer>> twoD : state) {
                List<List<Integer>> added = copyGrid(twoD);
                temp.add(added);
            }
            Collections.shuffle(temp);
            localPuzzlesStates.add(temp);
        }
        Collections.shuffle(localPuzzlesStates);

        processState(stateIdx, localPuzzlesStates);
        checkIfSolutionFound();
    }


    public  void putMe(List<List<Integer>> puzzle, int i, int j) {
        int rows = puzzle.size();
        int cols = puzzle.get(0).size();
        int myColor = -1;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (puzzle.get(x).get(y) != 0) {
                    myColor = puzzle.get(x).get(y);
                    break;
                }
            }
            if (myColor != -1) {
                break;
            }
        }
        for (int cnt1 = 0; cnt1 < rows; cnt1++) {
            for (int cnt2 = 0; cnt2 < cols; cnt2++) {
                Rectangle cell = (Rectangle) getNodeByRowColumnIndex(i + cnt1, j + cnt2, gridPane);
                if (cell.getFill() == Color.WHITE && puzzle.get(cnt1).get(cnt2) == myColor) {
                    cell.setFill(listOfColors.get(myColor));
                }

            }
        }
    }

    public void clearMe(List<List<Integer>> puzzle, int i, int j) {
        int rows = puzzle.size();
        int cols = puzzle.get(0).size();
        int myColor = -1;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (puzzle.get(x).get(y) != 0) {
                    myColor = puzzle.get(x).get(y);
                    break;
                }
            }
            if (myColor != -1) {
                break;
            }
        }
        // Traverse through the puzzle and grid
        for (int cnt1 = 0; cnt1 < rows; cnt1++) {
            for (int cnt2 = 0; cnt2 < cols; cnt2++) {
                Rectangle cell = (Rectangle) getNodeByRowColumnIndex(i + cnt1, j + cnt2, gridPane);
                if (cell.getFill() == listOfColors.get(myColor)) {
                    // Set the grid cell to 0 if it matches puzzleID
                    cell.setFill(Color.WHITE);
                }
            }
        }
    }

    public synchronized boolean isValid(List<List<Integer>> puzzle, int i, int j) {
        int rows = puzzle.size();
        int cols = puzzle.get(0).size();

        // Traverse the puzzle and grid to check if it's valid
        for (int cnt1 = 0; cnt1 < rows; cnt1++) {
            for (int cnt2 = 0; cnt2 < cols; cnt2++) {
                if (i + cnt1 >= 4 || j + cnt2 >= 4) {
                    return false;
                } else {
                    Rectangle rectangle = (Rectangle) getNodeByRowColumnIndex(i + cnt1, j + cnt2, gridPane);
                    if ((puzzle.get(cnt1).get(cnt2) != 0 && rectangle.getFill() != listOfColors.get(0))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void printFourD(List<List<List<List<Integer>>>> arrayOf3DMatrices) {
        for (List<List<List<Integer>>> threeDMatrix : arrayOf3DMatrices) {
            printThreeD(threeDMatrix);
        }
        try {
            fw.close();
        } catch (Exception e) {
        }
    }

    public List<List<Integer>> copyGrid(List<List<Integer>> grid) {
        List<List<Integer>> newGrid = new ArrayList<>(grid.size());
        for (List<Integer> row : grid) {
            List<Integer> newRow = new ArrayList<>(row);
            newGrid.add(newRow);
        }
        return newGrid;
    }
    public void checkIfSolutionFound()
    {
        isSolutionFound = true;
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                Rectangle rectangle = (Rectangle)getNodeByRowColumnIndex(i, j, gridPane);
                if(rectangle.getFill() == Color.WHITE)
                {
                    isSolutionFound = false;
                    break;
                }
            }
        }
    }
}
