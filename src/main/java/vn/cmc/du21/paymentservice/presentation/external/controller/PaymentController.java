package vn.cmc.du21.paymentservice.presentation.external.controller;


import com.sun.xml.bind.api.impl.NameConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.cmc.du21.paymentservice.service.PaymentService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping(path = "api/v1.0/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;


    private static final String GET_DETAIL_PRODUCT = "/api/v1.0/product/get-detail-product/";
    private static final String PATH_INVENTORY_SERVICE = "path.inventory-service";

    @GetMapping("/create-vnpay")
    ResponseEntity<Object> createPaymentVNpay(HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.info("Mapped removeProduct method {{GET: /create-vnpay}}");
//        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
//        long userId = userLogin.getUserId();
        long userId = 8;


        String linkPay = paymentService.createLink(1234L, 40000000, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "successful",
                        linkPay

                )
        );
    }

    @GetMapping("/response")
    ResponseEntity<Object> checkPayment(@RequestParam(name = "vnp_ResponseCode") String vnp_ResponseCode
                                        , HttpServletRequest request, HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "successful",
                        paymentService.resultString(vnp_ResponseCode)

                )
        );
    }
}
