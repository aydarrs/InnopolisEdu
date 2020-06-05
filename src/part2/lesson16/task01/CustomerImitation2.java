package part2.lesson16.task01;

import part2.lesson16.task01.dto.Customer;

/**
 * CustomerImitation2.
 *
 * @author Aydar_Safiullin
 */
public class CustomerImitation2 {
    public static void main(String[] args) {
        Customer customer = new Customer("Stepan", "+79274567734", "Moscow");
        customer.connect();
    }
}
