package testME;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import static testME.game.instance;

public abstract class Sprite extends JPanel {

    public int xPos;
    public int yPos;
    public String direction;


    public Sprite(int x, int y) {
        setOpaque(true);
        setBounds(x, y, 25, 25);
    }

    public abstract void move();

    public int deriveSquareNumber(int x, int y) {
        int result=((y/25)*30)+(x/25);
        return result;
    }

    public void changeDirection(String turn) {
        if (turn=="up") {
            if (direction == "down") {
                direction = "up";
            } else if (direction == "left" && !frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos + 24, yPos) - 30)) {
                direction = "up";
            } else if (direction == "right" && !frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos, yPos) - 30)) {
                direction = "up";
            }
        }
        else if (turn=="down") {
            if (direction == "up") {
                direction = "down";
            }
            else if (direction =="left" && !frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos+24, yPos)+30)) {
                direction = "down";
            }
            else if (direction =="right" && !frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos, yPos)+30)) {
                direction = "down";
            }
        }
        else if (turn=="left") {
            if (direction == "right") {
                direction = "left";
            }
            else if (direction == "down" && !frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos, yPos)-1)) {
                direction = "left";
            }
            else if (direction == "up" && !frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos, yPos+24)-1) ) {
                direction = "left";
            }

        }
        else if (turn=="right") {
            if (direction == "left") {
                direction = "right";
            } else if (direction == "up" && !frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos, yPos + 24) + 1)) {
                direction = "right";
            } else if (direction == "down" && !frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos, yPos) + 1)) {
                direction = "right";

            }
        }
    }


}


class Pacman extends Sprite {
    private int diameter=20;
    private String desiredDirection;
    public boolean mouth = true;

    @Override
    public void paintComponent(Graphics g) {
        setBounds(xPos, yPos, 25, 25);
        new Circle(Color.yellow,0,0, 20).draw(g);
        if (mouth) {
            new Pie(Color.black, 0,0, 20,20, 330, 50).draw(g);
        }
    }

    public Pacman(int x, int y) {
        super(x,y);
        xPos = x;
        yPos = y;
        direction = "right";
        desiredDirection = "right";
        KeyList keys = new KeyList();
        instance.addKeyListener(keys);
    }
    @Override
    public void move() {
        try {
            if (frame.board.collectables.contains(deriveSquareNumber(xPos+12, yPos+12))) {
                frame.board.collectables.remove(frame.board.collectables.indexOf(deriveSquareNumber(xPos+12, yPos+12)));
                instance.addScore(10);
                if (frame.board.collectables.isEmpty()) {
                    frame.gameRunning = false;
                    instance.gameOver("win");
                }
            }

            if (direction.equals("left") && !frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos + 24, yPos + 12) - 1)) {
                if (xPos == 1 ) {
                    xPos+=725;
                }
                else {
                    xPos -= 1;
                }
            }
            else if (direction.equals("right") && !frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos, yPos + 12) + 1)) {
                if (xPos == 724 ) {
                    xPos-=725;
                }
                else {
                    xPos += 1;
                }
            }
            else if (direction.equals("up") && !frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos + 12, yPos + 24) - 30)) {
                if (yPos <= 0 ) {
                    yPos+=250;
                }
                yPos -= 1;
            }
            else if (direction.equals("down") && !frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos + 10, yPos) + 30)) {
                if (yPos >= 250 ) {
                    yPos-=250;
                }
                else {
                    yPos += 1;
                }
            }
            if (direction.equals("left")||direction.equals("right")) {
                if (xPos%25==0 || desiredDirection.equals("left") || desiredDirection.equals("right")) {
                    if (validTurn()) {
                        changeDirection(desiredDirection);
                    }
                }
            }
            if (direction.equals("up")||direction.equals("down")) {
                if (yPos%25==0 || desiredDirection.equals("up") || desiredDirection.equals("down")) {
                    if (validTurn()) {
                        changeDirection(desiredDirection);
                    }
                }
            }
            this.repaint();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public boolean validTurn() {
        if (desiredDirection.equals("up")) {
            if(!frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos, yPos)-30)) {
                return true;
            }
        }
        else if (desiredDirection.equals("down")) {
            if(!frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos, yPos)+30)) {
                return true;
            }
        }
        else if (desiredDirection.equals("left")) {
            if(!frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos, yPos)-1)) {
                return true;
            }

        }
        else if (desiredDirection.equals("right")) {
            if(!frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos, yPos)+1)) {
                return true;
            }

        }
        return false;
    }

    class KeyList implements KeyListener {
        public void keyPressed(KeyEvent keyboardInput) {
            int key = keyboardInput.getKeyCode();
            if (key == KeyEvent.VK_UP) {
                desiredDirection = "up";
            } else if (key == KeyEvent.VK_DOWN) {
                desiredDirection = "down";
            } else if (key == KeyEvent.VK_LEFT) {
                desiredDirection = "left";
            } else if (key == KeyEvent.VK_RIGHT) {
                desiredDirection = "right";
            }
        }

        public void keyReleased(KeyEvent keyboardInput){};
        public void keyTyped(KeyEvent keyboardInput){};
    }


}

