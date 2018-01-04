package com.github.adeynack.finances.service

import org.hamcrest.Matchers.equalTo
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class FinancesServiceApplicationTests {

	@Test
	fun contextLoads() {
        Assert.assertThat(1, equalTo(1))
	}

}
