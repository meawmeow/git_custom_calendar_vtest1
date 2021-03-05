package cat.meaw.meow.mycalendartestv1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.meaw.meow.mycalendartestv1.R
import kotlinx.android.synthetic.main.calendar_cell.view.*

class AdapterCalendar(
    val dayOfMonth:ArrayList<String>,
    val onItemListener: OnItemListener) :RecyclerView.Adapter<AdapterCalendar.CalendarViewHolder>(){

    inner class CalendarViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(string: String,onItemListener: OnItemListener){
            itemView.txt_CellDay.text = string
            itemView.txt_CellDay.setOnClickListener {
                onItemListener.onItemClick(0,string)
            }
        }
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.166666666).toInt()
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
       holder.bind(dayOfMonth[position],onItemListener)
    }

    override fun getItemCount(): Int {
        return dayOfMonth.size
    }
}