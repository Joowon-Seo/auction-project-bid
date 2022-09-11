package com.sjw.bid.dto;

import com.sjw.bid.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponse {

	private ErrorCode errorCode;
	private String message;

}
