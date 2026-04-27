package com.library.db.impl;

import com.library.db.EmployeeDao;
import com.library.model.Employee;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class EmployeeDaoImpl implements EmployeeDao {

    private final NamedParameterJdbcTemplate jdbc;

    private static final String CREATE_SQL = """
            INSERT INTO employee (employee_id, f_name, l_name, email, phone_number)
            VALUES (:employeeId, :fName, :lName, :email, :phoneNumber)
            """;

    public EmployeeDaoImpl(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("employeeId", employee.getEmployeeId())
                .addValue("fName", employee.getfName())
                .addValue("lName", employee.getlName())
                .addValue("email", employee.getEmail())
                .addValue("phoneNumber", employee.getPhoneNumber());

        jdbc.update(CREATE_SQL, params);
        return employee;
    }
}
