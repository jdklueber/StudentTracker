package com.nofussprogramming.studentracker.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "A database error has occurred.")
public class DatabaseErrorException extends RuntimeException {
}
