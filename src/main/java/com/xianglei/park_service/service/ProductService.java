package com.xianglei.park_service.service;

import com.xianglei.park_service.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> listProduct();

    Product findById(int id);


}
