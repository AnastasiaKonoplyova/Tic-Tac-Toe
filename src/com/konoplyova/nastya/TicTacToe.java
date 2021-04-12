package com.konoplyova.nastya;


public class TicTacToe {
    private Point[][] gameBoard;
    private Point winPoint;
    private final int ROW_SIZE=3;
    private int availableMoves = 9;
    private boolean nextTurn;
    private int gameMode;

    public TicTacToe(){
        gameBoard = new Point[ROW_SIZE][ROW_SIZE];
        nextTurn = true;
        winPoint = Point.EMPTY;
        fillBoard();
        instructions();
    }
    private void instructions(){
        System.out.println("Hello, it's Tic-Tac-Toe\nYou have choice play VS human(1) or computer(2)." +
                "To make move you need to enter two numbers [0-2][0-2]" +
                "\n00|01|02" +
                "\n--------" +
                "\n10|11|12" +
                "\n--------" +
                "\n20|21|22" +
                "\nAnd may the odds be ever in your favor!");
    }
    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }
    public int getGameMode() {
        return this.gameMode;
    }

    public int getAvailableMoves() {
        return availableMoves;
    }
    private void fillBoard(){
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int col = 0; col < ROW_SIZE; col++) {
                gameBoard[row][col] = Point.EMPTY;
            }
        }
    }
    public boolean moveByHuman(int row, int col){
        if (availablePoint(row,col)){
            setPoint(row, col);
            printBoard();
            return false;
        }
        return true;
    }
    private void setPoint(int row, int col){
           gameBoard[row][col] = (nextTurn) ? Point.X : Point.O;
           availableMoves--;
    }
    public void moveByCPU(){
        int[] move = getBestMove(gameBoard);
        setPoint(move[0], move[1]);
        printBoard();
    }
    public boolean isGameOver(){
        if (availableMoves == 0 || checkWinner()){
           return true;
        }
        return false;
    }
    public boolean checkWinner(){
        int winX = Point.X.getPoint() * ROW_SIZE;
        int winO = Point.O.getPoint() * ROW_SIZE;
        int checkLine = 0;
        for (int i =0; i< ROW_SIZE; i++){
            for (int j = 0; j < ROW_SIZE; j++){
                checkLine += gameBoard[i][j].getPoint();
            }
            if (checkLine == winX){
                winPoint = Point.X;
                return true;
            } else if (checkLine == winO){
                winPoint = Point.O;
                return true;
            }
            checkLine = 0;
        }
        checkLine = 0;
        for (int col = 0; col < ROW_SIZE; col++) {
            for (int row = 0; row < ROW_SIZE; row++) {
                checkLine += gameBoard[row][col].getPoint();
            }
            if (checkLine == winX){
                winPoint = Point.X;
                return true;
            } else if (checkLine == winO){
                winPoint = Point.O;
                return true;
            }
            checkLine = 0;
        }
        checkLine = 0;
        for (int i = 0; i < ROW_SIZE; i++) {
            checkLine += gameBoard[i][i].getPoint();
        }
        if (checkLine == winX){
            winPoint = Point.X;
            return true;
        } else if (checkLine == winO){
            winPoint = Point.O;
            return true;
        }
        checkLine = 0;
        int indexMax = ROW_SIZE - 1;
        for (int i = 0; i <= indexMax; i++) {
            checkLine += gameBoard[i][indexMax - i].getPoint();
        }
        if (checkLine == winX){
            winPoint = Point.X;
            return true;
        } else if (checkLine == winO){
            winPoint = Point.O;
            return true;
        }
        return false;
    }
    public void changedTurn(){
        nextTurn = !nextTurn;
    }
    private boolean availablePoint(int row, int col) { return gameBoard[row][col] == Point.EMPTY; }
    public void printWinner(){
        if (winPoint == Point.EMPTY){
            System.out.println("No one is win.");
        }else{
            System.out.println(winPoint.getPoint() + " is win!");
        }
    }
    private void printBoard(){
        System.out.println(gameBoard[0][0].getPoint()+ "|"+ gameBoard[0][1].getPoint() +"|"+ gameBoard[0][2].getPoint() +
                "\n-----\n" +
                gameBoard[1][0].getPoint()+ "|"+ gameBoard[1][1].getPoint() +"|"+ gameBoard[1][2].getPoint() +
                "\n-----\n" +
                gameBoard[2][0].getPoint()+ "|"+ gameBoard[2][1].getPoint() +"|"+ gameBoard[2][2].getPoint() );
    }
    private int miniMax(Point[][] board, int depth, boolean isMax) {
        int boardVal = evaluateBoard(board);
        // Terminal node (win/lose/draw) or max depth reached.
        if (Math.abs(boardVal) == 10 || getAvailableMoves() > 0) {
            return boardVal;
        }

        // Maximising player, find the maximum attainable value.
        if (isMax) {
            int highestVal = Integer.MIN_VALUE;
            for (int row = 0; row <ROW_SIZE; row++) {
                for (int col = 0; col < ROW_SIZE; col++) {
                    if (availablePoint(row, col)) {
                        gameBoard[row][col] = Point.X;
                        highestVal = Math.max(highestVal, miniMax(board,
                                depth + 1, false));
                        gameBoard[row][col] = Point.EMPTY;
                    }
                }
            }
            return highestVal;
            // Minimising player, find the minimum attainable value;
        } else {
            int lowestVal = Integer.MAX_VALUE;
            for (int row = 0; row < ROW_SIZE; row++) {
                for (int col = 0; col < ROW_SIZE; col++) {
                    if (availablePoint(row, col)) {
                        gameBoard[row][col] = Point.O;
                        lowestVal = Math.min(lowestVal, miniMax(board,
                                depth + 1, true));
                        gameBoard[row][col] = Point.EMPTY;
                    }
                }
            }
            return lowestVal;
        }
    }
    public int[] getBestMove(Point[][] board) {
        int[] bestMove = new int[]{-1, -1};
        int bestValue = Integer.MIN_VALUE;
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int col = 0; col < ROW_SIZE; col++) {
                if (availablePoint(row, col)) {
                    gameBoard[row][col] = Point.X;
                    int moveValue = miniMax(board, 0, false);
                    gameBoard[row][col] = Point.EMPTY;
                    if (moveValue > bestValue) {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        bestValue = moveValue;
                    }
                }
            }
        }
        return bestMove;
    }
    private int evaluateBoard(Point[][] board) {
        int rowSum = 0;
        int bWidth = ROW_SIZE;
        int Xwin = Point.X.getPoint() * bWidth;
        int Owin = Point.O.getPoint() * bWidth;

        // Check rows for winner.
        for (int row = 0; row < bWidth; row++) {
            for (int col = 0; col < bWidth; col++) {
                rowSum += board[row][col].getPoint();
            }
            if (rowSum == Xwin) {
                return 10;
            } else if (rowSum == Owin) {
                return -10;
            }
            rowSum = 0;
        }

        // Check columns for winner.
        rowSum = 0;
        for (int col = 0; col < bWidth; col++) {
            for (int row = 0; row < bWidth; row++) {
                rowSum += board[row][col].getPoint();
            }
            if (rowSum == Xwin) {
                return 10;
            } else if (rowSum == Owin) {
                return -10;
            }
            rowSum = 0;
        }

        // Check diagonals for winner.
        // Top-left to bottom-right diagonal.
        rowSum = 0;
        for (int i = 0; i < bWidth; i++) {
            rowSum += board[i][i].getPoint();
        }
        if (rowSum == Xwin) {
            return 10;
        } else if (rowSum == Owin) {
            return -10;
        }

        // Top-right to bottom-left diagonal.
        rowSum = 0;
        int indexMax = bWidth - 1;
        for (int i = 0; i <= indexMax; i++) {
            rowSum += board[i][indexMax - i].getPoint();
        }
        if (rowSum == Xwin) {
            return 10;
        } else if (rowSum == Owin) {
            return -10;
        }
        return 0;
    }
}

