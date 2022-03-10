package vn.cmc.du21.paymentservice.service;


import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.cmc.du21.paymentservice.common.Sha512;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PaymentService {
    final String VNP_TMNCODE = "H75QIGLL";
    final static String VNP_HASHSECRET = "AFWJXAEGYIFGFUOPUYPTOYUOOQBHRBSC";
    final String VNP_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html?";
    final String VNP_VERSION = "2.1.0";
    final String VNP_COMMAND = "pay";
    final String ORDER_TYPE = "150000";

    private RestTemplate restTemplate;
    private HttpHeaders headers;

    public String createLink(long orderId, long totalPrice, HttpServletRequest request) throws Exception {
        restTemplate = new RestTemplate();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime dateTimeNow = LocalDateTime.now();

        String vnp_Version = VNP_VERSION;
        String vnp_Command = VNP_COMMAND;
        String vnp_OrderInfo = "DH" + dtf.format(dateTimeNow);
        String vnp_OrderType = ORDER_TYPE;
        String vnp_TxnRef = String.valueOf(orderId);
        String vnp_IpAddr = request.getLocalAddr();
        String vnp_TmnCode = VNP_TMNCODE;
        //String vnp_BankCode = "NCB";
        String vnp_CreateDate = dtf.format(dateTimeNow);
        String vnp_Amount = String.valueOf(totalPrice * 100);
        String vnp_CurrCode = "VND";
        String vnp_Locale = "vn";
        String vnp_ReturnUrl = "http://localhost:8300/api/v1.0/payment/response";

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

    public String resultString( String vnp_ResponseCode){
        if("00".equals(vnp_ResponseCode)){
            return "Successful transaction";
        }
        else {
            return "Transaction failed";
        }
    }
}

