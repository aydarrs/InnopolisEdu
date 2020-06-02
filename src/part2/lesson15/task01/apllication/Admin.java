package part2.lesson15.task01.apllication;

import part2.lesson15.task01.Customer;

/**
 * Admin.
 * Administrator implementation.
 * @author Aydar_Safiullin
 */
public class Admin extends Customer {

    public static void main(String[] args) {
        Admin admin = new Admin();
        OnlineShop onlineShop = new OnlineShop(admin);
        onlineShop.useByAdmin();
    }
}
