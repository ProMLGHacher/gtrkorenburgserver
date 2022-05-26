import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.serialization.gson.*

lateinit var client: HttpClient

fun initClient() {
    client = HttpClient(CIO) {
        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            gson()
        }
    }
}