import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.recyclerview.widget.RecyclerView
import com.chs.lib_common_ui.R

/**
 * A View Holder that can display a loading or have click action.
 * It is used to show the network state of paging.
 */
class NetworkStateItemViewHolder(
    parent: ViewGroup,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.network_state_item, parent, false)
) {
    private val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)
    private val errorMsg = itemView.findViewById<TextView>(R.id.error_msg)
    private val retry = itemView.findViewById<Button>(R.id.retry_button)
        .also {
            it.setOnClickListener { retryCallback() }
        }

    fun bindTo(loadState: LoadState) {
        progressBar.isVisible = loadState is Loading
        retry.isVisible = loadState is Error
        errorMsg.isVisible = !(loadState as? Error)?.error?.message.isNullOrBlank()
        errorMsg.text = (loadState as? Error)?.error?.message
    }
}
