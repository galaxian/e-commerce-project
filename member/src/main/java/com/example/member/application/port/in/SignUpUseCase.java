package com.example.member.application.port.in;

import com.example.member.application.dto.req.SignUpReqDto;

public interface SignUpUseCase {

	Long signUp(SignUpReqDto reqDto);
}
