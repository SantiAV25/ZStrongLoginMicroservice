package com.ZstrongLoginService.ZstrongLoginMicroServices;

import java.util.Set;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.entity.PermissionEntity;
import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.entity.RoleEntity;
import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.entity.RoleEnum;
import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.entity.UserEntity;
import com.ZstrongLoginService.ZstrongLoginMicroServices.persistence.repository.UserRepository;

@SpringBootApplication
public class ZstrongLoginMicroServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZstrongLoginMicroServicesApplication.class, args);
	}

	/*@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository) {
		return args -> {
			PermissionEntity createPermission = PermissionEntity.builder().name("CREATE").build();
			PermissionEntity ReadPermission = PermissionEntity.builder().name("READ").build();
			PermissionEntity DeletePermission = PermissionEntity.builder().name("DELETE").build();

			RoleEntity adminRole = RoleEntity.builder().roleEnum(RoleEnum.ADMIN).permissionEntities(Set.of(createPermission, ReadPermission, DeletePermission)).build();
			RoleEntity userRole = RoleEntity.builder().roleEnum(RoleEnum.USER).permissionEntities(Set.of(ReadPermission)).build();

			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

			UserEntity user1 = UserEntity.builder()
			.userName("user1")
			.password(bCryptPasswordEncoder.encode("password"))
			.isEnabled(true)
			.accountNonExpired(true)
			.accountNonLocked(true)
			.credentialsNonExpired(true)s
			.roleEntitys(Set.of(adminRole)).build();

			UserEntity user2 = UserEntity.builder()
			.userName("user2")
			.password(bCryptPasswordEncoder.encode("password"))
			.isEnabled(true)
			.accountNonExpired(true)
			.accountNonLocked(true)
			.credentialsNonExpired(true)
			.roleEntitys(Set.of(userRole)).build();

			userRepository.saveAll(List.of(user1, user2));

		};
	}*/

}
