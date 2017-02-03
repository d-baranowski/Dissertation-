package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

/**
 * Information about the user of the application. Candidates aren't stored in this table.
 */
public final class User {
    private final String id;
    private final String name;
    private final String surname;
    private final String login;
    private final String password;

    private User(Builder builder) {
        id = builder.id;
        name = builder.name;
        surname = builder.surname;
        login = builder.login;
        password = builder.password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) { //NOSONAR
        if (this == o) return true; //NOSONAR
        if (o == null || getClass() != o.getClass()) return false; //NOSONAR
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && //NOSONAR
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getSurname(), user.getSurname()) &&
                Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getLogin(), getPassword());
    }

    public static class Builder {
        private String surname;
        private String name;
        private String id;
        private String login;
        private String password;

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
