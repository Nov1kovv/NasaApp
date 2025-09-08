package com.example.nasaapp.ui.details.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R

class DetailItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val resources = view.context.resources
        val adapter = parent.adapter as? DetailAdapter ?: return
        val items = adapter.items

        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return
        val item = items?.getOrNull(position) ?: return

        val verticalMargin = resources.getDimensionPixelSize(R.dimen.grid_1)
        val horizontalMargin = resources.getDimensionPixelSize(R.dimen.grid_2)
        val imgVerticalMargin = resources.getDimensionPixelSize(R.dimen.grid_3)

        when (item) {
            is DetailItem.TextItem -> outRect.run {
                left = horizontalMargin
                right = horizontalMargin
                top = verticalMargin
                bottom = verticalMargin
            }

            is DetailItem.Photo,
            is DetailItem.Video -> outRect.run {
                left = horizontalMargin
                right = horizontalMargin
                top = imgVerticalMargin
                bottom = imgVerticalMargin
            }

            is DetailItem.DownloadButtonItem,
            is DetailItem.ShareButtonItem -> outRect.run {
                left = horizontalMargin
                right = horizontalMargin
                top = verticalMargin / 2
                bottom = verticalMargin / 2
            }
        }
    }
}
