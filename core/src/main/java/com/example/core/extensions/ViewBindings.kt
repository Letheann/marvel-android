package com.example.core.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core.utils.RecyclerItemDecoration


@set:BindingAdapter("visible")
var View.visible
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }

@BindingAdapter("urlImage")
fun ImageView.loadImageUrl(url: String?) {
    load(url) {
        crossfade(true)
    }
}

@BindingAdapter("itemSpacing")
fun RecyclerView.itemSpacing(spacingPx: Float) {
    addItemDecoration(
        RecyclerItemDecoration(spacingPx.toInt())
    )
}
