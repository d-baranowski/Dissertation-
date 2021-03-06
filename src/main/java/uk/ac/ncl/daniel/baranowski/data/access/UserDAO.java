package uk.ac.ncl.daniel.baranowski.data.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Role;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.User;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@DataAccessObject
public class UserDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int getCount() {
        final String sql = String.format("select count(*) from %s", TableNames.USER);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<User> getAllUsers() {
        final String sql = String.format(
                "SELECT * FROM %s", TableNames.USER);

        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
        List<User> users = new ArrayList<>();
        for (Map<String, Object> row : resultList) {
            users.add(mapUser(row));
        }

        return users;
    }

    public List<User> getModuleLeaders(int moduleId) {
        final String sql = String.format(
                "SELECT u.`_id`, u.surname, u.name From %s u LEFT JOIN %s ml ON ml.userId = u.`_id` WHERE moduleId = ?",
                TableNames.USER, TableNames.MODULE_LEADER);

        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, moduleId);
        List<User> users = new ArrayList<>();
        for (Map<String, Object> row : resultList) {
            users.add(mapUser(row));
        }

        return users;
    }

    public User readUser(String objId) {
        final String sql = String.format("SELECT * FROM %s t WHERE t._id = ?", TableNames.USER);
        return mapUser(jdbcTemplate.queryForMap(sql, objId));
    }

    public User readUserByLogin(String login) {
        final String sql = String.format("SELECT * FROM %s t WHERE t.login = ?", TableNames.USER);
        return mapUser(jdbcTemplate.queryForMap(sql, login));
    }

    public int getRolesCount() {
        final String sql = String.format("select count(*) from %s", TableNames.ROLE);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Role getRoleById(String objId) {
        final String sql = String.format("SELECT * FROM %s role WHERE role.referenceName = ?", TableNames.ROLE);
        return mapRole(jdbcTemplate.queryForMap(sql, objId));
    }

    public List<Role> getAllRoles() {
        final String sql = String.format("SELECT * FROM %s", TableNames.ROLE);
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
        List<Role> roles = new ArrayList<>();

        for (Map<String, Object> row : resultList) {
            roles.add(mapRole(row));
        }

        return roles;
    }

    public List<Role> getRolesByUserId(String userId) {
        final String sql = String.format(
                "SELECT r.referenceName " + "FROM %s u, %s ur, %s r " + "WHERE ur.UserId = ?"
                        + "AND ur.UserId = u._id AND r.referenceName = ur.roleId",
                TableNames.USER, TableNames.USERROLE, TableNames.ROLE);

        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, userId);
        List<Role> roles = new ArrayList<>();

        for (Map<String, Object> row : resultList) {
            roles.add(mapRole(row));
        }

        return roles;
    }

    private User mapUser(Map<String, Object> row) {
        return new User.Builder()
                .setId((String) row.get("_id"))
                .setName((String) row.get("name"))
                .setSurname((String) row.get("surname"))
                .setLogin((String) row.get("login"))
                .setPassword((String) row.get("password"))
                .build();
    }

    private Role mapRole(Map<String, Object> row) {
        return new Role.Builder().setName((String) row.get("referenceName")).build();
    }
}

