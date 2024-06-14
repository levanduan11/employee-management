package com.coding.employee;

import com.coding.domain.model.Employee;
import net.datafaker.Faker;

import java.math.BigDecimal;

public class EmployeeFactory {
    static final Faker faker = new Faker();

    public static Employee create(){
        Employee employee = new Employee();
        employee.setFirstName(faker.name().firstName());
        employee.setLastName(faker.name().lastName());
        employee.setPhone(faker.phoneNumber().phoneNumber());
        employee.setImageUrl(faker.internet().url());
        employee.setGender(faker.demographic().sex());
        employee.setBirthDate(faker.date().birthday().toLocalDateTime().toLocalDate());
        employee.setSalary(BigDecimal.valueOf(faker.number().numberBetween(1000, 10000)));
        employee.setHireDate(faker.date().birthday().toLocalDateTime().toLocalDate());
        return employee;
    }
}
