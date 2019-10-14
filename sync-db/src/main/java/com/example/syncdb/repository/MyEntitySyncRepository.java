package com.example.syncdb.repository;

import com.example.dbmodel.MyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyEntitySyncRepository extends JpaRepository<MyEntity, Long> {
}
