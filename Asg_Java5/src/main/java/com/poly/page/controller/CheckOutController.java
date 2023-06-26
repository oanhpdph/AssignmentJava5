package com.poly.page.controller;

import com.poly.entity.CartDetail;
import com.poly.repository.CartDetailRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/custommer/")
public class CheckOutController {

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @GetMapping(value = "/checkout/{id}/order")
    private String checkoutPage(HttpSession session, @PathVariable(value = "id",required = false) int id) {
        CartDetail cartDetail = cartDetailRepository.findById(id).orElse(null);
        List<CartDetail> list  = new ArrayList<>();
        list.add(cartDetail);
        session.setAttribute("listCheckOut", list);
        session.setAttribute("totalPriceCheckout", cartDetail.getPrice());
        session.setAttribute("view", "page/product/checkout_product.html");
        return "page/layout";
    }

//    @PostMapping(value = "/checkout/order")
//    private String checkoutPage1(HttpSession session, @RequestParam(value = "checkCart",required = false) Integer[] listId, RedirectAttributes attributes) {
//        if (listId == null) {
//            attributes.addFlashAttribute("message", "Chưa chọn sản phẩm");
//            return "redirect:/custommer/cart";
//        }
//        List<CartDetail> listCart = new ArrayList<>();
//        double totalPrice = 0;
//        for (int idCart : listId) {
//            CartDetail cartDetail = cartDetailRepository.findById(idCart).orElse(null);
//            totalPrice = totalPrice + cartDetail.getPrice();
//            listCart.add(cartDetail);
//        }
//        session.setAttribute("listCheckout", listCart);
//        session.setAttribute("totalPriceCheckout", totalPrice);
//        session.setAttribute("view", "page/product/checkout_product.html");
//        return "page/layout";
//    }

}
