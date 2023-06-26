package com.poly.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.poly.entity.validation.ProductValidation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Products implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(groups = ProductValidation.class)
	@Column(name = "Name")
	private String name;

	@Column(name = "Image")
	private String image;

	@NotNull(groups = ProductValidation.class)
	@Min(value = 1, groups = ProductValidation.class)
	@Column(name = "Price")
	private double price;

	@Column(name = "CreateDate")
	@Temporal(TemporalType.DATE)
	private Date createDate = new Date();

	@Column(name = "Available")
	private boolean available;

	@NotNull(groups = ProductValidation.class)
	@ManyToOne
	@JoinColumn(name = "CategoryId")
	private Categories categoryId;

	// Phương thức equals(): Phương thức này được sử dụng để so sánh tính bằng nhau
	// của hai đối tượng
	// tác dụng: để xác nhận 2 đối tượng có thuộc tính giống nhau thì bằng nhau
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Products other = (Products) obj;
		return this.name.equals(other.name) && this.price == other.price && this.image.equals(other.image);
	}

	//Khi phương thức equals() được ghi đè, phương thức hashCode() cũng phải được ghi đè.
	@Override
	public int hashCode() {
		return Objects.hash(name, price, image);
	}

}
