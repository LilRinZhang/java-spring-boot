package com.example.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.example.backend.model.Customer;
import com.example.backend.model.Customer.DeleteId;
import com.example.backend.repository.CustomerRepository;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Sql("file:src/test/java/com/example/backend/TestSQL.sql")
@Transactional
class CustomerRepositoryTests {

	@Autowired
	private CustomerRepository rep;

	DeleteId id = DeleteId.of(0);

	// データベース初期化へ移行したので、Repositoryを使う処理が要らない
	// @Test
	// void 初期化() {
	// // 事前検証
	// List<Customer> csBefore = rep.findAll();
	// assertEquals(csBefore.size(), 3);

	// // 初期化処理
	// rep.defaultCustomer();

	// // 正常系
	// List<Customer> csAfter = rep.findAll();
	// assertEquals(csAfter.size(), 10);
	// }

	@Test
	void 顧客情報全件取得() {
		List<Customer> cs = rep.findAll();

		// 正常系
		assertEquals(cs.size(), 3);
	}

	@Test
	void IDによって顧客情報を取得() {
		Integer id = 1;
		Customer c = rep.findById(id);

		// 正常系
		assertEquals(c.getId(), id);
		assertEquals(c.getName(), "LilRin");
	}

	@Test
	void 顧客情報一件追加() {
		List<Customer> csBefore = rep.findAll();
		// 事前検証
		assertEquals(csBefore.size(), 3);
		// 追加処理
		Customer c = new Customer();
		c.setId(4);
		c.setName("AddMan");
		c.setEmail("AddEmail");
		c.setPhone("000");
		rep.create(c);

		// 正常系
		List<Customer> csAfter = rep.findAll();
		Customer addCustomer = csAfter.get(csAfter.size() - 1);
		assertAll(
				() -> assertEquals(csAfter.size(), csBefore.size() + 1),
				() -> assertEquals(addCustomer.getName(), c.getName()),
				() -> assertEquals(addCustomer.getPhone(), c.getPhone()),
				() -> assertEquals(addCustomer.getEmail(), c.getEmail()));
	}

	@Test
	void 顧客一件削除() {
		List<Customer> csBefore = rep.findAll();
		// 事前検証
		assertEquals(csBefore.size(), 3);

		id.setId(csBefore.size());

		// 削除処
		rep.delete(id);

		// 正常系
		List<Customer> csAfter = rep.findAll();
		assertEquals(csAfter.size(), csBefore.size() - 1);
		Customer c = rep.findById(csBefore.size());
		assertEquals(c, null);
	}

	@Test
	void 顧客情報変更() {
		Integer updateId = 1;
		// 事前検証
		Customer cBefore = rep.findById(updateId);
		assertAll(
				() -> assertEquals(cBefore.getId(), updateId),
				() -> assertEquals(cBefore.getName(), "LilRin"),
				() -> assertEquals(cBefore.getPhone(), "12345678901"),
				() -> assertEquals(cBefore.getEmail(), "lilrin@xon.jp"));

		// 変更処理
		cBefore.setName("updateName");
		cBefore.setPhone("000");
		cBefore.setEmail("updateEmail");
		rep.update(cBefore);

		// 正常系
		Customer cAfter = rep.findById(updateId);
		assertAll(
				() -> assertEquals(cAfter.getId(), cBefore.getId()),
				() -> assertEquals(cAfter.getName(), cBefore.getName()),
				() -> assertEquals(cAfter.getPhone(), cBefore.getPhone()),
				() -> assertEquals(cAfter.getEmail(), cBefore.getEmail()));
	}

}
