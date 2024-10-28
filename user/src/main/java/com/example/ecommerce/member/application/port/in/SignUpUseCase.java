package com.example.ecommerce.member.application.port.in;

import com.example.ecommerce.member.application.dto.req.SignUpReqDto;

public interface SignUpUseCase {

	Long signUp(SignUpReqDto reqDto);
}
