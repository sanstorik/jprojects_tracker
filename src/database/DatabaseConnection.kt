package database

import activities.Activity

/**
 * Main source of interaction with database.
 * @param T type of object to be stored
 */
interface DatabaseConnection <T>  where T : Activity {
    /**
     * Store [obj] in the file.
     * @param obj stored objects
     * @param holderName name of the file that holds data,
     * for example holderJson.txt, sqliteHolder.db
     * @return false if couln't store object
     */
    fun save(obj : Set<T>, holderName : String) : Boolean

    /**
     * Reading objects from file
     * @param holderName name of the file that holds data.
     * @return retrieved object
     */
    fun read(holderName: String) : Set<T>
}