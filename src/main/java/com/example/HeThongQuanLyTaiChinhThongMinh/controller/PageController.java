package com.example.HeThongQuanLyTaiChinhThongMinh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/page/login")
    public String loginPage() {
        return "login.html";
    }

    @GetMapping("/page/home")
    public String homePage() {
        return "home.html";
    }

    @GetMapping("/page/profile")
    public String information() {
        return "qltt.html";
    }

    @GetMapping("/page/register")
    public String register() {
        return "register.html";
    }

    @GetMapping("/page/bank")
    public String bankPage() {
        return "lKBank.html";
    }

    @GetMapping("/page/create/category")
    public String tdmPage() {
        return "tdm.html";
    }

    @GetMapping("/page/budget")
    public String budgetPage() {
        return "thietlapngansach.html";
    }

    @GetMapping("/page/transfer")
    public String transferPage() {
        return "transfer.html";
    }

    @GetMapping("/page/category")
    public String xemDmPage() {
        return "danhMuc.html";
    }

    @GetMapping("/page/transaction")
    public String xemLichSu(){
        return "transaction.html";
    }
}

