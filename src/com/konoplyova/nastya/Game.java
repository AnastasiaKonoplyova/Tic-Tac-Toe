package com.konoplyova.nastya;

import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicTacToe game = new TicTacToe();
        System.out.println("Please, choose gameMode: ");
        game.setGameMode(scanner.nextInt());
        while (game.getAvailableMoves()>0){
            if (game.getGameMode() == 1){
                do {
                    System.out.println("Please, enter position:");
                } while (game.moveByHuman(scanner.nextInt(), scanner.nextInt()));
                game.changedTurn();
                if (game.isGameOver()){
                    break;
                }
            } else if(game.getGameMode() == 2){
                do {
                    System.out.println("Please, enter position:");
                } while (game.moveByHuman(scanner.nextInt(), scanner.nextInt()));
                game.changedTurn();
                if (game.isGameOver()){
                    break;
                }
                game.moveByCPU();
                if (game.isGameOver()){
                    break;
                }
                game.changedTurn();
            }
        }
        game.printWinner();
    }
}
