import enums.ActionLetter;
import model.*;
import paymentMethods.CardAcceptor;
import paymentMethods.CoinAcceptor;
import paymentMethods.PaymentAcceptor;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private PaymentAcceptor paymentStrategy;

    private static final CoinAcceptor coinAcceptor = new CoinAcceptor(100);
    private static final CardAcceptor cardAcceptor = new CardAcceptor();

    private static boolean isExit = false;

    private final Scanner sc = new Scanner(System.in);

    private AppRunner() {
        this.paymentStrategy = choosePaymentMethod();
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        print("Ваш баланс: " + paymentStrategy.getBalance());

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);

    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (paymentStrategy.getBalance() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выйти");
        print(" i - Сменить способ оплаты");
        String action = fromConsole().substring(0, 1);
        if ("a".equalsIgnoreCase(action)) {
            paymentStrategy.addFunds(10);
            print("Вы пополнили баланс на 10");
            return;
        }
        if ("h".equalsIgnoreCase(action)) {
            isExit = true;
            return;
        }
        if ("i".equalsIgnoreCase(action)) {
            this.paymentStrategy = choosePaymentMethod();
            print("Способ оплаты успешно изменён.");
            return;
        }
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    boolean paid = paymentStrategy.pay(products.get(i).getPrice());
                    if (paid) {
                        print("Вы купили " + products.get(i).getName());
                    } else {
                        print("Недостаточно средств.");
                    }
                    return;
                }
            }
            print("Такого действия нет среди доступных. Попробуйте снова.");
        } catch (IllegalArgumentException e) {
                print("Недопустимая буква. Попрбуйте еще раз.");
        }
    }

    private PaymentAcceptor choosePaymentMethod() {
        System.out.println("Выберите способ оплаты:");
        System.out.println("1 - Монеты");
        System.out.println("2 - Банковская карта");

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            return coinAcceptor;
        } else {
            return cardAcceptor;
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
