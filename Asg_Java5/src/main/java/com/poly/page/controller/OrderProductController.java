package com.poly.page.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.entity.Accounts;
import com.poly.entity.Cart;
import com.poly.entity.CartDetail;
import com.poly.entity.OrderDetails;
import com.poly.entity.Orders;
import com.poly.entity.Products;
import com.poly.repository.CartDetailRepository;
import com.poly.repository.CartRepository;
import com.poly.repository.OrderDetailRepository;
import com.poly.repository.OrderRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/custommer")
public class OrderProductController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @PostMapping("/product/OrderOrCart")
    private String orderProduct(@RequestParam("OrderOrCart") String orderOrCart,
                                @RequestParam(name = "quantity", defaultValue = "0") int quantity, RedirectAttributes attributes,
                                HttpServletRequest request, HttpSession session) {
        if (quantity == 0 && quantity <= 0) {
            attributes.addFlashAttribute("message", "Số lượng phải lớn hơn 0");
            return "redirect:/custommer/product/detail";
        }
        Products products = (Products) request.getSession().getAttribute("productDetail");
        Accounts accounts = (Accounts) request.getSession().getAttribute("accountLogged");
        if ("cart".equals(orderOrCart)) {
            Cart cartFind = cartRepository.findByAccountId(accounts);
            if (cartFind == null) {
                cartFind = new Cart();
                cartFind.setAccountId(accounts);
                cartRepository.save(cartFind);
            }
            CartDetail cartDetail = cartDetailRepository.findByIdCartAndProductId(cartFind, products);
            if (cartDetail == null) {
                cartDetail = new CartDetail();
                cartDetail.setIdCart(cartFind);
                cartDetail.setQuantity(quantity);
                cartDetail.setProductId(products);
                cartDetail.setPrice(quantity * products.getPrice());
            } else {
                cartDetail.setQuantity(quantity);
                cartDetail.setPrice(quantity * products.getPrice());
            }
            cartDetailRepository.save(cartDetail);
            if (cartRepository.findByAccountId(accounts) != null) {
                session.setAttribute("listCart", cartRepository.findByAccountId(accounts).getCartId());
            }
            attributes.addFlashAttribute("message", "Thêm vào giỏ hàng thành công");
        }
        return "redirect:/custommer/product/detail";
    }
}
