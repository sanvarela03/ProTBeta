package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.Enum.EPaymentMethod;
import com.example.springbootjwtauthentication.model.Enum.EStatus;
import com.example.springbootjwtauthentication.model.PaymentMethod;
import com.example.springbootjwtauthentication.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    Optional<PaymentMethod> findByName(EPaymentMethod name);
}
