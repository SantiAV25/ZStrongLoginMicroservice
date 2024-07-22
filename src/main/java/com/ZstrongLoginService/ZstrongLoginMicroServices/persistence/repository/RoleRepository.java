package com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.entity.RoleEntity;
import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.entity.RoleEnum;
import java.util.Optional;

/*
 * This is the RoleRepository this used JPARepository to interact with the database
 * @Author Santiago Agredo Vallejo
 * @Version 1.0
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    
    Optional<RoleEntity> findByRoleEnum(RoleEnum roleEnum);
    
}
