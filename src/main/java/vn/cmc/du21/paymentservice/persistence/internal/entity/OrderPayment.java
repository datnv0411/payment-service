package vn.cmc.du21.paymentservice.persistence.internal.entity;

import javax.persistence.*;

@Entity
@Table(name = "orderpayment")
public class OrderPayment {
    @EmbeddedId
    private OrderPaymentId orderPaymentId;

    private String paymentMethod;
    private long totalOrder;
}
