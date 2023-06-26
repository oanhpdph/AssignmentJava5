package com.poly.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="CartDetail")
@NoArgsConstructor
@AllArgsConstructor
public class CartDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "CartId", referencedColumnName = "id")
	private Cart idCart;

	@ManyToOne
	@JoinColumn(name = "ProductId", referencedColumnName = "id")
	private Products productId;

	@Column(name = "Price")
	private double price;

	@Column(name = "Quantity")
	private Integer quantity;
}
