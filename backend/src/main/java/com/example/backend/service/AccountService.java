package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.model.Account;
import com.example.backend.repository.AccountRepository;
import com.example.backend.repository.AccountRepository.AccountCreate;
import com.example.backend.repository.AccountRepository.LoginData;
import com.example.backend.repository.AccountRepository.PasswordChg;

/**
 * アカウントサービスクラス
 */
@Service
public class AccountService {

    /**
     * アカウントリポジトリ
     */
    @Autowired
    private AccountRepository repo;

    /**
     * アカウントIDにより情報取得
     */
    public Account findById(long id) {
        return repo.findById(id);
    }

    /**
     * アカウント作成
     */
    public void create(AccountCreate account) {
        repo.create(account);
    }

    /**
     * パスワード修正
     */
    public void changePassword(PasswordChg account) {
        repo.changePassword(account);
    }

    /**
     * ログイン
     */
    public long login(LoginData param) {
        return repo.login(param);
    }
}
