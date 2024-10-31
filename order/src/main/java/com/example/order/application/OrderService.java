package com.example.order.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.member.member.application.port.out.MemberRepository;
import com.example.member.member.domain.Member;
import com.example.order.application.dto.req.CreateOrderReqDto;
import com.example.order.application.port.in.CreateOrderUseCase;
import com.example.order.application.port.out.OrderItemRepository;
import com.example.order.application.port.out.OrderRepository;
import com.example.order.domain.Address;
import com.example.order.domain.Order;
import com.example.order.domain.OrderItem;
import com.example.product.application.port.out.ProductRepository;
import com.example.product.common.exception.ProductNotFoundException;
import com.example.product.domain.Product;

@Service
@Transactional
public class OrderService implements CreateOrderUseCase {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;

	public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
		ProductRepository productRepository, MemberRepository memberRepository) {
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.productRepository = productRepository;
		this.memberRepository = memberRepository;
	}

	@Override
	public Long createOrder(List<CreateOrderReqDto> createOrderReqDtos, Long userId) {
		Member member = findMemberById(userId);
		Map<Long, Product> productMap = fetchProductsByIds(createOrderReqDtos);
		BigDecimal totalAmount = calculateTotalAmount(createOrderReqDtos, productMap);

		Order order = createAndSaveOrder(member, totalAmount);
		createAndSaveOrderItems(createOrderReqDtos, order, productMap);

		return order.getId();
	}

	private Member findMemberById(Long userId) {
		return memberRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("Member not found with ID: " + userId));
	}

	private Map<Long, Product> fetchProductsByIds(List<CreateOrderReqDto> createOrderReqDtos) {
		List<Long> productIds = createOrderReqDtos.stream()
			.map(CreateOrderReqDto::productId)
			.collect(Collectors.toList());

		return productRepository.findAllById(productIds).stream()
			.collect(Collectors.toMap(Product::getId, product -> product));
	}

	private BigDecimal calculateTotalAmount(List<CreateOrderReqDto> createOrderReqDtos, Map<Long, Product> productMap) {
		return createOrderReqDtos.stream()
			.map(dto -> {
				Product product = productMap.get(dto.productId());
				if (product == null) throw new ProductNotFoundException("Product not found with ID: " + dto.productId());
				return product.getPrice().multiply(BigDecimal.valueOf(dto.quantity()));
			})
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private Order createAndSaveOrder(Member member, BigDecimal totalAmount) {
		Order order = new Order(totalAmount,
			new Address(member.getAddress().getState(), member.getAddress().getStreet(), member.getAddress().getCity(),
				member.getAddress().getZipCode()), member);
		return orderRepository.save(order);
	}

	private void createAndSaveOrderItems(List<CreateOrderReqDto> createOrderReqDtos, Order order, Map<Long, Product> productMap) {
		for (CreateOrderReqDto dto : createOrderReqDtos) {
			Product product = productMap.get(dto.productId());
			if (product == null) throw new ProductNotFoundException("Product not found with ID: " + dto.productId());

			OrderItem orderItem = new OrderItem(product.getPrice(), dto.quantity(), order, product);
			orderItemRepository.save(orderItem);
		}
	}
}
