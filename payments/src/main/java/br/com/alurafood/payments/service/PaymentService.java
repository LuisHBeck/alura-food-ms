package br.com.alurafood.payments.service;

import br.com.alurafood.payments.dto.PaymentDetailingDto;
import br.com.alurafood.payments.dto.PaymentRequestDto;
import br.com.alurafood.payments.model.Payment;
import br.com.alurafood.payments.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Page<PaymentDetailingDto> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(PaymentDetailingDto::new);
    }

    public PaymentDetailingDto getPaymentById(Long id) {
        var payment = paymentRepository.getReferenceById(id);
        return new PaymentDetailingDto(payment);
    }

    @Transactional
    public PaymentDetailingDto createPayment(PaymentRequestDto data) {
        var payment = new Payment(data);
        payment.setPaymentCreated();

        paymentRepository.save(payment);

        return new PaymentDetailingDto(payment);
    }

    @Transactional
    public PaymentDetailingDto updatePayment(Long id, PaymentRequestDto data) {
        var payment = new Payment(data);
        payment.setId(id);
        var updatedPayment = paymentRepository.save(payment);
        return new PaymentDetailingDto(updatedPayment);
    }

    @Transactional
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
