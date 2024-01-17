package com.findar.demo;

import com.findar.demo.book.BookRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FindarDemoApplicationTests
{
	@Autowired
	private BookRestController bookRestController;

	@Test
	void contextLoads() {
		assertNotNull(bookRestController);
	}

}
