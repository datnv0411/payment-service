package vn.cmc.du21.paymentservice.persistence.internal.entity;

import javax.persistence.*;

@Entity
@Table(name = "paymentorder")
public class PaymentOrder {
    @EmbeddedId
    private PaymentOrderId paymentOrderId;

    private long totalPaid;
    private String status;

    @ManyToOne
    @MapsId("paymentId")
    private Payment payment;

    public PaymentOrder() {
    }

    public PaymentOrder(PaymentOrderId paymentOrderId, long paymentPaid, String status, Payment payment) {
        this.paymentOrderId = paymentOrderId;
        this.totalPaid = paymentPaid;
        this.status = status;
        this.payment = payment;
    }

    public PaymentOrderId getPaymentOrderId() {
        return paymentOrderId;
    }

    public void setPaymentOrderId(PaymentOrderId paymentOrderId) {
        this.paymentOrderId = paymentOrderId;
    }

    public long getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(long totalPaid) {
        this.totalPaid = totalPaid;
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
