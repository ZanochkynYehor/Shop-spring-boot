package com.example.shopspringboot.controller;

import com.example.shopspringboot.bean.ProductsFiltrationBean;
import com.example.shopspringboot.domain.Product;
import com.example.shopspringboot.service.CategoryService;
import com.example.shopspringboot.service.ManufacturerService;
import com.example.shopspringboot.service.ProductService;
import com.example.shopspringboot.util.ProductSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.OptionalInt;

@Controller
public class ProductController {
    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, ManufacturerService manufacturerService,
                             CategoryService categoryService) {
        this.productService = productService;
        this.manufacturerService = manufacturerService;
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/products")
    public String getProducts(
            @ModelAttribute("productsFiltrationBean") @Valid ProductsFiltrationBean bean,
            BindingResult bindingResult, Model model, Pageable pageable, HttpServletRequest req) {
        if (bindingResult.hasErrors()) {
            return "redirect:/products";
        }

        Specification<Product> spec = ProductSpecificationBuilder.build(bean);
        Page<Product> allProducts = productService.getProducts(spec, Pageable.unpaged());
        int minPrice = getMinPrice(allProducts);
        int maxPrice = getMaxPrice(allProducts);

        HttpSession session = req.getSession();
        if (bean.getPrice() == null) {
            session.setAttribute("minPriceBorder", minPrice);
            session.setAttribute("maxPriceBorder", maxPrice);
        }
        model.addAttribute("minPrice", bean.getPrice() != null ?
                bean.getPrice().substring(0, bean.getPrice().indexOf("-")) : minPrice);
        model.addAttribute("maxPrice", bean.getPrice() != null ?
                bean.getPrice().substring(bean.getPrice().indexOf("-") + 1) : maxPrice);

        Page<Product> pageProducts = productService.getProducts(spec,
                pageable.getPageSize() == 20 ? PageRequest.of(0, 9, Sort.by("name")) : pageable);

        model.addAttribute("products", pageProducts);
        model.addAttribute("manufacturers", manufacturerService.getManufacturers());
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("manufacturersChecked", bean.getManufacturerIds());
        model.addAttribute("categoriesChecked", bean.getCategoryIds());

        return "products";
    }

    private int getMinPrice(Page<Product> products) {
        OptionalInt min = products.stream().mapToInt(Product::getPrice).min();
        return min.isPresent() ? min.getAsInt() : 0;
    }

    private int getMaxPrice(Page<Product> products) {
        OptionalInt max = products.stream().mapToInt(Product::getPrice).max();
        return max.isPresent() ? max.getAsInt() : 0;
    }

    @GetMapping(path = "/product/{id}")
    public String getProduct(@PathVariable(name = "id") String id, Model model) {
        Product product = productService.getProduct(Integer.parseInt(id));

        model.addAttribute("product", product);
        return "product";
    }
}