class Jelly extends Sprite {
    private int diameter = 20;
    private Color color;
    private Random rangen = new Random();
    private ArrayList<Integer> turns = new ArrayList<>();

    @Override
    public void paintComponent(Graphics g) {
        setBounds(xPos, yPos, 25, 25);
        new Pie(color, 0,0, 24,20, 0, 180).draw(g);
        new Rectangle(color, 1,10, 3,8).draw(g);
        new Rectangle(color, 11,10, 3,8).draw(g);
        new Rectangle(color, 21,10, 3,8).draw(g);
    }

    public Jelly(Color color, int x, int y) {
        super(x,y);
        xPos = x;
        yPos = y;
        direction="down";
        this.color = color;
    }

    public ArrayList getValidTurns(int opposite) {
        int squareNumber = deriveSquareNumber(xPos, yPos);
        ArrayList result = new ArrayList();
        if (!frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos, yPos)-30) && opposite!=1) {
            result.add(1);
        }
        if (!frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos, yPos)+1) && opposite!=2) {
            result.add(2);
        }
        if (!frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos, yPos)+30) && opposite!=3) {
            result.add(3);
        }
        if (!frame.board.currentLevelInfo.contains(deriveSquareNumber(xPos, yPos)-1) && opposite!=4) {
            result.add(4);
        }
        if (result.isEmpty()) {
            result.add(opposite);
        }
        return result;
    }

    public void intToDirection(int turn) {
        if (turn==1) {changeDirection("up");}
        else if (turn==2) {changeDirection("right");}
        else if (turn==3) {changeDirection("down");}
        else if (turn==4) {changeDirection("left"); }
    }

    @Override
    public void move() {
        if (deriveSquareNumber(xPos, yPos) == deriveSquareNumber(instance.pacMan.xPos, instance.pacMan.yPos)) {
            instance.loseLife();
        }
        if (direction.equals("right")) {
            if (xPos == 724 ) {
                xPos-=725;
            }
            else {xPos+=1;}
            if (xPos%25==0) {
                turns = getValidTurns(4);
                int index = rangen.nextInt(turns.size());
                int turn = (int) turns.get(index);
                intToDirection(turn);
            }
        }
        else if (direction.equals("left")) {
            if (xPos == 1 ) {
                xPos+=725;
            }
            else {
                xPos-=1;
            }
            if (xPos%25==0) {
                turns = getValidTurns(2);
                int index = rangen.nextInt(turns.size());
                int turn = (int) turns.get(index);
                intToDirection(turn);
            }
        }
        else if (direction.equals("down")) {
            if (yPos >= 250 ) {
                yPos-=250;
            }
            else {
                yPos += 1;
            }
            if (yPos%25==0) {
                turns = getValidTurns(1);
                int index = rangen.nextInt(turns.size());
                int turn = (int) turns.get(index);
                intToDirection(turn);
            }
        }
        else if (direction.equals("up")) {
            if (yPos <= 0 ) {
                yPos+=250;
            }
            yPos -= 1;
            if (yPos%25==0) {
                turns = getValidTurns(3);
                int index = rangen.nextInt(turns.size());
                int turn = (int) turns.get(index);
                intToDirection(turn);
            }
        }
        repaint();

    }
}


