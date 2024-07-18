package org.example.crud_template.service;

import org.example.crud_template.config.jwt.JwtTokenProvider;
import org.example.crud_template.model.User;
import org.example.crud_template.repository.RoleRepository;
import org.example.crud_template.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;


    public boolean verifyUser(String email, String password) {
        return userRepository.findByEmail(email).map(user -> passwordEncoder.
                matches(password, user.getPassword())).orElseThrow(() -> new UsernameNotFoundException(" User Not Found "));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(" User Not Found "));
    }

    public boolean checkUserNameExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public String generateToken(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;

    }

    public boolean createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(user.getRoles().stream().map(r -> roleRepository.findByName(r.getName()).orElse(r)).collect(Collectors.toSet()));
        userRepository.save(user);
        return true;
    }

}
