package com.minidwep.wasteSorting;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GarbageClassificationApplicationTests {
    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    void contextLoads() {
        String encryptPwd = stringEncryptor.encrypt("GAGdJIzU6k$Pcxe^");
        System.out.println("加密:：" + encryptPwd);
        System.out.println(stringEncryptor.decrypt("DIYk8lQhfr6TM5qVjsBi0g=="));
    }

}
