package org.bookmc.indexer.impl.maven

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import com.dropbox.android.external.store4.get
import com.google.gson.Gson
import io.ktor.client.request.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.bookmc.indexer.api.Database
import org.bookmc.indexer.impl.maven.data.MavenMetadata
import org.bookmc.indexer.impl.maven.resolve.MavenDataKey
import org.bookmc.indexer.impl.maven.util.http
import org.json.JSONArray
import org.json.JSONObject
import org.json.XML
import java.net.URL


class MavenDatabase(private val mavenUrl: URL) : Database<MavenDataKey, MavenMetadata> {
    private val gson = Gson()
    private lateinit var databaseCache: Store<MavenDataKey, MavenMetadata>

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override suspend fun create() {
        databaseCache = StoreBuilder
            .from<MavenDataKey, MavenMetadata>(Fetcher.of { fetch(it) })
            .build()
    }

    override suspend fun fetch(key: MavenDataKey): MavenMetadata {
        val res = http.get<String>(buildMavenUrl(key.group, key.artifact))
        return gson.fromJson(XML.toJSONObject(res).toString(), MavenMetadata::class.java)
    }

    override suspend fun get(key: MavenDataKey) = databaseCache.get(key)

    private fun buildMavenUrl(group: String, name: String): String {
        return mavenUrl.toString() + "/${group.replace(".", "/")}/$name/maven-metadata.xml"
    }
}