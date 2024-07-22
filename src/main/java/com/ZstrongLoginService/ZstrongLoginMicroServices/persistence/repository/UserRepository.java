package com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.repository;


import java.util.Optional;
import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap.Option;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{
    
    Optional<UserEntity> findByUserName(String userName);

}
