package com.example.assignmentjavabootcamp.payment.controller;

import com.example.assignmentjavabootcamp.payment.entity.PaymentChannel;
import com.example.assignmentjavabootcamp.payment.exception.PaymentChannelNotFoundException;
import com.example.assignmentjavabootcamp.payment.repository.PaymentChannelRepository;
import com.example.assignmentjavabootcamp.utils.enums.ResponseMessageEnum;
import com.example.assignmentjavabootcamp.utils.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentController {
    @Autowired
    PaymentChannelRepository paymentChannelRepository;

    @GetMapping("/payment_channel")
    public Response getAllPaymentChannel() {
        List<PaymentChannel> paymentChannelEntities = paymentChannelRepository.findAll();
        if (CollectionUtils.isEmpty(paymentChannelEntities)) {
            throw new PaymentChannelNotFoundException();
        }
        return new Response(paymentChannelEntities, ResponseMessageEnum.SUCCESS.getMessage());
    }
}
