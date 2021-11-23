package com.laserinfinite.java;

import com.laserinfinite.java.algorithm.Algorithm;
import com.laserinfinite.java.board.Board;
import com.laserinfinite.java.move.Move;
import com.laserinfinite.java.pieces.Piece;
import com.laserinfinite.java.pieces.Shade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Executor extends JPanel implements MouseListener {

    //SWING FIELDS
    public static final JFrame window = new JFrame("Klotski Solver");

    //BOARD FIELDS
    private static Board startingBoard = new Board("24422442233221121  1", new ArrayList<>());
    private static final ArrayList<Board> savedBoards = new ArrayList<>();
    private static int positionIndex = -1;

    //RAINBOW FRAME FIELDS
    private static final double[] color = new double[]{255, 0, 0};
    private static int indexIncreasing = 1;
    private static int increaseOrDecrease = 1;
    private static final double colorIteratingSpeed = 8;

    //SHADE FIELDS
    private static final Shade shade = new Shade(0, 0, 0);
    public static boolean showShade = false;
    private static String isHeld = "no";
    private static Point heldLocation = new Point();
    public static ArrayList<Integer> selected = new ArrayList<>();
    private static char currentTarget = ' ';
    private static int subtractedX = -1;
    private static int subtractedY = -1;

    private static boolean solve = false;
    public static boolean crash = false;
    private static final Color background = new Color(64, 8, 0);

    public static void main(String[] args) {
        initFrame();
        savedBoards.add(startingBoard);
        positionIndex++;

        while (true) {
            solve = false;
            while (!solve) window.repaint();

            long startTime = System.nanoTime();
            ArrayList<Move> awesomeness = Algorithm.solve(startingBoard.getPosition());
            System.out.println("Took " + ((System.nanoTime() - startTime) / 1000000) + " ms");

            playMoveList(awesomeness);
        }
    }

    public static void initFrame() {

        for (int i = 0; i < 10; i++) {
            JButton b = new JButton();
            b.setOpaque(false);
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
            b.setBounds(700, i * 84, 100, 84);
            if (i == 0) {
                b.addActionListener(e -> shade.setPieceType(1));
            } else if (i == 1) {
                b.addActionListener(e -> shade.setPieceType(2));
            } else if (i == 2) {
                b.addActionListener(e -> shade.setPieceType(3));
            } else if (i == 3) {
                b.addActionListener(e -> shade.setPieceType(4));
            } else if (i == 4) {
                b.addActionListener(e -> {
                    solve = false;
                    showShade = !showShade;
                    selected.clear();
                });
            } else if (i == 5) {
                b.addActionListener(e -> {
                    if (!showShade && !crash) {
                        if (solve) {
                            solve = false;
                        } else {
                            selected.clear();
                            if (startingBoard.getPosition().contains("4")) {
                                solve = true;
                            }
                        }
                    }
                });
            } else if (i == 6) {
                b.addActionListener(e -> {
                    if (!solve && !crash) {
                        try {
                            savedBoards.remove(positionIndex);
                        } catch (IndexOutOfBoundsException ignore) {
                        }
                        if (positionIndex > 0) positionIndex--;
                        startingBoard = new Board(savedBoards.get(positionIndex).getPosition(), startingBoard.getMoveHistory());
                        selected.clear();
                    }
                });
            } else if (i == 7) {
                b.addActionListener(e -> {
                    if (!solve) {
                        startingBoard.setPosition("                    ");
                        startingBoard.getPieces().clear();
                        crash = false;
                        selected.clear();
                        showShade = true;
                    }
                });

            } else if (i == 8) {
                b.addActionListener(e -> {
                    selected.clear();
                    if (!solve && !crash)
                        startingBoard = new Board("24422442233221121  1", startingBoard.getMoveHistory());
                });
            }
            window.add(b);
        }

        window.getContentPane().add(new Executor());
        window.setBackground(background);
        window.setSize(800, 875);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setVisible(true);

        Executor executor = new Executor();
        window.addMouseListener(executor);
    }

    public static void playMoveList(ArrayList<Move> moves) {
        for (Move move : moves) {
            for (int timer = 0; timer < 49600000; timer += 1) {
                window.repaint();
            }
            if (!solve) return;
            startingBoard = new Board(startingBoard.playMove(move).getPosition(), new ArrayList<>());
            savedBoards.add(startingBoard);
            positionIndex++;
        }
    }

    private static void updateColor() {
        if (increaseOrDecrease == 1) {
            color[indexIncreasing] += (colorIteratingSpeed / 100.0);
            if (color[indexIncreasing] > 254) increaseOrDecrease = 0;
        } else {
            color[indexIncreasing == 0 ? 2 : indexIncreasing - 1] -= (colorIteratingSpeed / 100.0);
            if (color[indexIncreasing == 0 ? 2 : indexIncreasing - 1] < 1) {
                increaseOrDecrease = 1;
                indexIncreasing = indexIncreasing == 2 ? 0 : indexIncreasing + 1;
            }
        }
    }

    private Color getColorFromChar(char c) {
        int transparency = 45;
        if (c == '1' || c == '2' || c == '3' || c == '4') {
            transparency += 100;
        }

        switch (c) {
            case '1':
                return new Color(128, 0, 255, transparency);
            case '2':
                return new Color(0, 125, 175, transparency);
            case '3':
                return new Color(255, 128, 0, transparency);
            case '4':
                return new Color(0, 255, 128, transparency);
        }
        return new Color(0, 0, 0, 0);
    }


    private int convertToMovement(int direction) {
        switch (direction) {
            case 1:
                return -4;
            case 2:
                return 1;
            case 3:
                return 4;
            case 4:
                return -1;
        }
        return 0;
    }

    @Override
    public void paint(Graphics g) {

        updateColor();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        shade.update();
        shade.draw(g2d);

        for (Piece piece : startingBoard.getPieces()) {
            piece.paint(g);
        }

        g.setColor(new Color((int) Math.floor(color[0]), (int) Math.floor(color[1]), (int) Math.floor(color[2]), 200));

        g2d.setStroke(new BasicStroke(50));
        g2d.drawRect(25, 25, 650, 800);
        g2d.setColor(background);
        g2d.fillRect(200, 800, 300, 51);
        g2d.setStroke(new BasicStroke(8));

        g2d.setFont(new Font("Bahnschrift", Font.PLAIN, 20));

        g2d.setColor(getColorFromChar('1'));
        g2d.fillRoundRect(733, 25, 30, 30, 10, 10);
        g2d.setColor(getColorFromChar('2'));
        g2d.fillRoundRect(733, 95, 30, 60, 10, 10);
        g2d.setColor(getColorFromChar('3'));
        g2d.fillRoundRect(718, 195, 60, 30, 10, 10);
        g2d.setColor(getColorFromChar('4'));
        g2d.fillRoundRect(718, 265, 60, 60, 10, 10);

        g2d.setColor(new Color((int) color[0], (int) color[1], (int) color[2], 200));

        String s = "HINT";
        if (solve) s = "STOP";
        String t = "SETUP";
        if (showShade) t = "PLAY";

        if (!crash) {
            int length1 = (int) g.getFontMetrics().getStringBounds(t, g).getWidth();
            g2d.drawString(t, 748 - length1 / 2, 390);
        }

        if (!crash && !showShade) {
            int length2 = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            g2d.drawString(s, 748 - length2 / 2, 470);
        }

        if (!solve && !crash) {
            int length3 = (int) g.getFontMetrics().getStringBounds("UNDO", g).getWidth();
            g2d.drawString("UNDO", 748 - length3 / 2, 550);

            int length5 = (int) g.getFontMetrics().getStringBounds("DEFAULT", g).getWidth();
            g2d.drawString("DEFAULT", 748 - length5 / 2, 710);
        }

        if (!solve) {
            int length4 = (int) g.getFontMetrics().getStringBounds("CLEAR", g).getWidth();
            g2d.drawString("CLEAR", 748 - length4 / 2, 630);
        }

        g2d.setFont(new Font("Century Gothic", Font.PLAIN, 40));

        int length7 = (int) g.getFontMetrics().getStringBounds("E X I T", g).getWidth();
        g2d.setColor(new Color((int) color[0], (int) color[1], (int) color[2]));
        g2d.drawString("E X I T", 350 - length7 / 2, 835);

        if (startingBoard.getPosition().charAt(13) == '4' && startingBoard.getPosition().charAt(18) == '4') {
            g2d.setColor(new Color(0, 255, 0, 128));
            g2d.setFont(new Font("Bahnschrift", Font.PLAIN, 100));

            int length6 = (int) g.getFontMetrics().getStringBounds("CONGRATS!", g).getWidth();
            g2d.drawString("CONGRATS!", 350 - length6 / 2, 350);

        }

        if (crash) {
            g2d.setColor(new Color(255, 0, 0, 150));
            g2d.setFont(new Font("Bahnschrift", Font.PLAIN, 80));

            int length6 = (int) g.getFontMetrics().getStringBounds("NO SOLUTION", g).getWidth();
            g2d.drawString("NO SOLUTION", 350 - length6 / 2, 350);
        }

        if (isHeld.equals("go to hell")) {
            Point p = MouseInfo.getPointerInfo().getLocation();

            int x = (int) Math.round((p.x - (Executor.window.getLocation().x) + 25) / 150.0);
            int y = (int) Math.round((p.y - (Executor.window.getLocation().y)) / 150.0);


            int index = y * 4 + x;
            index -= 5;

            int countX = 0, countY = 0;

            if (currentTarget != '1') {

                if (subtractedX == -1) {
                    try {
                        while (startingBoard.getPosition().charAt(index - (countX)) == currentTarget && !((index - countX) % 4 == 0))
                            countX++;
                    } catch (StringIndexOutOfBoundsException ignore) {
                    }

                    if (countX == 2 || countX == 4 && currentTarget == '3') subtractedX = 150;
                    if (subtractedX == -1) subtractedX = 0;
                }

                if (subtractedY == -1) {
                    try {
                        while (startingBoard.getPosition().charAt(index - (countY * 4)) == currentTarget && !((index - countX) < 3))
                            countY++;
                    } catch (StringIndexOutOfBoundsException ignore) {
                    }

                    if (countY == 2 || countY == 4 && currentTarget == '2') subtractedY = 150;
                    if (subtractedY == -1) subtractedY = 0;
                }
            }

            if (currentTarget == '1') {
                g.setColor(new Color(128, 0, 255, 200));
                g.fillRoundRect((p.x - heldLocation.x) + ((int) Math.round((heldLocation.x - subtractedX - (Executor.window.getLocation().x) + 25) / 150.0) * 150) - 95, (p.y - heldLocation.y - subtractedY) + ((int) Math.round((heldLocation.y - (Executor.window.getLocation().y) + 25) / 150.0) * 150) - 95, 140, 140, 25, 25);
            } else if (currentTarget == '2') {
                g.setColor(new Color(0, 128, 192, 200));
                g.fillRoundRect((p.x - heldLocation.x) + ((int) Math.round((heldLocation.x - subtractedX - (Executor.window.getLocation().x) + 25) / 150.0) * 150) - 95, (p.y - heldLocation.y - subtractedY) + ((int) Math.round((heldLocation.y - (Executor.window.getLocation().y) + 25) / 150.0) * 150) - 95, 140, 290, 25, 25);
            } else if (currentTarget == '3') {
                g.setColor(new Color(255, 128, 0, 200));
                g.fillRoundRect((p.x - heldLocation.x) + ((int) Math.round((heldLocation.x - subtractedX - (Executor.window.getLocation().x) + 25) / 150.0) * 150) - 95, (p.y - heldLocation.y - subtractedY) + ((int) Math.round((heldLocation.y - (Executor.window.getLocation().y) + 25) / 150.0) * 150) - 95, 290, 140, 25, 25);
            } else if (currentTarget == '4') {
                g.setColor(new Color(0, 255, 128, 200));
                g.fillRoundRect((p.x - heldLocation.x) + ((int) Math.round((heldLocation.x - subtractedX - (Executor.window.getLocation().x) + 25) / 150.0) * 150) - 95, (p.y - heldLocation.y - subtractedY) + ((int) Math.round((heldLocation.y - (Executor.window.getLocation().y) + 25) / 150.0) * 150) - 95, 290, 290, 25, 25);
            }

            g.setColor(new Color(255, 255, 255, 0));
            switch (Integer.parseInt(String.valueOf(currentTarget))) {
                case 1:
                    if (x > 0 && y > 0 && x <= 4 && y <= 5)
                        g.fillRoundRect(x * 150 - 95, y * 150 - 95, 140, 140, 25, 25);
                    break;
                case 2:
                    if (x > 0 && y > 0 && x <= 4 && y <= 4)
                        g.fillRoundRect(x * 150 - 95, y * 150 - 95, 140, 290, 25, 25);
                    break;
                case 3:
                    if (x > 0 && y > 0 && x <= 3 && y <= 5)
                        g.fillRoundRect(x * 150 - 95, y * 150 - 95, 290, 140, 25, 25);
                    break;
                case 4:
                    if (x > 0 && y > 0 && x <= 3 && y <= 4)
                        g.fillRoundRect(x * 150 - 95, y * 150 - 95, 290, 290, 25, 25);
                    break;
            }
        }

        if (!showShade) {
            Point p = MouseInfo.getPointerInfo().getLocation();
            heldLocation = p;
            int x = (int) Math.round((p.x - (Executor.window.getLocation().x) + 25) / 150.0);
            int y = (int) Math.round((p.y - (Executor.window.getLocation().y)) / 150.0);
            int index = (x + y * 4) - 5;

            if (index >= 0 && index < 20) {
                char target = startingBoard.getPosition().charAt(index);
                if (target != ' ') {
                    updateSelected(target, index);
                    currentTarget = target;
                } else {

                    if (!selected.isEmpty() && !solve && isHeld.equals("yes")) {
                        String targetForMoving = String.valueOf(startingBoard.getPosition().charAt(selected.get(0)));

                        int directionMoving = 0;
                        for (int i : selected) {
                            if (index == i - 4)
                                directionMoving = 1;

                            if (index == i - 1)
                                directionMoving = 4;

                            if (index == i + 1)
                                directionMoving = 2;

                            if (index == i + 4)
                                directionMoving = 3;

                            if (directionMoving != 0) break;
                        }
                        if (directionMoving != 0) {
                            String futurePosition = startingBoard.playMove(new Move(selected.get(0), directionMoving)).getPosition().replaceAll(targetForMoving, " ");
                            if (futurePosition.equals(startingBoard.getPosition().replaceAll(targetForMoving, " "))) {
                                if (!(directionMoving == 4 && (selected.contains(0) || selected.contains(4) || selected.contains(8) || selected.contains(12) || selected.contains(16)))) {
                                    if (!(directionMoving == 2 && (selected.contains(3) || selected.contains(7) || selected.contains(11) || selected.contains(15) || selected.contains(19)))) {
                                        startingBoard = startingBoard.playMove(new Move(selected.get(0), directionMoving));
                                        for (int i = 0; i < selected.size(); i++)
                                            selected.set(i, selected.get(i) + convertToMovement(directionMoving));
                                        window.repaint();

                                        savedBoards.add(startingBoard);

                                        positionIndex++;
                                    }
                                }
                            }
                        }

                    } else {
                        updateSelected(target, index);
                    }

                }
                window.repaint();
            }
        }
    }

    private void updateSelected(char target, int index) {
        if (target != ' ') {
            if (target == '2' && index > 3) {
                int count = 0;
                try {
                    while (startingBoard.getPosition().charAt(index - (count * 4)) == '2') count++;
                } catch (StringIndexOutOfBoundsException ignore) {
                }
                if (count == 2 || count == 4) {
                    index -= 4;
                }
            }
            if (target == '3' && index > 0) {
                int count = 0;
                try {
                    while (startingBoard.getPosition().charAt(index - (count)) == '3')
                        if ((index - count) % 4 == 0) {
                            count++;
                            break;
                        } else {
                            count++;
                        }
                } catch (StringIndexOutOfBoundsException ignore) {
                }
                if (count == 2 || count == 4) index -= 1;
            }

            if (target == '4' && index > 0)
                if (startingBoard.getPosition().charAt(index - 1) == '4') index -= 1;
            if (target == '4' && index > 3)
                if (startingBoard.getPosition().charAt(index - 4) == '4') index -= 4;
        }
        selected.clear();
        switch (target) {
            case '1':
                selected.add(index);
                break;
            case '2':
                selected.add(index);
                selected.add(index + 4);
                break;
            case '3':
                selected.add(index);
                selected.add(index + 1);
                break;
            case '4':
                selected.add(index);
                selected.add(index + 1);
                selected.add(index + 4);
                selected.add(index + 5);
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        isHeld = "yes";

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isHeld = "no";

        if (shade.getPieceType() != 0 && shade.getX() > 0 && shade.getY() > 0 && shade.isDrawn()) {
            int index = (shade.getY() - 1) * 4 + (shade.getX() - 1);
            try {

                switch (shade.getPieceType()) {
                    case 1:
                        if (startingBoard.getPosition().charAt(index) == ' ') {
                            startingBoard = new Board(new StringBuilder(startingBoard.getPosition()).replace(index, index + 1, "1").toString(), startingBoard.getMoveHistory());
                        }
                        window.repaint();
                        break;
                    case 2:
                        if (startingBoard.getPosition().charAt(index) == ' ' && startingBoard.getPosition().charAt(index + 4) == ' ') {
                            startingBoard = new Board((new StringBuilder(startingBoard.getPosition()).replace(index + 4, index + 5, "2").replace(index, index + 1, "2").toString()), startingBoard.getMoveHistory());
                        }
                        window.repaint();
                        break;
                    case 3:
                        if (startingBoard.getPosition().charAt(index) == ' ' && startingBoard.getPosition().charAt(index + 1) == ' ') {
                            startingBoard = new Board(new StringBuilder(startingBoard.getPosition()).replace(index, index + 1, "3").replace(index + 1, index + 2, "3").toString(), startingBoard.getMoveHistory());
                        }
                        window.repaint();
                        break;
                    case 4:
                        if (startingBoard.getPosition().charAt(index) == ' ' && startingBoard.getPosition().charAt(index + 1) == ' ' && startingBoard.getPosition().charAt(index + 4) == ' ' && startingBoard.getPosition().charAt(index + 5) == ' ') {
                            startingBoard = new Board(new StringBuilder(startingBoard.getPosition()).replace(index, index + 1, "4").replace(index + 1, index + 2, "4").replace(index + 4, index + 5, "4").replace(index + 5, index + 6, "4").toString(), startingBoard.getMoveHistory());
                        }
                        window.repaint();
                        break;
                }
            } catch (StringIndexOutOfBoundsException ignore) {
            }
            savedBoards.add(startingBoard);
            positionIndex++;

        }
        selected.clear();
        window.repaint();


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
