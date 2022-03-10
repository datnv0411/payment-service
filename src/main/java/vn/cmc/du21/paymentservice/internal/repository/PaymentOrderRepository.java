package vn.cmc.du21.paymentservice.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.paymentservice.internal.entity.PaymentOrder;
import vn.cmc.du21.paymentservice.internal.entity.PaymentOrderId;

@Repository
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, PaymentOrderId> {
}
