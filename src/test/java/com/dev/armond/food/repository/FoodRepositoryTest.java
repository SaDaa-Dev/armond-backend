package com.dev.armond.food.repository;

import com.dev.armond.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FoodRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(FoodRepositoryTest.class);
    @Test
    void addTset() throws Exception {
        //given
        JwtTokenProvider provider = new JwtTokenProvider();
        String token = provider.createAccessToken("sada1223@naver.com", "ADMIN");
        logger.debug("TOKEN = {}", token);

        //when

        //then
    }

}