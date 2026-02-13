package es.cide.programacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PracticaExamen {

    // Variable booleana que controla si algo ha sido pulsado (no se usa en este código)
    private static boolean pulsado = false;

    // Array con las vocales disponibles para seleccionar en el JComboBox
    private static String[] vocales = {"A", "E", "I", "O", "U"};

    // Array con los géneros de películas disponibles
    private static String[] generos = {"Accion", "Comedia", "Drama", "Terror", "Ciencia Ficcion"};

    // Variable String auxiliar para almacenar texto temporalmente
    private static String s;

    // Método recursivo para contar cuántas veces aparece una vocal específica en una cadena
    private static int contvoc(String s, char vocalBuscada) {
        // Caso base: si la cadena está vacía, no hay más caracteres que contar
        if (s.length() == 0) {
            return 0;
        } else {
            // Comparamos el primer carácter de la cadena con la vocal buscada
            if (s.charAt(0) == vocalBuscada) {
                // Si coincide, sumamos 1 y llamamos recursivamente con el resto de la cadena
                return 1 + contvoc(s.substring(1), vocalBuscada);
            } else {
                // Si no coincide, solo llamamos recursivamente con el resto de la cadena
                return contvoc(s.substring(1), vocalBuscada);
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Aplicamos el Look and Feel del sistema operativo para que la interfaz
            // se vea nativa (Windows, Mac, Linux, etc.)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Error al aplicar el Look and Feel");
        }

        // Creamos la ventana principal de la aplicación
        JFrame frame = new JFrame("Ficha de Película");
        frame.setSize(500, 450); // Establecemos el tamaño de la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Al cerrar la ventana, termina el programa

        // Creamos el panel donde colocaremos todos los componentes
        JPanel panel = new JPanel();

        // Usamos GridBagLayout que permite un control más preciso de la posición y tamaño de los componentes
        panel.setLayout(new GridBagLayout());

        // GridBagConstraints define cómo se posicionará cada componente en el GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();

        // fill = HORIZONTAL hace que los componentes se estiren horizontalmente para llenar el espacio
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Insets define los márgenes alrededor de cada componente (arriba, izquierda, abajo, derecha)
        gbc.insets = new Insets(5, 10, 5, 10);

        // Campo de texto donde el usuario escribirá el título de la película
        JTextField titol = new JTextField(25);

        // ComboBox (lista desplegable) para seleccionar el género de la película
        JComboBox genero = new JComboBox();
        // Añadimos cada género del array al ComboBox
        for (int i = 0; i < generos.length; i++) {
            genero.addItem(generos[i]);
        }

        // Etiqueta que mostrará el año seleccionado en el slider
        JLabel yearselect = new JLabel("2000");

        // Slider (barra deslizante) para seleccionar el año de estreno
        // Rango de 1900 a 2025, valor inicial 2000
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 1900, 2025, 2000);
        slider.setPaintTicks(true); // Mostrar las marcas en el slider
        slider.setPaintLabels(true); // Mostrar los números en el slider
        slider.setMajorTickSpacing(25); // Marcas grandes cada 25 unidades
        slider.setMinorTickSpacing(25); // Marcas pequeñas cada 25 unidades

        // ChangeListener: se ejecuta cada vez que el usuario mueve el slider
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Obtenemos el valor actual del slider
                int mida = slider.getValue();
                // Actualizamos la etiqueta con el año seleccionado
                yearselect.setText(String.valueOf(mida));
            }
        });

        // Etiqueta para mostrar la ficha de la película
        JLabel fichaLabel = new JLabel("");

        // Botón para mostrar la ficha de la película
        JButton ficha = new JButton("Mostrar Ficha");

        // ActionListener: se ejecuta cuando el usuario hace clic en el botón
        ficha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtenemos el texto del título (esta línea es redundante)
                s = new StringBuilder(titol.getText()).toString();
                // Obtenemos los valores de los componentes
                String peli = titol.getText(); // Título de la película
                String gen = (String) genero.getSelectedItem(); // Género seleccionado
                int year = slider.getValue(); // Año del slider

                // Actualizamos la etiqueta con la información de la película
                fichaLabel.setText("Película: " + peli + " || Género: " + gen + " || Año: " + year);
            }
        });

        if (genero.getSelectedItem().equals("Terror")) {
            JOptionPane.showMessageDialog(frame, "Cuidado que Terror es mayores de 18");
        }

        // ComboBox para seleccionar qué vocal queremos contar
        JComboBox vocal = new JComboBox();
        // Añadimos cada vocal del array al ComboBox
        for (int i = 0; i < vocales.length; i++) {
            vocal.addItem(vocales[i]);
        }

        // Etiqueta que mostrará el número de vocales contadas
        JLabel numvocal = new JLabel("0");

        // Botón para contar las vocales
        JButton cuenta = new JButton("Contar Vocales");

        // ActionListener: se ejecuta cuando el usuario hace clic en "Contar Vocales"
        cuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtenemos el título de la película y lo convertimos a minúsculas
                // para hacer la comparación sin importar mayúsculas/minúsculas
                s = titol.getText().toLowerCase();

                // Obtenemos la vocal seleccionada del ComboBox
                String vocalSeleccionada = ((String) vocal.getSelectedItem()).toLowerCase();
                // Extraemos el primer carácter (la vocal) como tipo char
                char vocalChar = vocalSeleccionada.charAt(0);

                // Llamamos al método recursivo para contar cuántas veces aparece esa vocal
                int num = contvoc(s, vocalChar);

                // Mostramos el resultado en la etiqueta
                numvocal.setText(String.valueOf(num));
            }
        });

        // ===== AÑADIMOS LOS COMPONENTES AL PANEL CON GRIDBAGCONSTRAINTS =====
        // Fila 0: Título de la película
        gbc.gridx = 0; // Columna 0
        gbc.gridy = 0; // Fila 0
        gbc.gridwidth = 1; // Ocupa 1 columna
        gbc.weightx = 0.3; // 30% del espacio horizontal
        panel.add(new JLabel("Título de la película: "), gbc);

        gbc.gridx = 1; // Columna 1
        gbc.weightx = 0.7; // 70% del espacio horizontal
        panel.add(titol, gbc);

        // Fila 1: Género
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        panel.add(new JLabel("Género: "), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(genero, gbc);

        // Fila 2: Slider de año
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        panel.add(new JLabel("Año de estreno: "), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(slider, gbc);

        // Fila 3: Año seleccionado
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        panel.add(new JLabel("Año seleccionado: "), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(yearselect, gbc);

        // Fila 4: Botón Mostrar Ficha (ocupa las 2 columnas)
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Ocupa 2 columnas
        gbc.weightx = 1.0; // 100% del espacio
        panel.add(ficha, gbc);

        // Fila 5: Etiqueta de la ficha (ocupa las 2 columnas)
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(fichaLabel, gbc);

        // Fila 6: Seleccionar vocal
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1; // Volvemos a 1 columna
        gbc.weightx = 0.3;
        panel.add(new JLabel("Selecciona una vocal: "), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(vocal, gbc);

        // Fila 7: Botón Contar Vocales (ocupa las 2 columnas)
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panel.add(cuenta, gbc);

        // Fila 8: Número de vocales
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(new JLabel("Número de vocales: "), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(numvocal, gbc);

        // Añadimos el panel al frame
        frame.add(panel);

        // Centramos la ventana en la pantalla
        frame.setLocationRelativeTo(null);

        // Hacemos visible la ventana
        frame.setVisible(true);
    }
}
