package vn.cmc.du21.paymentservice.presentation.external.mapper;

import vn.cmc.du21.paymentservice.persistence.internal.entity.PaymentOrder;
import vn.cmc.du21.paymentservice.presentation.external.response.PaymentResponse;

public class PaymentOrderMapper {
    private PaymentOrderMapper(){super();}

    public static PaymentResponse convertPaymentOrderToPaymentResponse(PaymentOrder paymentOrder)
    {
        String status = paymentOrder.getStatus() == null ? null : paymentOrder.getStatus();
        return new PaymentResponse(paymentOrder.getPaymentOrderId().getPaymentId(),
                paymentOrder.getPaymentOrderId().getOrderId(), paymentOrder.getTotalPaid(),
                paymentOrder.getPayment().getPaymentName(), paymentOrder.getStatus());
    }
}
