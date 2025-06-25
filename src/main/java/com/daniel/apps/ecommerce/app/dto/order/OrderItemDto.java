package com.daniel.apps.ecommerce.app.dto.order;

import com.daniel.apps.ecommerce.app.dto.product.ProductBoughtDto;


public record OrderItemDto(ProductBoughtDto purchasedProduct)  {
}
