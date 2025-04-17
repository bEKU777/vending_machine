package paymentMethods;

public class CoinAcceptor implements PaymentAcceptor{
    private int balance;

    public CoinAcceptor(int initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public boolean pay(int amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public void addFunds(int amount) {
        balance += amount;
    }

    @Override
    public int getBalance() {
        return balance;
    }
}
