package com.example.guestbook.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.guestbook.entity.GuestBook;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestBookRepositoryTest {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            GuestBook guestBook = GuestBook.builder()
                .title("Title..." + i)
                .content("Content..." + i)
                .writer("user" + (i % 10))
                .build();

            System.out.println(guestBookRepository.save(guestBook));
        });
    }

    @Test
    public void updateTest() {

        Optional<GuestBook> result = guestBookRepository.findById(300L);

        if (result.isPresent()) {

            GuestBook guestBook = result.get();

            guestBook.setTitle("Changed Title...");
            guestBook.setContent("Changed Content...");

            guestBookRepository.save(guestBook);
        }
    }

}