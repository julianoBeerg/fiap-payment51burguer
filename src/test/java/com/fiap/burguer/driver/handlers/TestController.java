package com.fiap.burguer.driver.handlers;

import com.fiap.burguer.core.application.Exception.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/impossible-checkout")
    public void throwImpossibleToCheckoutException() {
        throw new ImpossibleToCheckoutException("Não foi possível realizar o checkout");
    }

    @GetMapping("/unauthorized")
    public void throwUnauthorizedException() {
        throw new RequestUnauthorized("Acesso não autorizado");
    }

    @GetMapping("/not-found")
    public void throwResourceNotFoundException() {
        throw new ResourceNotFoundException("Recurso não encontrado");
    }

    @GetMapping("/request-exception")
    public void throwRequestException() {
        throw new RequestException("Erro de requisição");
    }

    @GetMapping("/global-exception")
    public void throwGlobalException() {
        throw new RuntimeException("Erro genérico");
    }

    @GetMapping("/not-acceptable")
    public void throwNotAcceptableException() {
        throw new ResourceNotAcceptableException("Recurso não aceitável");
    }
}
