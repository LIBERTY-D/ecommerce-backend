package com.daniel.apps.ecommerce.app.db;

import com.daniel.apps.ecommerce.app.environment.AdminUserProperties;
import com.daniel.apps.ecommerce.app.environment.DemoUserProperties;
import com.daniel.apps.ecommerce.app.model.User;
import com.daniel.apps.ecommerce.app.repository.AddressRepository;
import com.daniel.apps.ecommerce.app.repository.CategoryRepository;
import com.daniel.apps.ecommerce.app.repository.ProductRepository;
import com.daniel.apps.ecommerce.app.repository.UserRepository;
import com.daniel.apps.ecommerce.app.sample.SampleDataFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "custom.populate.data", havingValue = "true")
public class InitializeDb implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminUserProperties adminUserProperties;
    private final DemoUserProperties demoUserProperties;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Optional<User> opAdminUser = userRepository.findUserByEmail(adminUserProperties.getAdmin_user());

        Optional<User> opDemoUser =
                userRepository.findUserByEmail(demoUserProperties.getDemo_user());
        if (opAdminUser.isEmpty() && opDemoUser.isEmpty()) {
            log.info("POPULATING USERS IN DB");

            User demoUser = new User(
                    "super",
                    "sudo",
                    demoUserProperties.getDemo_user(),
                    passwordEncoder.encode(demoUserProperties.getDemo_password()),
                    Set.of(demoUserProperties.role()), true
            );
            User adminUser = new User(
                    "super",
                    "sudo",
                    adminUserProperties.getAdmin_user(),
                    passwordEncoder.encode(adminUserProperties.getAdmin_password()),
                    adminUserProperties.getAdminRolesAsEnumSet(), true
            );
            userRepository.saveAll(List.of(demoUser, adminUser));

        }

        log.info("POPULATING DATA IN DB");
        categoryRepository.saveAll(SampleDataFactory.createCategories());
        productRepository.saveAll(SampleDataFactory.createSampleProducts());


    }
}
