package com.ZstrongLoginService.ZstrongLoginMicroServices.service;
import org.apache.catalina.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import com.ZstrongLoginService.ZstrongLoginMicroServices.Controller.DTO.AuthCreateUser;
import com.ZstrongLoginService.ZstrongLoginMicroServices.Controller.DTO.AuthLoginRequest;
import com.ZstrongLoginService.ZstrongLoginMicroServices.Controller.DTO.AuthResponse;
import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.entity.RoleEntity;
import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.entity.RoleEnum;
import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.entity.UserEntity;
import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.repository.RoleRepository;
import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.repository.UserRepository;
import com.ZstrongLoginService.ZstrongLoginMicroServices.util.jwtUtils.JwtUtils;



import org.springframework.security.core.userdetails.User;

@Service
public class UserDatailsServiceImpl implements UserDetailsService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        UserEntity userEntity = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoleEntitys().forEach(role -> {
            authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name())));
        });

        userEntity.getRoleEntitys().stream().flatMap(role -> role.getPermissionEntities().stream())
        .forEach(permission -> {
            authorityList.add(new SimpleGrantedAuthority(permission.getName()));
        });


        return new User(userEntity.getUserName(), 
        userEntity.getPassword(), 
        userEntity.isEnabled(), 
        userEntity.isAccountNonExpired(),
         userEntity.isCredentialsNonExpired(), 
         userEntity.isAccountNonLocked(), 
         authorityList);
    }
    
    public AuthResponse loginUser(AuthLoginRequest authLoginRequest){

        System.out.println("Entro al login User Request");

        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authentication(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accesToken = jwtUtils.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(username, "User Logged Succesfull", accesToken, true);

        return authResponse;

    }

    public Authentication authentication(String username, String password){
        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username or password");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());

    }

    public UserEntity crateUser(AuthCreateUser authCreateUser){

        String username = authCreateUser.username();
        String password = authCreateUser.password();
        List<String> roles = authCreateUser.roleRequest().roles();
        Set<RoleEntity> roleList = new HashSet<>();

        roles.forEach(role -> {
            RoleEntity roleEntity = roleRepository.findByRoleEnum(RoleEnum.valueOf(role)).orElseThrow(() -> new RuntimeException("Role not found"));
            roleList.add(roleEntity);
        });

        UserEntity userEntity = UserEntity.builder()
        .userName(username)
        .password(passwordEncoder.encode(password))
        .isEnabled(true)
        .accountNonExpired(true)
        .accountNonLocked(true)
        .credentialsNonExpired(true)
        .roleEntitys(roleList)
        .build();


        userRepository.save(userEntity);

        return userEntity;
    }

}