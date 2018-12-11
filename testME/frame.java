package testME;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;

import static java.lang.Thread.sleep;

public class frame extends JFrame {
    public static Level board;
    public Pacman pacMan;
    private Sprite jell1, jell2, jell3, jell4;
    public int score;
    public int lives;
    public static boolean gameRunning;
    public informationPanel infoPanel;
    private JPanel overlay =  new JPanel();
    private JPanel endPanel = new JPanel(new BorderLayout());
    private JLayeredPane holder = new JLayeredPane();
    public static Font font =  new Font("Times Roman",Font.BOLD, 20);
    public ScoreHistory myFile;
    public JLabel resultMessage = new JLabel();
    JLabel endScore = new JLabel();
    private StyledDocument hs;
    private long startTime, endTime;
    double timeElapsed;
    int counter=0;

    public frame(String title) {
        super(title);
        setSize(760, 350);
        setPreferredSize(new Dimension(760,350));
        setBackground(Color.black);

        add(holder, BorderLayout.CENTER);

        JLabel message = new JLabel("Click to Start");
        message.setFont(font);
        message.setForeground(Color.white);
        overlay.setBackground(Color.black);
        overlay.add(message);
        overlay.setOpaque(true);

        myFile = new ScoreHistory("scores.txt");

        holder.setBounds(0,0, 750,275);
        overlay.setBounds(0,0, 775,350);

        holder.add(overlay, new Integer(2), 0);

        overlay.addMouseListener(new ml());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        repaint();
    }

    public boolean loadLevel(int levelID) {
        board = new Level(levelID);
        pacMan = new Pacman(board.pacInitialX, board.pacInitialY);
        jell1 = new Jelly(Color.red, 250, 100);
        jell2 = new Jelly(Color.orange, 250, 150);
        jell3 = new Jelly(Color.pink, 250, 160);
        jell4 = new Jelly(Color.green, 250, 125);
        score = 0;
        lives = 3;
        counter = 0;
        infoPanel = new informationPanel();
        board.setOpaque(true);
        board.setBounds(0,0, 750,275);
        holder.add(board, 0, 0);
        holder.add(infoPanel, 1, 0);
        holder.add(jell1, 1, 0);
        holder.add(jell2, 1, 0);
        holder.add(jell3,1,0);
        holder.add(jell4,1,0);
        holder.add(pacMan, 2, 0);
        gameRunning = true;
        startTime = System.currentTimeMillis();
        new spriteThread();
        new jellyThread();

        return true;
    }

    public void addScore(int amount) {
        score+=amount;
        informationPanel.scoreMessage.setText("Score: "+score);
    }


    public void loseLife() {
        lives-=1;
        if (lives==0) {
            gameOver("lose");
        }
        else {
            informationPanel.livesMessage.setText("Lives: " + lives);
            reset();
        }

    }

    public void reset() {
        pacMan.xPos = board.pacInitialX;
        pacMan.yPos = board.pacInitialY;
        jell1.xPos = jell2.xPos = jell3.xPos = jell4.xPos = board.jellRestartX;
        jell1.yPos = jell2.yPos = jell3.yPos = jell4.yPos = board.jellRestartY;

    }

    public void move() {
        pacMan.move();
        jell1.move();
        jell2.move();
        jell3.move();
        jell4.move();
        if (counter%10==0) {
            pacMan.mouth ^= true;
        }
        counter++;
        repaint();
    }

    public void releaseJelly(int jellyID) {
        if (jellyID == 1 )  {
            jell1.xPos = board.jellRestartX;
            jell1.yPos = board.jellRestartY;
        }
        else if (jellyID == 2 )  {
            jell2.xPos = board.jellRestartX;
            jell2.yPos = board.jellRestartY;
        }
        else if (jellyID == 3 )  {
            jell3.xPos = board.jellRestartX;
            jell3.yPos = board.jellRestartY;
        }
        else if (jellyID == 4 )  {
            jell4.xPos = board.jellRestartX;
            jell4.yPos = board.jellRestartY;
        }
    }

    public void gameOver(String result) {
        holder.removeAll();
        gameRunning=false;

        endTime = System.currentTimeMillis();
        timeElapsed = endTime-startTime;

        if (result.equals("win")) {
            resultMessage.setText("Congratulations, you won!");
            score =(int)( score * (1/(Math.log(timeElapsed*0.001))+1) * lives);
        }
        else if (result.equals("lose")) {
            resultMessage.setText("You're out of lives, better luck next time.");
        }

        myFile.write(score);

        JPanel textPanel =  new JPanel(new BorderLayout());
        textPanel.setBackground(Color.black);

        JTextPane highScores = new JTextPane();
        highScores.setBackground(Color.black);
        highScores.setEditable(false);
        highScores.setForeground(Color.white);
        hs = highScores.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        hs.setParagraphAttributes(0, hs.getLength(), center, false);

        JPanel butPanel = new JPanel();
        butPanel.setBackground(Color.black);

        resultMessage.setFont(font);
        resultMessage.setForeground(Color.white);
        endScore.setFont(font);
        endScore.setHorizontalAlignment(JLabel.CENTER);
        endScore.setForeground(Color.white);
        resultMessage.setHorizontalAlignment(JLabel.CENTER);
        endPanel.setBackground(Color.black);
        endPanel.setBounds(0,0, 750,310);

        highScores.setText(myFile.getTop10());
        endScore.setText("<html>You got "+score+" points. Rank: "+myFile.getRank(score)+"<br> That round took "+String.format("%.2f",timeElapsed*0.001)+" seconds</html>");

        JButton restartButton =  new JButton("New Game!");
        restartButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        holder.setVisible(true);
                        remove(endPanel);
                        endPanel.removeAll();
                        loadLevel(1);

                    }});
        restartButton.setFocusable(false);
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                }
        );
        JButton clearScoresButton = new JButton("Clear High Scores");
        clearScoresButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        myFile.clear();
                    }
                }
        );
        clearScoresButton.setFocusable(false);

        textPanel.add(endScore, BorderLayout.NORTH);
        textPanel.add(highScores, BorderLayout.CENTER);
        butPanel.add(restartButton);
        butPanel.add(quitButton);
        butPanel.add(clearScoresButton);
        endPanel.add(resultMessage, BorderLayout.NORTH);
        endPanel.add(textPanel, BorderLayout.CENTER);
        endPanel.add(butPanel, BorderLayout.SOUTH);
        holder.add(endPanel);
        board.requestFocus();
        repaint();
    }

    private class ml extends MouseAdapter {
        public void mouseClicked(MouseEvent ev) {
            overlay.setVisible(false);
            loadLevel(1);
        }
    }
}

class spriteThread extends Thread {

    public spriteThread() {
        start();
    }

    public void run() {
        while (frame.gameRunning) {

            try {
                sleep(10);
            }
            catch (Exception e) {}
            game.instance.move();
        }
    }
}

class jellyThread extends Thread {

    public jellyThread() {
        start();
    }

    public void run() {
        for (int i=1; i<5; i++) {
            game.instance.releaseJelly(i);
            try {
                sleep(10000);
            }
            catch (Exception e) {}
        }
    }
}