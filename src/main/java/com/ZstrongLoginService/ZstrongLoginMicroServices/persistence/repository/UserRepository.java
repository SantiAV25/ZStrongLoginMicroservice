package com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.repository;


import java.util.Optional;
import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap.Option;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.entity.UserEntity;

/**
 * This is the UserRepository this used JPARepository to interact with the database
 * @Author Santiago Agredo Vallejo
 * @Version 1.0
 */

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{
    
    Optional<UserEntity> findByUserName(String userName);

}
