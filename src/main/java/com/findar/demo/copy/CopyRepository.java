package com.findar.demo.copy;

import com.findar.demo.entity.Copy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface CopyRepository extends CrudRepository<Copy, Integer> {

    int countByBookId(int bookId);

    Stream<Copy> streamAllByBookId(int bookId);

    boolean existsByIdAndStatusId(int id, int status);

}
