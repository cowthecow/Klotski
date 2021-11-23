package com.laserinfinite.java.pieces;

import com.laserinfinite.java.Executor;
import com.laserinfinite.java.board.Board;
import com.laserinfinite.java.move.Move;

import java.awt.*;
import java.util.ArrayList;

public class Piece2x2 extends Piece {

    private final ArrayList<Integer> piecePositions;

    public Piece2x2(ArrayList<Integer> piecePositions) {
        this.piecePositions = piecePositions;
    }

    @Override
    public ArrayList<Move> getLegalMoves(Board board) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        if (piecePositions.get(0) > 3) {//UP
            if (board.getPosition().charAt((piecePositions.get(0) - 4)) == ' ' &&
                    board.getPosition().charAt((piecePositions.get(1) - 4)) == ' ')
                legalMoves.add(new Move(piecePositions.get(0), 1));
        }
        if (piecePositions.get(0) < 16) {//DOWN
            try {
                if (board.getPosition().charAt((piecePositions.get(2) + 4)) == ' ' &&
                        board.getPosition().charAt((piecePositions.get(3) + 4)) == ' ')
                    legalMoves.add(new Move(piecePositions.get(0), 3));
            } catch (StringIndexOutOfBoundsException ignore) {
            }
        }
        if (piecePositions.get(1) % 4 != 3) {//RIGHT
            if (board.getPosition().charAt((piecePositions.get(1) + 1)) == ' ' &&
                    board.getPosition().charAt((piecePositions.get(3) + 1)) == ' ')
                legalMoves.add(new Move(piecePositions.get(0), 2));
        }
        if (piecePositions.get(0) % 4 != 0) {//LEFT
            if (board.getPosition().charAt((piecePositions.get(0) - 1)) == ' '
                    && board.getPosition().charAt((piecePositions.get(2) - 1)) == ' ')
                legalMoves.add(new Move(piecePositions.get(0), 4));
        }
        return legalMoves;
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0,255,128,128));
        if(!Executor.showShade)g2d.setColor(new Color(0,255,128,200));

        int x = piecePositions.get(0)%4;
        int y = (int)(piecePositions.get(0)/4.0);
        x += 1;
        y += 1;

        g2d.fillRoundRect(x*150-95,y*150-95,290,290,25,25);
    }

}
