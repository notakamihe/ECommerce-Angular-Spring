package com.michaelakamihe.ecommercebackend.controller;

import com.michaelakamihe.ecommercebackend.model.Product;
import com.michaelakamihe.ecommercebackend.model.User;
import com.michaelakamihe.ecommercebackend.model.cart.CartItem;
import com.michaelakamihe.ecommercebackend.model.cart.CartItemPK;
import com.michaelakamihe.ecommercebackend.service.CartItemService;
import com.michaelakamihe.ecommercebackend.service.ProductService;
import com.michaelakamihe.ecommercebackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class APIController {
    private final UserService userService;
    private final ProductService productService;
    private final CartItemService cartItemService;

    public APIController(UserService userService, ProductService productService, CartItemService cartItemService) {
        this.userService = userService;
        this.productService = productService;
        this.cartItemService = cartItemService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers () {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser (@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser (@PathVariable("id") Long id, @RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
    }

    @GetMapping("/users/{id}/cart")
    public ResponseEntity<List<CartItem>> getUserCart (@PathVariable("id") Long id) {
        System.out.println(userService.getUser(id).getCartItems().size());
        return new ResponseEntity<>(userService.getUser(id).getCartItems(), HttpStatus.OK);
    }

    @PostMapping("/users/{id}/cart/add/{productId}")
    public ResponseEntity<User> addToUserCart (@PathVariable("id") Long id,
                                               @PathVariable("productId") Long productId) {
        User user = userService.getUser(id);
        Product product = productService.getProduct(productId);

        CartItem cartItem = new CartItem(user, product, 1);
        cartItemService.addCartItem(cartItem);

        return new ResponseEntity<>(userService.getUser(id), HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}/cart/update/{productId}")
    public ResponseEntity<User> updateCartItem (@PathVariable("id") Long id,
                                                @PathVariable("productId") Long productId,
                                                @RequestBody CartItem cartItem) {
        User user = userService.getUser(id);
        Product product = productService.getProduct(productId);

        cartItem.setPk(new CartItemPK(user, product));
        cartItemService.updateCartItem(cartItem);

        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}/cart/remove/{productId}")
    public ResponseEntity<User> removeFromUserCart (@PathVariable("id") Long id,
                                                    @PathVariable("productId") Long productId) {
        cartItemService.deleteCartItem(id, productId);

        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts () {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct (@PathVariable("id") Long id) {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct (@RequestBody Product product) {
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct (@PathVariable("id") Long id, @RequestBody Product product) {
        return new ResponseEntity<>(productService.updateProduct(id, product), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct (@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/cart-items")
    public ResponseEntity<List<CartItem>> getCartItems () {
        return ResponseEntity.ok(cartItemService.getCartItems());
    }

    @GetMapping("/cart-items/{id}/{productId}")
    public ResponseEntity<CartItem> getCartItem (@PathVariable("id") Long id,
                                                 @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(cartItemService.getCartItem(id, productId));
    }
}
