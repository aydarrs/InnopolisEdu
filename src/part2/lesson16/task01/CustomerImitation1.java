package part2.lesson16.task01;

import part2.lesson16.task01.dto.Customer;

/**
 * CustomerImitation.
 * Client imitation.
 * @author Aydar_Safiullin
 */
public class CustomerImitation1 {
    public static void main(String[] args) {
        Customer customer = new Customer("Igor", "+79173332211", "Kazan");
        customer.connect();
    }
}
