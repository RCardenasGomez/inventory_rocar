package com.example.demo.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseRest extends Response_Rest {
	ProductResponse product = new ProductResponse();
}
