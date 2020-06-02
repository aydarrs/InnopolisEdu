package part2.lesson15.task01.dao;

import java.util.List;

/**
 * GeneralDao.
 * Database working interface.
 * @author Aydar_Safiullin
 */
public interface GeneralDao<T> {
    /**
     * Add collection of objects.
     * @param items - collection of added objects.
     * @return id of added objects
     */
    List<Long> addNewItem(List<T> items);

    /**
     * Looking for and get object.
     * @param id - id of object in table.
     * @return needed object and reduce it's quantity int database.
     */
    T getItemById(long id);

    /**
     * Change a variableValue of object.
     * @param id - id of needed object.
     * @param variableValue - change variableValue.
     * @return true, if variableValue has changed. Else return false.
     */
    boolean updateItemById(long id, int variableValue);

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
}
