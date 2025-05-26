package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.requests.AddItemToCartRequest;
import com.codewithmosh.store.dtos.requests.UpdateCartItemRequest;
import com.codewithmosh.store.dtos.responses.CartItemDto;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.dtos.responses.CartDto;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import com.codewithmosh.store.mappers.CartMapper;
import com.codewithmosh.store.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
@Tag(name="Carts")
public class CartController {
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Operation(summary = "Create a new cart", description = "Creates a new shopping cart and returns its details.")
    @ApiResponse(responseCode = "201", description = "Cart created successfully")
    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @Operation(summary = "Add item to cart", description = "Adds a product to an existing cart by cart ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item added to cart"),
            @ApiResponse(responseCode = "404", description = "Cart or product not found")
    })
    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(
            @PathVariable UUID cartId,
            @RequestBody AddItemToCartRequest request) {
        var cartItemDto = cartService.addToCart(cartId, request.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @Operation(summary = "Get cart details", description = "Retrieves the full details of a cart by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/{cartId}")
    public CartDto getCart(@PathVariable UUID cartId) {
        return cartService.getCart(cartId);
    }

    @Operation(summary = "Update cart item quantity", description = "Updates the quantity of a specific item in a cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item updated"),
            @ApiResponse(responseCode = "404", description = "Cart or product not found")
    })
    @PutMapping("/{cartId}/items/{productId}")
    public CartItemDto updateItem(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("productId") Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
    ) {
        return cartService.updateItem(cartId, productId, request.getQuantity());
    }

    @Operation(summary = "Remove item from cart", description = "Removes a specific product from the cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item removed successfully"),
            @ApiResponse(responseCode = "404", description = "Cart or product not found")
    })
    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> removeItem(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("productId") Long productId
    ) {
        cartService.removeItem(cartId, productId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Clear cart", description = "Removes all items from the specified cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cart cleared"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable UUID cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }
}
