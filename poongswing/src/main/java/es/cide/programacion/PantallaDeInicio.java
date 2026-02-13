package es.cide.programacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class PantallaDeInicio {

    public static void main(String[] args) {

        try {
            // aplica el look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Error al aplicar el Look and Feel");
        }

        // crea la ventana principal 
        JFrame frame = new JFrame("Poong");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // establece el tamaño de la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // cierra programa al cerrar ventana
        frame.setResizable(false); // esto sirve para que no pueda el usuario modificar la ventana
        frame.setLayout(new GridLayout(2, 2, 15, 15)); // divide en 4 cuadrantes (2x2) con separacion de 15px

        JPanel panel = new JPanel();

        // Usamos GridBagLayout que permite un control más preciso de la posición y
        // tamaño de los componentes
        panel.setLayout(new GridBagLayout());
        // GridBagConstraints define cómo se posicionará cada componente en el
        // GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel text1 = new JLabel("Introduce el nombre del jugador 1");
        JLabel text2 = new JLabel("Introduce el nombre del jugador 2");
        JTextField user1 = new JTextField(20);
        JTextField user2 = new JTextField(20);

        JButton begin = new JButton("Pulsa para Comenzar");

        frame.add(panel);
        gbc.gridx = 0; // Columna 0
        gbc.gridy = 0; // Fila 0
        panel.add(text1, gbc);

        gbc.gridx = 0; // Columna 0
        gbc.gridy = 1; // Fila 1
        panel.add(user1, gbc);

        gbc.gridx = 2; // Columna 2
        gbc.gridy = 0; // Fila 0
        panel.add(text2, gbc);

        gbc.gridx = 2; // Columna 2
        gbc.gridy = 1; // Fila 1
        panel.add(user2, gbc);

        gbc.gridx = 1; // Columna 1
        gbc.gridy = 2; // Fila 2
        panel.add(begin, gbc);

        frame.setVisible(true); // hace visible la ventana

    }
}
