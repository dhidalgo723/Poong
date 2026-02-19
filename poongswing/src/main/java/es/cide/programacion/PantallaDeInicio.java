package es.cide.programacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class PantallaDeInicio {

    // variables
    private static String player1, player2;

    public static void main(String[] args) {

        try {
            // Aplica el look and feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Error al aplicar el Look and Feel");
        }

        // Crea la ventana principal
        JFrame frame = new JFrame("POong - Start Screen");
        frame.setSize(600, 450); // Tamaño personalizado para la pantalla de inicio
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra programa al cerrar ventana
        frame.setResizable(false); // No permite que el usuario modifique la ventana
        frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(173, 216, 230));
        panel.setLayout(new GridBagLayout());

        // gridbagconstraints para estructurar las cosas
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // titulo del juego
        JLabel titulo = new JLabel("POong", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 40));

        // jugador 1
        JLabel text1 = new JLabel("Nombre Player 1");
        text1.setFont(new Font("Arial", Font.PLAIN, 16));
        text1.setForeground(new Color(0, 0, 139));

        // para poner el nombre del jugador 1
        JTextField user1 = new JTextField(20); // 20 columnas 
        user1.setFont(new Font("Arial", Font.PLAIN, 16));
        user1.setText("Inserte Player 1");

        // jugador 2
        JLabel text2 = new JLabel("Nombre Player 2:");
        text2.setFont(new Font("Arial", Font.PLAIN, 16));
        text2.setForeground(new Color(0, 0, 139));

        // lo mismo pal jugador 2
        JTextField user2 = new JTextField(20); // 20 columnas
        user2.setFont(new Font("Arial", Font.PLAIN, 16));
        user2.setText("Inserte Player 2");

        // boton de comenzar juego
        JButton begin = new JButton("Pulsa para comenzar el juego");
        begin.setFont(new Font("Arial", Font.BOLD, 20));

        // boton para instrucciones
        JButton rules = new JButton("Intrucciones del juego");
        rules.setFont(new Font("Arial", Font.BOLD, 20));

        // al presionar el boton
        begin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // guarda los nombres de los jugadores
                player1 = user1.getText();
                player2 = user2.getText();

                // si los campos estan vacios pone estos nombres por defecto
                if (player1.trim().isEmpty()) {
                    player1 = "Player 1";
                }
                if (player2.trim().isEmpty()) {
                    player2 = "Player 2";
                }

                // Cierra la ventana de inicio
                frame.dispose();

                // Abre la ventana del juego
                abrirJuego(player1, player2);
            }
        });

        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Muestra un mensaje con las instrucciones del juego
                JOptionPane.showMessageDialog(frame, "Instrucciones del juego:\n\n"
                        + "Player 1 (El de la izquierda): Usa las teclas W y S para mover la pala hacia arriba y abajo.\n"
                        + "Player 2 (El de la derecha): Usa las flechas arriba y abajo para mover la pala hacia arriba y abajo");
            }
        });

        // titulo del juego
        gbc.gridx = 0; // Columna 0
        gbc.gridy = 0; // Fila 0
        gbc.gridwidth = 2; // Ocupa 2 columnas
        panel.add(titulo, gbc);

        // etiqueta y textfield player 1
        gbc.gridx = 0; // Columna 0
        gbc.gridy = 1; // Fila 1
        gbc.gridwidth = 2; // Ocupa 2 columnas
        panel.add(text1, gbc);

        gbc.gridx = 0; // Columna 0
        gbc.gridy = 2; // Fila 2
        gbc.gridwidth = 2; // Ocupa 2 columnas
        panel.add(user1, gbc);

        // etiqueta y textfield player 2
        gbc.gridx = 0; // Columna 0
        gbc.gridy = 3; // Fila 3
        gbc.gridwidth = 2; // Ocupa 2 columnas
        panel.add(text2, gbc);

        gbc.gridx = 0; // Columna 0
        gbc.gridy = 4; // Fila 4
        gbc.gridwidth = 2; // Ocupa 2 columnas
        panel.add(user2, gbc);

        // boton
        gbc.gridx = 0; // Columna 0
        gbc.gridy = 5; // Fila 5
        gbc.gridwidth = 2; // Ocupa 2 columnas
        panel.add(begin, gbc);

        // boton
        gbc.gridx = 0; // Columna 0
        gbc.gridy = 6; // Fila 6
        gbc.gridwidth = 2; // Ocupa 2 columnas
        panel.add(rules, gbc);

        // Añade el panel a la ventana
        frame.add(panel);

        // Hace visible la ventana
        frame.setVisible(true);
    }

    // Método que abre la ventana del juego Pong
    private static void abrirJuego(String jugador1, String jugador2) {
        // Crea la ventana del juego
        JFrame frameJuego = new JFrame("PONG - " + jugador1 + " VS " + jugador2);
        frameJuego.setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
        frameJuego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameJuego.setResizable(false);

        // Crea la instancia del juego Pong
        POong pong = new POong();
        pong.setFocusable(true); // Permite que reciba eventos de teclado

        // Añade el juego a la ventana
        frameJuego.add(pong);
        frameJuego.setLocationRelativeTo(null);
        frameJuego.setVisible(true);
    }

    // Métodos getter para obtener los nombres de los jugadores
    public static String getPlayer1() {
        return player1;
    }

    public static String getPlayer2() {
        return player2;
    }
}
