package ass2.ass2;

import javax.swing.*;
import java.awt.*;

import static ass2.ass2.game.instance;

public class informationPanel extends JPanel {
    public static JLabel scoreMessage;
    public static JLabel livesMessage;

    public informationPanel() {
        setBounds(0,275, 750,50);
        setBackground(Color.black);
        setLayout(new GridLayout(1,2));

        livesMessage = new JLabel("Lives: "+instance.lives);
        scoreMessage = new JLabel("Score: "+instance.score);
        livesMessage.setFont(frame.font);
        scoreMessage.setFont(frame.font);
        livesMessage.setForeground(Color.white);
        scoreMessage.setForeground(Color.white);
        scoreMessage.setHorizontalAlignment(JLabel.CENTER);
        add(livesMessage);
        add(scoreMessage);

        repaint();
    }
}
