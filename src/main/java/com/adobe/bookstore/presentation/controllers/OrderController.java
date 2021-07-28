package com.adobe.bookstore.presentation.controllers;

import com.adobe.bookstore.application.requests.RetrieveOrdersRequest;
import com.adobe.bookstore.application.responses.CreateOrderResponse;
import com.adobe.bookstore.application.responses.RetrieveOrdersResponse;
import com.adobe.bookstore.application.use_cases.*;
import com.adobe.bookstore.application.requests.CreateOrderRequest;
import com.adobe.bookstore.domain.entities.Order;
import com.adobe.bookstore.domain.services.LoggerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.CDL;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;
    private final RetrieveOrdersUseCase retrieveOrdersUseCase;
    private final UpdateStockAfterOrderUseCase updateStockAfterOrderUseCase;
    private final LoggerService loggerService;

    @Autowired
    public OrderController(CreateOrderUseCase createOrderUseCase, RetrieveOrdersUseCase retrieveOrdersUseCase,
                           UpdateStockAfterOrderUseCase updateStockAfterOrderUseCase, LoggerService loggerService) {
        this.createOrderUseCase = createOrderUseCase;
        this.retrieveOrdersUseCase = retrieveOrdersUseCase;
        this.updateStockAfterOrderUseCase = updateStockAfterOrderUseCase;
        this.loggerService = loggerService;
    }

    @GetMapping(value="/getOrders", produces = {"text/csv", "application/json"})
    @Operation(summary = "Retrieve all orders",
            description = "This endpoint returns all orders registered by the system.")
    public ResponseEntity<String> retrieveOrders(@Parameter(name="returnType", description = "Can be either CSV or JSON." +
            " Indicates the final format of the result returned by the endpoint.", required=true) String returnType) {
        RetrieveOrdersRequest request;
        try {
            request = new RetrieveOrdersRequest(returnType);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Please provide a valid returnType parameter.");
        }
        try {
            RetrieveOrdersResponse ordersResponse = retrieveOrdersUseCase.retrieveAllOrders();
            HashMap<String, Object> result = new HashMap<>();
            ArrayList<Object> listOfOrdersSerialized = new ArrayList<>();
            for (Order order : ordersResponse.getListOfOrders()) {
                listOfOrdersSerialized.add(order.toMap());
            }
            result.put("orders", listOfOrdersSerialized);
            JSONObject ordersAsJSON = new JSONObject(result);
            if (request.getFormat().equals("JSON")) {
                return ResponseEntity.ok().body(ordersAsJSON.toString());
            }
            else {
                return ResponseEntity.ok().body(CDL.toString(ordersAsJSON.getJSONArray("orders")));
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/newOrder", consumes = {"application/json"})
    @Operation(summary = "Create new order", description = "Endpoint to create a new order in the system. The body" +
            "of the request must be a JSON with the same format as stock.json (a list of books with parameters" +
            "id, name and quantity).")
    public ResponseEntity<String> createOrder(
            @RequestBody String newOrder
    ) {
        CreateOrderRequest createOrderRequest;
        ResponseEntity<String> response;
        try {
            createOrderRequest = new CreateOrderRequest(newOrder);
        } catch (Exception e) {
            return new ResponseEntity<>("Please provide a valid new order.", HttpStatus.BAD_REQUEST);
        }
        try {
            CreateOrderResponse orderResponse = createOrderUseCase.createOrder(createOrderRequest);
            response = new ResponseEntity<>(orderResponse.getOrderId(), HttpStatus.OK);
            try {
                this.updateStockAfterOrderUseCase.updateStock(createOrderRequest);
            } catch (Exception e) {
                loggerService.log("Error updating stock. Inconsistent stock when adding order " + orderResponse.getOrderId());
            }
        } catch (BookNotFoundError e) {
            response = new ResponseEntity<>(e.getErrorMessage(), HttpStatus.NOT_FOUND);
        } catch (InvalidStockError e) {
            response = new ResponseEntity<>(e.getErrorMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            response = new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

}
