package com.tzuchaedahy.utils;

import java.util.Scanner;

public class Utils {
    public static void sleepScreen(Integer seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void clearBuffer(Scanner scanner) {
        scanner.nextLine();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void displayMessage(String message) {
        System.out.println(message);
        sleepScreen(2);
    }

}
