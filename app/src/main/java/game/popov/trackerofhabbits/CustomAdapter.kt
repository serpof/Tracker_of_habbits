package game.popov.trackerofhabbits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ProgressAdapter(
    private val progressModelArrayList: ArrayList<ItemsViewModel>
) : RecyclerView.Adapter<ProgressAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // to inflate the layout for each item of recycler view.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // to set data to textview and imageview of each card layout
        val model: ItemsViewModel = progressModelArrayList[position]
        holder.tooth1.setImageResource(model.getTooth1())
        holder.tooth2.setImageResource(model.getTooth2())
        holder.tooth3.setImageResource(model.getTooth3())
        holder.tooth4.setImageResource(model.getTooth4())
        holder.tooth5.setImageResource(model.getTooth5())
        holder.tooth6.setImageResource(model.getTooth6())
        holder.animal.setImageResource(model.getAnimal())
    }

    override fun getItemCount(): Int {
        // this method is used for showing number of card items in recycler view.
        return progressModelArrayList.size
    }

    // View holder class for initializing of your views such as TextView and Imageview.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tooth1: ImageView = itemView.findViewById(R.id.tooth1)
        val tooth2: ImageView = itemView.findViewById(R.id.tooth2)
        val tooth3: ImageView = itemView.findViewById(R.id.tooth3)
        val tooth4: ImageView = itemView.findViewById(R.id.tooth4)
        val tooth5: ImageView = itemView.findViewById(R.id.tooth5)
        val tooth6: ImageView = itemView.findViewById(R.id.tooth6)
        val animal: ImageView = itemView.findViewById(R.id.animal)
    }
}
