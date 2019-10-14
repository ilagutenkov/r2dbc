package com.example.syncdb.repository;

import com.example.dbmodel.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  PersonRepository  extends JpaRepository<Person,Long> {
}
