package com.example.guestbook.service;

import com.example.guestbook.dto.GuestBookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.GuestBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestBookServiceTest {

    @Autowired
    private GuestBookService guestBookService;

    @Test
    public void testRegister() {

        GuestBookDTO guestBookDTO = GuestBookDTO.builder()
            .title("Sample Title...")
            .content("Sample Content...")
            .writer("user0")
            .build();

        System.out.println(guestBookService.register(guestBookDTO));

    }

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
            .page(1)
            .size(10)
            .build();

        PageResultDTO<GuestBookDTO, GuestBook> resultDTO = guestBookService.getList(pageRequestDTO);

        System.out.println("PREV: " + resultDTO.isPrevEnable());
        System.out.println("NEXT: " + resultDTO.isNextEnable());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("--------------------------------------");
        for (GuestBookDTO guestBookDTO : resultDTO.getDtoList()) {
            System.out.println(guestBookDTO);
        }

        System.out.println("======================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));

        for (GuestBookDTO guestBookDTO : resultDTO.getDtoList()) {
            System.out.println(guestBookDTO);
        }

    }

    @Test
    public void testSearch() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
            .page(1)
            .size(10)
            .type("tc") // 검색 조건
            .keyword("방명록")
            .build();

        PageResultDTO<GuestBookDTO, GuestBook> resultDTO = guestBookService.getList(pageRequestDTO);

        System.out.println("PREV: " + resultDTO.isPrevEnable());
        System.out.println("NEXT: " + resultDTO.isNextEnable());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("-----------------------------");
        for (GuestBookDTO guestBookDTO : resultDTO.getDtoList()) {
            System.out.println(guestBookDTO);
        }
        System.out.println("=============================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}