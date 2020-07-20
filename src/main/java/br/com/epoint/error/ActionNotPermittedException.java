package br.com.epoint.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ActionNotPermittedException extends RuntimeException {
    public ActionNotPermittedException(String message) {
        super(message);
    }
}
