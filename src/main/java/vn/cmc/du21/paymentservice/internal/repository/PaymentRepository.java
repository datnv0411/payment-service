package vn.cmc.du21.paymentservice.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.paymentservice.internal.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
