package com.example.payment.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.member.infrastructure.entity.MemberEntity;
import com.example.order.infrastructure.entity.OrderEntity;
import com.example.payment.common.entity.BaseEntity;
import com.example.payment.domain.Payment;
import com.example.payment.domain.PaymentMethod;
import com.example.payment.domain.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "payment_amount", nullable = false)
	private BigDecimal paymentAmount;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status", nullable = false)
	private PaymentStatus paymentStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method")
	private PaymentMethod paymentMethod;

	@Column(name = "payment_date")
	private LocalDateTime paymentDate;

	@OneToOne
	@JoinColumn(name = "order_id")
	private OrderEntity orderEntity;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private MemberEntity memberEntity;

	public PaymentEntity(Long id, BigDecimal paymentAmount, PaymentStatus paymentStatus, PaymentMethod paymentMethod,
		LocalDateTime paymentDate, OrderEntity orderEntity, MemberEntity memberEntity) {
		this.id = id;
		this.paymentAmount = paymentAmount;
		this.paymentStatus = paymentStatus;
		this.paymentMethod = paymentMethod;
		this.paymentDate = paymentDate;
		this.orderEntity = orderEntity;
		this.memberEntity = memberEntity;
	}

	public static PaymentEntity from(Payment payment) {
		return new PaymentEntity(payment.getId(), payment.getPaymentAmount(), payment.getPaymentStatus(),
			payment.getPaymentMethod(), payment.getPaymentDate(), OrderEntity.from(payment.getOrder()),
			MemberEntity.from(payment.getMember()));
	}

	public Payment toPaymentDomain() {
		return new Payment(id, paymentAmount, paymentStatus, paymentMethod, paymentDate, orderEntity.toOrderDomain(),
			memberEntity.toMemberDomain(), getCreatedAt(), getUpdatedAt());
	}
}
