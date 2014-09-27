package com.itechart.courses.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect input data")
public class IllegalInputDataException extends RuntimeException {}
