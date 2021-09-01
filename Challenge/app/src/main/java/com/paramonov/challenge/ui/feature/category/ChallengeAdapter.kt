package com.paramonov.challenge.ui.feature.category

import android.content.Context
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.paramonov.challenge.R
import com.paramonov.challenge.data.repository.model.Challenge
import com.paramonov.challenge.databinding.ChallengeItemBinding
import com.paramonov.challenge.ui.feature.category.ChallengeAdapter.*
import com.paramonov.challenge.ui.utils.loadByUrl

class ChallengeAdapter(
    private val context: Context,
    private val onClickListener: ItemListener
) :
    RecyclerView.Adapter<ChallengeHolder>() {

    var challenges = listOf<ChallengeSelection>()
    override fun getItemCount() = challenges.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ChallengeItemBinding.inflate(inflater, parent, false)
        return ChallengeHolder(context, view)
    }

    override fun onBindViewHolder(holder: ChallengeHolder, pos: Int) {
        val item = challenges[pos]
        holder.bind(item)
    }

    fun setItemSelection(item: ChallengeSelection, pos: Int) {
        item.selection = !item.selection
        notifyItemChanged(pos)
    }

    inner class ChallengeHolder(
        private val context: Context,
        private val binding: ChallengeItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }

        fun bind(item: ChallengeSelection) {
            with(binding) {
                val color = if (item.selection) {
                    ContextCompat.getColor(context, R.color.selection_item)
                } else {
                    ContextCompat.getColor(context, R.color.white)
                }
                binding.root.setBackgroundColor(color)

                ratingText.text = item.challenge.rating
                challengeText.text = item.challenge.name
                coverImage.loadByUrl(item.challenge.imgUrl)
            }
        }

        override fun onClick(v: View?) {
            getItemPosition()?.let { pos ->
                onClickListener.onClick(v, challenges[pos], pos)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            getItemPosition()?.let { pos ->
                return onClickListener.onLongClick(challenges[pos], pos)
            }
            return true
        }

        private fun getItemPosition(): Int? {
            return when (val pos = adapterPosition) {
                RecyclerView.NO_POSITION -> null
                else -> pos
            }
        }
    }

    class ChallengeSelection(var selection: Boolean, val challenge: Challenge)

    interface ItemListener {
        fun onClick(v: View?, item: ChallengeSelection, pos: Int)
        fun onLongClick(item: ChallengeSelection, pos: Int): Boolean
    }
}