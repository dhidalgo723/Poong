package es.cide.programacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

// Clase principal que representa el juego Pong
// Extiende JPanel para poder dibujar y ActionListener para manejar el timer
public class POong extends JPanel implements ActionListener {

    private int x = 50, y = 50;
    private int dx = 2, dy = 2;
    private static int rec1y = 100, rec1x = 30;
    private static int rec2y = 100, rec2x = 1470;
    private static int rec1w = 25, rec1h = 100;
    private static int rec2w = 25, rec2h = 100;
    private static int rec1derecha, rec1abajo, rec2derecha, rec2abajo;
    private static int derecha, abajo;
    private static boolean restart = false;
    private static Graphics2D rec1, rec2;
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

    // Método que dibuja todos los elementos gráficos del juego
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // limpia el panel antes de redibujar

        // pelota
        Graphics2D ball = (Graphics2D) g;
        ball.setColor(Color.RED); // color rojo
        ball.fillOval(x, y, RADI * 2, RADI * 2); // dibuja la "pelota"

        // pala jugador 1
        Graphics2D rec1 = (Graphics2D) g;
        rec1.setColor(Color.black); // Color negro
        rec1.fillRect(rec1x, rec1y, rec1w, rec1h); // Dibuja rectángulo relleno

        // pala jugador 2
        Graphics2D rec2 = (Graphics2D) g;
        rec2.setColor(Color.black); // Color negro
        rec2.fillRect(rec2x, rec2y, rec2w, rec2h); // Dibuja rectángulo relleno

        // la "malla"
        Graphics2D red = (Graphics2D) g;
        red.setColor(Color.white); // malla de color blanco
        red.fillRect(getWidth() / 2 - 5, 0, 5, 1000);

        // puntos jugador 1
        Graphics2D points1 = (Graphics2D) g;
        points1.setColor(Color.BLACK); // le pone color
        points1.setFont(new Font("Arial", Font.PLAIN, 30)); // fuente
        points1.drawString("Jugador 1: 0", getWidth() / 4, 50); // "dibujo" el texto

        // puntos jugador 2
        Graphics2D points2 = (Graphics2D) g;
        points2.setColor(Color.BLACK);
        points2.setFont(new Font("Arial", Font.PLAIN, 30));
        points2.drawString("Jugador 2: 0", 1152, 50);
    }

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
        if (derecha >= getWidth() || x <= 0) {
            // reinicia la pelota al centro cuando sale por los lados
            x = getWidth() / 2;
            y = getHeight() / 2;
            // cambia la direccion para que vaya hacia el otro lado
            dx = -dx;
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

    public static void main(String[] args) {
        // creacion y redimencion del frame
        JFrame frame = new JFrame("Cercle Rebotant");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Usamos GridBagLayout que permite un control más preciso de la posición y
        // tamaño de los componentes
        frame.setLayout(new GridLayout(1, 1, 15, 15));

        // panel para el juego
        JPanel game = new JPanel();
        game.setLayout(new GridLayout());

        // Crea la instancia del juego Pong
        POong pong = new POong();
        pong.setFocusable(true); // Permite que reciba eventos de teclado

        // Añade listener para detectar teclas presionadas
        pong.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // No se utiliza en este caso
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // No se utiliza en este caso
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Captura la tecla presionada
                char key1 = e.getKeyChar();

                // pala izquierda
                // Si presiona W (mayúscula o minúscula), mueve pala 1 hacia ARRIBA
                if (key1 == 'w' || key1 == 'W') {
                    rec1y = rec1y - 25; // Reduce Y (sube en pantalla)
                } // Si presiona S (mayúscula o minúscula), mueve pala 1 hacia ABAJO
                else if (key1 == 's' || key1 == 'S') {
                    rec1y = rec1y + 25; // Aumenta Y (baja en pantalla)
                }

                // pala derecha
                // Usa las flechas del teclado para mover la pala derecha
                char key2 = e.getKeyChar(); // Obtiene código de tecla especial

                if (key2 == 'o' || key2 == KeyEvent.VK_UP) { // Flecha arriba
                    rec2y = rec2y - 25; // Sube la pala derecha
                } else if (key2 == 'l' || key2 == 'L') { // Flecha abajo
                    rec2y = rec2y + 25; // Baja la pala derecha
                }
            }
        });

        // añadidos al frame y al panel
        frame.add(game);
        game.add(pong);

        // Centra la ventana en la pantalla
        frame.setLocationRelativeTo(null);

        // Hace visible la ventana
        frame.setVisible(true);
    }
}
