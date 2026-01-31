import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.List;

public class Main2 {

  public static void main(String[] args) {

    List<Person> roster = Person.createRoster();

    for (Person p : roster) {
      p.printPerson();
    }

    // TODO
    //   search for Persons that are MALE and between ages of 18, 25

    // TODO
    //   BONUS: implement this using streams and lambda expressions

  }

  public static class Person {
    public enum Gender {
      MALE,
      FEMALE
    }

    String name;
    LocalDate birthday;
    Gender gender;
    String emailAddress;

    Person(String nameArg, LocalDate birthdayArg, Gender genderArg, String emailArg) {
      name = nameArg;
      birthday = birthdayArg;
      gender = genderArg;
      emailAddress = emailArg;
    }

    public int getAge() {
      return birthday.until(IsoChronology.INSTANCE.dateNow()).getYears();
    }

    public void printPerson() {
      System.out.println(name + ", " + this.getAge());
    }

    public Gender getGender() {
      return gender;
    }

    public String getName() {
      return name;
    }

    public String getEmailAddress() {
      return emailAddress;
    }

    public LocalDate getBirthday() {
      return birthday;
    }

    public static int compareByAge(Person a, Person b) {
      return a.birthday.compareTo(b.birthday);
    }

    public static List<Person> createRoster() {

      List<Person> roster = new ArrayList<>();
      roster.add(
          new Person(
              "Fred",
              IsoChronology.INSTANCE.date(1980, 6, 20),
              Gender.MALE,
              "fred@example.com"));
      roster.add(
          new Person(
              "Jane",
              IsoChronology.INSTANCE.date(1990, 7, 15),
              Gender.FEMALE,
              "jane@example.com"));
      roster.add(
          new Person(
              "George",
              IsoChronology.INSTANCE.date(1991, 8, 13),
              Gender.MALE,
              "george@example.com"));
      roster.add(
          new Person(
              "Bob", IsoChronology.INSTANCE.date(2000, 9, 12), Gender.MALE, "bob@example.com"));

      return roster;
    }
  }

  interface CheckPerson {
    boolean test(Person p);
  }
}
