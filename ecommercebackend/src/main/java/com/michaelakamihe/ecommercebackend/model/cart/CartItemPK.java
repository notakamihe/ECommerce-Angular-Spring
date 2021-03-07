package com.michaelakamihe.ecommercebackend.model.cart;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.michaelakamihe.ecommercebackend.model.Product;
import com.michaelakamihe.ecommercebackend.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartItemPK implements Serializable {
    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public CartItemPK () {

    }
    
    public CartItemPK(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        CartItemPK that = (CartItemPK) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(user, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, product);
    }
}
