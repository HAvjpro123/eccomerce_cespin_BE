package com.cespin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cespin.exception.OrderException;
import com.cespin.model.Order;
import com.cespin.repository.OrderReponsitory;
import com.cespin.repository.PaymentLinkResponse;
import com.cespin.response.ApiResponse;
import com.cespin.service.OrderService;
import com.cespin.service.UserService;
import com.paypal.core.PaypalClient;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Transaction;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.RedirectUrls;

@RestController
@RequestMapping("/api")
public class PaymentController {

	@Value("${razorpay.api.key}")
	String apiKey;

	@Value("${razorpay.api.secret}")
	String apiSecret;

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@Autowired
	private OrderReponsitory orderRepository;

	@PostMapping("/payments/{orderId}")
//    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,
//    		@RequestHeader("Authorization") String jwt) throws OrderException, RazorpayException {
//  
//    	Order order = orderService.findOrderById(orderId);
//    	
//    	
//    	try {
//    		
//    		RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
//    		
//    		JSONObject paymentLinkRequest = new JSONObject();
//    		paymentLinkRequest.put("amount", order.getTotalPrice()*100);
//    		paymentLinkRequest.put("currency", "INR");
//    		
//    		JSONObject customer = new JSONObject();
//    		customer.put("name", order.getUser().getFirstName());
//    		customer.put("email", order.getUser().getEmail());
//    		paymentLinkRequest.put("customer", customer);
//    		
//    		JSONObject notify= new JSONObject();
//    		notify.put("sms", true);
//    		notify.put("email", true);
//    		paymentLinkRequest.put("notify", notify);
//    		
//    		paymentLinkRequest.put("callback_url", "http://localhost:3000/payment/"+orderId);
//    		paymentLinkRequest.put("callback_method", "get");
//    		
//    		PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
//    		
//    		String paymentLinkId=payment.get("id");
//    		String paymentLinkUrl=payment.get("short_url");
//    		
//    		PaymentLinkResponse res = new PaymentLinkResponse();
//    		res.setPayment_link_id(paymentLinkId);
//    		res.setPayment_link_url(paymentLinkUrl);
//    		
//    		return new ResponseEntity<PaymentLinkResponse>(res, HttpStatus.CREATED);
//    		
//		} catch (Exception e) {
//			// TODO: handle exception
//			throw new RazorpayException(e.getMessage());
//		}
//    }
	public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException, PayPalRESTException {

		Order order = orderService.findOrderById(orderId);

		try {
			// Lấy APIContext từ lớp PayPalClient
			APIContext apiContext = new PaypalClient().getAPIContext();

			double getTotalDiscountedPrice = order.getTotalDiscountedPrice();
			// Tạo thông tin thanh toán
			Amount amount = new Amount();
			amount.setCurrency("USD"); // Thay đổi thành "INR" nếu bạn muốn sử dụng đồng rupee Ấn Độ
			amount.setTotal(String.format("%.2f", getTotalDiscountedPrice)); // Định dạng tổng giá trị đơn hàng

			Transaction transaction = new Transaction();
			transaction.setDescription("Order Payment for Order ID: " + orderId);
			transaction.setAmount(amount);

			List<Transaction> transactions = new ArrayList<>();
			transactions.add(transaction);

			Payer payer = new Payer();
			payer.setPaymentMethod("paypal");

			// Cấu hình URL điều hướng sau khi thanh toán
			RedirectUrls redirectUrls = new RedirectUrls();
			redirectUrls.setCancelUrl("http://localhost:3000/payment/cancel/"+orderId);
			redirectUrls.setReturnUrl("http://localhost:3000/payment/" + orderId);

			// Tạo đối tượng thanh toán
			com.paypal.api.payments.Payment payment = new com.paypal.api.payments.Payment();
			payment.setIntent("sale");
			payment.setPayer(payer);
			payment.setTransactions(transactions);
			payment.setRedirectUrls(redirectUrls);

			// Tạo thanh toán trên PayPal
			com.paypal.api.payments.Payment createdPayment = payment.create(apiContext);

			// Lấy URL chuyển hướng người dùng đến PayPal để thực hiện thanh toán
			String approvalUrl = createdPayment.getLinks().stream().filter(link -> "approval_url".equals(link.getRel()))
					.findFirst().orElseThrow(() -> new RuntimeException("Approval URL not found")).getHref();

			PaymentLinkResponse res = new PaymentLinkResponse();
			res.setPayment_link_url(approvalUrl);

			return new ResponseEntity<>(res, HttpStatus.CREATED);

		} catch (PayPalRESTException e) {
			throw new PayPalRESTException(e.getMessage());
		}
	}

