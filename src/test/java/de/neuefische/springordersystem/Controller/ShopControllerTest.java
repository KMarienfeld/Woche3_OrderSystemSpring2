package de.neuefische.springordersystem.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

class ShopControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllOrders_thenReturnEmptyOrderList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }


    @Test
    void whenAddOrderWithProductIds_then200OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("[1]"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {
                         "products": [
                             {
                                 "id": 1,
                                 "name": "Apfel"
                             }
                         ]
                     }
""")).andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void getAllProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                     [{
                            "id": 3,
                            "name": "Zitrone"
                        },
                        {
                            "id": 4,
                            "name": "Mandarine"
                        },
                        {
                            "id": 1,
                            "name": "Apfel"
                        },
                        {
                            "id": 2,
                            "name": "Banane"
                        }]
"""));
    }
    @Test
    void getOneProductById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/2"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                { 
                            "id": 2,
                            "name": "Banane"
                        }
"""));
    }
    @Test
    void getOneOrderById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1,2]"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
              {
                    "id": 1,
                   "products": 
                    [ 
                {
                            "id": 1,
                            "name": "Apfel"
                },
                {
                            "id": 2,
                            "name": "Banane"
                }]}

""")
        );

    }
}