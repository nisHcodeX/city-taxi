package com.citytaxi.city_taxi.services.impl;

import com.citytaxi.city_taxi.exceptions.NotFoundException;
import com.citytaxi.city_taxi.models.dtos.payment.request.PaymentCreateRequest;
import com.citytaxi.city_taxi.models.dtos.payment.response.PaymentCreateResponse;
import com.citytaxi.city_taxi.models.dtos.payment.response.PaymentGetResponse;
import com.citytaxi.city_taxi.models.entities.Booking;
import com.citytaxi.city_taxi.models.entities.Payment;
import com.citytaxi.city_taxi.models.enums.EPaymentType;
import com.citytaxi.city_taxi.repositories.BookingRepository;
import com.citytaxi.city_taxi.repositories.PaymentRepository;
import com.citytaxi.city_taxi.services.IPaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class PaymentService implements IPaymentService {
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    /**
     * Creates new payments based on the provided payload.
     *
     * @param payload A list of PaymentCreateRequest objects containing the details of the payments to be created.
     * @return A list of PaymentCreateResponse objects containing the created payments.
     * @throws NotFoundException if the booking with the specified ID is not found.
     */
    @Override
    @Transactional
    public List<PaymentCreateResponse> create(List<PaymentCreateRequest> payload) throws NotFoundException {
        final List<PaymentCreateResponse> response = new ArrayList<>();

        for (PaymentCreateRequest request : payload) {
            final Booking booking = bookingRepository.findById(request.getBookingId()).orElseThrow(
                    () -> new NotFoundException(String.format("Booking with id %s not found", request.getBookingId()))
            );

            final Payment payment = Payment.builder()
                    .cost(request.getCost())
                    .type(EPaymentType.ONLINE_TRANSFER)
                    .createdAt(OffsetDateTime.now())
                    .booking(booking)
                    .build();

            paymentRepository.save(payment);
            log.debug("payment created");

            response.add(PaymentCreateResponse.builder()
                    .id(payment.getId())
                    .cost(payment.getCost())
                    .type(payment.getType())
                    .createdAt(payment.getCreatedAt())
                    .build());
        }
        return response;
    }

    /**
     * Retrieves payments based on the provided ID.
     *
     * @param id The ID of the payment to be retrieved. If null, retrieves all payments.
     * @return A list of PaymentGetResponse objects containing the details of the retrieved payments.
     * @throws NotFoundException if the payment with the specified ID is not found.
     */
    @Override
    public List<PaymentGetResponse> getPayments(Long id) throws NotFoundException {
        if (id != null) {
            final Payment payment = paymentRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Payment with id %s not found", id))
            );

            return List.of(PaymentGetResponse.builder()
                    .id(payment.getId())
                    .cost(payment.getCost())
                    .type(payment.getType())
                    .createdAt(payment.getCreatedAt())
                    .build());
        }
        return paymentRepository.findAllPayments();
    }
}
