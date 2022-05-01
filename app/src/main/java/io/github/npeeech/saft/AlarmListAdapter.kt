package io.github.npeeech.saft

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalTime

class AlarmListAdapter(private val listener: ClickButtonListener) : RecyclerView.Adapter<AlarmListAdapter.ViewHolder>() {

    private var dataSet = listOf<LocalTime>()

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val button: Button

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.alarmTimeText)
            button = view.findViewById(R.id.setButton)
        }
    }

    fun setList(dataSet: List<LocalTime>){
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.alarm_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = dataSet[position].toString()
        viewHolder.button.setOnClickListener {
            listener.onClick(position)
            Log.i("click", "clickされたよん")
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}

interface ClickButtonListener{
    fun onClick(position: Int)
}