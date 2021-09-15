package com.photographer.app.mapper;

import com.photographer.app.model.Order;
import com.photographer.app.model.OrderItem;

import java.util.List;

public interface OrderMapper {
    List<Order> getAllOrders();
    List<Order> getOrdersByUserId(long id);
    List<OrderItem> getAllOrderItemsByOrderID(long id);
    Order getOrderById(long id);

}
