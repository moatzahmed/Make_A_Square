package com.example.makeasquare;

import java.util.*;

public class MakeASquareAlgorithm {

    public  Scanner in = new Scanner(System.in);
    public  int n;
    public  List<List<Integer>> grid = new ArrayList<>();
    public  List<List<List<List<Integer>>>> puzzles = new ArrayList<>();
    public  Set<List<List<Integer>>> goodGrids;
    public  Set<List<List<Integer>>> badGrids;

    public  void initialize() {
        goodGrids = new HashSet<>();
        badGrids = new HashSet<>();
        n = in.nextInt();
        grid = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            List<Integer> row = new ArrayList<>(Collections.nCopies(4, 0));
            grid.add(row);
        }

        puzzles = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            puzzles.add(new ArrayList<>());
        }
    }

    public  int checkGrid() {
        int ans = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ans &= (grid.get(i).get(j) != 0) ? 1 : 0;
            }
        }

        if (ans == 1) {
            goodGrids.add(copyGrid(grid));
        } else {
            goodGrids.add(copyGrid(grid));
        }

        return ans;
    }

    public  void printTwoD(List<List<Integer>> matrix) {
        for (List<Integer> row : matrix) {
            for (int number : row) {
                System.out.print(number + " ");
            }
            System.out.println();
        }
        System.out.print("--------------------------------------------");
        System.out.println();
    }

    public  void printThreeD(List<List<List<Integer>>> arrayOfMatrices) {
        for (List<List<Integer>> matrix : arrayOfMatrices) {
            printTwoD(matrix);
        }
    }

    public  void printFourD(List<List<List<List<Integer>>>> arrayOf3DMatrices) {
        for (List<List<List<Integer>>> threeDMatrix : arrayOf3DMatrices) {
            printThreeD(threeDMatrix);
        }
    }

    public  List<List<Integer>> rotate(List<List<Integer>> puzzle) {
        int n = puzzle.size();
        int m = puzzle.get(0).size();

        // Initialize rotatedPuzzle with m rows and n columns
        List<List<Integer>> rotatedPuzzle = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            rotatedPuzzle.add(new ArrayList<>(Collections.nCopies(n, 0)));
        }

        // Rotate the matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                rotatedPuzzle.get(j).set(i, puzzle.get(i).get(j));
            }
        }

        // Reverse each row in the rotatedPuzzle
        for (int i = 0; i < m; i++) {
            Collections.reverse(rotatedPuzzle.get(i));
        }

        return rotatedPuzzle;
    }

    public  List<List<Integer>> flip(List<List<Integer>> puzzle) {
        // Create a new List to store the flipped puzzle
        List<List<Integer>> flippedPuzzle = new ArrayList<>();

        // Copy the rows from the original puzzle to the flippedPuzzle
        for (List<Integer> row : puzzle) {
            List<Integer> flippedRow = new ArrayList<>(row);
            Collections.reverse(flippedRow);  // Reverse each row
            flippedPuzzle.add(flippedRow);
        }

        return flippedPuzzle;
    }

    public  List<List<List<Integer>>> handleThisPuzzle(List<List<Integer>> puzzle) {
        List<List<List<Integer>>> onePuzzleAllStates = new ArrayList<>(8);

        // Initialize the 8 states
        for (int i = 0; i < 8; i++) {
            onePuzzleAllStates.add(new ArrayList<>());
        }

        // First state is the original puzzle
        onePuzzleAllStates.set(0, puzzle);

        // Second state is the flipped puzzle
        onePuzzleAllStates.set(1, flip(onePuzzleAllStates.get(0)));

        // Rotate and flip the puzzle for the next states
        for (int i = 2; i < 8; i += 2) {
            onePuzzleAllStates.set(i, rotate(onePuzzleAllStates.get(i - 2)));
            onePuzzleAllStates.set(i + 1, flip(onePuzzleAllStates.get(i)));
        }

        return onePuzzleAllStates;
    }

    public  List<List<Integer>> takeOnePuzzle(int n, int m, int val) {
        // Create a 2D list with 'n' rows and 'm' columns
        List<List<Integer>> ret = new ArrayList<>(n);

        // Loop through each row and column, reading input and setting the values
        for (int i = 0; i < n; i++) {
            List<Integer> row = new ArrayList<>(m);
            for (int j = 0; j < m; j++) {
                int x = in.nextInt();
                row.add(x == 1 ? val : 0); // If x is 1, replace it with 'val', otherwise 0
            }
            ret.add(row); // Add the row to the 2D list
        }

        return ret;
    }

    public  boolean isValid(List<List<Integer>> puzzle, int i, int j) {
        int rows = puzzle.size();
        int cols = puzzle.get(0).size();

        // Traverse the puzzle and grid to check if it's valid
        for (int cnt1 = 0; cnt1 < rows; cnt1++) {
            for (int cnt2 = 0; cnt2 < cols; cnt2++) {
                if (i + cnt1 >= 4 || j + cnt2 >= 4 || (puzzle.get(cnt1).get(cnt2) != 0 && grid.get(cnt1 + i).get(cnt2 + j) != 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    public  void putMe(List<List<Integer>> puzzle, int i, int j) {
        int rows = puzzle.size();
        int cols = puzzle.get(0).size();

        // Traverse through the puzzle and place its values in the grid
        for (int cnt1 = 0; cnt1 < rows; cnt1++) {
            for (int cnt2 = 0; cnt2 < cols; cnt2++) {
                // Check if the grid cell is empty (0)
                if (grid.get(i + cnt1).get(j + cnt2) == 0) {
                    // Set the puzzle value into the grid cell
                    grid.get(i + cnt1).set(j + cnt2, puzzle.get(cnt1).get(cnt2));
                }
            }
        }
    }

    public  void clearMe(List<List<Integer>> puzzle, int i, int j, int puzzleID) {
        int rows = puzzle.size();
        int cols = puzzle.get(0).size();

        // Traverse through the puzzle and grid
        for (int cnt1 = 0; cnt1 < rows; cnt1++) {
            for (int cnt2 = 0; cnt2 < cols; cnt2++) {
                // Check if the grid cell matches the puzzleID
                if (grid.get(i + cnt1).get(j + cnt2) == puzzleID) {
                    // Set the grid cell to 0 if it matches puzzleID
                    grid.get(i + cnt1).set(j + cnt2, 0);
                }
            }
        }
    }

    public  void go(int remain, int stateIdx) {
        if (remain == 0) {
            checkGrid();

            return;
        }
        for (List<List<Integer>> oneState : puzzles.get(stateIdx)) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (isValid(oneState, i, j)) {
                        putMe(oneState, i, j);
                        go(remain - 1, stateIdx + 1);
                        clearMe(oneState, i, j, stateIdx + 1);
                    }
                }
            }
        }
    }

    public  List<List<Integer>> copyGrid(List<List<Integer>> grid) {
        List<List<Integer>> newGrid = new ArrayList<>(grid.size());
        for (List<Integer> row : grid) {
            List<Integer> newRow = new ArrayList<>(row);
            newGrid.add(newRow);
        }
        return newGrid;
    }
    public void allInputs()
    {
        for(int i = 0; i < n; i++)
        {
            int rows, columns;
            rows = in.nextInt();
            columns = in.nextInt();
            List<List<Integer>> currentPuzzle = takeOnePuzzle(rows, columns, i+1);
            puzzles.set(i,handleThisPuzzle(currentPuzzle));
            go(n, 0);
        }
    }
}
