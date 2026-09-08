package com.production.url_shortener;

import com.production.url_shortener.service.UrlShortnerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UrlShortenerApplicationTests {
	@Autowired
	private UrlShortnerService urlService;

	@Test
	void testUrlShortnerFlow() {
		String originalLongUrl = "https://github.com";
		String generatedKey = urlService.shortenUrl(originalLongUrl);
		assertNotNull(generatedKey , "short key should not be null!");
		assertEquals(6, generatedKey.length(), "short key should be 6 characters!");
		String retrievedLongUrl = urlService.getLongUrl(generatedKey);
		assertEquals(originalLongUrl , retrievedLongUrl , "url should match!");
	}

}
