package ass2.ass2;

import javax.swing.*;
import java.awt.*;

public class shapeComponent extends JComponent {
    Shape shape;
    int i;

    public void paintComponent(Graphics g) {
        if (frame.board.currentLevelInfo.contains(i)) {
            if (frame.board.defaultBorders.contains(i)) {
                new Square(Color.blue, 0,0,24, true).draw(g);
            }
            else {
                if (frame.board.currentTop.contains(i)) {
                    new Line(Color.blue, 25, 1).draw(g);
                }
                if (frame.board.currentBottom.contains(i)) {
                    new Line(Color.blue, 25, 2).draw(g);
                }
                if (frame.board.currentLeft.contains(i)) {
                    new Line(Color.blue, 25, 3).draw(g);
                }
                if (frame.board.currentRight.contains(i)) {
                    new Line(Color.blue, 25, 4).draw(g);
                }
            }
        }

        else if (frame.board.collectables.contains(i)) {
            new Square(Color.black,0,0, 25, false).draw(g);
            int[] xs = {12, 10, 15};
            int[] ys = {15, 20, 20};
            new Triangle(Color.orange, xs, ys, 3).draw(g);
        }
        else {
            new Square(Color.black,0,0, 25, false).draw(g);
        }
    }
    public shapeComponent(int i) {
        this.i = i;

    }
}
