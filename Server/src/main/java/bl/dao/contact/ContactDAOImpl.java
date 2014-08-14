package bl.dao.contact;

/**
 * Created by Margarita on 14.08.2014.
 */
public class ContactDAOImpl implements ContactDAO {
    private static ContactDAOImpl ourInstance = new ContactDAOImpl();

    public static ContactDAOImpl getInstance() {
        return ourInstance;
    }

    private ContactDAOImpl() {
    }
}
