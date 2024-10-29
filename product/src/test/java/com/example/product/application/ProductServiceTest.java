package com.example.product.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.product.application.dto.res.FindAllProductResDto;
import com.example.product.application.port.out.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

	@DisplayName("상품 목록이 없는 경우 빈 리스트 반환")
	@Test
	void findAllProductsReturnEmptyList() {
	    //given
		given(productRepository.findAll())
			.willReturn(Collections.emptyList());

	    //when
		List<FindAllProductResDto> result = productService.findAllProduct();

		//then
		assertThat(result).isEmpty();
		then(productRepository).should(times(1)).findAll();

	}

}
