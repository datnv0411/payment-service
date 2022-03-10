package vn.cmc.du21.paymentservice.internal.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PaymentOrderId implements Serializable {
    @Column(name = "paymentId")
    private long paymentId;

    @Column(name = "orderId")
    private long orderId;

    public PaymentOrderId(long paymentId, long orderId) {
        this.paymentId = paymentId;
        this.orderId = orderId;
    }

    public PaymentOrderId() {
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
