package com.example.guestbook.repository;


import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.entity.QGuestBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    @Test
    public void testQuery1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestBook qGuestBook = QGuestBook.guestBook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qGuestBook.title.contains(keyword);
        builder.and(expression);
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);

        result.stream().forEach(guestBook -> {
            System.out.println(guestBook);
        });

    }

    @Test
    public void testQuery2() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestBook qGuestBook = QGuestBook.guestBook;

        String keyword = "1";

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        BooleanExpression isContainKeyword = qGuestBook.title.contains(keyword);
        BooleanExpression isContainContent = qGuestBook.content.contains(keyword);
        BooleanExpression booleanExpressionChain = isContainKeyword.or(isContainContent);

        booleanBuilder
            .and(booleanExpressionChain)
            .and(qGuestBook.gno.gt(0L));

        Page<GuestBook> result = guestBookRepository.findAll(booleanBuilder, pageable);

        result.stream().forEach(guestBook -> {
            System.out.println(guestBook);
        });
    }
}