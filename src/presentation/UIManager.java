package presentation;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UIManager {

    private static Scanner scanner;

    public UIManager() {
        UIManager.scanner = new Scanner(System.in);
    }
    public void showMessage(String message) {
        System.out.println(message);
    }

    public String askForString(String message) {
        System.out.print(message);
        return scanner.next();
    }

    public int askForInt(String message) throws InputMismatchException {
        System.out.print(message);
        return scanner.nextInt();
    }

    public double askForDouble(String message) throws InputMismatchException {
        System.out.print(message);
        return scanner.nextDouble();
    }

    public short askForShort(String message) throws InputMismatchException {
        System.out.print(message);
        return scanner.nextShort();
    }


}
