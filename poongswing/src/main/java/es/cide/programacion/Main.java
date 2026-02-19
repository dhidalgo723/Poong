package es.cide.programacion;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends POong {

    public static void main(String[] args) {
        // creacion y redimencion del frame
        JFrame frame = new JFrame("Cercle Rebotant");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // usamos GridBagLayout que permite un control más preciso de la posición y
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
                // w pa subir
                if (key1 == 'w' || key1 == 'W') {
                    rec1y = rec1y - 25; // hace que suba
                } // s pa bajar
                else if (key1 == 's' || key1 == 'S') {
                    rec1y = rec1y + 25; // hace que baje
                }

                // pala derecha
                // lo mismo pero con las flechas para la pala derecha
                int key2 = e.getKeyCode();
                if (key2 == KeyEvent.VK_UP) {
                    rec2y = rec2y - 25;
                } else if (key2 == KeyEvent.VK_DOWN) {
                    rec2y = rec2y + 25;
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
