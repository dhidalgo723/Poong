package es.cide.programacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class POong extends JPanel implements ActionListener {

    private int x = 750, y = 450;
    private double dx = 3, dy = 2;
    public static int rec1y = 100, rec1x = 30;
    public static int rec2y = 100, rec2x = 1470;
    public static int rec1w = 25, rec1h = 100;
    public static int rec2w = 25, rec2h = 100;
    private static int rec1derecha, rec1abajo, rec2derecha, rec2abajo;
    private static int derecha, abajo;
    public static int points1 = 0, points2 = 0;
    private final int RADI = 20;
    private static int DIA; // diametro de la pelota
    private final int DELAY = 10;
    private Timer timer;

    // constructor
    public POong() {
        setBackground(new Color(173, 216, 230)); // fondo azul claro
        timer = new Timer(DELAY, this); // crea timer que llama a actionPerformed cada 10ms
        timer.start(); // inicia la animacion
    }

    // dibuja todos los elementos del juego
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // limpia el panel antes de redibujar

        // dibuja la pelota
        Graphics2D ball = (Graphics2D) g;
        ball.setColor(Color.RED); // color rojo
        ball.fillOval(x, y, RADI * 2, RADI * 2); // dibuja circulo

        // dibuja la pala del jugador 1 (izquierda)
        Graphics2D rec1 = (Graphics2D) g;
        rec1.setColor(Color.black);
        rec1.fillRect(rec1x, rec1y, rec1w, rec1h);

        // dibuja la pala del jugador 2 (derecha)
        Graphics2D rec2 = (Graphics2D) g;
        rec2.setColor(Color.black);
        rec2.fillRect(rec2x, rec2y, rec2w, rec2h);

        // dibuja la linea divisoria central (malla)
        Graphics2D red = (Graphics2D) g;
        red.setColor(Color.white); // color blanco
        red.fillRect(getWidth() / 2 - 2, 0, 4, getHeight()); // linea vertical en el centro

        // obtiene los nombres de los jugadores
        String nombre1 = Main.getPlayer1();
        String nombre2 = Main.getPlayer2();

        // si no hay nombres, usa valores por defecto
        if (nombre1 == null) {
            nombre1 = "Jugador 1";
        }
        if (nombre2 == null) {
            nombre2 = "Jugador 2";
        }

        // dibuja los puntos del jugador 1
        Graphics2D pointsply1 = (Graphics2D) g;
        pointsply1.setColor(Color.BLACK);
        pointsply1.setFont(Main.CanadaGothic.deriveFont(30f)); // usa la fuente personalizada
        pointsply1.drawString(nombre1 + ": " + points1, getWidth() / 4, 50);

        // dibuja los puntos del jugador 2
        Graphics2D pointsply2 = (Graphics2D) g;
        pointsply2.setColor(Color.BLACK);
        pointsply2.setFont(Main.CanadaGothic.deriveFont(30f)); // usa la fuente personalizada
        pointsply2.drawString(nombre2 + ": " + points2, getWidth() - 350, 50);
    }

    // controla el movimiento y la logica del juego
    @Override
    public void actionPerformed(ActionEvent e) {
        // calcula las hitboxes de la pelota y las palas
        derecha = (int) (x + RADI * 2); // borde derecho de la pelota
        abajo = (int) (y + RADI * 2); // borde inferior de la pelota
        rec1derecha = rec1x + rec1w; // borde derecho de la pala izquierda
        rec1abajo = rec1y + rec1h; // borde inferior de la pala izquierda
        rec2derecha = rec2x + rec2w; // borde derecho de la pala derecha
        rec2abajo = rec2y + rec2h; // borde inferior de la pala derecha
        DIA = RADI * 2; // diametro de la pelota

        // detecta colision con la pala izquierda
        if (x <= rec1derecha && derecha >= rec1x && abajo >= rec1y && y <= rec1abajo) {
            dx = -dx; // invierte la direccion horizontal
            x = rec1derecha; // ajusta la posicion para que no se quede pegada

            // incrementa la velocidad en un 5% cada vez que toca una pala
            dx = dx * 1.05;
            dy = dy * 1.05;
        }

        // detecta colision con la pala derecha
        if (derecha >= rec2x && x <= rec2derecha && abajo >= rec2y && y <= rec2abajo) {
            dx = -dx; // invierte la direccion horizontal
            x = rec2x - DIA; // ajusta la posicion para que no se quede pegada

            // incrementa la velocidad en un 5% cada vez que toca una pala
            dx = dx * 1.05;
            dy = dy * 1.05;
        }

        // si la pelota sale por el borde derecho, punto para jugador 1
        if (derecha >= getWidth()) {
            points1++; // incrementa puntos del jugador 1
            restartBall(); // reinicia la pelota al centro
            win(); // verifica si alguien gano
        }

        // si la pelota sale por el borde izquierdo, punto para jugador 2
        if (x <= 0) {
            points2++; // incrementa puntos del jugador 2
            restartBall(); // reinicia la pelota al centro
            win(); // verifica si alguien gano
        }

        // detecta colision con los bordes superior e inferior
        if (abajo >= getHeight() || y <= 0) {
            dy = -dy; // invierte la direccion vertical
        }

        // limita el movimiento de la pala izquierda
        if (rec1y < 0) {
            rec1y = 0; // no puede subir mas arriba
        }
        if (rec1abajo > getHeight()) {
            rec1y = getHeight() - rec1h; // no puede bajar mas abajo
        }

        // limita el movimiento de la pala derecha
        if (rec2y < 0) {
            rec2y = 0; // no puede subir mas arriba
        }
        if (rec2abajo > getHeight()) {
            rec2y = getHeight() - rec2h; // no puede bajar mas abajo
        }

        // actualiza la posicion de la pelota
        x += (int) dx;
        y += (int) dy;

        // redibuja el panel
        repaint();
    }

    // reinicia la pelota al centro y resetea la velocidad
    private void restartBall() {
        x = getWidth() / 2; // centra horizontalmente
        y = getHeight() / 2; // centra verticalmente
        dx = -dx; // cambia la direccion

        // resetea la velocidad a valores iniciales
        if (dx > 0) {
            dx = 3;
        } else {
            dx = -3;
        }
        if (dy > 0) {
            dy = 2;
        } else {
            dy = -2;
        }
    }

    // verifica si algun jugador llego a 10 puntos
    private void win() {
        if (points1 >= 10 || points2 >= 10) {
            timer.stop(); // detiene el juego

            // determina el ganador
            String ganador = (points1 >= 10) ? Main.getPlayer1() : Main.getPlayer2();

            // muestra dialogo con opciones
            Object[] opciones = {"Jugar de Nuevo", "Salir"};
            int respuesta = JOptionPane.showOptionDialog(
                    this,
                    "¡" + ganador + " ha ganado!\n\n¿Que deseas hacer?",
                    "Fin del Juego",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            // si elige jugar de nuevo
            if (respuesta == JOptionPane.YES_OPTION) {
                reiniciarJuego();
            } // si elige salir o cierra el dialogo
            else {
                System.exit(0); // cierra el programa
            }
        }
    }

    // reinicia el juego completo
    private void reiniciarJuego() {
        // resetea los puntos
        points1 = 0;
        points2 = 0;

        // resetea la posicion de la pelota
        x = 750;
        y = 450;

        // resetea la velocidad
        dx = 3;
        dy = 2;

        // resetea la posicion de las palas (como al inicio)
        rec1y = 100;
        rec1x = 30;
        rec2y = 100;
        rec2x = 1470;

        // reinicia el timer
        timer.start();
    }
}
