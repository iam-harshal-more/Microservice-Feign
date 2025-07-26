package org.example.service;

import org.example.client.UserServiceClient;
import org.example.model.Order;
import org.example.model.User;
import org.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        // Enrich orders with user information
        orders.forEach(this::enrichOrderWithUserInfo);
        return orders;
    }

    public Optional<Order> getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        order.ifPresent(this::enrichOrderWithUserInfo);
        return order;
    }

    public List<Order> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        orders.forEach(this::enrichOrderWithUserInfo);
        return orders;
    }

    public Order createOrder(Order order) {
        // Validate if user exists by calling user service
        User user = userServiceClient.getUserById(order.getUserId());
        if (user == null || user.getId() == null) {
            throw new RuntimeException("User not found with id: " + order.getUserId());
        }

        Order savedOrder = orderRepository.save(order);
        enrichOrderWithUserInfo(savedOrder);
        return savedOrder;
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        order.setProductName(orderDetails.getProductName());
        order.setQuantity(orderDetails.getQuantity());
        order.setPrice(orderDetails.getPrice());
        order.setStatus(orderDetails.getStatus());

        Order updatedOrder = orderRepository.save(order);
        enrichOrderWithUserInfo(updatedOrder);
        return updatedOrder;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private void enrichOrderWithUserInfo(Order order) {
        try {
            User user = userServiceClient.getUserById(order.getUserId());
            order.setUser(user);
        } catch (Exception e) {
            // Handle gracefully - user service might be down
            User fallbackUser = new User();
            fallbackUser.setId(order.getUserId());
            fallbackUser.setName("Unknown User");
            fallbackUser.setEmail("unknown@example.com");
            order.setUser(fallbackUser);
        }
    }
}
