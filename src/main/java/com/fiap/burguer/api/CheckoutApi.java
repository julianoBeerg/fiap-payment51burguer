package com.fiap.burguer.api;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.driver.dto.CheckoutRequest;
import com.fiap.burguer.driver.dto.CheckoutResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/checkout")
public interface CheckoutApi {

    @GetMapping("/{order_id}")
    @Operation(summary = "Consulta checkout do pedido por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta de checkout do Pedido, realizada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Checkout não encontrado!")
    })
    ResponseEntity<CheckoutResponse> getCheckoutByOrderId(
            @Parameter(description = "ID do pedido (order_id)") @PathVariable("order_id") int orderId);

    @GetMapping("/search")
    @Operation(summary = "Busca checkouts com filtros como orderId, status, clientId e cpf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta de checkouts realizada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Nenhum checkout encontrado!")
    })
    ResponseEntity<?> searchCheckouts(
            @Parameter(description = "ID do pedido") @RequestParam(required = false) Integer orderId,
            @Parameter(description = "Status do pagamento") @RequestParam(required = false) StatusOrder status,
            @Parameter(description = "ID do cliente") @RequestParam(required = false) Integer clientId,
            @Parameter(description = "CPF do cliente") @RequestParam(required = false) String cpf,
            @RequestHeader("Authorization") String authorizationHeader);

    @PostMapping("/create")
    @Operation(summary = "Cria um checkout e processa o pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Checkout e pagamento processados com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao processar pagamento!"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado!")
    })
    ResponseEntity<?> createCheckout(
            @RequestBody @Valid CheckoutRequest checkoutRequest,
            @RequestHeader("Authorization") String authorizationHeader
         );

    @PutMapping("/update-status/{order_id}")
    @Operation(summary = "Atualiza o status de um checkout existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar status!"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado!")
    })
    public ResponseEntity<?> updateCheckoutStatus(
            @Parameter(description = "ID do pedido (order_id)") @PathVariable("order_id") int orderId,
            @Parameter(description = "Novo status do pagamento") @RequestParam StatusOrder newStatus,
            @RequestHeader("Authorization") String authorizationHeader);
}

