package cat.meaw.meow.mycalendartestv1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.meaw.meow.mycalendartestv1.R
import kotlinx.android.synthetic.main.date_row_item.view.*

class AdapterRecycDateList : RecyclerView.Adapter<AdapterRecycDateList.DateViewHolder>() {

    var dateList = ArrayList<String>()
    inner class DateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(strDate: String) {
            itemView.txt_date.text = strDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.date_row_item,parent,false)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.bind(dateList[position])
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    fun submitItemBottom( newList :String){
        if(!dateList.contains(newList)){
            dateList.add(newList)
            notifyDataSetChanged()
        }

    }
    fun submitItemTop( newList :String){
        if(!dateList.contains(newList)){
            dateList.add(0,newList)
            notifyItemInserted(0)
        }
    }
}