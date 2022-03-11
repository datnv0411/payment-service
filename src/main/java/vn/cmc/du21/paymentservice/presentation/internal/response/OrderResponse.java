package vn.cmc.du21.paymentservice.presentation.internal.response;

import vn.cmc.du21.paymentservice.presentation.external.response.PaymentResponse;

public class OrderResponse {
    // order
    private long orderId;
    private long userId;
    private String statusOrder;

    // payment response
    private PaymentResponse paymentResponse;

    // total response
    private TotalOrderResponse totalResponse;

    public OrderResponse() {
    }

    public OrderResponse(long orderId, long userId, String statusOrder, PaymentResponse paymentResponse, TotalOrderResponse totalResponse) {
        this.orderId = orderId;
        this.userId = userId;
        this.statusOrder = statusOrder;
        this.paymentResponse = paymentResponse;
        this.totalResponse = totalResponse;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public PaymentResponse getPaymentResponse() {
        return paymentResponse;
    }

    public void setPaymentResponse(PaymentResponse paymentResponse) {
        this.paymentResponse = paymentResponse;
    }

    public TotalOrderResponse getTotalResponse() {
        return totalResponse;
    }

    public void setTotalResponse(TotalOrderResponse totalResponse) {
        this.totalResponse = totalResponse;
    }
}