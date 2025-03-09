package com.lindum.learnandroid

import androidx.recyclerview.widget.RecyclerView

/**
 * $
 *
 * @author zhuhao
 * @date  12:32
 **/
class PagerGridLayoutManager(
    private val spanCount: Int, // 每页的行数
    private val spanSize: Int   // 每行的列数
) : RecyclerView.LayoutManager() {

    private var horizontalScrollOffset = 0 // 水平滚动偏移量

    private val itemWidth: Int
        get() = width / spanSize
    private val itemHeight: Int
        get() = height / spanCount

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (itemCount == 0) return
        detachAndScrapAttachedViews(recycler)

        layoutVisibleChildren(recycler, state)
    }

    private fun layoutVisibleChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)

        val pageSize = spanCount * spanSize
        val startIndex = getCurrentPage() * pageSize
        val endIndex = (startIndex + pageSize).coerceAtMost(itemCount)

        for (index in startIndex until endIndex) {
            val view = recycler.getViewForPosition(index)
            addView(view)
            measureChildWithMargins(view, 0, 0)

            val page = index / pageSize
            val posInPage = index % pageSize
            val row = posInPage / spanSize
            val col = posInPage % spanSize

            val left = page * width + col * itemWidth - horizontalScrollOffset
            val top = row * itemHeight
            val right = left + itemWidth
            val bottom = top + itemHeight

            layoutDecoratedWithMargins(view, left, top, right, bottom)
        }
    }

    override fun canScrollHorizontally(): Boolean = true

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        val totalScrollRange = width * ((itemCount + spanCount * spanSize - 1) / (spanCount * spanSize))
        val newOffset = (horizontalScrollOffset + dx).coerceIn(0, totalScrollRange - width)

        val delta = newOffset - horizontalScrollOffset
        horizontalScrollOffset = newOffset

        offsetChildrenHorizontal(-delta)
        layoutVisibleChildren(recycler, state)

        return delta
    }

    private fun getCurrentPage(): Int {
        return horizontalScrollOffset / width
    }
}
