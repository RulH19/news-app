import java.text.SimpleDateFormat
import java.util.*

fun formatPublishedDate(dateString: String): String {
    try {

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")


        val outputFormat = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale("id", "ID"))
        outputFormat.timeZone = TimeZone.getDefault()
        val date = inputFormat.parse(dateString)
        return date?.let { outputFormat.format(it) } ?: "Format salah"
    } catch (e: Exception) {
        e.printStackTrace()
        return "Error formatting Tanggal"
    }
}
