package com.practice.springbootkafkaproducer.model;

public class Person {

    private String name;
    private String city;
    private Integer age;
    private Double salary;

    public Person() {
    }

    public Person(String name, String city, Integer age, Double salary) {
        this.name = name;
        this.city = city;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
