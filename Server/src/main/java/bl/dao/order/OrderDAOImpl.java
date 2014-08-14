package bl.dao.order;

/**
 * Created by Margarita on 14.08.2014.
 */
public class OrderDAOImpl implements OrderDAO {
    private static OrderDAOImpl ourInstance = new OrderDAOImpl();

    public static OrderDAOImpl getInstance() {
        return ourInstance;
    }

    private OrderDAOImpl() {
    }
}
