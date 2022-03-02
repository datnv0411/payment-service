package vn.cmc.du21.paymentservice.common;

import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import vn.cmc.du21.paymentservice.presentation.external.controller.ImageController;

public class PathImageUtil {
    private PathImageUtil(){super();}

    public static String getPathImage(String fileName){
            return MvcUriComponentsBuilder.fromMethodName(ImageController.class,
                    "readDetailFile", fileName).toUriString();
    }
}
