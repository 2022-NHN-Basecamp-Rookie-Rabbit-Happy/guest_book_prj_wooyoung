package com.example.guestbook.service;

import com.example.guestbook.dto.GuestBookDTO;
import com.example.guestbook.entity.GuestBook;

public interface GuestBookService {

    Long register(GuestBookDTO dto);

    default GuestBook dtoToEntity(GuestBookDTO dto) {
        GuestBook entity = GuestBook.builder()
            .gno(dto.getGno())
            .title(dto.getTitle())
            .content(dto.getContent())
            .writer(dto.getWriter())
            .build();
        return entity;
    }
}
