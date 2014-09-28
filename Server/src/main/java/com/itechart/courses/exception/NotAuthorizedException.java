package com.itechart.courses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED,
reason = "User not authorized or does not have permission to view the contents of the page")
public class NotAuthorizedException extends RuntimeException {}
