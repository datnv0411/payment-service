package vn.cmc.du21.paymentservice.presentation.external.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import vn.cmc.du21.paymentservice.common.restful.StandardResponse;
import vn.cmc.du21.paymentservice.common.restful.StatusResponse;
import vn.cmc.du21.paymentservice.presentation.external.mapper.PaymentOrderMapper;
import vn.cmc.du21.paymentservice.presentation.external.response.PaymentResponse;
import vn.cmc.du21.paymentservice.presentation.internal.response.OrderResponse;
import vn.cmc.du21.paymentservice.service.ImageService;
import vn.cmc.du21.paymentservice.service.PaymentService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping(path = "api/v1.0/payment")
public class PaymentController {
    @Autowired
    private Environment env;
    @Autowired
    PaymentService paymentService;
    @Autowired
    ImageService imageService;


    private static final String GET_DETAIL_ORDER = "/api/v1.0/order/";
    private static final String PATH_ORDER_SERVICE = "path.order-service";

    @GetMapping("get-info-payment")
    ResponseEntity<Object> checkPaid(@RequestParam(name = "orderId") long orderId,
                                     @RequestParam(name = "paymentId") long paymentId) throws Throwable {
        PaymentResponse paymentResponse = PaymentOrderMapper.convertPaymentOrderToPaymentResponse(
                paymentService.getInfoPayment(orderId, paymentId)
        );

       return ResponseEntity.ok().body(
         new StandardResponse<>(
                 StatusResponse.SUCCESSFUL,
                 "Get info payment",
                 paymentResponse
         )
       );
    }

    @GetMapping("vnpay/{orderId}")
    void createPaymentVNpay(HttpServletRequest request, HttpServletResponse response,
                                              @PathVariable long orderId) throws Exception {

        log.info("Mapped removeProduct method {{GET: /payment/vnpay/orderId}}");

        //get info order
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBearerAuth(request.getHeader("Authorization").split(" ")[1]);
        String uri = env.getProperty(PATH_ORDER_SERVICE) + GET_DETAIL_ORDER + orderId;

        HttpEntity<StandardResponse<OrderResponse>> entity = new HttpEntity<>(new StandardResponse<>(), httpHeaders);
        ResponseEntity<StandardResponse<OrderResponse>> res = restTemplate
                .exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<StandardResponse<OrderResponse>>() {});

        OrderResponse orderResponse = res.getBody().getData();
        paymentService.checkStatusOrder(orderResponse.getStatusOrder());

