package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.example.backend.model.Customer;
import com.example.backend.model.Customer.DeleteId;
import com.example.backend.service.CustomerService;

/**
 * 顧客コントローラ
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    /**
     * 顧客サービス(コンテナよりサービスオブジェクトを取得)
     */
    @Autowired
    CustomerService service;

    /**
     * 顧客一覧
     * 
     * @return 画面
     */

    @RequestMapping(value = "/retrieve", method = RequestMethod.GET)
    public List<Customer> listCustomer() {
        List<Customer> customers = service.findAll();
        return customers;
    }

    /**
     * 顧客ページにより一覧
     * 
     * @param pageable page:ページ数、size:1ページあたりの項目数
     * @return 画面
     */
    @RequestMapping(value = "/retrieveWithPage", method = RequestMethod.GET)
    public Page<Customer> getCustomers(@RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Customer> customers = service.findAll(pageable);
        return customers;
    }

    /**
     * 顧客IDにより一件取得
     */
    @RequestMapping(value = "/searchById", method = RequestMethod.GET)
    public Customer findById(@RequestParam Integer id, Model model) {
        // paramの“id”のGET値をidに渡す
        model.addAttribute("id", id);
        // サービスより、顧客IDにより一件取得する。
        Customer customer = service.findById(id);
        return customer;
    }

    /**
     * 顧客一件追加
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Validated Customer customer) {
        service.create(customer);
    }

    /**
     * 顧客一件削除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody DeleteId id, Model model) {
        model.addAttribute("id", id);
        service.delete(id);
    }

    /**
     * 顧客情報更新
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public void update(@RequestBody @Validated Customer customer) {
        service.update(customer);
    }

    /**
     * 顧客情報検索
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<Customer> search(@RequestParam @Validated String keyword) {
        return service.search(keyword);
    }

}
