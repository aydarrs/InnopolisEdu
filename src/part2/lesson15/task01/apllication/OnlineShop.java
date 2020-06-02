package part2.lesson15.task01.apllication;

import part2.lesson15.task01.Customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * OnlineShop.
 *
 * @author Aydar_Safiullin
 */
public class OnlineShop {
    private Customer customer;
    private Controller controller;

    public OnlineShop(Customer customer) {
        this.customer = customer;
        controller = new Controller(customer);
    }

    /**
     * Client interface.
     */
    public void useByClient() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("Выберите действие:\n" +
                        "1 - посмотреть каталог\n" +
                        "2 - выбрать товар\n" +
                        "3 - купить\n" +
                        "Любой другой символ - выйти\n");
                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        controller.viewCatalog();
                        break;
                    case "2":
                        System.out.println("Введите артикул товара");
                        controller.choiceGood(Integer.parseInt(reader.readLine()));
                        break;
                    case "3":
                        controller.pay();
                        break;
                    default:
                        return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Administrator interface.
     */
    protected void useByAdmin() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("Выберите действие:\n" +
                        "1 - посмотреть каталог\n" +
                        "2 - посмотреть список доставки\n" +
                        "3 - посмотреть список клиентов\n" +
                        "4 - докупить товар\n" +
                        "5 - обновить базу данных\n" +
                        "6 - доставить товар\n" +
                        "7 - обновить информацию о клиенте\n" +
                        "Любой другой символ - выйти\n");
                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        controller.viewCatalog();
                        break;
                    case "2":
                        controller.viewDeliveryList();
                        break;
                    case "3":
                        controller.viewCustomers();
                        break;
                    case "4":
                        System.out.println("Введите артикул товара");
                        controller.purchaseGood(Integer.parseInt(reader.readLine()));
                        break;
                    case "5":
                        controller.reset();
                        break;
                    case "6":
                        controller.delivery();
                        break;
                    case "7":
                        System.out.println("Введите id клиента");
                        int id = Integer.parseInt(reader.readLine());
                        System.out.println("Введите новый телефонный номер клиента");
                        int newPhoneNumber = Integer.parseInt(reader.readLine());
                        controller.updateCustomer(id, newPhoneNumber);
                        break;
                    default:
                        return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
