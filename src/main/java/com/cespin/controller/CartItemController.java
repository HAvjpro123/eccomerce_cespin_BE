package com.cespin.controller;

import com.cespin.exception.CartItemException;
import com.cespin.exception.UserException;
import com.cespin.model.CartItem;
import com.cespin.model.User;
import com.cespin.service.CartItemService;
import com.cespin.service.UserService;
import com.cespin.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;
    
    @Autowired
    private UserService userService;

    @DeleteMapping("/{cartItemId}")
    @Operation(description = "Remove Cart Item From Cart")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Delete Item")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,
    		@RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
    	User user=userService.findUserProfileByJwt(jwt);
    	cartItemService.removeCartItem(user.getId(), cartItemId);
    	
    	ApiResponse res = new ApiResponse();
    	res.setMessage("Deleted item from cart");
    	res.setStatus(true);
    	return new ResponseEntity<>(res, HttpStatus.OK);
    	
    }
    
    
    @PutMapping("/{cartItemId}")
    @Operation(description = "Update Item To Cart")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem,
    		@PathVariable Long cartItemId,
    		@RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
    	User user=userService.findUserProfileByJwt(jwt);
    	
    	CartItem updatedCartItem=cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
    	
    	return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }
    
    
    
}
