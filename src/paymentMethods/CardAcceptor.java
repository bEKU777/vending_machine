package paymentMethods;

import java.util.Scanner;

public class CardAcceptor implements PaymentAcceptor {
    private int balance = 1000;

    Scanner sc = new Scanner(System.in);

    @Override
    public boolean pay(int amount) {
        System.out.print("Введите номер карты: ");
        String cardNumber = sc.nextLine();
        System.out.print("Введите одноразовый пароль: ");
        String otp = sc.nextLine();

        if (validateCard(cardNumber) && validateOtp(otp)) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Оплата по карте прошла успешно.");
                return true;
            } else {
                System.out.println("На карте недостаточно средств.");
            }
        } else {
            System.out.println("Ошибка оплаты: неверный номер карты или пароль.");
        }
        return false;
    }

    @Override
    public void addFunds(int amount) {
        System.out.println("Пополнение баланса недоступно для карты.");
    }

    @Override
    public int getBalance() {
        return balance;
    }

    private boolean validateCard(String number) {
        return number != null && number.matches("\\d{16}");
    }

    private boolean validateOtp(String code) {
        return code != null && code.matches("\\d{4}");
    }
}
