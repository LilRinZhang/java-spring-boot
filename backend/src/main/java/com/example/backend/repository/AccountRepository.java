package com.example.backend.repository;

import java.util.regex.Pattern;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.backend.model.Account;
import com.example.backend.values.AccountResultStatusValues;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

/**
 * @Repository :Repositoryのインフラ層を宣言する（ドメイン層から呼び出され、データベースやファイルとのインターフェースとしてデータの入出力を担当する層のことを指し）
 * @Transactional :トランザクションをかける
 */
@Repository
@Transactional
public class AccountRepository {
    /**
     * アカウント全件取得
     */
    // private static final String FIND_ALL_SQL = "SELECT * FROM ACCOUNT ORDER BY
    // ID";

    /**
     * IDによりアカウント情報取得
     */
    private static final String FIND_BY_ID_SQL = "SELECT * FROM ACCOUNT WHERE ID = :id";

    /**
     * アカウント作成
     */
    private static final String INSERT_SQL = "INSERT INTO ACCOUNT(NAME,ACCOUNT,PASSWORD,EMAIL,VAILD,UPDATE_DATE,CREATE_DATE) VALUES ( :name, :account,:password, :email,:vaild,current_timestamp,current_timestamp)";

    /**
     * 未定：アカウント無/有効化する
     */
    // private static final String VAILD_SQL = "UPDATE ACCOUNT SET VAILD = :vaild,
    // UPDATE_DATE = :updateDate WHERE ID = :id";

    /**
     * パスワード修正
     */
    private static final String PASSWOED_UPDATE_SQL = "UPDATE ACCOUNT SET PASSWORD = :password, UPDATE_DATE = current_timestamp WHERE ID = :id";

    /**
     * アカウントによりアカウント情報取得
     */
    private static final String FIND_BY_ID_ACCOUNT = "SELECT * FROM ACCOUNT WHERE ACCOUNT = :account";

    /**
     * パスワード正規表現
     * パスワードに6〜16文字が必要であり
     * パスワードに数字、小文字、大文字が含まれている必要
     */
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,16}$");

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    /**
     * アカウントIDにより情報取得
     * 
     * @param id アカウントID
     * @return アカウント情報
     */
    public Account findById(long id) {
        // SQLを定義,paramを設定す
        Query query = entityManager.createNativeQuery(FIND_BY_ID_SQL, Account.class).setParameter("id", id);
        try {
            // アカウントデータを取得する
            return (Account) query.getSingleResult();
        } catch (Exception e) {
            // TODO:ID不存在エラー発生
            return null;
        }

    }

    /**
     * アカウント追加
     * 
     * @param customer 追加アカウント情報
     */
    public void create(AccountCreate account) {
        // TODO:アカウントとメールの重複チェック
        // SQLを定義,paramを設定する
        Query query = entityManager.createNativeQuery(INSERT_SQL)
                .setParameter("name", account.getName())
                .setParameter("account", account.getAccount())
                .setParameter("password", account.getPassword())
                .setParameter("email", account.getEmail())
                .setParameter("vaild", true);
        // SQLを実行する。
        query.executeUpdate();
    }

    /**
     * パスワード修正
     */
    public void changePassword(PasswordChg account) {
        // パスワードバリエーションチェック
        if (passwordCheck(account.getPassword())) {
            // SQLを定義,paramを設定する
            Query query = entityManager.createNativeQuery(PASSWOED_UPDATE_SQL)
                    .setParameter("password", account.getPassword())
                    .setParameter("id", account.getId());
            // SQLを実行する。
            query.executeUpdate();
        } else {
            // TODO:パスワード要件満足しない処理
        }
    }

    /**
     * ログイン
     */
    public long login(LoginData param) {
        if (passwordCheck(param.getPassword())) {
            // SQLを定義,paramを設定する
            Query query = entityManager.createNativeQuery(FIND_BY_ID_ACCOUNT, Account.class)
                    .setParameter("account", param.getAccount());
            try {
                // アカウントデータを取得する
                Account result = (Account) query.getSingleResult();
                String passwordTrue = result.getPassword(), passwordInput = param.getPassword();
                if (passwordTrue.equals(passwordInput))
                    return result.getId();
                else
                    return AccountResultStatusValues.PASSWORD_WRONG.getResult();
                // パスワード一致チェック
                // return (result.getPassword() == param.getPassword()) ? result.getId()
                // : AccountResultStatusValues.PASSWORD_WRONG.getResult();
            } catch (Exception e) {
                // アカウント不存在エラー発生
                return AccountResultStatusValues.NO_ACCOUNT.getResult();
            }
        } else {
            // パスワードバリュエーションチェックエラー発生
            return AccountResultStatusValues.PASSWORD_VAILDATION_WRONG.getResult();
        }
    }

    /**
     * パスワードバリエーションチェック
     */
    public Boolean passwordCheck(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * アカウント作成用
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class AccountCreate {
        /** ユーザー名 */
        @NonNull
        private String name;

        /** アカウント名 */
        @NonNull
        private String account;

        /** パスワード */
        @NonNull
        private String password;

        /** メールアドレス */
        @NonNull
        private String email;
    }

    /**
     * パスワードバ修正用
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class PasswordChg {
        /** ID */
        @NonNull
        private long id;

        /** パスワード */
        @NonNull
        private String password;
    }

    /**
     * ログイン用
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class LoginData {
        /** ID */
        @NonNull
        private String account;

        /** パスワード */
        @NonNull
        private String password;
    }
}
