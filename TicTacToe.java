public class TicTacToe {
    public static void main(String[] args) {
        int[][] ticTacToeBoard = new int[6][6]; // A board initially set to 0 --> EMPTY
        ticTacToeBoard[2][1] = 2; // 2 --> O
        ticTacToeBoard[2][2] = 1; // 1 --> X
        ticTacToeBoard[3][1] = 2;
        ticTacToeBoard[3][2] = 2;
        ticTacToeBoard[3][3] = 1;
        ticTacToeBoard[4][0] = 2;
        ticTacToeBoard[4][1] = 1;
        ticTacToeBoard[4][2] = 1;
        ticTacToeBoard[4][3] = 2;
        ticTacToeBoard[5][1] = 1;

        printMatrix(ticTacToeBoard);

        int heuristic = calculateHeuristic(ticTacToeBoard);
        System.out.print("Heuristic: " + heuristic);
    }

    public static void printMatrix(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j] == 0) {
                    System.out.print("/");
                } else if (mat[i][j] == 1) {
                    System.out.print("X");
                } else {
                    System.out.print("O");
                }
                System.out.print(" ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static int calculateHeuristic(int[][] mat) {
        int h = 0;
        h += (5 * calculate2sidesOpenOn3forVal(mat, 1));
        h -= (10 * calculate2sidesOpenOn3forVal(mat, 2));
        h += (3 * calculate1sideOpenOn3forVal(mat, 1));
        h -= (6 * calculate1sideOpenOn3forVal(mat, 2));
        h += calculate1orBothSidesOpen2forVal(mat, 1);
        h -= calculate1orBothSidesOpen2forVal(mat, 2);
        return h;
    }

    public static int calculate2sidesOpenOn3forVal(int[][] mat, int val) {
        // 2 sides open on each end of a '3in row' for val (1==X, 2==O)
        // Do not duplicate rows -- only need to calculate path going 'East', 'Southeast', and 'South'
        int num = 0;

        // Go through every element in the array. Check if there are empty spaces on either side of 3in row
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                // First, check if the element is even val
                if (mat[i][j] == val) {
                    // Check East
                    // First, check that going left once and going right thrice is not out of bounds
                    if ((j >= 1) && (j <= 2)) {
                        // Possible for 3 in a row with a space on either end horizontally
                        if (mat[i][j-1] == 0 && mat[i][j+3] == 0) {
                            // Spaces on either side of the board are indeed empty
                            // Now check if the next two elements are all val
                            if (mat[i][j+1] == val && mat[i][j+2] == val) {
                                // Good! There is a row of 3 vals with an empty space on its left and right
                                num++;
                            }
                        }
                    }

                    // Check South
                    // First, check that going up once and going down thrice is not out of bounds
                    if ((i >= 1) && (i <= 2)) {
                        // Possible for 3 in a row with a space on either side vertically
                        if (mat[i-1][j] == 0 && mat[i+3][j] == 0) {
                            // Spaces on either side of the board are indeed empty
                            // Now check if the next two elements are all val
                            if (mat[i+1][j] == val && mat[i+2][j] == val) {
                                // Good! There is a row of 3 vals with an empty space on its bottom and top
                                num++;
                            }
                        }
                    }

                    // Check SouthEast
                    // First, check that going down and right thrice is not out of bounds, and up and left once is not
                    if ((i >= 1) && (i <= 2) && (j >= 1) && (j <= 2)) {
                        // Possible for 3 in a row with a space on either side diagonally (\)
                        if (mat[i - 1][j - 1] == 0 && mat[i + 3][j + 3] == 0) {
                            // Spaces on either side of the board are indeed empty
                            // Now check if the next two elements are all val
                            if (mat[i + 1][j + 1] == val && mat[i + 2][j + 2] == val) {
                                // Good! There is a row of 3 vals with an empty space on its bottom-right and top-left
                                num++;
                            }
                        }
                    }

                    // Check SouthWest
                    // First, check that going down and left thrice is not out of bounds, and up and right once is not
                    if ((i >= 1) && (i <= 2) && (j >= 3) && (j <= 4)) {
                        // Possible for 3 in a row with a space on either side diagonally (/)
                        if (j + 2 <= 5) {
                            if (mat[i - 1][j + 1] == 0 && mat[i + 3][j - 3] == 0) {
                                // Spaces on either side of the board are indeed empty
                                // Now check if the next two elements are all val
                                if (mat[i + 1][j - 1] == val && mat[i + 2][j - 2] == val) {
                                    // Good! There is a row of 3 vals with an empty space on its bottom-right and top-left
                                    num++;
                                }
                            }
                        }
                    }
                }
            }
        }

        return num;
    }

    public static int calculate1sideOpenOn3forVal(int[][] mat, int val) {
        // 1 side open on either end of a '3in row' for val (1==X, 2==O)
        // Do not duplicate rows -- only need to calculate path going 'East', 'Southeast', and 'South'
        int num = 0;

        // Go through every element in the array. Check if there are empty spaces on either side of 3in row
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j] == val) {
                    // Check East
                    if (j == 0) {
                        // Empty space has to be on right
                        if (mat[i][j+3] == 0) {
                            // Space on right is empty
                            if (mat[i][j+1] == val && mat[i][j+2] == val) {
                                // Good!
                                num++;
                            }
                        }
                    } else if (j == 3) {
                        // Empty space has to be on left
                        if (mat[i][j-1] == 0) {
                            // Space on left is empty
                            if (mat[i][j+1] == val && mat[i][j+2] == val) {
                                // Good!
                                num++;
                            }
                        }
                    } else {
                        // Empty space can be on its left OR right, but not both
                        if (j - 1 >= 0 && j + 3 <= 5) {
                            if (mat[i][j - 1] == 0 && mat[i][j + 3] != 0) {
                                // Left side is empty, right side not
                                if (mat[i][j + 1] == val && mat[i][j + 2] == val) {
                                    // Good!
                                    num++;
                                }
                            } else if (mat[i][j - 1] != 0 && mat[i][j + 3] == 0) {
                                // Right side is empty, left side is not
                                if (mat[i][j + 1] == val && mat[i][j + 2] == val) {
                                    // Good!
                                    num++;
                                }
                            }
                        }
                    }

                    // Check South
                    if (i == 0) {
                        // Empty space must be on bottom
                        if (mat[i+3][j] == 0) {
                            // Space on bottom is empty
                            if (mat[i+1][j] == val && mat[i+2][j] == val) {
                                // Good!
                                num++;
                            }
                        }
                    } else if (i == 3) {
                        // Empty space must be on top
                        if (mat[i-1][j] == 0) {
                            // Space on top is empty
                            if (mat[i+1][j] == val && mat[i+2][j] == val) {
                                // Good!
                                num++;
                            }
                        }
                    } else {
                        // Empty space may be on top or bottom, but not both
                        if (i - 1 >= 0 && i + 3 <= 5) {
                            if (mat[i - 1][j] == 0 && mat[i + 3][j] != 0) {
                                // Space on top is empty, space on bottom is not
                                if (mat[i + 1][j] == val && mat[i + 2][j] == val) {
                                    // Good!
                                    num++;
                                }
                            } else if (mat[i - 1][j] != 0 && mat[i + 3][j] == 0) {
                                // Space on bottom is empty, space on top is not
                                if (mat[i + 1][j] == val && mat[i + 2][j] == val) {
                                    // Good!
                                    num++;
                                }
                            }
                        }
                    }

                    // Check Southeast
                    if (i == 0 || j == 0) {
                        // Empty space must be on bottom-right
                        if (i + 3 <= 5 && j + 3 <= 5) {
                            if (mat[i + 3][j + 3] == 0) {
                                // Space on bottom-right is empty
                                if (mat[i + 1][j + 1] == val && mat[i + 2][j + 2] == val) {
                                    num++;
                                }
                            }
                        }
                    } else if (i == 3 || j == 3) {
                        // Empty space must be on top-left
                        if (i - 1 >= 0 && j - 1 >= 0 && i + 2 <= 5 && j + 2 <= 5) {
                            if (mat[i - 1][j - 1] == 0) {
                                // Space on top-left is empty
                                if (mat[i + 1][j + 1] == val && mat[i + 2][j + 2] == val) {
                                    num++;
                                }
                            }
                        }
                    } else {
                        // Empty space can be on bottom-right ot top-left, but not both
                        if (i - 1 >= 0 && j - 1 >= 0 && i + 3 <= 5 && j + 3 <= 5) {
                            if (mat[i - 1][j - 1] == 0 && mat[i + 3][j + 3] != 0) {
                                // Space on top-left is empty, bottom right is not
                                if (mat[i + 1][j + 1] == val && mat[i + 2][j + 2] == val) {
                                    num++;
                                }
                            } else if (mat[i - 1][j - 1] != 0 && mat[i + 3][j + 3] == 0) {
                                // Space on bottom-right is empty, top-left is not
                                if (mat[i + 1][j + 1] == val && mat[i + 2][j + 2] == val) {
                                    num++;
                                }
                            }
                        }
                    }

                    // Check Southwest
                    if (i == 0 || j == 5) {
                        // Empty space must be on bottom-left
                        if (i + 3 <= 5 && j - 3 >= 0) {
                            if (mat[i+3][j-3] == 0) {
                                // Space on bottom-left is empty
                                if (mat[i+1][j-1] == val && mat[i+2][j-2] == val) {
                                    // Good!
                                    num++;
                                }
                            }
                        }
                    } else if (j == 2 || i == 3) {
                        // Empty space must be on top-right
                        if (i - 1 >= 0 && j + 1 <= 5 && j - 2 >= 0 && i + 2 <= 5) {
                            if (mat[i-1][j+1] == 0) {
                                // Space on top-right is empty
                                if (mat[i+1][j-1] == val && mat[i+2][j-2] == val) {
                                    // Good!
                                    num++;
                                }
                            }
                        }
                    } else {
                        // Empty space can be on bottom-left or top-right, but not both
                        if (i - 1 >= 0 && i + 3 <= 5 && j - 3 >= 0 && j + 1 <= 5) {
                            if (mat[i - 1][j + 1] == 0 && mat[i + 3][j - 3] != 0) {
                                // Space on top-right is empty, but space on bottom-left is not
                                if (mat[i + 1][j - 1] == val && mat[i + 2][j - 2] == val) {
                                    // Good!
                                    num++;
                                }
                            } else if (mat[i - 1][j + 1] != 0 && mat[i + 3][j - 3] == 0) {
                                // Space on bottom-left is empty, but space on top-right is not
                                if (mat[i + 1][j - 1] == val && mat[i + 2][j - 2] == val) {
                                    // Good!
                                    num++;
                                }
                            }
                        }
                    }
                }
            }
        }


        return num;
    }

    public static int calculate1orBothSidesOpen2forVal(int[][] mat, int val) {
        // Calculate how many two in a row there are (DO NOT include 3 in a row
        // Can have empty spaces on one or both ends
        int num = 0;

        // Go through every element in the array. Check if there are empty spaces on either side of 3in row
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                // First, check if this spot is the val we're looking for
                if (mat[i][j] == val) {
                    // Check East
                    if (j >= 1 && j <= 3) {
                        // Empty space can be on either side
                        if (mat[i][j - 1] == 0 || mat[i][j + 2] == 0) {
                            // Empty spot exists on one or both of the sides
                            if (mat[i][j + 1] == val && mat[i][j-1] != val && mat[i][j+2] != val) {
                                // Good! Not three in a row
                                num++;
                            }
                        }
                    } else if (j == 0) {
                        // Empty space can only be on right
                        if (mat[i][j+2] == 0) {
                            // Empty spot exists on right side
                            if (mat[i][j+1] == val) {
                                // Good!
                                num++;
                            }
                        }
                    } else if (j == 4) {
                        // Empty space can only be on left
                        if (mat[i][j-1] == 0) {
                            // Space on left is empty
                            if (mat[i][j+1] == val) {
                                // Good!
                                num++;
                            }
                        }
                    } // else j == 5 and we have no point to explore it

                    // Check South
                    if (i >= 1 && i <= 3) {
                        // Empty space can be on top or bottom
                        if (mat[i-1][j] == 0 || mat[i+2][j] == 0) {
                            // Empty spot exists on one or both of the sides
                            if (mat[i+1][j] == val && mat[i-1][j] != val && mat[i+2][j] != val) {
                                // Good! Two in a row without 3 in a row
                                num++;
                            }
                        }
                    } else if (i == 0) {
                        // Empty space must be on bottom
                        if (mat[i+2][j] == 0) {
                            // Space on bottom is empty
                            if (mat[i+1][j] == val) {
                                // Good!
                                num++;
                            }
                        }
                    } else if (i == 4) {
                        // Empty space must be on top
                        if (mat[i-1][j] == 0) {
                            // Space on top is empty
                            if (mat[i+1][j] == val) {
                                // Good!
                                num++;
                            }
                        }
                    }

                    // Check SouthEast
                    if (i >= 1 && i <= 3 && j >= 1 && j <= 3) {
                        // Empty space can be on bottom-right or top-left
                        if (mat[i-1][j-1] == 0 || mat[i+2][j+2] == 0) {
                            // Empty space on one or both sides
                            if (mat[i+1][j+1] == val && mat[i-1][j-1] != val && mat[i+2][j+2] != val) {
                                // Good! Two in a row, but not three
                                num++;
                            }
                        }
                    } else if (i == 0 || j == 0) {
                        // Empty space must be on bottom-right (but if j == 4 || i == 4, quit)
                        if (j != 4 && i != 4) {
                            // Check space on bottom right
                            if (mat[i+2][j+2] == 0) {
                                // Space on bottom-right is empty
                                if (mat[i+1][j+1] == val) {
                                    // Good! Two in a row
                                    num++;
                                }
                            }
                        }
                    } else if (i == 4 || j == 4) {
                        // Empty space must be on top-left (but if j == 0 || i == 0, quit)
                        if (j != 0 && i != 0) {
                            // Check space on top-left
                            if (mat[i-1][j-1] == 0) {
                                // Space on top-left is empty
                                if (mat[i+1][j+1] == val) {
                                    // Good! Two in a row
                                    num++;
                                }
                            }
                        }
                    } // Else, don't bother checking

                    // Check SouthWest
                    if (i >= 1 && i <= 3 && j >= 2 && j <= 4) {
                        // Empty space can be on top-right or bottom-left
                        if (mat[i-1][j+1] == 0 || mat[i+2][j-2] == 0) {
                            // One or both corners are empty
                            if (mat[i+1][j-1] == val && mat[i-1][j+1] != val && mat[i+2][j-2] != val) {
                                // Good! Two in a row but no three
                                num++;
                            }
                        }
                    } else if (i == 0 || j == 5) {
                        // Empty space must be on bottom-left (if i == 4 || j == 1, quit)
                        if (i != 4 && j != 1) {
                            // Check space on bottom-left
                            if (mat[i+2][j-2] == 0) {
                                // Space on bottom-left is empty
                                if (mat[i+1][j-1] == val) {
                                    // Good!
                                    num++;
                                }
                            }
                        }
                    } else if (i == 4 || j == 1) {
                        // Empty space must be on top-right (if i == 0 || j == 5, quit)
                        if (i != 0 && j != 5) {
                            // Check space on top-right
                            if (mat[i-1][j+1] == 0) {
                                // Space on top-right is empty
                                if (mat[i+1][j-1] == val) {
                                    // Good!
                                    num++;
                                }
                            }
                        }
                    } // Else, don't bother continuing
                }
            }
        }

        return num;
    }
}
