package es.cide.programacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class POong extends JPanel implements ActionListener {

    private int x = 750, y = 450;
    private int dx = 3, dy = 2;
    public static int rec1y = 100, rec1x = 30;
    public static int rec2y = 100, rec2x = 1470;
    public static int rec1w = 25, rec1h = 100;
    public static int rec2w = 25, rec2h = 100;
    private static int rec1derecha, rec1abajo, rec2derecha, rec2abajo;
    private static int derecha, abajo;
    private static boolean restart = false;
    static Graphics2D rec1, rec2, pointsply1, pointsply2;
    private static int points1, points2;
    private final int RADI = 20;
    private static int DIA;
    private final int DELAY = 10;
    private Timer timer;

    // Constructor: inicializa el panel y el timer
    public POong() {
        setBackground(new Color(173, 216, 230)); // fondo para el juego
        timer = new Timer(DELAY, this); // crea timer que llama a actionPerformed cada 10ms
        timer.start(); // inicia la animacion
    }

    // dibuja las figuras
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // limpia el panel antes de redibujar

        // pelota
        Graphics2D ball = (Graphics2D) g;
        ball.setColor(Color.RED); // color rojo
        ball.fillOval(x, y, RADI * 2, RADI * 2); // dibuja la "pelota"

        // pala jugador 1
        Graphics2D rec1 = (Graphics2D) g;
        rec1.setColor(Color.black);
        rec1.fillRect(rec1x, rec1y, rec1w, rec1h);

        // pala jugador 2
        Graphics2D rec2 = (Graphics2D) g;
        rec2.setColor(Color.black);
        rec2.fillRect(rec2x, rec2y, rec2w, rec2h);

        // la "malla"
        Graphics2D red = (Graphics2D) g;
        red.setColor(Color.white); // malla de color blanco
        red.fillRect(getWidth() / 2 - 5, 0, 5, 1000);

        // puntos jugador 1
        Graphics2D pointsply1 = (Graphics2D) g;
        pointsply1.setColor(Color.BLACK); // le pone color
        pointsply1.setFont(new Font("Arial", Font.PLAIN, 30)); // fuente
        pointsply1.drawString("Jugador 1:" + points1, getWidth() / 4, 50); // "dibujo" el texto

        // puntos jugador 2
        Graphics2D pointsply2 = (Graphics2D) g;
        pointsply2.setColor(Color.BLACK);
        pointsply2.setFont(new Font("Arial", Font.PLAIN, 30));
        pointsply2.drawString("Jugador 2:" + points2, 1152, 50);
    }

    // hace que se mueva la pelota
    @Override
    public void actionPerformed(ActionEvent e) {
        // estas serian las "hitbox's" de la pelota y las palas
        // es mas que todo para ubicarme mejor mas adelante y hacerlo mas "legible" en otras practicas
        derecha = x + RADI * 2;
        abajo = y + RADI * 2;
        rec1derecha = rec1x + rec1w;
        rec1abajo = rec1y + rec1h;
        rec2derecha = rec2x + rec2w;
        rec2abajo = rec2y + rec2h;
        DIA = RADI * 2;

        // con la primera pala
        // si la pelota toca los bordes o toca los rectangulos cambia de direccion
        if (x <= rec1derecha && derecha >= rec1x && abajo >= rec1y && y <= rec1abajo) {
            dx = -dx; // invierta la direccion para que rebote
            x = rec1x + DIA; // ajusta la posicion para que no se quede pegada
        }

        // lo mismo pero con la segunda pala
        if (derecha >= rec2x && x <= rec2derecha && abajo >= rec2y && y <= rec2abajo) {
            dx = -dx; // hace que "rebote"
            x = rec2x - DIA; // ajusta la posicion para que no se quede pegada
        }

        // detecta colision con los bordes laterales
        // si la pelota sale por los lados, la devuelve al centro para reiniciar el juego
        if (derecha >= getWidth()) {
            points1++; // incrementa puntos del jugador 1
            // reinicia la pelota al centro cuando sale por los lados
            x = getWidth() / 2;
            y = getHeight() / 2;
            // cambia la direccion para que vaya hacia el otro lado
            dx = -dx;
        }

        if (x <= 0) {
            // reinicia la pelota al centro cuando sale por los lados
            x = getWidth() / 2;
            y = getHeight() / 2;
            // cambia la direccion para que vaya hacia el otro lado
            dx = -dx;
            points2++; // incrementa puntos del jugador 2
        }

        // detecta colision con los bordes de arriba o abajo
        // si la pelota toca arriba o abajo invierte la direccion vertical
        if (abajo >= getHeight() || y <= 0) {
            dy = -dy; // cambia la direccion en vertical
        }

        // si la pala de la izquierda sube mas de 0 se queda ahi
        if (rec1y < 0) {
            rec1y = 0;
        }

        // si la pala izquierda baja mas de la altura del frame
        if (rec1abajo > getHeight()) {
            rec1y = getHeight() - rec1h; // si se sale por abajo, la ajusta
        }

        // lo mismo pero con la pala de la derecha
        if (rec2y < 0) {
            rec2y = 0;
        }
        if (rec2abajo > getHeight()) {
            rec2y = getHeight() - rec2h;
        }

        // actualiza la posicion sumandole la velocidad
        x += dx;
        y += dy;

        // redibuja el panel con las nuevas posiciones
        repaint();
    }

}
