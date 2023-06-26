package com.poly.page.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.entity.Accounts;
import com.poly.entity.CartDetail;
import com.poly.entity.OrderDetails;
import com.poly.entity.Orders;
import com.poly.repository.CartDetailRepository;
import com.poly.repository.CartRepository;
import com.poly.repository.OrderDetailRepository;
import com.poly.repository.OrderRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/custommer")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @GetMapping("/cart")
    private String pageCart(HttpSession session) {
        // TODO Auto-generated method stub
        Accounts accounts = (Accounts) session.getAttribute("accountLogged");
        if (cartRepository.findByAccountId(accounts) != null) {
            session.setAttribute("listCart", cartRepository.findByAccountId(accounts).getCartId());
        }
        session.setAttribute("view", "page/product/cart_product.html");
        return "page/layout";
    }

    @GetMapping(value = "/cart/{id}/delete")
    private String deleteProductInCart(@PathVariable("id") int id) {
        // TODO Auto-generated method stub
        cartDetailRepository.deleteById(id);
        return "redirect:/custommer/cart";
    }

    @GetMapping(value = "/home/{id}/delete")
    private String deleteProductInHome(@PathVariable("id") int id, HttpServletResponse request) {
        // TODO Auto-generated method stub
        cartDetailRepository.deleteById(id);
        return "redirect:/custommer/home";
    }

    // mua hàng
    @GetMapping(value = "/cart/order")
    private String orderInCart(HttpServletRequest request) {
        // TODO Auto-generated method stub
        List<CartDetail> list = (List<CartDetail>) request.getSession().getAttribute("listCheckOut");
        Accounts accounts = (Accounts) request.getSession().getAttribute("accountLogged");

        Orders order = new Orders();
        order.setAccountId(accounts);
        // thêm sản phẩm vào hóa đơn
        OrderDetails details = new OrderDetails();
        details.setOrderId(orderRepository.save(order));
        for (CartDetail detail : list) {
            CartDetail cartDetail = cartDetailRepository.findById(detail.getId()).orElse(null);
            details.setQuantity(cartDetail.getQuantity());
            details.setProductId(cartDetail.getProductId());
            details.setPrice(cartDetail.getQuantity() * cartDetail.getProductId().getPrice());
            orderDetailRepository.save(details);
            cartDetailRepository.deleteById(detail.getId());
        }
        return "redirect:/custommer/cart";
    }

    @PostMapping("/cart")
    private String orderOrDelete(@RequestParam(value = "OrderOrDelete", required = false) String orderOrDelete,
                                 @RequestParam(value = "checkCart", required = false) Integer[] checkCart, RedirectAttributes attributes,
                                 HttpServletRequest request, HttpSession session) {
        // TODO Auto-generated method stub
        if (checkCart == null) {
            attributes.addFlashAttribute("message", "Chưa chọn sản phẩm");
            return "redirect:/custommer/cart";
        }
        Accounts accounts = (Accounts) request.getSession().getAttribute("accountLogged");
        if ("order".equals(orderOrDelete)) {
            List<CartDetail> listCart = new ArrayList<>();
            double totalPrice = 0;
            for (Integer idCart : checkCart) {
                CartDetail cartDetail = cartDetailRepository.findById(idCart).orElse(null);
                totalPrice = totalPrice + cartDetail.getPrice();
                System.out.println(cartDetail.getIdCart());
                listCart.add(cartDetail);
            }
            session.setAttribute("listCheckOut", listCart);
            session.setAttribute("totalPriceCheckout", totalPrice);
            session.setAttribute("view", "page/product/checkout_product.html");
            return "page/layout";
        }
        if ("delete".equals(orderOrDelete)) {
            for (int id : checkCart) {
                cartDetailRepository.deleteById(id);
            }
            return "redirect:/custommer/cart";
        }

        return "redirect:/custommer/cart";
    }
}
