import java.math.BigDecimal;
import java.util.UUID;

// 1. Result representation
record PaymentResult(boolean isSuccess, String transactionId, String message) {}

// 2. Common Payment Strategy Interface
interface PaymentMethod {
    PaymentResult pay(BigDecimal amount);
}

// 3. Concrete Implementation: Credit Card
class CreditCardPayment implements PaymentMethod {
    private final String cardNumber;
    private final String cvv;

    public CreditCardPayment(String cardNumber, String cvv) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    @Override
    public PaymentResult pay(BigDecimal amount) {
        // Basic input validation
        if (cardNumber == null || cardNumber.length() < 16) {
            return new PaymentResult(false, null, "Invalid card details.");
        }

        // Simulate gateway authorization
        String txnId = "CC-" + UUID.randomUUID().toString().substring(0, 8);
        return new PaymentResult(true, txnId, "Charged " + amount + " to card ending in " + cardNumber.substring(cardNumber.length() - 4));
    }
}

// 4. Concrete Implementation: PayPal
class PayPalPayment implements PaymentMethod {
    private final String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public PaymentResult pay(BigDecimal amount) {
        if (email == null || !email.contains("@")) {
            return new PaymentResult(false, null, "Invalid PayPal email.");
        }

        String txnId = "PP-" + UUID.randomUUID().toString().substring(0, 8);
        return new PaymentResult(true, txnId, "Charged " + amount + " via PayPal account: " + email);
    }
}

// 5. Checkout Service (Context)
class PaymentProcessor {
    public PaymentResult processOrder(BigDecimal amount, PaymentMethod method) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new PaymentResult(false, null, "Payment amount must be greater than zero.");
        }

        return method.pay(amount);
    }
}

// 6. Execution Example
public class Main {
    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor();

        // Process a Credit Card transaction
        PaymentMethod creditCard = new CreditCardPayment("4111222233334444", "123");
        PaymentResult ccResult = processor.processOrder(new BigDecimal("79.99"), creditCard);
        System.out.println("Status: " + (ccResult.isSuccess() ? "SUCCESS" : "FAILED"));
        System.out.println("Txn ID: " + ccResult.transactionId());
        System.out.println("Details: " + ccResult.message());

        System.out.println("----------------------------------------");

        // Process a PayPal transaction
        PaymentMethod paypal = new PayPalPayment("customer@example.com");
        PaymentResult ppResult = processor.processOrder(new BigDecimal("24.50"), paypal);
        System.out.println("Status: " + (ppResult.isSuccess() ? "SUCCESS" : "FAILED"));
        System.out.println("Txn ID: " + ppResult.transactionId());
        System.out.println("Details: " + ppResult.message());
    }
}