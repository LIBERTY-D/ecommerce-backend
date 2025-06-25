package com.daniel.apps.ecommerce.app.service.imp;


import com.daniel.apps.ecommerce.app.dto.address.AddressDto;
import com.daniel.apps.ecommerce.app.exception.NoSuchAddressException;
import com.daniel.apps.ecommerce.app.model.Address;
import com.daniel.apps.ecommerce.app.model.User;
import com.daniel.apps.ecommerce.app.repository.AddressRepository;
import com.daniel.apps.ecommerce.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressServiceImp {

    private final AddressRepository addressRepository;
    private final UserServiceImp userServiceImp;
    private final UserRepository userRepository;

    @Transactional
    public Collection<Address> findAllAddress() {
        log.info("GETTING ALL ADDRESSES");
        return this.addressRepository.findAll();
    }

    public Address findAddressById(Long id) {
        log.info("GET ADDRESS BY ID");
        return this.findById(id);
    }


    @Transactional
    public void deleteAddressById(Long id) {
        log.info("DELETE ADDRESS BY ID");

        Address address = findById(id);
        User user = address.getUser();
        if(user!=null){
            user.setAddress(null);
            userRepository.save(user);
        }

        addressRepository.delete(address);

    }

    @Transactional
    public Address createAddress(AddressDto addressDto) {
        log.info("CREATING ADDRESS");
        Address address = new Address();
        address.setCity(addressDto.city());
        address.setState(addressDto.state());
        address.setStreet(addressDto.street());
        address.setCountry(addressDto.country());
        address.setPostalCode(addressDto.postalCode());
        Address newAddress = addressRepository.save(address);
        User user = userServiceImp.findUserById (addressDto.userId());
        user.setAddress(newAddress);
        user = userRepository.save(user);
        address.setUser(user);
        return newAddress;

    }

    public Address updateAddress(Long id, AddressDto addressDto) {
        log.info("UPDATING ADDRESS");
        Address address = findById(id);
        address.setCity(addressDto.city());
        address.setState(addressDto.state());
        address.setStreet(addressDto.street());
        address.setCountry(addressDto.country());
        address.setPostalCode(addressDto.postalCode());
        return addressRepository.save(address);

    }

    private Address findById(Long id) {
        Optional<Address> AddressOpt = addressRepository.findById(id);
        if (AddressOpt.isEmpty()) {
            throw new NoSuchAddressException("no address with such id");
        }
        return AddressOpt.get();
    }


}

