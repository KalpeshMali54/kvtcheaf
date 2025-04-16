import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kvtcheaf.R

class ChefAdapter(
    private val chefList: List<Chef>,
    private val onItemClick: (Chef) -> Unit
) : RecyclerView.Adapter<ChefAdapter.ChefViewHolder>() {

    class ChefViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chefImage: ImageView = itemView.findViewById(R.id.chefImage)
        val chefName: TextView = itemView.findViewById(R.id.chefName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChefViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chef, parent, false)
        return ChefViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChefViewHolder, position: Int) {
        val chef = chefList[position]
        holder.chefImage.setImageResource(chef.imageResId)
        holder.chefName.text = chef.name

        // Attach the click listener
        holder.itemView.setOnClickListener {
            onItemClick(chef)
        }
    }

    override fun getItemCount(): Int = chefList.size
}
