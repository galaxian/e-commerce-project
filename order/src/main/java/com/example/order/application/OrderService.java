package com.example.order.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.member.application.port.out.MemberRepository;
import com.example.member.domain.Member;
import com.example.order.application.dto.req.CreateOrderReqDto;
import com.example.order.application.port.in.CancelOrderUseCase;
import com.example.order.application.port.in.CreateOrderUseCase;
import com.example.order.application.port.out.OrderItemRepository;
import com.example.order.application.port.out.OrderRepository;
import com.example.order.common.exception.NotFoundException;
import com.example.order.domain.Address;
import com.example.order.domain.Order;
import com.example.order.domain.OrderItem;
import com.example.product.application.port.out.ProductRepository;
import com.example.product.domain.Product;

@Service
@Transactional
public class OrderService implements CreateOrderUseCase, CancelOrderUseCase {

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

	@Override
	public void cancelOrder(Long orderId, Long memberId) {
		Order findOrder = orderRepository.findByOrderIdAndMemberId(orderId, memberId).orElseThrow(
			() -> new NotFoundException("주문을 찾을 수 없습니다.")
		);

		findOrder.cancel();

		orderRepository.save(findOrder);

		List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId);
		for (OrderItem orderItem : orderItemList) {
			Product product = orderItem.getProduct();
			product.increaseStock(orderItem.getQuantity());
			productRepository.save(product);
		}
	}

	private Member findMemberById(Long userId) {
		return memberRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
	}

	private Map<Long, Product> fetchProductsByIds(List<CreateOrderReqDto> createOrderReqDtos) {
		List<Long> productIds = createOrderReqDtos.stream()
			.map(CreateOrderReqDto::productId)
			.collect(Collectors.toList());

		Map<Long, Product> productMap = productRepository.findAllById(productIds).stream()
			.collect(Collectors.toMap(Product::getId, product -> product));

		List<Long> missingProductIds = productIds.stream()
			.filter(id -> !productMap.containsKey(id))
			.toList();

		if (!missingProductIds.isEmpty()) {
			throw new NotFoundException("상품을 찾을 수 없습니다. 대상 상품 ID: " + missingProductIds);
		}

		return productMap;
	}

	private BigDecimal calculateTotalAmount(List<CreateOrderReqDto> createOrderReqDtos, Map<Long, Product> productMap) {
		return createOrderReqDtos.stream()
			.map(dto -> {
				Product product = productMap.get(dto.productId());

				product.decreaseStock(dto.quantity());
				productRepository.save(product);

				return product.getProductPrice().multiply(BigDecimal.valueOf(dto.quantity()));
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

			OrderItem orderItem = new OrderItem(product.getProductPrice(), dto.quantity(), order, product);
			orderItemRepository.save(orderItem);
		}
	}
}
