package com.nabilanam.api.uselessapis.service.security;

import com.nabilanam.api.uselessapis.model.security.Role;
import com.nabilanam.api.uselessapis.model.security.RoleName;
import com.nabilanam.api.uselessapis.model.security.User;
import com.nabilanam.api.uselessapis.repository.security.RoleRepository;
import com.nabilanam.api.uselessapis.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserSecurityService {

	private final RoleRepository roleRepository;

	private final UserRepository userRepository;

	@Autowired
	public UserSecurityService(RoleRepository roleRepository, UserRepository userRepository) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	public void setUselessAdmin() {
		Optional<User> admin = userRepository.findByUsername("useless_admin");
		if (!admin.isPresent()) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			Role role_admin = new Role(RoleName.ROLE_ADMIN);
			Role role_user = new Role(RoleName.ROLE_USER);
			Set<Role> roles = new HashSet<>();
			roles.add(role_admin);
			roles.add(role_user);
			User user = new User("Admin", "useless_admin", "useless_admin@uselessdomain.com", encoder.encode("agabaga"), roles);
			roleRepository.save(role_admin);
			roleRepository.save(role_user);
			userRepository.save(user);
		}
	}
}
