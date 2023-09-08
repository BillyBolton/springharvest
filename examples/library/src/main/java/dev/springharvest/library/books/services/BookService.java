package dev.springharvest.library.books.services;

import dev.springharvest.crud.service.AbstractBaseCrudService;
import dev.springharvest.library.books.mappers.IBookMapper;
import dev.springharvest.library.books.models.dtos.BookDTO;
import dev.springharvest.library.books.models.entities.BookEntity;
import dev.springharvest.library.books.persistence.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService extends AbstractBaseCrudService<BookDTO, BookEntity, Long> {

    @Autowired
    protected BookService(IBookMapper baseMapper, IBookRepository baseRepository) {
        super(baseMapper, baseRepository);
    }
}
