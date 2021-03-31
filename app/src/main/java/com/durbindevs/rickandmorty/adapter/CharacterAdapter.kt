package com.durbindevs.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.durbindevs.rickandmorty.databinding.CharactorRowBinding
import com.durbindevs.rickandmorty.models.Result

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(val binding: CharactorRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

     val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
      return CharacterViewHolder(CharactorRowBinding.inflate(
          LayoutInflater.from(parent.context),
          parent,
          false
      ))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val newCharList = differ.currentList[position]
       holder.binding.apply {
           tvCharacterName.text = newCharList.name
           tvCharacterStatus.text = newCharList.status
     //      tvCharacterLocation.text = newCharList.location.name
           tvCharacterSpecies.text = newCharList.species
           Glide.with(this@CharacterAdapter.CharacterViewHolder(this).itemView)
               .load(newCharList.image)
               .into(ivCharacter)
       }
    }

    override fun getItemCount() = differ.currentList.size
}