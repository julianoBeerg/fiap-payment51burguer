package com.fiap.burguer.api;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.CheckOut;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/checkout")
public interface CheckoutApi {

    @GetMapping("/{id}")
    @Operation(summary = "Consulta checkout do pedido por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta de checkout do Pedido, realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Checkout não encontrado para este ID!"),
            @ApiResponse(responseCode = "404", description = "Checkout não encontrado!")
    })
    public ResponseEntity<?> getCheckoutById(@Parameter(description = "ID do checkout") @PathVariable("id") int id);

    @PostMapping("/create/{id}/{status_order}")
    @Operation(summary = "Cria um checkout e processa o pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Checkout e pagamento processados com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao processar pagamento!"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado!")
    })
    public ResponseEntity<?> createCheckout(@Parameter(description = "ID do checkout") @PathVariable("id") int id,
                                            @Parameter(description = "Status do pagamento") @PathVariable("status_order") StatusOrder statusOrder);
}