package com.example.backend.model;

import org.springframework.lang.NonNull;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

@Entity
@Data
@Builder
@Table(name = "customer")
public class Customer {

    /** ID */
    @Id
    @NonNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 顧客名前 */
    @NonNull
    @Column(name = "name")
    private String name;

    /** 顧客電話番号 */
    @NonNull
    @Column(name = "phone")
    private String phone;

    /** 顧客Email */
    @NonNull
    @Column(name = "email")
    private String email;

    /** デフォルトコンストラクタ */
    @Tolerate
    public Customer() {
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class DeleteId {
        /** ID */
        private Integer id;
    }
}
