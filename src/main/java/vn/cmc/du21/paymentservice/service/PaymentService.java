package vn.cmc.du21.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.cmc.du21.paymentservice.common.Sha512;
import vn.cmc.du21.paymentservice.persistence.internal.entity.Payment;
import vn.cmc.du21.paymentservice.persistence.internal.entity.PaymentOrder;
import vn.cmc.du21.paymentservice.persistence.internal.entity.PaymentOrderId;
import vn.cmc.du21.paymentservice.persistence.internal.repository.PaymentOrderRepository;
import vn.cmc.du21.paymentservice.persistence.internal.repository.PaymentRepository;

import javax.transaction.Transactional;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentOrderRepository paymentOrderRepository;
    final String VNP_TMNCODE = "H75QIGLL";
    final static String VNP_HASHSECRET = "AFWJXAEGYIFGFUOPUYPTOYUOOQBHRBSC";
    final String VNP_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html?";
    final String VNP_VERSION = "2.1.0";
    final String VNP_COMMAND = "pay";
    final String ORDER_TYPE = "180000";

    private RestTemplate restTemplate;
    private HttpHeaders headers;

    public String createLink(long orderId, long totalPrice, String ipClient, String returnUrl) throws Exception {
        restTemplate = new RestTemplate();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime dateTimeNow = LocalDateTime.now();

        String vnp_Version = VNP_VERSION;
        String vnp_Command = VNP_COMMAND;
        String vnp_OrderInfo = "DH" + dtf.format(dateTimeNow);
        String vnp_OrderType = ORDER_TYPE;
        String vnp_TxnRef = String.valueOf(orderId);
        String vnp_IpAddr = ipClient;
        String vnp_TmnCode = VNP_TMNCODE;
        //String vnp_BankCode = "NCB";
        String vnp_CreateDate = dtf.format(dateTimeNow);
        String vnp_Amount = String.valueOf(totalPrice * 100);
        String vnp_CurrCode = "VND";
        String vnp_Locale = "vn";
        String vnp_ReturnUrl = returnUrl;

        String rawHash = "vnp_Amount=" + vnp_Amount +
//                         "&vnp_BankCode=" + vnp_BankCode +
                "&vnp_Command=" + URLEncoder.encode(vnp_Command, StandardCharsets.US_ASCII.toString()) +
                "&vnp_CreateDate=" + URLEncoder.encode(vnp_CreateDate, StandardCharsets.US_ASCII.toString())+
                "&vnp_CurrCode=" + URLEncoder.encode(vnp_CurrCode, StandardCharsets.US_ASCII.toString()) +
                "&vnp_IpAddr=" + URLEncoder.encode(vnp_IpAddr, StandardCharsets.US_ASCII.toString()) +
                "&vnp_Locale=" + URLEncoder.encode(vnp_Locale, StandardCharsets.US_ASCII.toString()) +
                "&vnp_OrderInfo=" + URLEncoder.encode(vnp_OrderInfo, StandardCharsets.US_ASCII.toString()) +
                "&vnp_OrderType=" + URLEncoder.encode(vnp_OrderType, StandardCharsets.US_ASCII.toString()) +
                "&vnp_ReturnUrl=" + URLEncoder.encode(vnp_ReturnUrl, StandardCharsets.US_ASCII.toString())+
                "&vnp_TmnCode=" + URLEncoder.encode(vnp_TmnCode, StandardCharsets.US_ASCII.toString()) +
                "&vnp_TxnRef=" + URLEncoder.encode(vnp_TxnRef, StandardCharsets.US_ASCII.toString()) +
                "&vnp_Version=" + URLEncoder.encode(vnp_Version, StandardCharsets.US_ASCII.toString());

        String vnp_SecureHash = Sha512.hmac(VNP_HASHSECRET, rawHash);

        String urlResult = VNP_URL+rawHash+"&vnp_SecureHash=" + vnp_SecureHash;
        return urlResult;
    }

    @Transactional
    public String checkResultPaidVnpay(String vnp_ResponseCode, String vnp_TxnRef, String vnp_Amount){
        if("00".equals(vnp_ResponseCode)){
            long orderId = Long.parseLong(vnp_TxnRef);
            long totalPaid = Long.parseLong(vnp_Amount);

            Payment payment = paymentRepository.findByPaymentName("Vnpay");
            if(payment == null)
            {
                throw new RuntimeException("Not found payment method!!!");
            }
            PaymentOrder newPaymentOrder = new PaymentOrder();
            newPaymentOrder.setStatus("Thành công");
            newPaymentOrder.setTotalPaid(totalPaid);
            newPaymentOrder.setPayment(payment);
            PaymentOrderId paymentOrderId = new PaymentOrderId(payment.getPaymentId(), orderId);
            newPaymentOrder.setPaymentOrderId(paymentOrderId);
            paymentOrderRepository.save(newPaymentOrder);
            return "Successful transaction";
        }
        else {
            return "Transaction failed";
        }
    }

    @Transactional
    public String checkResultPaid(String responseCode, String orderId, String totalPaid){
        if("00".equals(responseCode)){
            long orderIdLong = Long.parseLong(orderId);
            long totalPaidLong = Long.parseLong(totalPaid);

            Payment payment = paymentRepository.findByPaymentName("Vnpay");
            if(payment == null)
            {
                throw new RuntimeException("Not found payment method!!!");
            }
            PaymentOrder newPaymentOrder = new PaymentOrder();
            newPaymentOrder.setStatus("Thành công");
            newPaymentOrder.setTotalPaid(totalPaidLong);
            newPaymentOrder.setPayment(payment);
            PaymentOrderId paymentOrderId = new PaymentOrderId(payment.getPaymentId(), orderIdLong);
            newPaymentOrder.setPaymentOrderId(paymentOrderId);
            paymentOrderRepository.save(newPaymentOrder);
            return "Successful transaction";
        }
        else {
            return "Transaction failed";
        }
    }

    public void checkStatusOrder(String statusOrder) {
        if(statusOrder.equals("Đã thanh toán"))
        {
            throw new RuntimeException("Order has been paid !!!");
        }
    }

    public PaymentOrder getInfoPayment(long orderId, long paymentId) throws Throwable{
        Payment foundPayment = paymentRepository.findById(paymentId).orElseThrow(
                ()->{throw new RuntimeException("Not found!!!");}
        );

        PaymentOrderId paymentOrderId = new PaymentOrderId(paymentId, orderId);

        if(foundPayment.getPaymentName().equals("COD"))
        {
            PaymentOrder paymentOrder = new PaymentOrder();
            paymentOrder.setPayment(foundPayment);
            paymentOrder.setPaymentOrderId(paymentOrderId);
            return paymentOrder;
        }

        PaymentOrder foundPaymentOrder = paymentOrderRepository.findById(paymentOrderId).orElse(null);
        if(foundPaymentOrder == null)
        {
            PaymentOrder paymentOrder = new PaymentOrder();
            paymentOrder.setPayment(foundPayment);
            paymentOrder.setPaymentOrderId(paymentOrderId);
            return paymentOrder;
        }

        return foundPaymentOrder;
    }

    public long getPaymentIdByPaymentName(String paymentName) {
        Payment payment = paymentRepository.findByPaymentName(paymentName);
        if(payment == null)
        {
            throw new RuntimeException("Not found payment menthod!!!");
        }
        return payment.getPaymentId();
    }
}
