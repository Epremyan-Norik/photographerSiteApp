package com.photographer.app.mapper;

import com.photographer.app.modelsNew.CartItem;

import java.util.List;

public interface CartItemMapper {

    CartItem getCartItemById(long id);
    List<CartItem> getUserCartItems(long en_id);
    List<CartItem> getGuestCartItems(long guest_id);
    int insertCartItem(CartItem cartItem);
    int updateCartItem(CartItem cartItem);
    int deleteCartItemById(long id);
    int deleteAllUserCartItems(long en_id);
    int deleteAllGuestCartItems(long guest_id);


}
