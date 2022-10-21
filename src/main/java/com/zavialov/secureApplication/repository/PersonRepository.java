package com.zavialov.secureApplication.repository;

import com.zavialov.secureApplication.entity.PersonalData;
import com.zavialov.secureApplication.entity.Persons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Persons, PersonalData> {

    @Query("Select p from Persons p where p.city = :city")
    List<Persons>filterByCity(@Param("city") String name);

    @Query("select p from Persons p where p.personalData.age < :age order by p.personalData.age")
    List<Persons> filterByAge(@Param("age") int age);

    @Query("select p from Persons p where p.personalData.name = :name and p.personalData.surname = :surname")
    Optional<Persons> filterByNameAndSurname(@Param("name") String name, @Param("surname") String surname);
}