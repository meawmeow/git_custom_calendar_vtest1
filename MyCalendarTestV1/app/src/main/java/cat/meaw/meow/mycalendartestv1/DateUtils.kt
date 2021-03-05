package cat.meaw.meow.mycalendartestv1

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

class DateUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    companion object {

        fun getDayMonthYearFromDate(date: LocalDate): String? {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            return date.format(formatter)
        }

        fun getMonthYearFromDate(date: LocalDate): String? {
            val formatter = DateTimeFormatter.ofPattern("MMM yyyy")//MMMM
            return date.format(formatter)
        }

        fun getLastDayOfTheMonth(date: String?): String? {
            var lastDayOfTheMonth = ""
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            try {
                val dt: Date = formatter.parse(date)
                val calendar = Calendar.getInstance()
                calendar.time = dt
                calendar.add(Calendar.MONTH, 1)
                calendar[Calendar.DAY_OF_MONTH] = 1
                calendar.add(Calendar.DATE, -1)
                val lastDay = calendar.time
                lastDayOfTheMonth = formatter.format(lastDay)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return lastDayOfTheMonth
        }

        fun getDaysInMonthArray(selectedDate: LocalDate): ArrayList<String>? {
            val daysInMonthArray = ArrayList<String>()
            val yearMonth = YearMonth.from(selectedDate)
            val daysInMonth = yearMonth.lengthOfMonth()
            val firstOfMonth = selectedDate!!.withDayOfMonth(1)
            val dayOfWeek = firstOfMonth.dayOfWeek.value
            for (i in 1..42) {
                if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                    daysInMonthArray.add("")
                } else {
                    daysInMonthArray.add((i - dayOfWeek).toString())
                }
            }
            return daysInMonthArray
        }
    }
}