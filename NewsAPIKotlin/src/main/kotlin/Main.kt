import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.*
import java.io.IOException

data class Article(val title: String, val description: String)

fun main() {
    val apiKey = "NEWS API KEY HERE"
    val newsapiBaseUrl = "https://newsapi.org/v2"

    val client = OkHttpClient()

    val request = Request.Builder()
        .url("$newsapiBaseUrl/top-headlines?language=en&pageSize=15&apiKey=$apiKey")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("Error fetching news data.")
        }

        override fun onResponse(call: Call, response: Response) {
            val responseBody = response.body?.string()
            if (responseBody != null) {
                val gson = Gson()
                val jsonObject = gson.fromJson(responseBody, JsonObject::class.java)
                if (jsonObject.has("articles")) {
                    val articles = gson.fromJson(jsonObject.getAsJsonArray("articles"), Array<Article>::class.java)
                    for ((index, article) in articles.withIndex()) {
                        println("Headline ${index + 1}: ${article.title}")
                        println("Description: ${article.description}")
                        println("-".repeat(40))  // Separator for clarity
                    }
                } else {
                    println("No articles found.")
                }
            } else {
                println("Error fetching news data.")
            }
        }
    })
}
