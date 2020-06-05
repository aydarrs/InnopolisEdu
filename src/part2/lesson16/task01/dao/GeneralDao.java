package part2.lesson16.task01.dao;

import java.util.List;

/**
 * GeneralDao.
 * Database working interface.
 * @author Aydar_Safiullin
 */
public interface GeneralDao<T> {
    /**
     * Add collection of object.
     * @param item - added object.
     * @return id of added object.
     */
    long addNewItem(T item);

    /**
     * Looking for and get object.
     * @param id - id of object in table.
     * @return needed object and reduce it's quantity int database.
     */
    T getItemById(long id);

    /**
     * Change an object.
     * @param item - updating object.
     * @return true, if variableValue has changed. Else return false.
     */
    boolean updateItemByObject(T item);

    /**
     * Delete object by it's id.
     * @param id - id of deleted object.
     * @return true, if object has deleted. Else return false.
     */
    boolean deleteItemById(long id);

    /**
     * @return all id's of table items.
     */
    List<Integer> getDatabaseItemsIdList();

    /**
     * Reset dataBase.
     */
    void reset();

    /**
     * @return table name.
     */
    String getTableName();
}
