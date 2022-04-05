package com.example.shopspringboot.util;

import com.example.shopspringboot.bean.ProductsFiltrationBean;
import com.example.shopspringboot.domain.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecificationBuilder {

    private ProductSpecificationBuilder() {
    }

    public static Specification<Product> build(ProductsFiltrationBean bean) {
        List<Specification<Product>> specs = getSpecifications(bean);
        if (specs.isEmpty()) {
            return null;
        }

        Specification<Product> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

        return result;
    }

    private static List<Specification<Product>> getSpecifications(ProductsFiltrationBean bean) {
        List<Specification<Product>> specs = new ArrayList<>();

        if (!bean.getCategoryIds().isEmpty()) {
            specs.add(belongsToCategory(bean.getCategoryIds()));
        }
        if (!bean.getManufacturerIds().isEmpty()) {
            specs.add(belongsToManufacturer(bean.getManufacturerIds()));
        }
        if (bean.getPrice() != null) {
            String price = bean.getPrice();
            String min = price.substring(0, price.indexOf("-"));
            String max = price.substring(price.indexOf("-") + 1);

            specs.add(pricesAreBetween(Integer.parseInt(min), Integer.parseInt(max)));
        }
        if (bean.getName() != null) {
            specs.add(nameLike(bean.getName()));
        }

        return specs;
    }

    private static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"),
                "%" + name + "%");
    }

    private static Specification<Product> pricesAreBetween(int min, int max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("price"), min,
                max);
    }

    private static Specification<Product> belongsToCategory(List<Integer> categories) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("category").get("id"))
                .value(categories);
    }

    private static Specification<Product> belongsToManufacturer(List<Integer> manufacturers) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(
                root.get("manufacturer").get("id")).value(manufacturers);
    }
}