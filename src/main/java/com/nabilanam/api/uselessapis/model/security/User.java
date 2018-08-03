package com.nabilanam.api.uselessapis.model.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint(columnNames = {
				"username"
		}),
		@UniqueConstraint(columnNames = {
				"email"
		})
})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 40)
	private String name;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NaturalId
	@NotBlank
	@Size(max = 40)
	@Email
	private String email;

	@NotBlank
	@Size(max = 40)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User(@NotBlank @Size(max = 40) String name, @NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(max = 40) String password, Set<Role> roles) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
}
