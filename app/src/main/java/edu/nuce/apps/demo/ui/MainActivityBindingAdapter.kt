package edu.nuce.apps.demo.ui

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import edu.nuce.apps.demo.R

@BindingAdapter("app:goneIf")
fun goneIf(progressBar: ProgressBar, loading: LiveData<Boolean>) {
    progressBar.visibility = if (loading.value == true) View.VISIBLE else View.GONE
}

@BindingAdapter("app:loadImage")
fun loadImage(appCompatImageView: AppCompatImageView, src: String) {
    Glide.with(appCompatImageView.context)
        .load(src)
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)
        .into(appCompatImageView)
}