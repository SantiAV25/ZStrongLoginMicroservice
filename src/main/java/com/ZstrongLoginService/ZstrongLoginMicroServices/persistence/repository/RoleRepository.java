package com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.entity.RoleEntity;
import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.entity.RoleEnum;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    
    Optional<RoleEntity> findByRoleEnum(RoleEnum roleEnum);
    
}
