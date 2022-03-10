package vn.cmc.du21.paymentservice.internal.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long paymentId;
    private String paymentName;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<PaymentOrder> paymentOrders;

    public Payment() {
    }

    public Payment(long paymentId, String paymentName, List<PaymentOrder> paymentOrders) {
        this.paymentId = paymentId;
        this.paymentName = paymentName;
        this.paymentOrders = paymentOrders;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public List<PaymentOrder> getPaymentOrders() {
        return paymentOrders;
    }

    public void setPaymentOrders(List<PaymentOrder> paymentOrders) {
        this.paymentOrders = paymentOrders;
    }
}
