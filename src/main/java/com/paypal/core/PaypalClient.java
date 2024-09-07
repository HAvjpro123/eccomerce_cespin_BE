package com.paypal.core;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;


public class PaypalClient {
	private String clientId = "AQosJx26VtsYev37qZx2XaRy-XnXlK-9iZu6jLkDS6YWOCxvncCisAjbFAM8Xbpy4J0moS8j3ebX5Hea";
    private String clientSecret = "EATJU_6DWlXlp8Pb49R-qM_mTqIBpYkS-QiDTf23M3yxfrXHjoDb3car82n7o1xlPGhtzAuxEHgTihKx";
    private String mode = "sandbox"; 

    public APIContext getAPIContext() {
        return new APIContext(clientId, clientSecret, mode);
    }

}