        String linkPay = paymentService.createLink(
                orderId,
                orderResponse.getTotalResponse().getTotalOrder(),
                request.getRemoteAddr(),
                MvcUriComponentsBuilder.fromController(PaymentController.class).toUriString() +"/response/vnpay"
        );
        response.sendRedirect(linkPay);
    }

    @GetMapping("momo/{orderId}")
    ResponseEntity<byte[]> createPaymentMomo(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable long orderId) throws Exception {

        log.info("Mapped removeProduct method {{GET: /payment/momo/orderId}}");

        //get info order
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBearerAuth(request.getHeader("Authorization").split(" ")[1]);
        String uri = env.getProperty(PATH_ORDER_SERVICE)+GET_DETAIL_ORDER + orderId;

        HttpEntity<StandardResponse<OrderResponse>> entity = new HttpEntity<>(new StandardResponse<>(), httpHeaders);
        ResponseEntity<StandardResponse<OrderResponse>> res = restTemplate
                .exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<StandardResponse<OrderResponse>>() {});

        OrderResponse orderResponse = res.getBody().getData();
        paymentService.checkStatusOrder(orderResponse.getStatusOrder());


        try {
            byte[] bytes = imageService.readFileContent("qr.png");
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("zalopay/{orderId}")
    ResponseEntity<byte[]> createPaymentZalopay(HttpServletRequest request, HttpServletResponse response,
                           @PathVariable long orderId) throws Exception {

        log.info("Mapped removeProduct method {{GET: /payment/zalopay/orderId}}");

        //get info order
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBearerAuth(request.getHeader("Authorization").split(" ")[1]);
        String uri = env.getProperty(PATH_ORDER_SERVICE)+GET_DETAIL_ORDER + orderId;

        HttpEntity<StandardResponse<OrderResponse>> entity = new HttpEntity<>(new StandardResponse<>(), httpHeaders);
        ResponseEntity<StandardResponse<OrderResponse>> res = restTemplate
                .exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<StandardResponse<OrderResponse>>() {});

        OrderResponse orderResponse = res.getBody().getData();
        paymentService.checkStatusOrder(orderResponse.getStatusOrder());
        try {
            byte[] bytes = imageService.readFileContent("qr.png");
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/response/vnpay", name = "responsePaymentVnpay")
    ResponseEntity<Object> checkPaymentVnpay(@RequestParam(name = "vnp_ResponseCode") String vnp_ResponseCode,
                                             @RequestParam(name = "vnp_TxnRef") String vnp_TxnRef,
                                             @RequestParam(name = "vnp_Amount") String vnp_Amount,
                                             HttpServletRequest request, HttpServletResponse response){

        String result = paymentService.checkResultPaidVnpay(vnp_ResponseCode, vnp_TxnRef, vnp_Amount);
        final String uri = env.getProperty("path.order-service") + "/api/v1.0/order/paid"
                + "?orderId=" +vnp_TxnRef + "&paymentId=" + paymentService.getPaymentIdByPaymentName("Vnpay");

        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBearerAuth(request.getHeader("Authorization").split(" ")[1]);

        HttpEntity<StandardResponse<OrderResponse>> entity = new HttpEntity<>(new StandardResponse<>(), httpHeaders);
        ResponseEntity<StandardResponse<OrderResponse>> res = restTemplate
                .exchange(uri, HttpMethod.PUT, entity, new ParameterizedTypeReference<StandardResponse<OrderResponse>>() {});

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        result
                )
        );
    }

    @GetMapping("/response/momo")
    ResponseEntity<Object> checkPaymentMomo(@RequestParam(name = "responseCode", required = false) String responseCode,
                                            @RequestParam(name = "orderId") String orderId,
                                            @RequestParam(name = "totalPaid", required = false) String totalPaid,
                                            HttpServletRequest request, HttpServletResponse response){

        if (responseCode==null || !responseCode.chars().allMatch(Character::isDigit) || responseCode.equals("")) responseCode="00";
        if (totalPaid==null || !totalPaid.chars().allMatch(Character::isDigit) || totalPaid.equals("")) responseCode="0";

        String result = paymentService.checkResultPaid(responseCode, orderId, totalPaid);
        final String uri = env.getProperty("path.order-service") + "/api/v1.0/order/paid"
                + "?orderId=" +orderId + "&paymentId=" + paymentService.getPaymentIdByPaymentName("Momo");

        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBearerAuth(request.getHeader("Authorization").split(" ")[1]);

        HttpEntity<StandardResponse<OrderResponse>> entity = new HttpEntity<>(new StandardResponse<>(), httpHeaders);
        ResponseEntity<StandardResponse<OrderResponse>> res = restTemplate
                .exchange(uri, HttpMethod.PUT, entity, new ParameterizedTypeReference<StandardResponse<OrderResponse>>() {});

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        result
                )
        );
    }

    @GetMapping("/response/zalopay")
    ResponseEntity<Object> checkPaymentZalopay(@RequestParam(name = "responseCode", required = false) String responseCode,
                                               @RequestParam(name = "orderId") String orderId,
                                               @RequestParam(name = "totalPaid", required = false) String totalPaid,
                                               HttpServletRequest request, HttpServletResponse response){

        if (responseCode==null || !responseCode.chars().allMatch(Character::isDigit) || responseCode.equals("")) responseCode="00";
        if (totalPaid==null || !totalPaid.chars().allMatch(Character::isDigit) || totalPaid.equals("")) responseCode="0";

        String result = paymentService.checkResultPaid(responseCode, orderId, totalPaid);
        final String uri = env.getProperty("path.order-service") + "/api/v1.0/order/paid"
                + "?orderId=" +orderId + "&paymentId=" + paymentService.getPaymentIdByPaymentName("Zalopay");

        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBearerAuth(request.getHeader("Authorization").split(" ")[1]);

        HttpEntity<StandardResponse<OrderResponse>> entity = new HttpEntity<>(new StandardResponse<>(), httpHeaders);
        ResponseEntity<StandardResponse<OrderResponse>> res = restTemplate
                .exchange(uri, HttpMethod.PUT, entity, new ParameterizedTypeReference<StandardResponse<OrderResponse>>() {});

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        result
                )
        );
    }
}
