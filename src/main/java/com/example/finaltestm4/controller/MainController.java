package com.example.finaltestm4.controller;


import com.example.finaltestm4.model.Category;
import com.example.finaltestm4.model.Product;
import com.example.finaltestm4.service.CategoryService;
import com.example.finaltestm4.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    private static final int PAGE_SIZE = 5;

    @GetMapping("/")
    public String showAllProducts(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Product> products = productService.findAllProducts(page, PAGE_SIZE);
        model.addAttribute("productList", products.getContent());
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        return "home";
    }

    @PostMapping("/products/delete")
    public String deleteProduct(@RequestParam("productIds") List<Long> productIds){
        for(Long id : productIds){
            productService.deleteProduct(id);
        }
        return "redirect:/";
    }


    @GetMapping("/products/add")
    public String showAddProductForm(Model model){
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);
        return "addProduct";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute("product") Product product, RedirectAttributes redirectAttributes){
        productService.saveProduct(product);
        redirectAttributes.addFlashAttribute("message", "Product added successfully");
        return "redirect:/";
    }


    @GetMapping("/products/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "editProduct";

    }

    @GetMapping("/products/search")
    public String searchProducts(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String price,
                                 @RequestParam(required = false) Long categoryId,
                                 @RequestParam(required = false) String status,
                                 @RequestParam(defaultValue = "0") int page,
                                 Model model) {
        Long priceValue = null;
        try {
            if (price != null && !price.isEmpty()) {
                priceValue = Long.parseLong(price);
            }
        } catch (NumberFormatException e) {
            // Xử lý khi giá trị price không hợp lệ
        }

        Page<Product> products = productService.searchProducts(name, priceValue, categoryId, status, page, PAGE_SIZE);
        model.addAttribute("productList", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "home";
    }


}

