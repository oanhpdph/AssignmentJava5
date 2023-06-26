package com.poly.page.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.entity.Accounts;
import com.poly.entity.OrderDetails;
import com.poly.entity.Orders;
import com.poly.repository.OrderRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/custommer")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;


    // hiển thị trang check out
    @GetMapping("/order")
    private String orderPage(HttpSession session, @RequestParam(name = "pageSize", defaultValue = "8") int pageSize,
                             @RequestParam(name = "pageNum", defaultValue = "1", required = false) int pageNumber) {
        // TODO Auto-generated method stub
        Accounts accounts = (Accounts) session.getAttribute("accountLogged");
        Sort sort = Sort.by(Sort.Direction.DESC, "createDate");
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize);
        List<Orders> list = orderRepository.findByAccountId(accounts, sort);
        if (list != null) {
            session.setAttribute("listOrder", list);
        }
        double totalPrice = 0;
        List<Double> listPrice = new ArrayList<>();
        for (Orders temp : list) {
            totalPrice = 0;
            for (OrderDetails orders : temp.getOrderDetails()) {
                totalPrice = (totalPrice + orders.getPrice());
            }
            listPrice.add(totalPrice);
        }
        session.setAttribute("totalPrice", listPrice);
        session.setAttribute("view", "/page/product/order_product.html");
        return "page/layout";
    }

}
