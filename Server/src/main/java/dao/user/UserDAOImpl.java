package dao.user;

/**
 * Created by Margarita on 14.08.2014.
 */
public class UserDAOImpl implements UserDAO {
    private static UserDAOImpl ourInstance = new UserDAOImpl();

    public static UserDAOImpl getInstance() {
        return ourInstance;
    }

    private UserDAOImpl() {
    }
}
