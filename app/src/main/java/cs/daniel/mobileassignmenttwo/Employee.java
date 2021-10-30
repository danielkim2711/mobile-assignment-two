package cs.daniel.mobileassignmenttwo;

public class Employee {
    String country, email, name, position;

    Employee() {

    }
    public Employee(String country, String email, String name, String position) {
        this.country = country;
        this.email = email;
        this.name = name;
        this.position = position;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
