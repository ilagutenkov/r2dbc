package com.example.syncdb.repository;

import com.example.dbmodel.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  PersonRepository  extends JpaRepository<Person,Long> {

    List<Person> findByDeletedAtNull();

}
