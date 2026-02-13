package es.cide.programacion;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ExamenJavaSwing {

    private static JSlider slider;
    private static String[] hoteles = {"Hotel Blau", "Hotel Illa", "Hotel Vall"};
    private static String s = "";

    /**
     * Mètode per validar un número de DNI espanyol.
     *
     * @param dni El número de DNI a validar, en format de cadena (8 dígits
     * seguits d'una lletra).
     * @return true si el DNI és vàlid, false en cas contrari.
     */
    public static boolean validarDNI(String dni) {
        // Comprova si el format del DNI és correcte:
        // - Ha de tenir exactament 8 dígits seguits d'una lletra (majúscula o
        // minúscula).
        if (!dni.matches("\\d{8}[A-HJ-NP-TV-Za-hj-np-tv-z]")) {
            return false;
        }

        // Conjunt de lletres possibles per a la validació del DNI.
        String lletres = "TRWAGMYFPDXBNJZSQVHLCKE";

        // Converteix els primers 8 caràcters (els dígits) a un número enter.
        int numero = Integer.parseInt(dni.substring(0, 8));

        // Converteix la lletra proporcionada a majúscula per evitar errors de
        // comparació.
        char lletraDNI = Character.toUpperCase(dni.charAt(8));

        // Comprova si la lletra calculada a partir del número coincideix amb la lletra
        // proporcionada.
        return lletres.charAt(numero % 23) == lletraDNI;
    }

    /**
     * Comprova si una data donada en format "dd/MM/aaaa" és anterior a la data
     * actual.
     *
     * @param dataString La data en format "dd/MM/aaaa" com a String.
     * @return Cert si la data és anterior a avui, fals en cas contrari.
     */
    public static boolean esDataAnterior(String dataString) {
        try {
            // Defineix el format de la data esperada
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Converteix el String a un objecte LocalDate
            LocalDate dataEntrada = LocalDate.parse(dataString, formatter);

            // Obté la data actual
            LocalDate dataActual = LocalDate.now();

            // Compara si la data entrada és anterior a la data actual
            return dataEntrada.isBefore(dataActual);
        } catch (DateTimeParseException e) {
            // Retorna fals en cas d'error en el format de la data
            System.out.println("Error: Format de data invàlid. Utilitza dd/MM/aaaa.");
            return false;
        }
    }

    private static void error(Frame parent, String text) {
        JDialog error = new JDialog(parent, "Mensaje de error", true);
        error.setSize(250, 150);
        error.setLayout(new BorderLayout());
        error.add(new JLabel(text), BorderLayout.CENTER);
        JButton close = new JButton("De acuerdo");
        error.add(close, BorderLayout.SOUTH);
        error.setVisible(true);
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
        JFrame frame = new JFrame("Reserves d'Hotel");
        frame.setSize(450, 300); // Establecemos el tamaño de la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Al cerrar la ventana, termina el programa

        // Creamos el panel donde colocaremos todos los componentes
        JPanel panel = new JPanel();

        // Usamos GridBagLayout que permite un control más preciso de la posición y
        // tamaño de los componentes
        panel.setLayout(new GridBagLayout());
        // GridBagConstraints define cómo se posicionará cada componente en el
        // GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();

        // fill = HORIZONTAL hace que los componentes se estiren horizontalmente para
        // llenar el espacio
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Insets define los márgenes alrededor de cada componente (arriba, izquierda,
        // abajo, derecha)
        gbc.insets = new Insets(5, 10, 5, 10);

        // Campo de texto donde el usuario escribirá el nombre
        JTextField nom = new JTextField(25);

        // Campo de texto donde el usuario pondra el DNI
        JTextField dni = new JTextField(9);
        JLabel ok = new JLabel("OK");

        dni.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent dev) {

            }

            public void insertUpdate(DocumentEvent dev) {
                if (validarDNI(dni.getText()) == true) {
                    ok.setText("OK");
                } else {
                    ok.setText("Error");
                }
            }

            public void removeUpdate(DocumentEvent dev) {
                if (validarDNI(dni.getText()) == true) {
                    ok.setText("OK");
                } else if (dni.getText().isEmpty()) {
                    ok.setText("");
                } else {
                    ok.setText("Error");
                }
            }
        });

        // Campo de texto donde el usuario pondra la fecha
        JTextField data = new JTextField(25);
        data.setText("07/03/2025");

        data.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (data.getText().isEmpty()) {
                    error(frame, "Este campo es obligatorio");
                } else if (esDataAnterior(data.getText()) == true) {
                    error(frame, "Error: Data invalida");
                }
            }
        });

        if (!esDataAnterior(data.getText())) {
            JOptionPane.showMessageDialog(frame, "No pot ser la data d'avui");
        }
        if (data.getText().equals(null) || data.getText().equals("")) {
            JOptionPane.showMessageDialog(frame, "No pot estar buida");
        }

        // Cmbobox para hoteles
        JComboBox hotel = new JComboBox();
        // Añadimos cada vocal del array al ComboBox
        for (int i = 0; i < hoteles.length; i++) {
            hotel.addItem(hoteles[i]);
        }

        // Slider para noches
        JLabel noches = new JLabel();
        // Slider (barra deslizante) para seleccionar el año de estreno
        // Rango de 1 al 15, con ticks visibles y labels visibles
        int maxnits = 15;
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 15, 9);
        slider.setMajorTickSpacing(1); // Marcas grandes cada 25 unidades
        slider.setMinorTickSpacing(1); // Marcas pequeñas cada 25 unidades

        hotel.addItemListener(new ItemListener() {
            @Override
            //Cuando se cambiel objeto en el combobox cambiamos los dias maximos
            public void itemStateChanged(ItemEvent e) {
                if (hotel.getSelectedItem().equals("Hotel Blau")) {
                    slider.setMaximum(3); // El maximo que tendra
                    slider.setMinimum(1); // El minimo que tendra
                    slider.setValue(2); // El valor por defecto que aparecera
                    slider.setMajorTickSpacing(1); // Marcas grandes cada 25 unidades
                    slider.setMinorTickSpacing(1); // Marcas pequeñas cada 25 unidades
                } else if (hotel.getSelectedItem().equals("Hotel Illa")) {
                    slider.setMaximum(10);
                    slider.setMinimum(1);
                    slider.setValue(5);
                    slider.setMajorTickSpacing(1); // Marcas grandes cada 25 unidades
                    slider.setMinorTickSpacing(1); // Marcas pequeñas cada 25 unidades
                } else if (hotel.getSelectedItem().equals("Hotel Vall")) {
                    slider.setMaximum(60);
                    slider.setMinimum(1);
                    slider.setValue(25);
                    slider.setMajorTickSpacing(10); // Marcas grandes cada 25 unidades
                    slider.setMinorTickSpacing(1); // Marcas pequeñas cada 25 unidades
                }

            }
        });

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Obtenemos el valor actual del slider
                int mida = slider.getValue();
                // Actualizamos la etiqueta con el año seleccionado
                noches.setText(String.valueOf(mida));
            }
        });
        slider.setPaintTicks(true); // Mostrar las marcas en el slider
        slider.setPaintLabels(true); // Mostrar los números en el slider

        // Si el slider es el maximo de noches aparece ese mensaje
        if (slider.getValue() == maxnits) {
            JOptionPane.showMessageDialog(frame, "Estada máxima a aquest hotel");
        }

        // Etiquetas y botones para el botón guardar reserva
        JLabel reservalabel = new JLabel();
        // Botón para mostrar la ficha de la película
        JButton reserva = new JButton("Guardar Reserva");

        // ActionListener: se ejecuta cuando el usuario hace clic en el botón
        reserva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtenemos el texto del título (esta línea es redundante)
                s = new StringBuilder(nom.getText()).toString();
                // Obtenemos los valores de los componentes
                String nombre = nom.getText(); // Nombre de la persona
                String Dni = dni.getText(); // Nombre de la persona
                String fecha = data.getText(); // Nombre de la persona
                String hot = (String) hotel.getSelectedItem(); // Hotel seleccionado
                int nit = slider.getValue(); // Noches del slider

                // Actualizamos la etiqueta con la información de la reserva
                reservalabel.setText("Reserva: " + nombre + ", " + Dni + ", " + fecha + ", " + hot + ", " + nit);
            }
        });

        // Añadimos el panel al frame
        frame.add(panel);

        // ===== AÑADIMOS LOS COMPONENTES AL PANEL CON GRIDBAGCONSTRAINTS =====
        // Fila 0: Nombre
        gbc.gridx = 0; // Columna 0
        gbc.gridy = 0; // Fila 0
        gbc.gridwidth = 1; // Ocupa 1 columna
        gbc.weightx = 0.3; // 30% del espacio horizontal
        panel.add(new JLabel("Nom: "), gbc);

        gbc.gridx = 1; // Columna 1
        gbc.weightx = 0.7; // 70% del espacio horizontal
        gbc.gridwidth = 2;
        panel.add(nom, gbc);

        // Fila 1: DNI
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        gbc.gridwidth = 1;
        panel.add(new JLabel("DNI: "), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.4;
        gbc.gridwidth = 1;
        panel.add(dni, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.3;
        gbc.gridwidth = 1;
        panel.add(ok, gbc);

        // // Fila 2: Fecha
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        panel.add(new JLabel("Data Check-in: (dd/MM/yyyy)"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.gridwidth = 2;
        panel.add(data, gbc);

        // Fila 3: Hoteles
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1; // Volvemos a 1 columna
        gbc.weightx = 0.3;
        panel.add(new JLabel("Hotel: "), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.4;
        gbc.gridwidth = 2;
        panel.add(hotel, gbc);

        // Fila 4: Slider
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3; // Ocupa 2 columnas
        gbc.weightx = 1.0; // 100% del espacio
        panel.add(slider, gbc);

        // Fila 5: Reserva
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        panel.add(reserva, gbc);

        // Fila 6: Reserva Label
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        panel.add(reservalabel, gbc);

        // Centramos la ventana en la pantalla
        frame.setLocationRelativeTo(null);

        // Hacemos visible la ventana
        frame.setVisible(true);
    }
}
