package ass2.ass2;

import java.awt.*;

public abstract class Shape {
    Color color;
    public Shape(Color color) {
         this.color = color;
    }
    public abstract void draw(Graphics g);
}

class Circle extends Shape {
    int diameter;
    int x,y;

    public Circle(Color color,int x, int y, int diameter) {
        super(color);
        this.diameter = diameter;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x,y,diameter, diameter);
    }

}

class Square extends Shape {
    int size;
    boolean hollow;
    int x,y;
    public Square(Color color, int x, int y, int size, boolean hollow) {
        super(color);
        this.size = size;
        this.hollow = hollow;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        if (hollow) {
            g.drawRect(x,y, size, size);
        }
        else {
            g.fillRect(x, y, size, size);
        }
    }
}

class Line extends Shape {
    int width;
    int location;
    /*
        1:top
        2:bottom
        3:left
        4:right
    */
    public Line(Color color, int width, int location) {
        super(color);
        this.location=location;
        this.width=width;
    }

    public void draw(Graphics g) {
        Graphics2D g2 =  (Graphics2D)g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(3));
        if (location==1) {
            g2.drawLine(0,0,25,0);
        }
        else if (location==2) {
            g2.drawLine(0,24,24,24);
        }
        else if (location==3) {
            g2.drawLine(0,0,0,25);
        }
        else if (location==4) {
            g2.drawLine(24,0,24,24);
        }
    }
}

class Triangle extends Shape {
    int[] xcords, ycords;
    int npoints;
    public Triangle(Color color, int[] xcords, int[] ycords, int npoints) {
        super(color);
        this.xcords =  xcords;
        this.ycords = ycords;
        this.npoints = npoints;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillPolygon(xcords, ycords, npoints);
    }
}

class Pie extends Shape {
    int x, y, width, height, startAngle, endAngle;

    public Pie(Color color, int x, int y, int width, int height, int startAngle, int endAngle) {
        super(color);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillArc(x, y, width, height, startAngle, endAngle);
    }
}

class Rectangle extends Shape {
    int x,y,width,height;

    public Rectangle(Color color, int x, int y, int width, int height) {
        super(color);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x,y,width,height);
    }
}
