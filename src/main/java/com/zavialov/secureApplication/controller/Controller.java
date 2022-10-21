package com.zavialov.secureApplication.controller;

import com.zavialov.secureApplication.entity.Persons;
import com.zavialov.secureApplication.exeption.EmptyRequestExeption;
import com.zavialov.secureApplication.exeption.NotFindExeption;
import com.zavialov.secureApplication.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/")
@RestController
public class Controller {

    private final Repository repository;

    public Controller(Repository repository) {
        this.repository = repository;
    }

    @GetMapping("/by-city")
    public String getByCity(@RequestParam(name = "city", required = false) String city) {
        StringBuilder builder = new StringBuilder();
        if (city == null) {
            return
                    "<html> " +
                            "<body>" +
                            "<h1>Search users by city</h1>" +
                            "<form action=\"/by-city\" method=\"get\" target=\"_self\">" +
                            "<label for=\"city\">Enter the name of the city:</label>" +
                            "<input type=\"text\" id=\"city\" name=\"city\">" +
                            "<br><br>" +
                            "<button type=\"submit\">Confirm city</button>" +
                            "</form>" +
                            "</body>" +
                            "</html>";
        }
        if (city.isEmpty()) {
            throw new EmptyRequestExeption("Field \"city\" is empty!");
        }
        List<Persons> personsList = repository.getPersonsByCity(city);
        if (personsList.isEmpty()) {
            throw new NotFindExeption("Unknown city " + city + "!");
        } else {
            for (Persons person : personsList) {
                builder
                        .append("<b style=\"font-size:120%; margin-left: 20px; color:#4B0082\";>")
                        .append(person.getPersonalData().getName())
                        .append(" ")
                        .append(person.getPersonalData().getSurname())
                        .append("</b>")
                        .append("<br>");
            }
        }
        return
                "<html>" +
                        "<body>" +
                        "<h1>Search user by city result:</h1>" + builder + "</font>" +
                        "</body>" +
                        "</html>";
    }

    @GetMapping("/by-age")
    public String getByAge(@RequestParam(name = "age", required = false) String responseAge) {
        StringBuilder builder = new StringBuilder();
        if (responseAge == null) {
            return
                    "<html>" +
                            "<body>" +
                            "<h1>Search users by max age</h1>" +
                            "<form action=\"/by-age\" method=\"get\" target=\"_self\">" +
                            "<label for=\"age\">Enter the max person age:</label>" +
                            "<input type=\"text\" id=\"age\" name=\"age\">" +
                            "<br><br>" +
                            "<button type=\"submit\">Confirm max age</button>" +
                            "</form>" +
                            "</body>" +
                            "</html>";
        }
        if (responseAge.isEmpty()) {
            throw new EmptyRequestExeption("Field \"age\" is empty!");
        } else {
            int age = checkEntry(responseAge);
            List<Persons> personsList = repository.getPersonsByAge(age);
            if (personsList.isEmpty()) {
                throw new NotFindExeption("Not found persons with age " + age + "!");
            } else {
                for (Persons person : personsList) {
                    builder
                            .append("<b style=\"font-size:120%; margin-left: 20px; color:#4B0082\";>")
                            .append(person.getPersonalData().getAge())
                            .append("   ")
                            .append(person.getPersonalData().getName())
                            .append(" ")
                            .append(person.getPersonalData().getSurname())
                            .append("</b>")
                            .append("<br>");
                }
            }
        }
        return
                "<html>" +
                        "<body>" +
                        "<h1>Search user by age result:</h1>" + builder + "</font>" +
                        "</body>" +
                        "</html>";
    }

    @GetMapping("/by-name-surname")
    public String getByNameAndSurname(@RequestParam(name = "name", required = false) String name,
                                      @RequestParam(name = "surname", required = false) String surname) {
        if (name == null && surname == null) {
            return
                    "<html> " +
                            "<body>" +
                            "<h1>Search user by name and surname</h1>" +
                            "<form action=\"/by-name-surname\" method=\"get\" target=\"_self\">" +
                            "<label for=\"name\">Enter the person`s name:</label>" +
                            "<input type=\"text\" id=\"name\" name=\"name\">" +
                            "<br><br>" +
                            "<label for=\"surname\">Enter the person`s surname:</label>" +
                            "<input type=\"text\" id=\"surname\" name=\"surname\">" +
                            "<br><br>" +
                            "<button type=\"submit\">Confirm person`s data</button>" +
                            "</form>" +
                            "</form>" +
                            "</body>" +
                            "</html>";
        }
        StringBuilder builder = new StringBuilder();
        if (name.isEmpty() || surname.isEmpty()) {
            throw new EmptyRequestExeption("Field \"name\" or \"surname\" is empty!");
        } else {
            Optional<Persons> filteredData = repository.getPersonsByNameAndSurname(name, surname);
            if (filteredData.isEmpty()) {
                throw new NotFindExeption("Not found persons with data " + name + "  " + surname + " !");
            } else {
                List<Persons> personsList = filteredData.stream().toList();
                for (Persons person : personsList) {
                    builder
                            .append("<b style=\"font-size:120%; margin-left: 20px; color:#4B0082\";>")
                            .append(person.getPersonalData().getAge())
                            .append("   ")
                            .append(person.getPersonalData().getName())
                            .append(" ")
                            .append(person.getPersonalData().getSurname())
                            .append("</b>")
                            .append("<br>");
                }
            }
        }
        return
                "<html>" +
                        "<body>" +
                        "<h1>Search user by name and surname result:</h1>" + builder + "</font>" +
                        "</body>" +
                        "</html>";
    }

    @GetMapping("/")
    public String getPersonMenu() {
        return
                "<html>" +
                        "<head>" +
                        "<h1>Persons search menu</h1>" +
                        "</head>" +
                        "<body>" +
                        "<form action=\"/by-age\">" +
                        "<button type=\"submit\">Search by age</button>" +
                        "</form>" +
                        "<br>" +
                        "<form action=\"/by-city\">" +
                        "<button type=\"submit\">Search by city</button>" +
                        "</form>" +
                        "<br>" +
                        "<form action=\"/by-name-surname\">" +
                        "<button type=\"submit\">Search by name & surname</button>" +
                        "</form>" +
                        "</body>" +
                        "</html>";
    }

    @ExceptionHandler(EmptyRequestExeption.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String resolveEmptyRequestException(EmptyRequestExeption exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(NotFindExeption.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String resolveNotFindException(NotFindExeption exception) {
        return exception.getMessage();
    }

    private static int checkEntry(String data) {
        String errorMessage = "Error entry \"" + data + "\"";
        int enter = -1;
        try {
            enter = Integer.parseInt(data);
            if (enter >= 0) {

            } else {
                throw new NotFindExeption(errorMessage);
            }
        } catch (NumberFormatException err) {
            throw new NotFindExeption(errorMessage);
        }
        return enter;
    }
}