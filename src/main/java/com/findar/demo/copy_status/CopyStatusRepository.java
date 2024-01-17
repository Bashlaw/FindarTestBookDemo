package com.findar.demo.copy_status;

import com.findar.demo.entity.CopyStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopyStatusRepository extends CrudRepository<CopyStatus, Integer> {
}
