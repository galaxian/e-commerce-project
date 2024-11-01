package com.example.member.member.application.port.in;

import com.example.member.member.application.dto.req.SignUpReqDto;

public interface SignUpUseCase {

	Long signUp(SignUpReqDto reqDto);
}
