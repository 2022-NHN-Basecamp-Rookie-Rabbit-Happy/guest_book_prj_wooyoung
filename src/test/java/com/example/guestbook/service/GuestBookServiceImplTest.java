package com.example.guestbook.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.guestbook.dto.GuestBookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestBookServiceImplTest {

    @Autowired
    private GuestBookService service;

    @Test
    public void testRegister() {

        GuestBookDTO guestBookDTO = GuestBookDTO.builder()
            .title("Sample Title...")
            .content("Sample Content...")
            .writer("user0")
            .build();

        System.out.println(service.register(guestBookDTO));

    }
}