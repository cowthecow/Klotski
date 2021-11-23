package com.laserinfinite.java.algorithm;

import com.laserinfinite.java.Executor;
import com.laserinfinite.java.board.Board;
import com.laserinfinite.java.move.Move;

import java.util.*;

public class Algorithm {

    public static ArrayList<Move> solve(String position) {
        if (position.charAt(13) == '4' && position.charAt(18) == '4') return new ArrayList<>();

        Set<String> previousPositions = new HashSet<>();
        Queue<Board> currentPositions = new ArrayDeque<>();
        currentPositions.add(new Board(position, new ArrayList<>()));

        int movesPlayed = 0;

        for (; ; ) {
            ArrayList<Board> childrenBoards = new ArrayList<>();
            while (!currentPositions.isEmpty()) {
                Board board = currentPositions.poll();
                if (previousPositions.contains((board.getPosition()))) continue;

                previousPositions.add((board.getPosition()));
                previousPositions.add((board.getMirrorPosition()));

                for (Move move : board.getAllLegalMoves()) {
                    Board child = new Board(board.getPosition(), new ArrayList<>(board.getMoveHistory())).playMove(move);

                    if (child.getPosition().charAt(13) == '4' && child.getPosition().charAt(18) == '4') return child.getMoveHistory();
                    if (previousPositions.contains((child.getPosition()))) continue;

                    childrenBoards.add(new Board(board.getPosition(), new ArrayList<>(board.getMoveHistory())).playMove(move));

                }
            }
            movesPlayed++;
            currentPositions.addAll(childrenBoards);

            //CRASH CONDITION
            if(movesPlayed > 300) {
                System.out.println("no");
                Executor.crash = true;
                return new ArrayList<>();
            }
        }
    }
}
