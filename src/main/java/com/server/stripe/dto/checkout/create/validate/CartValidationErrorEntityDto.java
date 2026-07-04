package com.server.stripe.dto.checkout.create.validate;

import org.springframework.data.annotation.Id;
//                                                                                                                                    СОЗДАТЬ ENUM ДЛЯ errorType
public record CartValidationErrorEntityDto(@Id String productId, String collectionName, int requestedQuantity, int availableQuantity, String errorType){
}
