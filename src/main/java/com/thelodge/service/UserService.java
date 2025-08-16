package com.thelodge.service;

import com.thelodge.entity.auth.Users;
import com.thelodge.entity.auth.Role;
import com.thelodge.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true) // Ensures lazy loading works if not EAGER
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if (!user.getIsActive()) {
            throw new UsernameNotFoundException("User " + username + " is not active.");
        }

        return new User(
                user.getUsername(),
                user.getPasswordHash(),
                getAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
                .flatMap(role -> {
                    Set<GrantedAuthority> authorities = new HashSet<>();
                    // Add role name as a "ROLE_" prefixed authority
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName().toUpperCase()));
                    // Add permissions as authorities
                    role.getPermissions().forEach(permission ->
                            authorities.add(new SimpleGrantedAuthority(permission.getPermissionName().toUpperCase()))
                    );
                    return authorities.stream();
                })
                .collect(Collectors.toSet());
    }
}
