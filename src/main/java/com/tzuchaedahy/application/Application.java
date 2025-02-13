package com.tzuchaedahy.application;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import com.tzuchaedahy.domain.Client;
import com.tzuchaedahy.domain.Product;
import com.tzuchaedahy.service.ClientService;
import com.tzuchaedahy.service.OrderService;
import com.tzuchaedahy.service.ProductService;
import com.tzuchaedahy.utils.Utils;

public class Application {
    private static Scanner scanner = new Scanner(System.in);
    private static ProductService productService = new ProductService();
    private static ClientService clientService = new ClientService();
    private static OrderService orderService = new OrderService();

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
                searchProduct();
                break;
            case 4:
                listAllAvailableProducts();
                break;
            case 5:
                processSell();
                break;
            case 6:
                listAllSells();
                break;
            case 0:
                Utils.displayMessage("Saindo...");
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

    private static void searchProduct() {
        Utils.clearScreen();

        System.out.println("---------- Busca de Produto por ID ----------\n");

        Utils.clearBuffer(scanner);

        Integer id = null;
        Boolean hasId = false;

        while (!hasId) {
            try {
                System.out.print("Digite o ID do produto: ");
                id = scanner.nextInt();
                hasId = !hasId;
            } catch (InputMismatchException e) {
                Utils.displayMessage("ID inválido!");
            } finally {
                Utils.clearBuffer(scanner);
            }
        }

        Product product = productService.searchProductById(id);

        if (product == null) {
            Utils.displayMessage("Produto não encontrado!");
        } else {
            System.out.println(product);

            System.out.println("Para sair, aperte qualquer tecla e pressione Enter...");
            scanner.nextLine();
        }
    }

    private static void listAllAvailableProducts() {
        Utils.clearScreen();

        System.out.println("---------- Lista de Produtos Disponíveis ----------\n");

        Utils.clearBuffer(scanner);

        productService.listAllAvailableProducts().forEach(product -> {
            System.out.println(product.toString());
            System.out.println();
        });

        System.out.println("Para sair, aperte qualquer tecla e pressione Enter...");
        scanner.nextLine();
    }

    private static void processSell() {
        Utils.clearScreen();

        System.out.println("---------- Efetuar Venda ----------\n");

        Utils.clearBuffer(scanner);

        System.out.print("Digite o CPF do cliente: ");
        String clientCpf = scanner.nextLine();

        System.out.print("Digite o CPF do funcionário: ");
        String employeeCpf = scanner.nextLine();

        Map<Integer, Integer> sellProducts = new HashMap<>();

        Boolean hasAnotherProduct = true;
        while (hasAnotherProduct) {
            System.out.print("Digite o ID do produto: ");
            Integer productId = scanner.nextInt();
    
            System.out.print("Digite a quantidade do produto: ");
            Integer quantity = scanner.nextInt();

            sellProducts.put(productId, quantity);

            System.out.println("Deseja adicionar outro produto? (S/N)");
            String answer = scanner.next();

            if (!answer.equalsIgnoreCase("S")) {
                hasAnotherProduct = false;
            }
        }

        try {
            orderService.processSell(clientCpf, employeeCpf, sellProducts);

            Utils.displayMessage("Venda realizada com sucesso!");
        } catch (RuntimeException e) {
            Utils.displayMessage(e.getMessage());
        }
    }

    private static void listAllSells() {
        Utils.clearScreen();

        System.out.println("---------- Lista de Vendas Realizadas ----------\n");

        Utils.clearBuffer(scanner);

        orderService.getAllOrders().forEach(order -> {
            System.out.println(order.toString());
            System.out.println("Produtos comprados: ");
            orderService.getAllOrderProducts(order.getId()).forEach(orderProduct -> {
                System.out.println(orderProduct.toString());
            });
        });

        System.out.println("Para sair, aperte qualquer tecla e pressione Enter...");
        scanner.nextLine();
    }

}
