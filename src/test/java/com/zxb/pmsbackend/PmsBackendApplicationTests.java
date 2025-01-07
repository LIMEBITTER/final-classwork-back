package com.zxb.pmsbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@SpringBootTest
class PmsBackendApplicationTests {


	private static final String PREFIX = "WF";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final Random RANDOM = new Random();

	@Test
	void contextLoads() {
	}

	@Test
	void generateId(){
		// 获取当前时间并格式化为字符串
		String dateTimeStr = LocalDateTime.now().format(DATE_TIME_FORMATTER);

		// 生成4个随机字母
		StringBuilder randomChars = new StringBuilder(4);
		for (int i = 0; i < 4; i++) {
			int index = RANDOM.nextInt(CHARACTERS.length());
			randomChars.append(CHARACTERS.charAt(index));
		}
		System.out.println("随机ID"+PREFIX + dateTimeStr + randomChars.toString());
	}





}
