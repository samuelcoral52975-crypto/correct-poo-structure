package ui;

import java.util.Scanner;

/**
 * EN: Utility class for console input/output. Centralizes all reading and
 * writing
 * operations to keep UI code clean and consistent.
 *
 * ES: Clase utilitaria para entrada/salida de consola. Centraliza todas las
 * operaciones
 * de lectura y escritura para mantener el código de la UI limpio y consistente.
 */
public class Console {
    public Console() {
        this.scanner = new Scanner(System.in);
    }

    // EN: Shared Scanner instance used for all console input.
    // ES: Instancia compartida de Scanner usada para toda la entrada desde consola.
    private Scanner scanner;

    /**
     * EN: Writes a message followed by a newline.
     * ES: Escribe un mensaje seguido de un salto de línea.
     */
    public void writeLine(Object message) {
        System.out.println(message);
    }

    /**
     * EN: Writes a message without adding a newline.
     * ES: Escribe un mensaje sin agregar un salto de línea.
     */
    public void write(Object message) {
        System.out.print(message);
    }

    /**
     * EN: Displays a prompt and reads a full line of text from the user.
     * ES: Muestra un mensaje y lee una línea completa de texto del usuario.
     */
    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * EN: Displays a prompt and reads an integer. Repeats until valid input is
     * entered.
     * ES: Muestra un mensaje y lee un entero. Repite hasta que el usuario ingrese
     * un valor válido.
     */
    public int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number");
            }
        }
    }

    /**
     * EN: Displays a prompt and reads a double. Repeats until valid input is
     * entered.
     * ES: Muestra un mensaje y lee un número double. Repite hasta que el usuario
     * ingrese un valor válido.
     */
    public double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number");
            }
        }
    }

    /**
     * EN: Reads a string line from the console using the provided message.
     * ES: Lee una línea de texto desde consola usando el mensaje proporcionado.
     */
    public String readString(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }
}