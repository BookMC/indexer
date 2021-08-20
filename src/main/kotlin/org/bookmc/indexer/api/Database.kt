package org.bookmc.indexer.api

/**
 * The [Database] is an abstraction from the indexer API
 * to allow custom implementations of a Database. A database
 * should be able to successfully generate/fetch it's data and
 * return it back to the developer unless it has failed then should
 * return null.
 *
 * @author ChachyDev
 * @since 0.0.1
 */
interface Database<Key, Data> {
    /**
     * This is where if the database is a statically generated database
     * then it should generate it's dataset or if it's remotely fetching
     * it's data should prepare for fetching. If this function is not called
     * things may go wrong end-users! Make sure to always call it. Implementations
     * can call it in the constructor for safety but do not have the responsibility
     * to invoke it themselves as it's of the end-user's job. If you would like to add
     * safety you CAN throw an exception stating the database hasn't been statically generated
     * / prepared due to failing to invoke [create]
     */
    suspend fun create()

    /**
     * When the [fetch] function is called it should return
     * fresh / non-cached data. If you are looking for cached data
     * instead move to [get]. If the data failed to be fetched
     * you should throw an exception to state that the operation
     * had failed as some caching systems may not allow for graceful
     * failure.
     * @author ChachyDev
     * @since 0.0.1
     */
    suspend fun fetch(key: Key): Data

    /**
     * [get] has the exact same function as [fetch]
     * apart from the data returned should be cached. Cache times
     * etc. should be either automatically chosen by the implementation
     * or requested via a constructor by the implementation. When rebuilding
     * the cache it should use [fetch] to grab the group again.
     * If the data failed to be retrieved then the function should return
     * null as there is currently no data available you should
     * throw an exeception
     * @author ChachyDev
     * @since 0.0.1
     */
    suspend fun get(key: Key): Data

    companion object {
    }
}