package com.paramonov.challenge.ui.feature.collection

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.paramonov.challenge.data.repository.model.Challenge
import com.paramonov.challenge.databinding.ChallengeUserItemBinding
import com.paramonov.challenge.ui.feature.collection.CollectionItemAdapter.CollectionItemHolder
import com.paramonov.challenge.ui.utils.loadByUrl

class CollectionItemAdapter(private val challenges: List<Challenge>) :
    RecyclerView.Adapter<CollectionItemHolder>() {
    override fun getItemCount() = challenges.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ChallengeUserItemBinding.inflate(inflater, parent, false)
        return CollectionItemHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionItemHolder, pos: Int) {
        val challenge = challenges[pos]
        holder.bind(challenge)
    }

    class CollectionItemHolder(private val binding: ChallengeUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(challenge: Challenge) {
            with(binding) {
                itemName.text = challenge.name
                coverImage.loadByUrl(challenge.imgUrl)
            }
        }
    }
}