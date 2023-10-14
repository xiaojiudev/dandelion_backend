package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.ShopOrder;
import com.dandelion.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<ShopOrder, Long> {
    List<ShopOrder> findByUser(User user);


}
