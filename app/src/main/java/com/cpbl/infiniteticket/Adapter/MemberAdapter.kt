import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.cpbl.infiniteticket.Model.Member
import com.cpbl.infiniteticket.R
import com.cpbl.infiniteticket.ViewHolder.MemberViewHolder

class MemberAdapter(private var inputData: MutableList<Member>, page: AppCompatActivity) :
    RecyclerView.Adapter<MemberViewHolder>() {

    private val parent = page
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        this.context = parent.context
        return MemberViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.tag_item, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(inputData[position],parent)
    }

}