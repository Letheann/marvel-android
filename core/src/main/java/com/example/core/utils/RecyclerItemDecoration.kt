
package com.example.core.utils

import android.graphics.Rect
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.ceil

class RecyclerItemDecoration(
    @VisibleForTesting(otherwise = PRIVATE)
    internal val spacingPx: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when (val layoutManager = parent.layoutManager) {
            is GridLayoutManager -> configSpacingForGridLayoutManager(
                outRect = outRect,
                layoutManager = layoutManager,
                position = parent.getChildViewHolder(view).adapterPosition,
                itemCount = state.itemCount
            )
        }
    }

    private fun configSpacingForGridLayoutManager(
        outRect: Rect,
        layoutManager: GridLayoutManager,
        position: Int,
        itemCount: Int
    ) {
        outRect.top = spacingPx
        outRect.left = spacingPx
        outRect.right = if (position % layoutManager.spanCount == layoutManager.spanCount - 1) spacingPx else 0
        outRect.bottom = if (position / layoutManager.spanCount == ceil(itemCount / layoutManager.spanCount.toDouble()).toInt() - 1) spacingPx else 0
    }

}