	@GetMapping("/payments")
//    public ResponseEntity<ApiResponse>redirect(@RequestParam(name="payment_id") String paymentId,
//    		@RequestParam(name="order_id") Long orderId) throws OrderException, RazorpayException {
//    	Order order = orderService.findOrderById(orderId);
//    	RazorpayClient razorpay= new RazorpayClient(apiKey, apiSecret);
////    	PayPalHttpClient paypal = new PayPalHttpClient();
//    	try {
//    		 
//			Payment payment = razorpay.payments.fetch(paymentId);
//			if (payment.get("status").equals("captured")) {
//				order.getPaymentDetails().setPaymentId(paymentId);
//				order.getPaymentDetails().setStatus("COMPLETED");
//				order.setOrderStatus("PLACED");
//				orderRepository.save(order);
//			}
//			ApiResponse res = new ApiResponse();
//			res.setMessage("your order get placed");
//			res.setStatus(true);
//			return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			throw new RazorpayException(e.getMessage());
//		}
//    }
	public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id") String paymentId,
			@RequestParam(name = "order_id") Long orderId, @RequestParam(name = "payer_id") String payerId)
			throws OrderException, PayPalRESTException {

// In ra các giá trị để kiểm tra chúng có đúng không
		System.out.println("payment_id: " + paymentId + ", order_id: " + orderId + ", payer_id: " + payerId);

// Kiểm tra các tham số đầu vào
		if (paymentId == null || orderId == null || payerId == null) {
			ApiResponse res = new ApiResponse();
			res.setMessage("Invalid request parameters");
			res.setStatus(false);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}

// Lấy thông tin đơn hàng
		Order order = orderService.findOrderById(orderId);
		if (order == null) {
			throw new OrderException("Order not found for id: " + orderId);
		}

		APIContext apiContext = new PaypalClient().getAPIContext();

		try {
			// Lấy thông tin thanh toán từ PayPal
			com.paypal.api.payments.Payment payment = com.paypal.api.payments.Payment.get(apiContext, paymentId);
			
			// Kiểm tra trạng thái đơn hàng
			if ("COMPLETED".equals(order.getPaymentDetails().getStatus())) {
		        ApiResponse res = new ApiResponse();
		        res.setMessage("Payment has already been completed.");
		        res.setStatus(true);
		        return new ResponseEntity<>(res, HttpStatus.OK);  // Trả về phản hồi rằng thanh toán đã hoàn tất
		    }
			
			// Kiểm tra trạng thái của thanh toán trước khi thực hiện thanh toán
		    if ("approved".equals(payment.getState())) {
		        ApiResponse res = new ApiResponse();
		        res.setMessage("Payment has already been completed.");
		        res.setStatus(true);
		        return new ResponseEntity<>(res, HttpStatus.OK);  // Trả về phản hồi rằng thanh toán đã hoàn tất
		    }
		    
			// Thực hiện thanh toán
			PaymentExecution paymentExecution = new PaymentExecution();
			paymentExecution.setPayerId(payerId);
			com.paypal.api.payments.Payment executedPayment = payment.execute(apiContext, paymentExecution);
			
			if ("approved".equals(executedPayment.getState())) {
// Cập nhật đơn hàng thành công
				order.getPaymentDetails().setPaymentId(paymentId);
				order.getPaymentDetails().setStatus("COMPLETED");
				order.setOrderStatus("PLACED");
				orderRepository.save(order);

				ApiResponse res = new ApiResponse();
				res.setMessage("Your order has been placed successfully.");
				res.setStatus(true);
				return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
			} else {
				throw new PayPalRESTException("Payment not approved");
			}

		} catch (PayPalRESTException e) {
			System.err.println("PayPal Error: " + e.getMessage()); // Log lỗi từ PayPal
			throw new PayPalRESTException("Payment execution failed: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Server Error: " + e.getMessage()); // Log lỗi chung
			ApiResponse res = new ApiResponse();
			res.setMessage("An error occurred while processing payment: " + e.getMessage());
			res.setStatus(false);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
