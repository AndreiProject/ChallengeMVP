package com.paramonov.challenge.ui.feature.category.list_adapter

import android.content.Context
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.ChallengeItemBinding
import com.paramonov.challenge.ui.feature.category.list_adapter.ChallengeAdapter.ChallengeHolder
import com.paramonov.challenge.ui.feature.category.list_adapter.ChallengeAdapterItemPresenterContract.*
import com.paramonov.challenge.ui.utils.loadByUrl

class ChallengeAdapter(
    private val context: Context,
    private val onClickListener: ItemListener,
    private val presenter: ItemPresenter<ItemView>
) :
    RecyclerView.Adapter<ChallengeHolder>() {

    override fun getItemCount() = presenter.getCount()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ChallengeItemBinding.inflate(inflater, parent, false)
        val holder = ChallengeHolder(view)
        view.apply {
            root.setOnClickListener {
                val item = presenter.getChallenge(holder.pos)
                onClickListener.onClick(it, item.item, holder.pos)
            }
            root.setOnLongClickListener {
                onClickListener.onLongClick(it, holder.pos)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ChallengeHolder, pos: Int) {
        holder.pos = pos
        presenter.bindView(holder)
    }

    private fun getColor(id: Int) = ContextCompat.getColor(context, id)

    inner class ChallengeHolder(private val binding: ChallengeItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        ItemView {

        override var pos = -1

        override fun setItemSelection(isActive: Boolean) {
            val color = if (isActive) {
                getColor(R.color.selection_item)
            } else {
                getColor(R.color.white)
            }
            binding.root.setBackgroundColor(color)
        }

        override fun setRating(rating: String) {
            binding.ratingText.text = rating
        }

        override fun setChallenge(challengeName: String) {
            binding.challengeText.text = challengeName
        }

        override fun loadImgByUrl(imgUrl: String) {
            binding.coverImage.loadByUrl(imgUrl)
        }
    }
}