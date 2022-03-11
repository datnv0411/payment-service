package vn.cmc.du21.paymentservice.presentation.external.request;

public class OrderPaymentRequest {
    private String vnpVersion;
    private String vnpCommand;
    private String vnpTmnCode;
    private String vnpAmount;
    private String vnpCreateDate;
    private String vnpCurrCode;
    private String vnpIpAddr;
    private String vnpLocale;
    private String vnpOrderInfo;
    private String vnpOrderType;
    private String vnpReturnUrl;
    private String vnpTxnRef;
    private String vnpSecureHash;

    public OrderPaymentRequest() {
    }

    public OrderPaymentRequest(String vnpVersion, String vnpCommand, String vnpTmnCode, String vnpAmount, String vnpCreateDate, String vnpCurrCode, String vnpIpAddr, String vnpLocale, String vnpOrderInfo, String vnpOrderType, String vnpReturnUrl, String vnpTxnRef, String vnpSecureHash) {
        this.vnpVersion = vnpVersion;
        this.vnpCommand = vnpCommand;
        this.vnpTmnCode = vnpTmnCode;
        this.vnpAmount = vnpAmount;
        this.vnpCreateDate = vnpCreateDate;
        this.vnpCurrCode = vnpCurrCode;
        this.vnpIpAddr = vnpIpAddr;
        this.vnpLocale = vnpLocale;
        this.vnpOrderInfo = vnpOrderInfo;
        this.vnpOrderType = vnpOrderType;
        this.vnpReturnUrl = vnpReturnUrl;
        this.vnpTxnRef = vnpTxnRef;
        this.vnpSecureHash = vnpSecureHash;
    }

    public String getVnpVersion() {
        return vnpVersion;
    }

    public void setVnpVersion(String vnpVersion) {
        this.vnpVersion = vnpVersion;
    }

    public String getVnpCommand() {
        return vnpCommand;
    }

    public void setVnpCommand(String vnpCommand) {
        this.vnpCommand = vnpCommand;
    }

    public String getVnpTmnCode() {
        return vnpTmnCode;
    }

    public void setVnpTmnCode(String vnpTmnCode) {
        this.vnpTmnCode = vnpTmnCode;
    }

    public String getVnpAmount() {
        return vnpAmount;
    }

    public void setVnpAmount(String vnpAmount) {
        this.vnpAmount = vnpAmount;
    }

    public String getVnpCreateDate() {
        return vnpCreateDate;
    }

    public void setVnpCreateDate(String vnpCreateDate) {
        this.vnpCreateDate = vnpCreateDate;
    }

    public String getVnpCurrCode() {
        return vnpCurrCode;
    }

    public void setVnpCurrCode(String vnpCurrCode) {
        this.vnpCurrCode = vnpCurrCode;
    }

    public String getVnpIpAddr() {
        return vnpIpAddr;
    }

    public void setVnpIpAddr(String vnpIpAddr) {
        this.vnpIpAddr = vnpIpAddr;
    }

    public String getVnpLocale() {
        return vnpLocale;
    }

    public void setVnpLocale(String vnpLocale) {
        this.vnpLocale = vnpLocale;
    }

    public String getVnpOrderInfo() {
        return vnpOrderInfo;
    }

    public void setVnpOrderInfo(String vnpOrderInfo) {
        this.vnpOrderInfo = vnpOrderInfo;
    }

    public String getVnpOrderType() {
        return vnpOrderType;
    }

    public void setVnpOrderType(String vnpOrderType) {
        this.vnpOrderType = vnpOrderType;
    }

    public String getVnpReturnUrl() {
        return vnpReturnUrl;
    }

    public void setVnpReturnUrl(String vnpReturnUrl) {
        this.vnpReturnUrl = vnpReturnUrl;
    }

    public String getVnpTxnRef() {
        return vnpTxnRef;
    }

    public void setVnpTxnRef(String vnpTxnRef) {
        this.vnpTxnRef = vnpTxnRef;
    }

    public String getVnpSecureHash() {
        return vnpSecureHash;
    }

    public void setVnpSecureHash(String vnpSecureHash) {
        this.vnpSecureHash = vnpSecureHash;
    }
}
