package com.example.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.model.Customer;
import com.example.backend.model.Customer.DeleteId;
import com.example.backend.repository.CustomerRepository;

/**
 * 顧客サービスクラス
 */
@Service
@Transactional
public class CustomerService {

    /**
     * 顧客リポジトリ
     */
    @Autowired
    private CustomerRepository repo;

    /**
     * 顧客全件取得
     */
    public List<Customer> findAll() {
        return repo.findAll();
    }

    /**
     * 顧客ページにより一覧
     * 
     * @param pageable page:ページ数、size:1ページあたりの項目数
     */
    public Page<Customer> findAll(Pageable pageable) {
        return repo.findByPage(pageable);
    }

    /**
     * 顧客IDにより情報取得
     */
    public Customer findById(Integer id) {
        return repo.findById(id);
    }

    /**
     * 顧客情報一件追加
     */
    public void create(Customer customer) {
        repo.create(customer);
    }

    /**
     * 顧客IDにより一件削除
     */
    public void delete(DeleteId id) {
        repo.delete(id);
    }

    /**
     * 顧客情報更新
     */
    public void update(Customer customer) {
        repo.update(customer);
    }

    /**
     * 顧客情報検索
     */
    public List<Customer> search(String keyword) {
        return repo.search(keyword);
    }
}
