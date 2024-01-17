package com.findar.demo.book;

import com.findar.demo.entity.BookCopyView;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BookCopyRepository extends CrudRepository<BookCopyView, Integer> {

    @Query("SELECT b.id, b.title, b.author, b.isbn, COUNT(c.id) as 'copy_count' " +
            "FROM Book b JOIN Copy c ON c.book_id = b.id " +
            "GROUP BY b.id")
    Collection<BookCopyView> findAllBooks();

}
