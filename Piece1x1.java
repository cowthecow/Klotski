package com.laserinfinite.java.pieces;

import com.laserinfinite.java.Executor;
import com.laserinfinite.java.board.Board;
import com.laserinfinite.java.move.Move;

import java.awt.*;
import java.util.ArrayList;

public class Piece1x1 extends Piece {

    private final ArrayList<Integer> piecePositions;

    public Piece1x1(ArrayList<Integer> piecePositions) {
        this.piecePositions = piecePositions;
    }

    @Override
    public ArrayList<Move> getLegalMoves(Board board) {
        ArrayList<Move> legalMoves = new ArrayList<>();
        if (piecePositions.get(0) > 3) {//UP
            if (board.getPosition().charAt((piecePositions.get(0) - 4)) == ' ')
                legalMoves.add(new Move(piecePositions.get(0), 1));
        }
        if (piecePositions.get(0) % 4 != 3) {//RIGHT
            if (board.getPosition().charAt((piecePositions.get(0) + 1)) == ' ')
                legalMoves.add(new Move(piecePositions.get(0), 2));
        }
        if (piecePositions.get(0) < 16) {//DOWN
            if (board.getPosition().charAt((piecePositions.get(0) + 4)) == ' ')
                legalMoves.add(new Move(piecePositions.get(0), 3));
        }
        if (piecePositions.get(0) % 4 != 0) {//LEFT
            if (board.getPosition().charAt((piecePositions.get(0) - 1)) == ' ')
                legalMoves.add(new Move(piecePositions.get(0), 4));
        }

        return legalMoves;
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(128,0,255,128));
        if(!Executor.showShade)g2d.setColor(new Color(128,0,255,200));

        int x = piecePositions.get(0)%4;
        int y = (int)(piecePositions.get(0)/4.0);
        x += 1;
        y += 1;

        g2d.fillRoundRect(x*150-95,y*150-95,140,140,25,25);
    }
}
