package database

import activities.Activity

/**
 * Main source of interaction with database.
 * @param T type of object to be stored
 */
interface DatabaseConnection <T>  where T : Activity {
    /**
     * Store [obj] in the file. Creates new file if
     * it didn't exist before.
     * @param obj stored objects
     * @param holderName name of the file that holds data,
     * for example holderJson.txt, sqliteHolder.db
     */
    fun save(obj : Set<T>, holderName : String)

    /**
     * Reading objects from file
     * @param holderName name of the file that holds data.
     * @throws NoSuchFileException no file to read json
     * @return retrieved object
     */
    fun read(holderName: String) : Set<T>
}