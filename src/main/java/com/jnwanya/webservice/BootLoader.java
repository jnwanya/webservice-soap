package com.jnwanya.webservice;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
/**
 * Created by jnwanya on
 * Sat, 03 Oct, 2020
 */

@Component
@AllArgsConstructor
public class BootLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final HajjServiceEncryption hajjServiceEncryption;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        testEncryption();
    }

    private void testEncryption() {
        try {
            String name = "My name is Justin";
            String encryptedResponse  = hajjServiceEncryption.encrypt(name);
            System.out.println("encryptedResponse - "+encryptedResponse);
            String decryptedResponse = hajjServiceEncryption.decrypt(encryptedResponse);
            System.out.println("decryptedResponse - "+decryptedResponse);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
