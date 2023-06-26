package com.poly.admin.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.common.FileUploadUtil;
import com.poly.entity.Products;
import com.poly.entity.validation.ProductValidation;
import com.poly.repository.CategoriesRepository;
import com.poly.repository.ProductsRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/")
public class ProductController {

	@Autowired
	private CategoriesRepository categoriesRepository;

	@Autowired
	private ProductsRepository productsRepository;

	@GetMapping(value = { "products" })
	public String productManager(Model model, HttpSession httpSession) {

		httpSession.setAttribute("categories", categoriesRepository.findAll());
		httpSession.setAttribute("view", "admin/products/product_manager.html");
		httpSession.setAttribute("active", "products");
		httpSession.setAttribute("listProduct", productsRepository.findAll());
		model.addAttribute("product", new Products());
		return "admin/layout";
	}

	@PostMapping(value = { "products" })
	public String saveProduct(Model model,
			@Validated(ProductValidation.class) @ModelAttribute("product") Products product,
			BindingResult bindingResult, @RequestParam("action") String action, RedirectAttributes attributes,
			@RequestParam("imageUpload") MultipartFile file) {

		System.out.println(bindingResult.hasErrors());
		if (bindingResult.hasErrors()) {
			return "admin/layout";
		}
		String fileName = StringUtils.cleanPath(file.getOriginalFilename()); // xóa ký tự đặc biệt
		product.setImage(fileName);

		if ("insert".equals(action)) {
			System.out.println("insert oanh");
			if ("".equals(fileName)) {
				model.addAttribute("errorImage", "Chưa chọn ảnh sản phẩm");
				return "admin/layout";
			}
			product.setImage(fileName);
			productsRepository.save(product);
			attributes.addFlashAttribute("message", "Thêm thành công");
			String uploadDir = "src/main/resources/static/images"; // đường dẫn upload
			try {
				FileUploadUtil.saveFile(uploadDir, fileName, file);
			} catch (IOException e) {
				//
				e.printStackTrace();
			}
		}

		if ("update".equals(action)) {
			Products findProducts = productsRepository.findById(product.getId()).orElse(null);
			System.out.println("oanh");
			findProducts.setName(product.getName());
			findProducts.setPrice(product.getPrice());
			findProducts.setAvailable(product.isAvailable());
			findProducts.setCategoryId(product.getCategoryId());
			if (!"".equals(fileName)) {
				findProducts.setImage(fileName);
				String uploadDir = "src/main/resources/static/images"; // đường dẫn upload
				try {
					FileUploadUtil.saveFile(uploadDir, fileName, file);
				} catch (IOException e) {
					//
					e.printStackTrace();
				}
			}
			productsRepository.save(findProducts);
		}

		return "redirect:/admin/products";
	}

	@GetMapping(value = "/product/{id}/edit")
	public String getProductEdit(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("product", productsRepository.findById(id).orElse(null));
		return "admin/layout";
	}

	@GetMapping(value = "/product/{id}/delete")
	public String deleteProduct(@PathVariable("id") Integer id) {
		// TODO Auto-generated method stub
		productsRepository.deleteById(id);
		return "redirect:/admin/products";
	}
}
