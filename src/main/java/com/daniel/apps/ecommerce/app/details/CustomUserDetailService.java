package com.daniel.apps.ecommerce.app.details;

import com.daniel.apps.ecommerce.app.model.User;
import com.daniel.apps.ecommerce.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private  final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user  = userRepository.findUserByEmail(username);
        if(user.isEmpty()){
            throw  new UsernameNotFoundException("Spring cannot find the user");
        }
        return new CustomUserDetail(user.get());
    }
}
