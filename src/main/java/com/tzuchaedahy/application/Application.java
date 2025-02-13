package com.tzuchaedahy.application;

import java.util.Scanner;

import com.tzuchaedahy.domain.Client;
import com.tzuchaedahy.domain.Product;
import com.tzuchaedahy.service.ClientService;
import com.tzuchaedahy.service.ProductService;
import com.tzuchaedahy.utils.Utils;

public class Application {
    private static Scanner scanner = new Scanner(System.in);
    private static ProductService productService = new ProductService();
    private static ClientService clientService = new ClientService();

    public static void run() {
        Boolean isRunning = true;

        while (isRunning) {
            Utils.clearScreen();

            showMenu();

            int option = -1;
            try {
                option = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Opção inválida!");
                scanner.next();
            }

            isRunning = chooseAnOption(option);
        }
    }

    private static void showMenu() {
        System.out.println("---------- Menu Inicial - Loja ----------\n");
        System.out.println("1 - Cadastrar produto");
        System.out.println("2 - Cadastrar cliente");
        System.out.println("3 - Buscar produto");
        System.out.println("4 - Listar os produtos disponiveis");
        System.out.println("5 - Efetuar venda");
        System.out.println("6 - Listar vendas realizadas");
        System.out.println("0 - Sair");

        System.out.print("\nDigite a opçao desejada: ");
    }

    private static Boolean chooseAnOption(int option) {
        switch (option) {
            case 1:
                registerProduct();
                break;
            case 2:
                registerClient();
                break;
            case 3:
                System.out.println("Buscar produto");
                break;
            case 4:
                System.out.println("Listar os produtos disponiveis");
                break;
            case 5:
                System.out.println("Efetuar venda");
                break;
            case 6:
                System.out.println("Listar vendas realizadas");
                break;
            case 0:
                System.out.println("Saindo...");
                return false;
            default:
                System.out.println("Opçao inválida!");
                Utils.sleepScreen(2);
        }

        return true;
    }

    private static void registerProduct() {
        Utils.clearScreen();

        System.out.println("---------- Cadastro de Produto ----------\n");

        Utils.clearBuffer(scanner);

        System.out.print("Digite o nome do produto: ");
        String name = scanner.nextLine();

        System.out.print("Digite o valor unitário do produto: ");
        Double unitPrice = scanner.nextDouble();

        System.out.print("Digite a quantidade do produto: ");
        Integer quantity = scanner.nextInt();

        try {
            Product product = new Product(null, name, unitPrice, quantity);

            productService.registerProduct(product);

            Utils.displayMessage("Produto cadastrado com sucesso!");
        } catch (RuntimeException e) {
            Utils.displayMessage(e.getMessage());
        }
    }

    private static void registerClient() {
        Utils.clearScreen();

        System.out.println("---------- Cadastro de Cliente ----------\n");

        Utils.clearBuffer(scanner);

        System.out.print("Digite o nome do cliente: ");
        String name = scanner.nextLine();

        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        System.out.print("Digite o endereço do cliente: ");
        String address = scanner.nextLine();

        System.out.print("Digite o telefone do cliente: ");
        String phone = scanner.nextLine();

        try {
            Client client = new Client(cpf, name, address, phone);

            clientService.registerClient(client);

            Utils.clearScreen();

            Utils.displayMessage("Cliente cadastrado com sucesso!");
        } catch (RuntimeException e) {
            Utils.displayMessage(e.getMessage());
        }
    }

}
