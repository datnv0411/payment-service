package vn.cmc.du21.paymentservice.presentation.external.controller;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import vn.cmc.du21.orderservice.common.restful.StandardResponse;
import vn.cmc.du21.orderservice.common.restful.StatusResponse;

@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Tất cả các Exception không được khai báo sẽ được xử lý tại đây
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public StandardResponse<Object> handleAllException(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        return new StandardResponse<>(
                StatusResponse.INTERNAL_SERVER_ERROR,
                ex.getMessage()
        );
    }

    /**
     * IndexOutOfBoundsException sẽ được xử lý riêng tại đây
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public StandardResponse<Object> toDoException(Exception ex, WebRequest request) {
        return new StandardResponse<>(
                StatusResponse.BAD_REQUEST,
                ex.getMessage()
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public StandardResponse<Object> unauthorizedException(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        return new StandardResponse<>(
                StatusResponse.UNAUTHORIZED,
                ex.getMessage()
        );
    }
}
