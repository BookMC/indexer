package org.bookmc.indexer.impl.maven.data

import com.google.gson.annotations.SerializedName

data class MavenMetadata(val metadata: Metadata) {
    data class Metadata(val versioning: Versioning) {
        data class Versioning(val versions: Versions, val latest: String) {
            data class Versions(@SerializedName("version") private val _version: Any) {
                /**
                 * Due to the way XML works it may not always return
                 * a list so to fix that we must private the intial
                 * function and make it Any. This means the type could
                 * be anything and then we convert the type to our wanted type
                 */
                @Suppress("UNCHECKED_CAST")
                val versions: List<String>
                    get() {
                        return if (_version is String) {
                            listOf(_version)
                        } else {
                            _version as List<String>
                        }
                    }

                override fun toString(): String {
                    return super.toString().replace("_versions=.+".toRegex(), "")
                }
            }
        }
    }
}