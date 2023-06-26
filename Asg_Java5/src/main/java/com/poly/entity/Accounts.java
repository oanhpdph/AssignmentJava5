package com.poly.entity;

import java.io.Serializable;
import java.util.List;

import com.poly.entity.validation.LoginValidation;
import com.poly.entity.validation.RegistrationValidation;
import com.poly.entity.validation.UpdateValidation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Accounts implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(groups = { RegistrationValidation.class, UpdateValidation.class })
	@Column(name = "Username")
	private String username;

	@Column(name = "Password")
	@NotBlank(groups = { LoginValidation.class })
	@Min(value = 8, groups = { RegistrationValidation.class })
	private String password;

	@NotBlank(groups = { RegistrationValidation.class, UpdateValidation.class })
	@Column(name = "Fullname")
	private String fullname;

	@Column(name = "Email")
	@NotBlank(groups = { RegistrationValidation.class, LoginValidation.class, UpdateValidation.class })
	@Email(groups = { RegistrationValidation.class, LoginValidation.class })
	private String email;

	@Column(name = "Photo")
	private String photo;

	@Column(name = "Activated")
	private boolean activated = true;

	@Column(name = "Admin")
	private boolean admin = false;

	@OneToMany(mappedBy = "accountId", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Orders> order;
}
