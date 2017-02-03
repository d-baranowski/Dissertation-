package uk.ac.ncl.daniel.baranowski.models;

import uk.ac.ncl.daniel.baranowski.common.enums.Roles;

import java.util.List;
import java.util.Objects;

public final class UserModel extends UserReferenceModel {
    private String login;
    private String password;
    private List<Roles> roles;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    /* Read this: http://www.artima.com/lejava/articles/equality.html Pitfall #4 */
    @Override
    public final boolean canEqual(Object other) {
        return other instanceof UserModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o.getClass() == this.getClass())) return false;  //NOSONAR
        if (!super.equals(o)) return false;
        UserModel that = (UserModel) o;
        return Objects.equals(getLogin(), that.getLogin()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getRoles(), that.getRoles()) &&
                that.canEqual(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLogin(), getPassword(), getRoles());
    }

}
