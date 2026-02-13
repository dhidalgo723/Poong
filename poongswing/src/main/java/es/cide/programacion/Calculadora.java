package es.cide.programacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Calculadora {

    // variables estaticas para almacenar los numeros y la operacion actual
    private static String numanterior = ""; // el numero anterior
    private static String numactual = ""; // pues el numero actual
    private static String operacion = ""; // el signo de operacion que hagamos
    private static boolean nuevaoper = false; // indica si se acaba de completar una operacion

    // agrega un numero al display de la calculadora
    private static void agregarnum(String numero, JTextField resultado) {
        // Si acabamos de hacer una operacion, limpiamos el numero actual
        if (nuevaoper) {
            numactual = "";
            nuevaoper = false;
        }
        numactual = numactual + numero;
        resultado.setText(numactual);
    }

    public static void main(String[] args) {

        try {
            // aplica el look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Error al aplicar el Look and Feel");
        }

        // frame principal
        JFrame frame = new JFrame("Calculadora");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 1, 10, 10));

        // panel superior del resultado y el historial
        JPanel resulpanel = new JPanel();
        resulpanel.setLayout(new GridLayout(2, 1));
        JLabel historial = new JLabel(); // muestra la operacion en curso
        JTextField resultado = new JTextField(25); // muestra el numero actual
        resultado.setEditable(false); // no permite que sea editable manualmente

        // panel para los botones de la calculadora
        JPanel btnpanel = new JPanel();
        btnpanel.setLayout(new GridLayout(4, 4, 15, 15)); // 4x4 grid con espaciado

        // creacion de los botones de numeros
        JButton[] numboton = new JButton[10];

        // botones de signos
        JButton numpor = new JButton("*");
        numpor.setBackground(Color.YELLOW);
        JButton nummas = new JButton("+");
        nummas.setBackground(Color.YELLOW);
        JButton numresta = new JButton("-");
        numresta.setBackground(Color.YELLOW);
        JButton numdividir = new JButton("/");
        numdividir.setBackground(Color.YELLOW);
        JButton numdel = new JButton("DEL");
        numdel.setBackground(Color.RED);
        JButton numigual = new JButton("=");
        numigual.setBackground(Color.BLUE);

        // botones numericos
        // crea y configura cada boton numerico
        for (int i = 0; i <= 9; i++) {
            numboton[i] = new JButton(String.valueOf(i));
            final int numero = i; // variable final para usar en el ActionListener

            // al hacer clic agrega el numero a la pantalla
            numboton[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    agregarnum(String.valueOf(numero), resultado);
                }
            });
            numboton[i].setFont(new Font("Arial", Font.BOLD, 20)); // Tamaño 20
        }

        // boton para eliminar el ultimo digito
        numdel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numactual.length() > 0) { // Verificar que haya algo que borrar
                    numactual = numactual.substring(0, numactual.length() - 1);
                    resultado.setText(numactual);
                }
            }
        });

        // botones de signos
        // suma
        nummas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!numactual.isEmpty()) {
                    // Si ya hay una operacion pendiente, calculamos primero
                    if (!numanterior.isEmpty() && !operacion.isEmpty()) {
                        int num1 = Integer.parseInt(numanterior);
                        int num2 = Integer.parseInt(numactual);
                        int answer = 0;
                        // hace la operacion con el signo
                        if (operacion.equals("+")) {
                            answer = num1 + num2;
                        } else if (operacion.equals("-")) {
                            answer = num1 - num2;
                        } else if (operacion.equals("*")) {
                            answer = num1 * num2;
                        } else if (operacion.equals("/")) {
                            // si el numero 2 es 0 pone que no se puede dividir entre 0
                            if (num2 == 0) {
                                resultado.setText("Error");
                                historial.setText("No se puede dividir entre 0");
                                // JOptionPane.showMessageDialog(frame, "No se puede dividir entre 0");
                                return;
                            }
                            answer = num1 / num2;
                        }
                        numanterior = String.valueOf(answer);
                        resultado.setText(numanterior);
                        // si no es ninguno el numero anterior sera igual al actual
                    } else {
                        numanterior = numactual;
                    }
                    operacion = "+";
                    historial.setText(numanterior + " + ");
                    numactual = "";
                    nuevaoper = false;
                }
            }
        });
        // resta
        numresta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // si no hay numero 
                if (!numactual.isEmpty()) {
                    // si ya hay una operacion pendiente, calculamos primero
                    if (!numanterior.isEmpty() && !operacion.isEmpty()) {
                        int num1 = Integer.parseInt(numanterior);
                        int num2 = Integer.parseInt(numactual);
                        int answer = 0;
                        if (operacion.equals("+")) {
                            answer = num1 + num2;
                        } else if (operacion.equals("-")) {
                            answer = num1 - num2;
                        } else if (operacion.equals("*")) {
                            answer = num1 * num2;
                        } else if (operacion.equals("/")) {
                            if (num2 == 0) {
                                resultado.setText("Error");
                                historial.setText("No se puede dividir entre 0");
                                // JOptionPane.showMessageDialog(frame, "No se puede dividir entre 0");
                                return;
                            }
                            answer = num1 / num2; // hace la operacion
                        }
                        numanterior = String.valueOf(answer);
                        resultado.setText(numanterior);
                    } else {
                        numanterior = numactual;
                    }
                    operacion = "-";
                    historial.setText(numanterior + " - ");
                    numactual = "";
                    nuevaoper = false;
                }
            }
        });
        // multiplicacion
        numpor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!numactual.isEmpty()) {
                    // Si ya hay una operacion pendiente, calculamos primero
                    if (!numanterior.isEmpty() && !operacion.isEmpty()) {
                        int num1 = Integer.parseInt(numanterior);
                        int num2 = Integer.parseInt(numactual);
                        int answer = 0;

                        if (operacion.equals("+")) {
                            answer = num1 + num2;
                        } else if (operacion.equals("-")) {
                            answer = num1 - num2;
                        } else if (operacion.equals("*")) {
                            answer = num1 * num2;
                        } else if (operacion.equals("/")) {
                            if (num2 == 0) {
                                resultado.setText("Error");
                                historial.setText("No se puede dividir entre 0");
                                // JOptionPane.showMessageDialog(frame, "No se puede dividir entre 0");
                                return;
                            }
                            answer = num1 / num2;
                        }
                        numanterior = String.valueOf(answer);
                        resultado.setText(numanterior);
                    } else {
                        numanterior = numactual;
                    }
                    operacion = "*";
                    historial.setText(numanterior + " * ");
                    numactual = "";
                    nuevaoper = false;
                }
            }
        });
        // division
        numdividir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!numactual.isEmpty()) {
                    // Si ya hay una operacion pendiente, calculamos primero
                    if (!numanterior.isEmpty() && !operacion.isEmpty()) {
                        int num1 = Integer.parseInt(numanterior);
                        int num2 = Integer.parseInt(numactual);
                        int answer = 0;

                        if (operacion.equals("+")) {
                            answer = num1 + num2;
                        } else if (operacion.equals("-")) {
                            answer = num1 - num2;
                        } else if (operacion.equals("*")) {
                            answer = num1 * num2;
                        } else if (operacion.equals("/")) {
                            if (num2 == 0) {
                                resultado.setText("Error");
                                historial.setText("No se puede dividir entre 0");
                                // JOptionPane.showMessageDialog(frame, "No se puede dividir entre 0");
                                return;
                            }
                            answer = num1 / num2;
                        }
                        numanterior = String.valueOf(answer);
                        resultado.setText(numanterior);
                    } else {
                        numanterior = numactual;
                    }
                    operacion = "/";
                    historial.setText(numanterior + " / ");
                    numactual = "";
                    nuevaoper = false;
                }
            }
        });
        // igual y calcula el resultado
        numigual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // si no hay numero anterior ni el actual ni hay operacion
                if (!numanterior.isEmpty() && !numactual.isEmpty() && !operacion.isEmpty()) {
                    try {
                        int num1 = Integer.parseInt(numanterior);
                        int num2 = Integer.parseInt(numactual);
                        int answer = 0;

                        // realiza la operacion que toque
                        if (operacion.equals("+")) {
                            answer = num1 + num2;
                        } else if (operacion.equals("-")) {
                            answer = num1 - num2;
                        } else if (operacion.equals("*")) {
                            answer = num1 * num2;
                        } else if (operacion.equals("/")) {
                            if (num2 == 0) {
                                resultado.setText("Error");
                                historial.setText("No se puede dividir entre 0");
                                JOptionPane.showMessageDialog(frame, "No se puede dividir entre 0");
                                return;
                            }
                            answer = num1 / num2;
                        }
                        // muestra el resultado
                        resultado.setText(String.valueOf(answer));
                        historial.setText(num1 + " " + operacion + " " + num2 + " = " + answer);

                        // prepara una nueva operacion
                        numactual = String.valueOf(answer); // guardamos el resultado en numactual
                        numanterior = ""; // limpiamos el numero anterior
                        operacion = ""; // limpiamos la operacion
                        nuevaoper = true; // indicamos que se acaba de completar una operacion
                    } catch (NumberFormatException ex) {
                        resultado.setText("Error");
                        historial.setText("Numero invalido");
                        JOptionPane.showMessageDialog(frame, "No se puede dividir entre 0");
                    }
                }
            }
        });

        // permite usar el teclado fisico
        resultado.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // no es necesario
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // no es necesario
            }

            @Override
            public void keyPressed(KeyEvent e) {
                char tecla = e.getKeyChar();
                // si es un digito agregarlo
                if (Character.isDigit(tecla)) {
                    agregarnum(String.valueOf(tecla), resultado);
                } // si es una operacion
                else if (tecla == '+') {
                    nummas.doClick(); // simula el boton de suma
                } else if (tecla == '-') {
                    numresta.doClick(); // simula el boton de resta
                } else if (tecla == '*') {
                    numpor.doClick(); // simula el boton de multiplicacion
                } else if (tecla == '/') {
                    numdividir.doClick(); // simula el boton de division
                } // si es enter o calcular
                else if (tecla == '\n' || tecla == '=') {
                    numigual.doClick(); // simula click en el boton "="
                } // si es backspace borrar
                else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    numdel.doClick(); // simula click en el boton "del"
                }
            }
        });
        // agregaciones al frame
        frame.add(resulpanel);
        frame.add(btnpanel);
        resulpanel.add(historial);
        resulpanel.add(resultado);
        btnpanel.add(numboton[1]);
        btnpanel.add(numboton[2]);
        btnpanel.add(numboton[3]);
        btnpanel.add(nummas);
        btnpanel.add(numboton[4]);
        btnpanel.add(numboton[5]);
        btnpanel.add(numboton[6]);
        btnpanel.add(numresta);
        btnpanel.add(numboton[7]);
        btnpanel.add(numboton[8]);
        btnpanel.add(numboton[9]);
        btnpanel.add(numpor);
        btnpanel.add(numdividir);
        btnpanel.add(numboton[0]);
        btnpanel.add(numigual);
        btnpanel.add(numdel);

        // lo centra en la pantalla
        frame.setLocationRelativeTo(null);

        // hace que se pueda ver
        frame.setVisible(true);
    }
}
