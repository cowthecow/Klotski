package com.laserinfinite.java.board;

import com.laserinfinite.java.Executor;
import com.laserinfinite.java.move.Move;
import com.laserinfinite.java.pieces.*;

import java.util.ArrayList;

public class Board {

    private String position;
    private final ArrayList<Piece> pieces;
    private final ArrayList<Move> moveHistory;

    public Board(String position, ArrayList<Move> moveHistory) {
        this.position = position;

        this.moveHistory = moveHistory;
        this.pieces = new ArrayList<>();

        ArrayList<Integer> skippedIndexes = new ArrayList<>();
        for (int i = 0; i < position.length(); i++) {
            if (skippedIndexes.contains(i)) continue;
            char c = position.charAt(i);
            switch (c) {
                case '1':
                    ArrayList<Integer> positions1 = new ArrayList<>();
                    positions1.add(i);

                    pieces.add(new Piece1x1(positions1));
                    break;

                case '2':
                    ArrayList<Integer> positions2 = new ArrayList<>();
                    positions2.add(i);
                    positions2.add(i + 4);

                    skippedIndexes.add(i + 4);

                    pieces.add(new Piece1x2(positions2));
                    break;
                case '3':
                    ArrayList<Integer> positions3 = new ArrayList<>();
                    positions3.add(i);
                    positions3.add(i + 1);

                    skippedIndexes.add(i + 1);

                    pieces.add(new Piece2x1(positions3));
                    break;
                case '4':
                    ArrayList<Integer> positions4 = new ArrayList<>();
                    positions4.add(i);
                    positions4.add(i + 1);
                    positions4.add(i + 4);
                    positions4.add(i + 5);

                    skippedIndexes.add(i + 1);
                    skippedIndexes.add(i + 4);
                    skippedIndexes.add(i + 5);

                    pieces.add(new Piece2x2(positions4));
                    break;
            }
        }
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ArrayList<Move> getMoveHistory() {
        return moveHistory;
    }

    public ArrayList<Move> getAllLegalMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        for (Piece piece : pieces) moves.addAll(piece.getLegalMoves(this));
        return moves;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public String getMirrorPosition() {
        StringBuilder mirrorPosition = new StringBuilder();
        for (int rows = 0; rows < 5; rows++) {
            for (int cols = 4; cols > 0; cols--) {
                mirrorPosition.append(this.position.charAt((rows * 4) + cols - 1));
            }
        }
        return mirrorPosition.toString();
    }


    public static String swapWithIndex(String s, int i1, int i2) {
        if (i1 < 0 || i1 >= 20 || i2 < 0 || i2 >= 20) return s;
        char[] array = s.toCharArray();
        char temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
        return String.valueOf(array);
    }

    public Board playMove(Move move) {
        moveHistory.add(move);
        char charOnPosition = position.charAt(move.startLocation());
        if (charOnPosition == '1') {
            switch (move.getDirection()) {
                case 1:
                    return new Board(swapWithIndex(position, move.startLocation(), move.startLocation() - 4), moveHistory);
                case 2:
                    return new Board(swapWithIndex(position, move.startLocation(), move.startLocation() + 1), moveHistory);
                case 3:
                    return new Board(swapWithIndex(position, move.startLocation(), move.startLocation() + 4), moveHistory);
                case 4:
                    return new Board(swapWithIndex(position, move.startLocation(), move.startLocation() - 1), moveHistory);
            }
        } else if (charOnPosition == '2') {
            switch (move.getDirection()) {
                case 1:
                    return new Board(swapWithIndex(swapWithIndex(position, move.startLocation(), move.startLocation() - 4), move.startLocation() + 4, move.startLocation()), moveHistory);
                case 2:
                    return new Board(swapWithIndex(swapWithIndex(position, move.startLocation(), move.startLocation() + 1), move.startLocation() + 4, move.startLocation() + 5), moveHistory);
                case 3:
                    return new Board(swapWithIndex(swapWithIndex(position, move.startLocation() + 4, move.startLocation() + 8), move.startLocation(), move.startLocation() + 4), moveHistory);
                case 4:
                    return new Board(swapWithIndex(swapWithIndex(position, move.startLocation(), move.startLocation() - 1), move.startLocation() + 4, move.startLocation() + 3), moveHistory);
            }
        } else if (charOnPosition == '3') {
            switch (move.getDirection()) {
                case 1:
                    return new Board(swapWithIndex(swapWithIndex(position, move.startLocation(), move.startLocation() - 4), move.startLocation() + 1, move.startLocation() - 3), moveHistory);
                case 2:
                    return new Board(swapWithIndex(swapWithIndex(position, move.startLocation() + 1, move.startLocation() + 2), move.startLocation(), move.startLocation() + 1), moveHistory);
                case 3:
                    return new Board(swapWithIndex(swapWithIndex(position, move.startLocation(), move.startLocation() + 4), move.startLocation() + 1, move.startLocation() + 5), moveHistory);
                case 4:
                    return new Board(swapWithIndex(swapWithIndex(position, move.startLocation(), move.startLocation() - 1), move.startLocation() + 1, move.startLocation()), moveHistory);
            }
        } else if (charOnPosition == '4') {
            switch (move.getDirection()) {
                case 1:
                    String moveUp = position;
                    moveUp = swapWithIndex(moveUp, move.startLocation(), move.startLocation() - 4);
                    moveUp = swapWithIndex(moveUp, move.startLocation() + 1, move.startLocation() - 3);
                    moveUp = swapWithIndex(moveUp, move.startLocation() + 4, move.startLocation());
                    moveUp = swapWithIndex(moveUp, move.startLocation() + 5, move.startLocation() + 1);
                    return new Board(moveUp, moveHistory);
                case 2:
                    String moveRight = position;
                    moveRight = swapWithIndex(moveRight, move.startLocation() + 1, move.startLocation() + 2);
                    moveRight = swapWithIndex(moveRight, move.startLocation() + 5, move.startLocation() + 6);
                    moveRight = swapWithIndex(moveRight, move.startLocation(), move.startLocation() + 1);
                    moveRight = swapWithIndex(moveRight, move.startLocation() + 4, move.startLocation() + 5);
                    return new Board(moveRight, moveHistory);
                case 3:
                    String moveDown = position;
                    moveDown = swapWithIndex(moveDown, move.startLocation() + 4, move.startLocation() + 8);
                    moveDown = swapWithIndex(moveDown, move.startLocation() + 5, move.startLocation() + 9);
                    moveDown = swapWithIndex(moveDown, move.startLocation() + 1, move.startLocation() + 5);
                    moveDown = swapWithIndex(moveDown, move.startLocation(), move.startLocation() + 4);
                    return new Board(moveDown, moveHistory);
                case 4:
                    String moveLeft = position;
                    moveLeft = swapWithIndex(moveLeft, move.startLocation(), move.startLocation() - 1);
                    moveLeft = swapWithIndex(moveLeft, move.startLocation() + 4, move.startLocation() + 3);
                    moveLeft = swapWithIndex(moveLeft, move.startLocation() + 1, move.startLocation());
                    moveLeft = swapWithIndex(moveLeft, move.startLocation() + 5, move.startLocation() + 4);
                    return new Board(moveLeft, moveHistory);
            }

        }

        return null;
    }
}
