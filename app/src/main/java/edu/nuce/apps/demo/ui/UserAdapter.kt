package edu.nuce.apps.demo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.nuce.apps.demo.data.models.User
import edu.nuce.apps.demo.databinding.ItemLayoutUserBinding
import edu.nuce.apps.demo.util.executeAfter

class UserAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val eventActions: EventActions
) : ListAdapter<User, UserViewHolder>(UserDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.from(parent, lifecycleOwner, eventActions)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}

class UserViewHolder(
    private val binding: ItemLayoutUserBinding,
    private val lifecycleOwner: LifecycleOwner,
    private val eventActions: EventActions
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: User) {
        binding.executeAfter {
            user = item
            lifecycleOwner = this@UserViewHolder.lifecycleOwner
        }
        binding.layoutUser.setOnClickListener { eventActions.onItemClicked(item) }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            lifecycleOwner: LifecycleOwner,
            eventActions: EventActions
        ): UserViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: ItemLayoutUserBinding = ItemLayoutUserBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return UserViewHolder(binding, lifecycleOwner, eventActions)
        }
    }
}

interface EventActions {
    fun onItemClicked(item: User)
}

object UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}