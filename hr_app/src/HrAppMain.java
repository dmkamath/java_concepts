import models.Department;
import models.Employee;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class HrAppMain {
  public static void main(String[] args) {
    System.out.println("Hello world!");

    Department development = new Department();
    development.setDepartmentId("D001");
    development.setName("Development");
    development.setLocation("Building A");

    Employee alice = new Employee();
    alice.setEmployeeId("E001");
    alice.setName("Alice Johnson");
    alice.setTitle("Software Engineer");
    alice.setDepartment(development);

    Employee bob = new Employee();
    bob.setEmployeeId("E002");
    bob.setName("Bob Smith");
    bob.setTitle("Senior Software Engineer");
    bob.setDepartment(development);

    Employee charlie = new Employee();
    charlie.setEmployeeId("E003");
    charlie.setName("Charlie Brown");
    charlie.setTitle("DevOps Engineer");
    charlie.setDepartment(development);

    Employee developmentManager = new Employee();
    developmentManager.setEmployeeId("E100");
    developmentManager.setName("Diana Prince");
    developmentManager.setTitle("Development Manager");

    development.setManager(developmentManager);
    development.setEmployees(List.of(alice, bob, charlie));

    Department sales = new Department();
    sales.setDepartmentId("D002");
    sales.setName("Sales");
    sales.setLocation("Building B");

    Employee eve = new Employee();
    eve.setEmployeeId("E004");
    eve.setName("Eve Adams");
    eve.setTitle("Sales Executive");
    eve.setDepartment(sales);

    Employee frank = new Employee();
    frank.setEmployeeId("E005");
    frank.setName("Frank Miller");
    frank.setTitle("Account Manager");
    frank.setDepartment(sales);

    Employee heidi = new Employee();
    heidi.setEmployeeId("E006");
    heidi.setName("Heidi Wilson");
    heidi.setTitle("Sales Intern");
    heidi.setDepartment(sales);

    Employee salesManager = new Employee();
    salesManager.setEmployeeId("E101");
    salesManager.setName("Grace Lee");
    salesManager.setTitle("Sales Manager");

    sales.setManager(salesManager);
    sales.setEmployees(List.of(eve, frank, heidi));

    List<Department> allDepartments = List.of(development, sales);
    List<Employee> allEmployees = new java.util.ArrayList<>();
    allEmployees.addAll(development.getEmployees());
    allEmployees.addAll(sales.getEmployees());
    //    List<Employee> allEmployees = departments.stream()
    //        .flatMap(dept -> dept.getEmployees().stream())
    //        .toList();

    // Write departments to CSV in the current working directory
    Path deptFile = Paths.get("all_departments.csv");
    try {
      writeDepartmentsToCsv(allDepartments, deptFile);
      System.out.println("Wrote departments CSV to: " + deptFile.toAbsolutePath());
    } catch (IOException e) {
      System.err.println("Failed to write CSV: " + e.getMessage());
    }

    // Write employees to CSV in the current working directory
    Path empFile = Paths.get("all_employees.csv");
    try {
      writeEmployeesToCsv(allEmployees, empFile);
      System.out.println("Wrote employees CSV to: " + empFile.toAbsolutePath());
    } catch (IOException e) {
      System.err.println("Failed to write CSV: " + e.getMessage());
    }

    // Write departments to CSV in the current working directory
    Path deptWithEmpFile = Paths.get("all_departments_with_employees.csv");
    try {
      writeDepartmentsWithEmployeesToCsv(allDepartments, deptWithEmpFile);
      System.out.println(
          "Wrote departments with employees CSV to: " + deptWithEmpFile.toAbsolutePath());
    } catch (IOException e) {
      System.err.println("Failed to write CSV: " + e.getMessage());
    }
  }

  // Writes one CSV row per department and manager info and employee count
  private static void writeDepartmentsToCsv(List<Department> departments, Path path)
      throws IOException {
    List<String> lines = new ArrayList<>();
    // header
    lines.add(
        "departmentId,departmentName,departmentLocation,managerId,department_managerName,department_employeeCount");

    for (Department d : departments) {
      String deptId = d.getDepartmentId();
      String deptName = d.getName();
      String deptLocation = d.getLocation();
      Employee mgr = d.getManager();
      String mgrId = mgr == null ? "" : mgr.getEmployeeId();
      String mgrName = mgr == null ? "" : mgr.getName();

      List<Employee> emps = d.getEmployees();
      if (emps == null || emps.isEmpty()) {
        // write a department row with empty employee fields
        String row =
            String.join(
                ",",
                escapeCsv(deptId),
                escapeCsv(deptName),
                escapeCsv(deptLocation),
                escapeCsv(mgrId),
                escapeCsv(mgrName),
                "",
                "",
                "");
        lines.add(row);
      } else {
        // write a single department row with employee count
        String row =
            String.join(
                ",",
                escapeCsv(deptId),
                escapeCsv(deptName),
                escapeCsv(deptLocation),
                escapeCsv(mgrId),
                escapeCsv(mgrName),
                String.valueOf(emps.size()));
        lines.add(row);
      }
    }

    // Ensure parent directories exist (if any) and write the file
    Path parent = path.toAbsolutePath().getParent();
    if (parent != null) {
      Files.createDirectories(parent);
    }
    Files.write(path, lines);
  }

  private static void writeEmployeesToCsv(List<Employee> employees, Path path) throws IOException {
    List<String> lines = new ArrayList<>();
    // header
    lines.add("departmentId,employeeId,employeeName,employeeTitle");

    for (Employee e : employees) {
      String deptId = e.getDepartment() == null ? "" : e.getDepartment().getDepartmentId();
      String empId = e.getEmployeeId();
      String empName = e.getName();
      String empTitle = e.getTitle();

      String row =
          String.join(
              ",", escapeCsv(deptId), escapeCsv(empId), escapeCsv(empName), escapeCsv(empTitle));
      lines.add(row);
    }

    // Ensure parent directories exist (if any) and write the file
    Path parent = path.toAbsolutePath().getParent();
    if (parent != null) {
      Files.createDirectories(parent);
    }
    Files.write(path, lines);
  }

  // Writes one CSV row per employee under each department with manager info
  private static void writeDepartmentsWithEmployeesToCsv(List<Department> departments, Path path)
      throws IOException {
    List<String> lines = new ArrayList<>();
    // header
    lines.add(
        "departmentId,departmentName,departmentLocation,managerId,managerName,employeeId,employeeName,employeeTitle");

    for (Department d : departments) {
      String deptId = d.getDepartmentId();
      String deptName = d.getName();
      String deptLocation = d.getLocation();
      Employee mgr = d.getManager();
      String mgrId = mgr == null ? "" : mgr.getEmployeeId();
      String mgrName = mgr == null ? "" : mgr.getName();

      List<Employee> emps = d.getEmployees();
      if (emps == null || emps.isEmpty()) {
        // write a department row with empty employee fields
        String row =
            String.join(
                ",",
                escapeCsv(deptId),
                escapeCsv(deptName),
                escapeCsv(deptLocation),
                escapeCsv(mgrId),
                escapeCsv(mgrName),
                "",
                "",
                "");
        lines.add(row);
      } else {
        for (Employee e : emps) {
          String row =
              String.join(
                  ",",
                  escapeCsv(deptId),
                  escapeCsv(deptName),
                  escapeCsv(deptLocation),
                  escapeCsv(mgrId),
                  escapeCsv(mgrName),
                  escapeCsv(e == null ? "" : e.getEmployeeId()),
                  escapeCsv(e == null ? "" : e.getName()),
                  escapeCsv(e == null ? "" : e.getTitle()));
          lines.add(row);
        }
      }
    }

    // Ensure parent directories exist (if any) and write the file
    Path parent = path.toAbsolutePath().getParent();
    if (parent != null) {
      Files.createDirectories(parent);
    }
    Files.write(path, lines);
  }

  // Minimal CSV escaping: wraps fields containing commas/quotes/newlines in double quotes and
  // doubles internal quotes
  private static String escapeCsv(String s) {
    if (s == null) return "";
    boolean needsQuotes =
        s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r");
    if (!needsQuotes) return s;
    return "\"" + s.replace("\"", "\"\"") + "\"";
  }
}
