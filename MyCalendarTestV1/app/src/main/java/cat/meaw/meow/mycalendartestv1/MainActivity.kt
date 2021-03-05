package cat.meaw.meow.mycalendartestv1

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.meaw.meow.mycalendartestv1.DateUtils.Companion.getDayMonthYearFromDate
import cat.meaw.meow.mycalendartestv1.DateUtils.Companion.getDaysInMonthArray
import cat.meaw.meow.mycalendartestv1.DateUtils.Companion.getLastDayOfTheMonth
import cat.meaw.meow.mycalendartestv1.DateUtils.Companion.getMonthYearFromDate
import cat.meaw.meow.mycalendartestv1.adapter.AdapterCalendar
import cat.meaw.meow.mycalendartestv1.adapter.AdapterRecycDateList
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity(),AdapterCalendar.OnItemListener {

    lateinit var selectedDate: LocalDate
    lateinit var selectedDateRow: LocalDate


    lateinit var adapterRecycDateList : AdapterRecycDateList
    lateinit var la_manager: LinearLayoutManager

    var isScrolling = false
    var lastDMY = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectedDate = LocalDate.now()
        selectedDateRow = LocalDate.now()

        initRecycDateRow()
        setMonthView()
        setRowRecycFetchBottom()

        preMonth.setOnClickListener {
           btnPreMonthAction()
        }
        nextMonth.setOnClickListener {
           btnNextMonthAction()
        }
    }


    private fun initRecycDateRow() {
        la_manager = LinearLayoutManager(this)
        adapterRecycDateList = AdapterRecycDateList()
        recycDate.apply {
            layoutManager = la_manager
            adapter = adapterRecycDateList
        }
        recycDate.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                var lastVisibleItem = la_manager.findLastVisibleItemPosition()
                var childCount = la_manager.childCount
                var itemCount = la_manager.itemCount
                var firstVisibleItem = la_manager.findFirstVisibleItemPosition()

                var topItem = adapterRecycDateList.dateList[firstVisibleItem]
                var endItem = adapterRecycDateList.dateList[lastVisibleItem]
                var topMY = topItem.substring(3, 10)
                var endMY = endItem.substring(3, 10)
//                Log.d("DSSG",
//                    "topMY = $topMY endMY = $endMY     ||    lastDMY = $lastDMY  ||  topItem = $topItem endItem = $endItem  | itemCount = $itemCount ")
                if ((childCount + firstVisibleItem) == itemCount) {
                    nextRowRecycBottom()
                    //Log.d("DSSG", "Load data.. lastDMY = $lastDMY")
                } else {

                    if (topMY == endMY && (topItem.substring(0, 2).toInt() == 1)) {
                        var dLastEnd = topItem.substring(3, 5).toInt()
                        var yLastEnd = topItem.substring(6, 10).toInt()
                        CurrentMonthAction(dLastEnd, yLastEnd)
                    }

                    if (endItem.substring(0, 2).toInt() == 1) {
                        var d = topMY.substring(0, 2).toInt()
                        var y = topMY.substring(3, 7).toInt()
                        CurrentMonthAction(d, y)
                    }
                }

//                if (!recyclerView.canScrollVertically(1)) {
//                    Toast.makeText(this@RecycActivity, "Last", Toast.LENGTH_LONG).show();
//                }
                if (!recyclerView.canScrollVertically(-1)) {
                    if(!isScrolling){
                        isScrolling = true
                    }else{
                        //Toast.makeText(this@MainActivity, "Begin", Toast.LENGTH_LONG).show();
                        preRowRecycTop()
                    }
                }
            }
        })


    }

    //btn action
    private fun btnNextMonthAction() {
        nextMonthAction()
        nextRowRecycBottom()

        var array = selectedDate.toString().split("-")
        var newFormat = "${array[2]}-${array[1]}-${array[0]}"
        val index = adapterRecycDateList.dateList.indexOf(newFormat)
        recycDate.scrollToPosition(index)
    }
    private fun btnPreMonthAction() {
        preMonthAction()
        preRowRecycTop()
        var array = selectedDate.toString().split("-")
        var newFormat = "${array[2]}-${array[1]}-${array[0]}"
        val index = adapterRecycDateList.dateList.indexOf(newFormat)
        recycDate.scrollToPosition(index)
    }



    //setup calendar & switch calendar view
    fun CurrentMonthAction(m: Int, y: Int) {
        selectedDate = selectedDate.withMonth(m).withYear(y)
        setMonthView()
    }
    fun setMonthView() {
        monthYearTV.text = getMonthYearFromDate(selectedDate)
        val daysInMonth = getDaysInMonthArray(selectedDate)
        val adapterCalendar = daysInMonth?.let { AdapterCalendar(it, this) }
        calendarRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 7)
            adapter = adapterCalendar
        }
    }
    fun preMonthAction() {
        selectedDate = selectedDate.minusMonths(1)
        setMonthView()
    }

    fun nextMonthAction() {
        selectedDate = selectedDate.plusMonths(1)
        setMonthView()
    }



    //Recyc date row bottom
    fun nextRowRecycBottom() {
        selectedDateRow = selectedDateRow.plusMonths(1)
        setRowRecycFetchBottom()
    }
    fun preRowRecycTop() {
        selectedDateRow = selectedDateRow.minusMonths(1)
        setRowRecycFetchTop()
    }

    fun setRowRecycFetchTop() {
        val dmy = getDayMonthYearFromDate(selectedDateRow)
        lastDMY = getLastDayOfTheMonth(dmy)!!
        getRangesFetchTop(lastDMY)
    }

    fun setRowRecycFetchBottom() {
        val dmy = getDayMonthYearFromDate(selectedDateRow)
        lastDMY = getLastDayOfTheMonth(dmy)!!
        getRangesFetchBottom(lastDMY)
    }

    fun getRangesFetchTop(strDate: String): Boolean {
        val dates = strDate.split("-")
        var end = dates[0].toInt()

        for (i in end downTo 1) {
            var strI = i.toString()
            if (i.toString().length == 1) {
                strI = "0$i"
            }
            adapterRecycDateList.submitItemTop("$strI-${dates[1]}-${dates[2]}")
        }
        return true
    }

    fun getRangesFetchBottom(strDate: String): Boolean {
        val dates = strDate.split("-")
        var end = dates[0].toInt()
        for (i in 1..end) {
            var strI = i.toString()
            if (i.toString().length == 1) {
                strI = "0$i"
            }
            adapterRecycDateList.submitItemBottom("$strI-${dates[1]}-${dates[2]}")
        }
        return true
    }

    override fun onItemClick(position: Int, dayText: String?) {

    }


}