package br.com.alurafood.payments.controller;

import br.com.alurafood.payments.dto.PaymentDetailingDto;
import br.com.alurafood.payments.dto.PaymentRequestDto;
import br.com.alurafood.payments.service.PaymentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/port")
    public String informApplicationInstancePort(@Value("${local.server.port}") String applicationPort) {
        return String.format("Requested on port: %s", applicationPort);
    }

    @GetMapping
    public ResponseEntity<Page<PaymentDetailingDto>> listPayments(@PageableDefault(size = 10, sort = {"id"}) Pageable pageable) {
        var payments = paymentService.getAllPayments(pageable);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity listPaymentById(@PathVariable Long id) {
        var payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    @Transactional
    public ResponseEntity createPayment(@RequestBody @Valid PaymentRequestDto paymentData, UriComponentsBuilder uriBuilder) {
        var payment = paymentService.createPayment(paymentData);
        var uri = uriBuilder.path("api/v1/payments/{id}").build(payment.id());
        return ResponseEntity.created(uri).body(payment);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updatePayment(@PathVariable Long id, @RequestBody @Valid PaymentRequestDto paymentData) {
        var updatedPayment = paymentService.updatePayment(id, paymentData);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

}
