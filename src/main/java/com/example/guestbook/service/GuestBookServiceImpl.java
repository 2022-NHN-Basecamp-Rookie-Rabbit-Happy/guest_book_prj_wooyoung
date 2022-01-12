package com.example.guestbook.service;

import com.example.guestbook.dto.GuestBookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.repository.GuestBookRepository;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestBookServiceImpl implements GuestBookService {

    private final GuestBookRepository guestBookRepository;

    @Override
    public Long register(GuestBookDTO dto) {

        log.info("DTO----------------");
        log.info(dto);

        GuestBook entity = dtoToEntity(dto);
        log.info(entity);

        guestBookRepository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<GuestBook> result = guestBookRepository.findAll(pageable);

        Function<GuestBook, GuestBookDTO> fn = entity -> entityToDTO(entity);

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestBookDTO read(Long gno) {

        Optional<GuestBook> result = guestBookRepository.findById(gno);

        return result.isPresent() ? entityToDTO(result.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        guestBookRepository.deleteById(gno);
    }

    @Override
    public void modify(GuestBookDTO dto) {

        Optional<GuestBook> result = guestBookRepository.findById(dto.getGno());

        if (result.isPresent()) {
            GuestBook entity = result.get();

            entity.setTitle(dto.getTitle());
            entity.setContent(dto.getContent());

            guestBookRepository.save(entity);
        }
    }
}