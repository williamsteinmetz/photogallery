package com.steinmetz.msu.photogallery

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.steinmetz.msu.photogallery.api.GalleryItem
import com.steinmetz.msu.photogallery.databinding.ListItemGalleryBinding

private const val TAG = "PhotoListAdapter"

class PhotoViewHolder(
    private val binding: ListItemGalleryBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(galleryItem: GalleryItem?) {
        galleryItem?.let {
            binding.itemImageView.load(it.url) {
                placeholder(R.drawable.bill_up_close)
            }
        }
    }
}

class PhotoListAdapter : PagingDataAdapter<GalleryItem, PhotoViewHolder>(GalleryItemComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGalleryBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    object GalleryItemComparator : DiffUtil.ItemCallback<GalleryItem>() {
        override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            return oldItem == newItem
        }
    }
    init {
        Log.d(TAG, "This is the Adapter loading Correctly.")
    }
}


