package paymentMethods;

public interface PaymentAcceptor {

    boolean pay(int amount);
    void addFunds(int amount);
    int getBalance();
}
