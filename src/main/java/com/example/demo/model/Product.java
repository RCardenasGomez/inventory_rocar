package com.example.demo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="product")
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6825884295044627239L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int price;
	private int count;
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Category category;
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name= "picture", length=1000, columnDefinition = "longblob")
	private byte[] picture;
	
}
