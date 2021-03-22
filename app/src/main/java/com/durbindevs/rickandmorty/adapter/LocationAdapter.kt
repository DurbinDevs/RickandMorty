package com.durbindevs.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.durbindevs.rickandmorty.databinding.LocationRowBinding
import com.durbindevs.rickandmorty.locationModels.Result

class LocationAdapter: RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    inner class LocationViewHolder(val binding: LocationRowBinding) :
            RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object: DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
           return oldItem == newItem
        }
    }

     val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
       return LocationViewHolder(
           LocationRowBinding.inflate(
           LayoutInflater.from(parent.context),
           parent,
           false
       ))
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val newLocationList = differ.currentList[position]
       holder.binding.apply {
           tvLocationName.text = newLocationList.name
           tvType.text = newLocationList.type
           tvDimension.text = newLocationList.dimension
       }
    }

    override fun getItemCount() = differ.currentList.size
}