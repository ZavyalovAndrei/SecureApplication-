package com.zavialov.secureApplication.repository;

import com.zavialov.secureApplication.entity.PersonalData;
import com.zavialov.secureApplication.entity.Persons;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class Repository implements CommandLineRunner {
    private final PersonRepository personRepository;

    public List<Persons> getPersonsByCity(String city) {
        return personRepository.filterByCity(city);
    }

    public List<Persons> getPersonsByAge(int age) {
        return personRepository.filterByAge(age);
    }

    public Optional<Persons> getPersonsByNameAndSurname(String name, String surname) {
        return personRepository.filterByNameAndSurname(name, surname);
    }

    @Override
    @Transactional
    public void run(String... args) {
        var persons = Stream.of(Persons.builder().personalData(PersonalData.builder().name("Miron")
                        .surname("Fedorov").age(37).build()).phoneNumber("9643675").city("Saint-Petersburg").build(),
                Persons.builder().personalData(PersonalData.builder().name("Aleksei").surname("Uzenjuk").age(28)
                        .build()).phoneNumber("7852323").city("Moscow").build(),
                Persons.builder().personalData(PersonalData.builder().name("Anessa").surname("Lippa").age(27).build())
                        .phoneNumber("45236856").city("Westminster").build(),
                Persons.builder().personalData(PersonalData.builder().name("Vladimir").surname("Kotlyarov").age(34)
                        .build()).phoneNumber("1120568").city("Moscow").build(),
                Persons.builder().personalData(PersonalData.builder().name("Billie")
                                .surname("Eilish Pirate Baird O'Connell").age(22).build()).phoneNumber("6687993")
                        .city("Los-Angeles").build(),
                Persons.builder().personalData(PersonalData.builder().name("Niccolò").surname("Moriconi").age(26)
                        .build()).phoneNumber("1351668").city("Napoli").build(),
                Persons.builder().personalData(PersonalData.builder().name("Laura").surname("Pergolizzi").age(41)
                        .build()).phoneNumber("41615656").city("Milano").build(),
                Persons.builder().personalData(PersonalData.builder().name("Julia").surname("Zivert").age(31).build())
                        .phoneNumber("3598568552").city("Moscow").build(),
                Persons.builder().personalData(PersonalData.builder().name("Robyn Rihanna").surname("Fenty").age(34)
                        .build()).phoneNumber("652006688").city("New-York").build(),
                Persons.builder().personalData(PersonalData.builder().name("Aleksandr").surname("Vasiliev").age(53)
                        .build()).phoneNumber("21300567441").city("Saint-Petersburg").build(),
                Persons.builder().personalData(PersonalData.builder().name("Brian Hugh").surname("Warner").age(53)
                        .build()).phoneNumber("6565656222").city("Kanton").build()).toList();
        personRepository.saveAll(persons);
    }
}