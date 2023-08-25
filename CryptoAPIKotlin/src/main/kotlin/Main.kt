import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.*

fun getPriceByName(cryptoName: String): Double? {
    val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
    val parameters = mapOf(
        "start" to "1",
        "limit" to "100",
        "convert" to "USD"
    )
    val headers = mapOf(
        "Accepts" to "application/json",
        "X-CMC_PRO_API_KEY" to "CRYPTO API HERE"
    )

    val httpClient = OkHttpClient()
    val urlWithParams = "$url?${formatParameters(parameters)}"
    val request = Request.Builder()
        .url(urlWithParams)
        .apply {
            headers.forEach { (key, value) -> addHeader(key, value) }
        }
        .build()

    try {
        val response = httpClient.newCall(request).execute()
        val responseBody = response.body?.string()
        val data = JSONObject(responseBody)
        val cryptocurrencies = data.getJSONArray("data")

        for (i in 0 until cryptocurrencies.length()) {
            val crypto = cryptocurrencies.getJSONObject(i)
            if (crypto.getString("name").equals(cryptoName, ignoreCase = true)) {
                val price = crypto.getJSONObject("quote").getJSONObject("USD").getDouble("price")
                return price
            }
        }
        return null

    } catch (e: Exception) {
        println(e)
        return null
    }
}

fun formatParameters(parameters: Map<String, String>): String {
    return parameters.entries.joinToString("&") { "${it.key}=${it.value}" }
}

fun main() {
    print("Enter the name of a cryptocurrency: ")
    val scanner = Scanner(System.`in`)
    val userInput = scanner.nextLine()

    val cryptoPrice = getPriceByName(userInput)

    if (cryptoPrice != null) {
        println("The price of $userInput is $$cryptoPrice")
    } else {
        println("Could not find price information for $userInput")
    }
}
