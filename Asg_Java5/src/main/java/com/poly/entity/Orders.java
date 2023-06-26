package com.poly.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Orders")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "AccountId")
	private Accounts accountId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreateDate")
	private java.util.Date createDate = new java.util.Date();

	@Column(name = "Address")
	private String address;

	@OneToMany(mappedBy = "orderId", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<OrderDetails> orderDetails;
}
