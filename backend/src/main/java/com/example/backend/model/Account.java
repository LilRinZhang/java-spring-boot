package com.example.backend.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * 
 * @Data :getter/setterでアクセスすることが可能となる
 * @Builder: 引数ありのコンストラクタを生成する
 * @Entity: エンティティクラスであることを指定
 * @Table: エンティティにマッピングされる物理テーブル名を指定
 */
@Entity
@Data
@Builder
@Table(name = "account")
public class Account {

    /**
     * ID
     * 
     * @GeneratedValue :自動採番
     */
    @Id
    @NonNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** ユーザー名 */
    @NonNull
    @Column(name = "name")
    private String name;

    /** アカウント名 */
    @NonNull
    @Column(name = "account")
    private String account;

    /** パスワード */
    @NonNull
    @Column(name = "password")
    private String password;

    /** メールアドレス */
    @NonNull
    @Column(name = "email")
    private String email;

    /** 有効性 */
    @NonNull
    @Column(name = "vaild")
    private boolean vaild;

    /** 変更時間 */
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss.fff")
    @NonNull
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    /** 作成時間 */
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss.fff")
    @NonNull
    @Column(name = "create_date")
    private LocalDateTime createDate;

    /**
     * @Tolerate : デフォルトコンストラクタと@Builderで生成するコンストラクタとのコンフリクト回避する
     */
    @Tolerate
    public Account() {
    }

}
