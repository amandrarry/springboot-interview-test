package com.adobe.bookstore.infrastructure.repositories;

import com.adobe.bookstore.domain.entities.Order;
import com.adobe.bookstore.domain.entities.OrderItem;
import com.adobe.bookstore.domain.repositories.OrderRepository;
import com.adobe.bookstore.infrastructure.DAOs.OrderDAO;
import com.adobe.bookstore.infrastructure.DAOs.OrderItemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
@Repository
public class JPAOrderRepository implements OrderRepository {

    JPAOrderBridge jpaOrderBridge;
    JPAOrderItemBridge jpaOrderItemBridge;

    @Autowired
    public JPAOrderRepository(JPAOrderBridge jpaOrderBridge,
                              JPAOrderItemBridge jpaOrderItemBridge) {
        this.jpaOrderBridge = jpaOrderBridge;
        this.jpaOrderItemBridge = jpaOrderItemBridge;
    }

    /***
     * Returns a of all Orders and its related OrderItems.
     * @return List of all Orders and its related OrderItems.
     */
    @Override
    public List<Order> getAllOrders() {
        try {
            List<OrderDAO> rawOrders = jpaOrderBridge.findAll();
            List<Order> orders = new ArrayList<>();
            for (OrderDAO orderDAO : rawOrders) {
                List<OrderItemDAO> listOfOrderDAOsRelatedToOrder = jpaOrderItemBridge.findByOrderId(orderDAO.getId());
                orders.add(orderDAO.toOrder(listOfOrderDAOsRelatedToOrder));
            }
            return orders;
        } catch (Exception e) {
            throw new JpaObjectRetrievalFailureException((EntityNotFoundException) e);
        }
    }

    /***
     * Returns an Order with its associated OrderItems.
     * @param id: id of the Order.
     * @return Order with its associated OrderItems.
     */
    @Override
    public Order getOrderById(String id) {
        try {
            if (jpaOrderBridge.findById(id).isPresent()) {
                List<OrderItemDAO> listOfOrderDAOsRelatedToOrder = jpaOrderItemBridge.findByOrderId(id);
                return jpaOrderBridge.findById(id).get().toOrder(listOfOrderDAOsRelatedToOrder);
            }
            else return null;
        } catch (Exception e) {
            throw new JpaSystemException((RuntimeException) e);
        }
    }

    /***
     * Saves the given order and all its related OrderItems.
     * @param order: Order to be added.
     */
    @Override
    public void saveOrder(Order order) {
        try {
            jpaOrderBridge.save(new OrderDAO(order));
            for (OrderItem orderItem : order.getOrderItemList()) {
                jpaOrderItemBridge.save(new OrderItemDAO(orderItem));
            }
        } catch (Exception e) {
            throw new JpaSystemException((RuntimeException) e);
        }
    }

    /***
     * Deletes the given order and all its related OrderItems.
     * @param id: Id of the order to delete.
     */
    @Override
    public void deleteOrder(String id) {
        try {
            List<OrderItemDAO> listOfOrderDAOsRelatedToOrder = jpaOrderItemBridge.findByOrderId(id);
            jpaOrderItemBridge.deleteAll(listOfOrderDAOsRelatedToOrder);
            jpaOrderBridge.deleteById(id);
        } catch (Exception e) {
            throw new JpaSystemException((RuntimeException) e);
        }
    }
}