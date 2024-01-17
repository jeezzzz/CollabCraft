import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.jeezzzz.collabcraft.DomainItem
import com.jeezzzz.collabcraft.R  // Make sure to import the correct R class

class DomainAdapter(context: Context?, domainList: ArrayList<DomainItem>) :
    ArrayAdapter<DomainItem?>(context!!, 0, domainList!! as List<DomainItem?>) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent.context)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent.context)
    }

    private fun initView(position: Int, convertView: View?, context: Context): View {
        var itemView = convertView
        if (itemView == null) {
            val inflater = LayoutInflater.from(context)
            itemView = inflater.inflate(R.layout.domain_spinner_item, null)
        }

        val textViewDomain = itemView!!.findViewById<TextView>(R.id.domain_text_view)
        val currentItem = getItem(position)
        if (currentItem != null) {
            textViewDomain.text = currentItem.domainName

            // Customize text color and background color based on domain
            when {
                currentItem.domainName.equals("• ml", ignoreCase = true) -> {
                    textViewDomain.setTextColor(context.resources.getColor(android.R.color.white))
                    textViewDomain.setBackgroundColor(context.resources.getColor(R.color.ml_color))
                }
                currentItem.domainName.equals("• android", ignoreCase = true) -> {
                    textViewDomain.setTextColor(context.resources.getColor(android.R.color.white))
                    textViewDomain.setBackgroundColor(context.resources.getColor(R.color.android_color))
                }
                currentItem.domainName.equals("• webd", ignoreCase = true) -> {
                    textViewDomain.setTextColor(context.resources.getColor(android.R.color.white))
                    textViewDomain.setBackgroundColor(context.resources.getColor(R.color.web_color))
                }
                currentItem.domainName.equals("• arvr", ignoreCase = true) -> {
                    textViewDomain.setTextColor(context.resources.getColor(android.R.color.white))
                    textViewDomain.setBackgroundColor(context.resources.getColor(R.color.arvr_color))
                }
                // Add more cases for other domains if needed
                else -> {
                    textViewDomain.setTextColor(context.resources.getColor(android.R.color.black))
                    textViewDomain.setBackgroundColor(context.resources.getColor(android.R.color.transparent))
                }
            }

        }


        return itemView!!
    }
}
