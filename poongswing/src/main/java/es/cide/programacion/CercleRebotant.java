package es.cide.programacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class CercleRebotant extends JPanel implements ActionListener {

    private int x = 50, y = 50;
    private int dx = 2, dy = 2;
    private final int RADI = 20;
    private final int DELAY = 10;
    private Timer timer;

    public CercleRebotant() {
        setBackground(Color.WHITE);
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.fillOval(x, y, RADI * 2, RADI * 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (x + 2 * RADI >= getWidth() || x <= 0) {
            dx = -dx;
        }
        if (y + 2 * RADI >= getHeight() || y <= 0) {
            dy = -dy;
        }
        x += dx;
        y += dy;
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Cercle Rebotant");
            CercleRebotant panel = new CercleRebotant();
            frame.add(panel);
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
