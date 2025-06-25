package com.daniel.apps.ecommerce.app.controller.imp;

import com.daniel.apps.ecommerce.app.controller.Controller;
import com.daniel.apps.ecommerce.app.dto.address.AddressDto;
import com.daniel.apps.ecommerce.app.http.HttpResponse;
import com.daniel.apps.ecommerce.app.model.Address;
import com.daniel.apps.ecommerce.app.service.imp.AddressServiceImp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;


@RestController
@RequiredArgsConstructor

public class AddressController extends BaseController implements Controller<AddressDto, Address,Long> {



    private  final AddressServiceImp addressServiceImp;
    @GetMapping("addresses")

    @Override
    public ResponseEntity<HttpResponse<Collection<Address>>> findAll() {
        Collection<Address> addresses= addressServiceImp.findAllAddress();
        HttpResponse<Collection<Address>> response =
                HttpResponse.<Collection<Address>>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Addresses retrieved successfully")
                        .data(addresses).build();

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("addresses/{id}")
    public ResponseEntity<HttpResponse<Address>> findOne(Long id) {

        HttpResponse<Address> response =
                HttpResponse.<Address>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Address retrieved successfully")
                        .data(addressServiceImp.findAddressById(id)).build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("addresses/{id}")
    @Override
    public ResponseEntity<HttpResponse<Address>> deleteOne(Long id) {
        addressServiceImp.deleteAddressById(id);
        HttpResponse<Address> response =
                HttpResponse.<Address>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Address deleted successfully").build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("addresses/{id}")
    @Override
    public ResponseEntity<HttpResponse<Address>> updateOne(Long id, AddressDto updatedEntity) {

        Address user =  addressServiceImp.updateAddress(id,updatedEntity);
        HttpResponse<Address> response =
                HttpResponse.<Address>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value()).data(user)
                        .message("Address updated successfully").build();
        return ResponseEntity.ok(response);

    }


    @PostMapping("addresses")
    @Override
    public ResponseEntity<HttpResponse<Address>> createOne(AddressDto newEntity) {
        Address address =  addressServiceImp.createAddress(newEntity);
        HttpResponse<Address> response =
                HttpResponse.<Address>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value()).
                        data(address)
                        .message("Address created successfully").build();
        return ResponseEntity.ok(response);
    }
}

