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
        return scanner.nextLine();
    }

    public int askForInt(String message) throws InputMismatchException {
        System.out.print(message);
        int returnValue = scanner.nextInt();
        scanner.nextLine();
        return returnValue;
    }

    public double askForDouble(String message) throws InputMismatchException {
        System.out.print(message);
        double returnValue = scanner.nextDouble();
        scanner.nextLine();
        return returnValue;
    }

    public short askForShort(String message) throws InputMismatchException {
        System.out.print(message);
        short returnValue = scanner.nextShort();
        scanner.nextLine();
        return returnValue;
    }

    public void scannerNext() {
        scanner.next();
    }


}
