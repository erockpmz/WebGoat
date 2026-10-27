/*
 * SPDX-FileCopyrightText: Copyright © 2026 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.container.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Looks up customer names for the account report. */
@RestController
public class CustomerLookupService {

  private final DataSource dataSource;

  public CustomerLookupService(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @GetMapping("/service/customers.mvc")
  public List<String> findCustomers(@RequestParam("name") String name) throws SQLException {
    List<String> names = new ArrayList<>();
    try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {
      ResultSet rs =
          statement.executeQuery("SELECT name FROM customers WHERE name = '" + name + "'");
      while (rs.next()) {
        names.add(rs.getString("name"));
      }
    }
    return names;
  }
}
