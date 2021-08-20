package org.bookmc.indexer.api.artifact

import java.net.URL

data class Artifact(val group: String, val version: String, val binaries: List<URL>)