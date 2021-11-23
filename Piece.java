package com.laserinfinite.java.pieces;

import com.laserinfinite.java.board.Board;
import com.laserinfinite.java.move.Move;

import java.awt.*;
import java.util.ArrayList;

public abstract class Piece {

    public abstract ArrayList<Move> getLegalMoves(Board board);
    public abstract void paint(Graphics g);
}
