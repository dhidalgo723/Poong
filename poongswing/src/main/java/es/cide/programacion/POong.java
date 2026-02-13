package es.cide.programacion;

import java.awt.Color;
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
import javax.swing.border.TitledBorder;

// Clase principal que representa el juego Pong
// Extiende JPanel para poder dibujar y ActionListener para manejar el timer
public class POong extends JPanel implements ActionListener {

    private int x = 50, y = 50;
    private int dx = 2, dy = 2;
    private static int rec1y = 100, rec1x = 30;
    private static int rec2y = 100, rec2x = 1470;
    private static int rec1w = 25, rec1h = 100;
    private static int rec2w = 25, rec2h = 100;
    private static Graphics2D rec1, rec2;
    private final int RADI = 20;
    private final int DELAY = 10;
    private Timer timer;

    // Constructor: inicializa el panel y el timer
    public POong() {
        setBackground(Color.blue); // fondo para el juego
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
        red.fillRect(775, 0, 5, 1000);
    }

    // Método llamado por el Timer cada 10ms - controla el movimiento de la pelota
    @Override
    public void actionPerformed(ActionEvent e) {
        // con la primera pala
        // si la pelota toca los bordes o toca los rectangulos cambia de direccion
        if (x <= rec1x + rec1w && x + RADI * 2 >= rec1x && y + RADI * 2 >= rec1y && y <= rec1y + rec1h) {
            dx = -dx; // invierta la direccion para que rebote
            x = rec1x + rec1w; // ajusta la posicion para que no se quede pegada
        }

        // lo mismo pero con la segunda pala
        if (x + RADI * 2 >= rec2x && x <= rec2x + rec2w && y + RADI * 2 >= rec2y && y <= rec2y + rec2h) {
            dx = -dx; // hace que "rebote"
            x = rec2x - RADI * 2; // ajusta la posicion para que no se quede pegada
        }

        // detecta colision con los bordes de arriba o abajo
        // si la pelota toca arriba o abajo invierte la direccion vertical
        if (y + 2 * RADI >= getHeight() || y <= 0) {
            dy = -dy; // cambia la direccion en vertical
        }

        // actualiza la posicion sumandole la velocidad
        x += dx;
        y += dy;

        // redibuja el panel con las nuevas posiciones
        repaint();
    }

    // Método principal - punto de entrada del programa
    public static void main(String[] args) {
        JFrame frame = new JFrame("Cercle Rebotant"); // Crea la ventana principal
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza la ventana a pantalla completa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra el programa al cerrar la ventana
        frame.setResizable(false); // Impide que el usuario redimensione la ventana

        // Layout de 2 filas, 1 columna con 15px de separación
        frame.setLayout(new GridLayout(2, 1, 15, 15));

        // Panel superior para mostrar puntuación (actualmente vacío)
        JPanel puntuacion = new JPanel();
        puntuacion.setBorder(new TitledBorder("Puntuacion de Jugadores"));
        puntuacion.setLayout(new GridLayout());

        // Panel inferior para el área de juego
        JPanel game = new JPanel();
        game.setBorder(new TitledBorder("Estat del Sistema"));
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

                // ===== CONTROLES JUGADOR 1 (PALA IZQUIERDA) =====
                // Si presiona W (mayúscula o minúscula), mueve pala 1 hacia ARRIBA
                if (key1 == 'w' || key1 == 'W') {
                    rec1y = rec1y - 15; // Reduce Y (sube en pantalla)
                } // Si presiona S (mayúscula o minúscula), mueve pala 1 hacia ABAJO
                else if (key1 == 's' || key1 == 'S') {
                    rec1y = rec1y + 15; // Aumenta Y (baja en pantalla)
                }

                // ===== CONTROLES JUGADOR 2 (PALA DERECHA) =====
                // Usa las flechas del teclado para mover la pala derecha
                char key2 = e.getKeyChar(); // Obtiene código de tecla especial

                if (key2 == KeyEvent.VK_UP) { // Flecha arriba
                    rec2y = rec2y - 15; // Sube la pala derecha
                } else if (key2 == KeyEvent.VK_DOWN) { // Flecha abajo
                    rec2y = rec2y + 15; // Baja la pala derecha
                }
            }
        });

        // Añade los paneles a la ventana
        frame.add(puntuacion); // Panel superior
        frame.add(game); // Panel inferior
        game.add(pong); // Juego dentro del panel inferior

        // Centra la ventana en la pantalla
        frame.setLocationRelativeTo(null);

        // Hace visible la ventana
        frame.setVisible(true);
    }
}
