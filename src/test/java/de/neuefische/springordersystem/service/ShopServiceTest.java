package de.neuefische.springordersystem.service;

import de.neuefische.springordersystem.model.Order;
import de.neuefische.springordersystem.model.Product;
import de.neuefische.springordersystem.repo.OrderRepo;
import de.neuefische.springordersystem.repo.ProductRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShopServiceTest {
    OrderRepo orderRepo = mock (OrderRepo.class);
    ProductRepo productRepo = mock (ProductRepo.class);
    ShopService shopService = new ShopService(productRepo,orderRepo);
    @Test
    void getOrder(){
        //GIVEN
        int orderId = 1;
        Order order = new Order(1, List.of(new Product(1, "Apfel" ), new Product(2, "Banane")));

        //WHEN
        when(orderRepo.getOrder(orderId)).thenReturn(order);
        Order actual = shopService.getOrder(orderId);
        //THEN
        assertEquals(order, actual);
        verify(orderRepo).getOrder(orderId);
    }

    @Test
    void listOrder(){
        Order order = new Order(1, List.of(new Product(1, "Apfel" ), new Product(2, "Banane")));
        Order order2 = new Order(2, List.of(new Product(2, "Birne" ), new Product(4, "Ananas")));
        //WHEN
        when(orderRepo.listOrders()).thenReturn(List.of(order, order2));
        List<Order> actual = shopService.listOrders();
        //THEN
        assertEquals(List.of(order, order2), actual);
        verify(orderRepo).listOrders();
    }

    @Test
    void getProduct(){
        //given
        int id = 1;
        Product product = new Product(1, "Apfel");
        //WHEN
        when(productRepo.getProduct(id)).thenReturn(product);
        Product actual = shopService.getProduct(id);
        //THEN
        assertEquals(product, actual);
        verify(productRepo).getProduct(id);
    }

    @Test
    void listProducts(){
        //given
        Product product = new Product(1, "Apfel");
        Product product2 = new Product(2, "Banane");
        //WHEN
        when(productRepo.listProducts()).thenReturn(List.of(product, product2));
        List <Product> actual = shopService.listProducts();
        //THEN
        assertEquals(List.of(product, product2), actual);
        verify(productRepo).listProducts();
    }

    @Test
    void addOrder() {
        //given
        int orderId = 1;

        Product product = new Product(1, "Apfel");
        Product product2 = new Product(2, "Banane");

        List<Integer> productIds = new ArrayList<>(List.of(
                1,
                2
        ));
        Order order = new Order(orderId, List.of(
                product,
                product2
        ));
        //when
        when(orderRepo.addOrder(order)).thenReturn(order);
        when(productRepo.getProduct(product.getId())).thenReturn(product);
        when(productRepo.getProduct(product2.getId())).thenReturn(product2);
        shopService.addOrder(orderId,List.of(1, 2));

        verify(orderRepo).addOrder(order);
        verify(productRepo).getProduct(product.getId());
    }
}