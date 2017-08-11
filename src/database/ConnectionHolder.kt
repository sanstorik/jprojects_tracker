package database

import activities.Activity
import java.io.File

/**
 * Template for instance, that holds
 * connection for activity.
 */
abstract class ConnectionHolder <T> (
        protected val _defaultHolderName: String,
        protected var _connection: DatabaseConnection<T>
) where T : Activity {

    /**
     * copy data to other source
     */
    public fun export(fileName : String) {
        val objSet = _connection.read(_defaultHolderName)
        _connection.save(objSet, fileName)
    }

    /**
     * recieve data from other source
     */
    public abstract fun import(fileName: String)

    protected fun initStartingConfiguration() {
        val file = File(_defaultHolderName)

        if (file.exists() && !file.isDirectory) {
            import(_defaultHolderName)
        } else {
            file.createNewFile()
        }
    }
}