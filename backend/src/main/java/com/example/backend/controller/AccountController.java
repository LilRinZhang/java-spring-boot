package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.Account;
import com.example.backend.repository.AccountRepository.AccountCreate;
import com.example.backend.repository.AccountRepository.LoginData;
import com.example.backend.repository.AccountRepository.PasswordChg;
import com.example.backend.service.AccountService;

/**
 * アカウントコントローラ
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {

    /**
     * アカウントサービス(コンテナよりサービスオブジェクトを取得)
     */
    @Autowired
    AccountService service;

    /**
     * アカウントIDにより一件取得
     */
    @RequestMapping(value = "/searchById", method = RequestMethod.GET)
    public Account findById(@RequestParam long id, Model model) {
        // paramの“id”のGET値をidに渡す
        model.addAttribute("id", id);
        // サービスより、アカウントIDにより一件取得する。
        Account account = service.findById(id);
        return account;
    }

    /**
     * アカウント作成
     */
    @PostMapping(value = "/create")
    public void create(@RequestBody @Validated AccountCreate account) {
        service.create(account);
    }

    /**
     * パスワード修正
     */
    @PostMapping(value = "/passwordChg")
    public void changePassword(@RequestBody @Validated PasswordChg account) {
        service.changePassword(account);
    }

    /**
     * ログイン
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public long login(@ModelAttribute LoginData param) {
        return service.login(param);
    }
}
