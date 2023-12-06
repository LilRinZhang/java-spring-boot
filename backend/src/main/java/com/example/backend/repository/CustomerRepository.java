package com.example.backend.repository;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.model.Customer;
import com.example.backend.model.Customer.DeleteId;

@Repository
@Transactional
public class CustomerRepository {
    /**
     * 全件取得
     */
    private static final String FIND_ALL_SQL = "SELECT * FROM CUSTOMER ORDER BY ID";

    /**
     * IDにより情報取得
     */
    private static final String FIND_BY_ID_SQL = "SELECT * FROM CUSTOMER WHERE ID = :id";

    /**
     * 顧客情報一件追加
     */
    private static final String INSERT_SQL = "INSERT INTO CUSTOMER(NAME, PHONE, EMAIL) VALUES ( :name, :phone, :email)";

    /**
     * 顧客情報一件削除
     */
    private static final String DELETE_SQL = "DELETE FROM CUSTOMER WHERE ID = :id";

    /**
     * 顧客情報更新
     */
    private static final String UPDATE_SQL = "UPDATE CUSTOMER SET NAME = :name, PHONE = :phone, EMAIL = :email WHERE ID = :id";

    /**
     * 顧客情報検索
     */
    private static final String SEARCH_SQL = "SELECT * FROM CUSTOMER WHERE NAME LIKE :key ORDER BY ID";

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    /**
     * 顧客全件検索する
     * 
     * @return 顧客リストオブジェクト
     */
    @SuppressWarnings("unchecked")
    public List<Customer> findAll() {
        // SQLを定義
        Query query = entityManager.createNativeQuery(FIND_ALL_SQL, Customer.class);
        // 顧客データを全件取得する。
        try {
            return query.getResultList();
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    /**
     * 顧客ページにより一覧
     * 
     * @param pageable page:ページ数、size:1ページあたりの項目数
     * @return 画面
     */
    public Page<Customer> findByPage(Pageable pageable) {
        List<Customer> list = this.findAll();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<Customer> listContext = list.subList(start, end);
        return new PageImpl<>(listContext, pageable, list.size());
    }

    /**
     * 顧客IDにより情報取得
     * 
     * @param id 顧客ID
     * @return 顧客一件情報
     */
    public Customer findById(Integer id) {
        // SQLを定義,paramを設定す
        Query query = entityManager.createNativeQuery(FIND_BY_ID_SQL, Customer.class).setParameter("id", id);
        // 顧客データを一件取得する。
        try {
            return (Customer) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 顧客情報一件追加
     * 
     * @param customer 追加顧客情報
     */
    public void create(Customer customer) {
        // SQLを定義,paramを設定する
        Query query = entityManager.createNativeQuery(INSERT_SQL)
                .setParameter("name", customer.getName())
                .setParameter("phone", customer.getPhone())
                .setParameter("email", customer.getEmail());
        // SQLを実行する。
        query.executeUpdate();
    }

    /**
     * 顧客情報一件削除
     * 
     * @param id 削除顧客ID
     */
    public void delete(DeleteId id) {
        // SQLを定義,paramを設定する
        Query query = entityManager.createNativeQuery(DELETE_SQL).setParameter("id", id.getId());
        // SQLを実行する。
        query.executeUpdate();
    }

    /**
     * 顧客情報更新
     * 
     * @param customer 更新顧客情報
     */
    @Transactional
    public void update(Customer customer) {
        // SQLを定義,paramを設定する
        Query query = entityManager.createNativeQuery(UPDATE_SQL)
                .setParameter("name", customer.getName())
                .setParameter("phone", customer.getPhone())
                .setParameter("email", customer.getEmail())
                .setParameter("id", customer.getId());
        // SQLを実行する。
        query.executeUpdate();
    }

    /**
     * 顧客情報検索
     * 
     * @param keyword 検索キーワード
     */
    @SuppressWarnings("unchecked")
    public List<Customer> search(String keyword) {
        Query query = entityManager.createNativeQuery(SEARCH_SQL, Customer.class).setParameter("key",
                "%" + keyword + "%");
        // 検索情報検索取得する
        List<Customer> result = new ArrayList<Customer>();
        try {
            result = query.getResultList();
        } catch (Exception e) {
            result = null;
        }

        try {
            Integer keyId = Integer.parseInt(keyword);
            if (findById(keyId) instanceof Customer)
                result.add(findById(keyId));
            result.sort(Comparator.comparing(Customer::getId));
            return result;
        } catch (Exception e) {
            return result;
        }
    }

}