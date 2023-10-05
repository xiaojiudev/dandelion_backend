package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepo extends JpaRepository<Address, Long> {
    Optional<Address> findByIsDefaultAndUser_Id(Boolean isDefault, Long id);

    Optional<Address> findByAddressLine1AndUser_Id(String addressLine1, Long id);

    Optional<Address> findByIdAndUser_Id(Long id, Long id1);

    List<Address> findByUser_Id(Long id);

    boolean existsByIsDefaultAndUser_Id(Boolean isDefault, Long id);


}
