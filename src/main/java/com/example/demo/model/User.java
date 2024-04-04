package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;

@Entity
@Table(name = "users", schema = "testdb")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotEmpty(message = "Field 'name' cannot be empty!")
    @Size(min = 2, max = 24, message = "Field 'name' cannot be shorter than 2 and longer than 24 characters!")
    private String name;

    @Column(name = "surname", nullable = false)
    @NotEmpty(message = "Field 'surname' cannot be empty!")
    @Size(min = 2, max = 24, message = "Field 'surname' cannot be shorter than 2 and longer than 24 characters!")
    private String surname;

    @Column(name = "age", nullable = false)
    @Min(value = 0, message = "Field 'age' cannot be lower than 0!")
    @Max(value = 120, message = "Field 'age' cannot be higher than 120!")
    private Byte age;

    @Column(name = "email", nullable = false)
    @Email(message = "Field 'email' is incorrect!")
    private String email;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Field 'password' is incorrect!")
    @Size(min = 4, max = 24, message = "Field 'password' cannot be shorter than 4 and longer than 24 characters!")
    private String password;

    public User() {

    }

    public User(String name, String surname, Byte age, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return age.equals(user.age)
                && id.equals(user.id)
                && name.equals(user.name)
                && surname.equals(user.surname)
                && email.equals(user.email)
                && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + age.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}