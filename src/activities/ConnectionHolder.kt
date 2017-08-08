package activities

import database.DatabaseConnection

/**
 * Template for instance, that holds
 * connection for activity.
 */
abstract class ConnectionHolder <T> (
        protected val defaultHolderName : String,
        protected var connection : DatabaseConnection<T>
) where T : Activity {

    /**
     * copy data to other source
     */
    public fun export(fileName : String) {
        val objSet = connection.read(defaultHolderName)
        connection.save(objSet, fileName)
    }

    /**
     * recieve data from other source
     */
    public abstract fun import(fileName: String)
}