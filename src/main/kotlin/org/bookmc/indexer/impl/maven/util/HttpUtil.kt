package org.bookmc.indexer.impl.maven.util

import io.ktor.client.*
import io.ktor.client.engine.apache.*

val http = HttpClient(Apache)