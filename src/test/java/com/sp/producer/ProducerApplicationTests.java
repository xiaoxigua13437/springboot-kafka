package com.sp.producer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;

@SpringBootTest
class ProducerApplicationTests {

    @Test
    void contextLoads() {

        String version = SpringVersion.getVersion();
        System.out.println(version+"/n");

        String version1 = SpringBootVersion.getVersion();
        System.out.println(version1);

    }

}
