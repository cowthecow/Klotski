package com.laserinfinite.java.pieces;

import com.laserinfinite.java.Executor;

import java.awt.*;

public class Shade {

    private int pieceType;
    private int x;
    private int y;
    private boolean isDrawn = false;

    public Shade(int pieceType, int x, int y) {
        this.pieceType = pieceType;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPieceType() {
        return pieceType;
    }

    public boolean isDrawn() {
        return isDrawn;
    }

    public void setPieceType(int pieceType) {
        this.pieceType = pieceType;
    }

    public void update(){
        Point p = MouseInfo.getPointerInfo().getLocation();
        this.x = (int)Math.round((p.x-(Executor.window.getLocation().x)+25) / 150.0);
        this.y = (int)Math.round((p.y-(Executor.window.getLocation().y)) / 150.0);
    }

    public void draw(Graphics2D g){
        g.setColor(new Color(255,255,255,128));
        if(Executor.showShade)
        switch (pieceType){
            case 1:
                if(x > 0 && y > 0 && x <= 4 && y <= 5)g.fillRoundRect(x*150-95,y*150-95,140,140,25,25);
                isDrawn = (x > 0 && y > 0 && x <= 4 && y <= 5);
                break;
            case 2:
                if(x > 0 && y > 0 && x <= 4 && y <= 4)g.fillRoundRect(x*150-95,y*150-95,140,290,25,25);
                isDrawn = (x > 0 && y > 0 && x <= 4 && y <= 4);
                break;
            case 3:
                if(x > 0 && y > 0 && x <= 3 && y <= 5)g.fillRoundRect(x*150-95,y*150-95,290,140,25,25);
                isDrawn = (x > 0 && y > 0 && x <= 3 && y <= 5);
                break;
            case 4:
                if(x > 0 && y > 0 && x <= 3 && y <= 4)g.fillRoundRect(x*150-95,y*150-95,290,290,25,25);
                isDrawn = (x > 0 && y > 0 && x <= 3 && y <= 4);
                break;
        }
    }
}
