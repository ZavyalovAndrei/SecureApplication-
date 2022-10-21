package com.zavialov.secureApplication.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Persons {


    @EmbeddedId
    private PersonalData personalData;

    private String phoneNumber;

    private String city;
}