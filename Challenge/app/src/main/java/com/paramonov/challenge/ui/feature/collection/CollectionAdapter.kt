package com.paramonov.challenge.ui.feature.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paramonov.challenge.data.repository.model.Category
import com.paramonov.challenge.databinding.CollectionListItemBinding
import com.paramonov.challenge.ui.feature.collection.CollectionAdapter.CollectionHolder

class CollectionAdapter : RecyclerView.Adapter<CollectionHolder>() {
    var categories = listOf<Category>()
    override fun getItemCount() = categories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = CollectionListItemBinding.inflate(inflater, parent, false)
        return CollectionHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionHolder, pos: Int) {
        val category = categories[pos]
        holder.bind(category)
    }

    class CollectionHolder(private val binding: CollectionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            with(binding) {
                categoryName.text = category.name
                collectionRv.setHasFixedSize(true)
                collectionRv.adapter = CollectionItemAdapter(category.items ?: listOf())
                collectionRv.adapter?.notifyDataSetChanged()
            }
        }
    }
}