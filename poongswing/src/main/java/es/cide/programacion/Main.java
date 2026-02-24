package es.cide.programacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Main {

    // fuente personalizada
    public static Font CanadaGothic;

    // variables para guardar los nombres de los jugadores
    public static String player1, player2;

    public static void main(String[] args) {

        // aplica el look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Error al aplicar el Look and Feel");
        }

        // aplica la fuente
        try {
            InputStream is = Main.class.getResourceAsStream("/otros/CanadaGothic.otf");
            CanadaGothic = Font.createFont(Font.TRUETYPE_FONT, is);
            CanadaGothic = CanadaGothic.deriveFont(20f); // tamaño de fuente 20
        } catch (Exception ex) {
            System.err.println("Error al cargar la fuente");
            CanadaGothic = new Font("Arial", Font.PLAIN, 20); // fuente por defecto si falla
        }

        // crea la ventana principal del menu
        JFrame frameGame = new JFrame("POong - Start Screen");
        frameGame.setSize(600, 450);
        frameGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameGame.setResizable(false);
        frameGame.setLocationRelativeTo(null); // centra la ventana

        // panel principal
        JPanel panel = new JPanel();
        panel.setBackground(new Color(173, 216, 230)); // color azul claro
        panel.setLayout(new GridBagLayout());

        // gridbagconstraints para organizar los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // expande horizontalmente

        // titulo del juego
        JLabel titulo = new JLabel("POong", JLabel.CENTER);
        titulo.setFont(CanadaGothic.deriveFont(40f)); // tamaño 40 para el titulo

        // etiqueta jugador 1
        JLabel text1 = new JLabel("Nombre Player 1");
        text1.setFont(CanadaGothic);
        text1.setForeground(new Color(0, 0, 139)); // azul oscuro

        // campo de texto para el nombre del jugador 1
        JTextField user1 = new JTextField(20); // 20 columnas de ancho
        user1.setFont(CanadaGothic);
        user1.setText("Player 1"); // texto por defecto

        // etiqueta jugador 2
        JLabel text2 = new JLabel("Nombre Player 2:");
        text2.setFont(CanadaGothic);
        text2.setForeground(new Color(0, 0, 139)); // azul oscuro

        // campo de texto para el nombre del jugador 2
        JTextField user2 = new JTextField(20); // 20 columnas de ancho
        user2.setFont(CanadaGothic);
        user2.setText("Player 2"); // texto por defecto

        // boton para comenzar el juego
        JButton begin = new JButton("Pulsa para comenzar el juego");
        begin.setFont(CanadaGothic);

        // boton para ver las instrucciones
        JButton rules = new JButton("Instrucciones del juego");
        rules.setFont(CanadaGothic);

        // accion al presionar el boton de comenzar
        begin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // obtiene los nombres de los campos de texto
                player1 = user1.getText().trim();
                player2 = user2.getText().trim();

                // si los campos estan vacios, usa nombres por defecto
                if (player1.isEmpty()) {
                    player1 = "Player 1";
                }
                if (player2.isEmpty()) {
                    player2 = "Player 2";
                }

                // cierra la ventana del menu
                frameGame.dispose();

                // abre la ventana del juego
                abrirJuego(player1, player2);
            }
        });

        // accion al presionar el boton de instrucciones
        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // muestra un dialogo con las instrucciones
                JOptionPane.showMessageDialog(frameGame,
                        "Instrucciones del juego:\n\n"
                        + "Player 1 (izquierda): Usa W y S para mover la pala\n"
                        + "Player 2 (derecha): Usa las flechas ARRIBA y ABAJO\n\n"
                        + "¡El primero en llegar a 10 puntos gana!");
            }
        });

        // posiciona el titulo en la primera fila
        gbc.gridx = 0; // columna 0
        gbc.gridy = 0; // fila 0
        gbc.gridwidth = 2; // ocupa 2 columnas
        panel.add(titulo, gbc);

        // posiciona la etiqueta del jugador 1
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(text1, gbc);

        // posiciona el campo de texto del jugador 1
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(user1, gbc);

        // posiciona la etiqueta del jugador 2
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(text2, gbc);

        // posiciona el campo de texto del jugador 2
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(user2, gbc);

        // posiciona el boton de comenzar
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(begin, gbc);

        // posiciona el boton de instrucciones
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(rules, gbc);

        // añade el panel a la ventana
        frameGame.add(panel);

        // hace visible la ventana
        frameGame.setVisible(true);
    }

    // metodo que abre la ventana del juego pong
    public static void abrirJuego(String jugador1, String jugador2) {
        // crea la ventana del juego
        JFrame frame = new JFrame("PONG - " + jugador1 + " VS " + jugador2);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // pantalla completa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // crea la instancia del juego pong
        POong pong = new POong();
        pong.setFocusable(true); // permite que reciba eventos de teclado

        // añade el listener de teclado al juego
        pong.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // no se utiliza
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // no se utiliza
            }

            @Override
            public void keyPressed(KeyEvent e) {
                char key1 = e.getKeyChar();

                // controles jugador 1 (w para arriba, s para abajo)
                if (key1 == 'w' || key1 == 'W') {
                    POong.rec1y = POong.rec1y - 25; // sube la pala izquierda
                } else if (key1 == 's' || key1 == 'S') {
                    POong.rec1y = POong.rec1y + 25; // baja la pala izquierda
                }

                int key2 = e.getKeyCode();

                // controles jugador 2 (flechas arriba y abajo)
                if (key2 == KeyEvent.VK_UP) {
                    POong.rec2y = POong.rec2y - 25; // sube la pala derecha
                } else if (key2 == KeyEvent.VK_DOWN) {
                    POong.rec2y = POong.rec2y + 25; // baja la pala derecha
                }
            }
        });

        // añade el juego a la ventana
        frame.add(pong);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // metodos getter para obtener los nombres de los jugadores
    public static String getPlayer1() {
        return player1;
    }

    public static String getPlayer2() {
        return player2;
    }
}
