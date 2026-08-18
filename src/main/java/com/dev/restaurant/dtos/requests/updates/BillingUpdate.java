package com.dev.restaurant.dtos.requests.updates;

import java.time.LocalDateTime;

import com.dev.restaurant.entities.Billing;

import jakarta.validation.constraints.NotBlank;

public record BillingUpdate(
    @NotBlank(message = "Campo closedAt não pode ser nulo")
    LocalDateTime closedAt
) {
    public Billing merge(Billing billing) {
        billing.setClosedAt(this.closedAt());
        return billing;
    }
}
