package vn.cmc.du21.paymentservice.internal.entity;

import javax.persistence.*;

@Entity
@Table(name = "paymentorder")
public class PaymentOrder {
    @EmbeddedId
    private PaymentOrderId paymentOrderId;

    private String status;

    @ManyToOne
    @MapsId("paymentId")
    private Payment payment;

    public PaymentOrder() {
    }

    public PaymentOrder(PaymentOrderId paymentOrderId, String status, Payment payment) {
        this.paymentOrderId = paymentOrderId;
        this.status = status;
        this.payment = payment;
    }

    public PaymentOrderId getPaymentOrderId() {
        return paymentOrderId;
    }

    public void setPaymentOrderId(PaymentOrderId paymentOrderId) {
        this.paymentOrderId = paymentOrderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
