import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

public class Board extends JComponent implements MouseInputListener, ComponentListener {
    private static final long serialVersionUID = 1L;
    private Point[][] points;
    private int size = 25;
    public int editType = 0;

    public Board(int length, int height) {
        addMouseListener(this);
        addComponentListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
        setOpaque(true);
    }

    private void initialize(int length, int height) {
        points = new Point[length][height];

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y] = new Point((y+1) % 2);
            }
        }

        for (int x = 0; x <= points.length - 1; ++x) {
            for (int y = 0; y < 2; ++y) {
                points[x][y].setType(5);
            }
            for (int y = 4; y < 6; ++y) {
                points[x][y].setType(5);
            }
        }

        for (int x = 0; x < points.length - 1; x++) {
            for (int y = 2; y < 4; y++) {
                points[x][y].setNextRight(points[x + 1][3]);
                points[x + 1][y].setPreviousRight(points[x][3]);
                points[x][y].setNextLeft(points[x + 1][2]);
                points[x + 1][y].setPreviousLeft(points[x][2]);
            }
        }

        for (int y = 2; y < 4; y++) {
            points[points.length - 1][y].setNextRight(points[0][3]);
            points[points.length - 1][y].setNextLeft(points[0][2]);
            points[0][y].setPreviousRight(points[points.length - 1][3]);
            points[0][y].setPreviousLeft(points[points.length - 1][2]);
        }
    }

    public void iteration() {
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].setMoved(false);
            }
        }

        for (int x = 0; x < points.length; ++x) {
            for (int y = 2; y < 4; ++y) {
                points[x][y].accelerate();
                points[x][y].slowDown();
                points[x][y].returnToLane();
                points[x][y].overtake();
                points[x][y].move();
            }
        }
        this.repaint();
    }

    public void clear() {
        for (int x = 0; x < points.length; ++x)
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].clear();
            }
        this.repaint();
    }


    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        g.setColor(Color.GRAY);
        drawNetting(g, size);
    }

    private void drawNetting(Graphics g, int gridSpace) {
        Insets insets = getInsets();
        int firstX = insets.left;
        int firstY = insets.top;
        int lastX = this.getWidth() - insets.right;
        int lastY = this.getHeight() - insets.bottom;

        int x = firstX;
        while (x < lastX) {
            g.drawLine(x, firstY, x, lastY);
            x += gridSpace;
        }

        int y = firstY;
        while (y < lastY) {
            g.drawLine(firstX, y, lastX, y);
            y += gridSpace;
        }

        for (x = 0; x < points.length; ++x) {
            for (y = 0; y < points[x].length; ++y) {
                float a = 1.0F;
                switch (points[x][y].getType()) {
                    case 0 -> g.setColor(new Color(1.0f, 1.0f, 1.0f, a));
                    case 1 -> g.setColor(new Color(0.0f, 1.0f, 1.0f, a));
                    case 2 -> g.setColor(new Color(0.0f, 0.0f, 1.0f, a));
                    case 3 -> g.setColor(new Color(1.0f, 0.0f, 0.0f, a));
                    case 5 -> g.setColor(new Color(0.0f, 1.0f, 0.0f, a));
                }
                g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
        clickOrClear(e);
    }

    private void clickOrClear(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x >= 0) && (y < points[x].length) && (y >= 0)) {
            if(editType==0){
                points[x][y].clicked();
            }
            else {
                points[x][y].setType(editType);
            }
            this.repaint();
        }
    }


    public void componentResized(ComponentEvent e) {
        int dlugosc = (this.getWidth() / size) + 1;
        int wysokosc = (this.getHeight() / size) + 1;
        initialize(dlugosc, wysokosc);
    }

    public void mouseDragged(MouseEvent e) {
        clickOrClear(e);
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

}
