import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class UserDaoTest {
  private UserDao dao;

  @Before
  public void setUp() throws Exception {
    dao = new UserDao(DBI.open(new H2DataSource()));
  }

  @After
  public void tearDown() throws Exception {
    dao.close();
  }

  @Test
  public void registerAndFindByUsernameAndPassword() {
    dao.register("foo", "bar");
    Optional<User> user = dao.findByUsernameAndPassword("foo", "bar");
    assertTrue(user.isPresent());
    assertEquals("foo", user.get().getUsername());
    assertEquals("bar", user.get().getPassword());
  }

  @Test
  public void updatePassword() {
    dao.register("foo", "bar");
    Optional<User> user = dao

    findByUsernameAndPassword("foo", "bar");
    assertTrue(user.isPresent());
    dao.updatePassword(user.get().getId(), "baz");
    Optional<User> updatedUser = dao.findByUsernameAndPassword("foo", "baz");
    assertTrue(updatedUser.isPresent());
  }
}