package com.paramonov.challenge.ui.feature.category_list

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.paramonov.challenge.data.repository.model.Category
import com.paramonov.challenge.databinding.CategoryItemBinding
import com.paramonov.challenge.ui.feature.category_list.CategoryListAdapter.CategoryListHolder
import com.paramonov.challenge.ui.utils.loadByUrl

class CategoryListAdapter(private val listener: ItemListener) :
    RecyclerView.Adapter<CategoryListHolder>() {
    var categories = listOf<Category>()
    override fun getItemCount() = categories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = CategoryItemBinding.inflate(inflater, parent, false)
        return CategoryListHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryListHolder, pos: Int) {
        val category = categories[pos]
        holder.bind(category)
    }

    inner class CategoryListHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(category: Category) {
            with(binding) {
                itemName.text = category.name
                coverImage.loadByUrl(category.imgUrl)
            }
        }

        override fun onClick(v: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                val category = categories[adapterPosition]
                listener.onClick(category)
            }
        }
    }

    interface ItemListener {
        fun onClick(category: Category)
    }
}