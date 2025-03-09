package com.lindum.learnandroid

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: ItemPagerAdapter
    private val items = mutableListOf<String>()
    private val itemsPerPage = 3
    private var currentPageIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始化数据
        for (i in 1..20) {
            items.add("Item $i")
        }

        viewPager = findViewById(R.id.viewPager)
        pagerAdapter = ItemPagerAdapter(this)
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPageIndex = position
            }
        })
        val sortButton = findViewById<AppCompatButton>(R.id.acb_sort)
        sortButton.setOnClickListener {
            shuffleItems()
        }
        updatePages()
    }

    // 随机排序
    private fun shuffleItems() {
        // 先保存原列表用于日志输出
        val originalOrder = items.toList()

        // 随机排序
        items.shuffle()

        // 打印排序前后的顺序
        for (i in items.indices) {
            Log.d("ShuffleItems", "Item at position $i changed from '${originalOrder.getOrNull(i)}' to '${items[i]}'")
        }

        // 也可以简单地打印整个列表
        Log.d("ShuffleItems", "Original order: $originalOrder")
        Log.d("ShuffleItems", "Shuffled order: $items")

        // 更新UI
        updatePages()
    }

    // 更新页面，可在数据变化时调用
    private fun updatePages() {
        pagerAdapter.updateData(items)
    }

    // 删除项目并重新布局
    fun removeItem(position: Int) {
        if (position >= 0 && position < items.size) {
            Log.i("Remove", items.get(position))
            Toast.makeText(this, items.get(position), Toast.LENGTH_SHORT).show()
            items.removeAt(position)
            updatePages()
        }
    }
}

// 5. ViewPager2 适配器
class ItemPagerAdapter(private val context: Context) :
    RecyclerView.Adapter<ItemPagerAdapter.PageViewHolder>() {

    private var items = listOf<String>()
    private val itemsPerPage = 3

    fun updateData(newItems: List<String>) {
        items = newItems.toList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return (items.size + itemsPerPage - 1) / itemsPerPage // 向上取整
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_page, parent, false)
        return PageViewHolder(view)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val startIndex = position * itemsPerPage
        val endIndex = minOf(startIndex + itemsPerPage, items.size)
        val pageItems = items.subList(startIndex, endIndex)

        holder.bind(pageItems)
    }

    inner class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)

        fun bind(pageItems: List<String>) {
            recyclerView.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            recyclerView.adapter = ItemAdapter(context, pageItems, adapterPosition)  // Pass page position
        }
    }
}

// 6. RecyclerView 适配器 (用于页内项目)
class ItemAdapter(
    private val context: Context,
    private val items: List<String>,
    private val pageIndex: Int  // Add this parameter
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemText: TextView = itemView.findViewById(R.id.itemText)

        fun bind(text: String) {
            itemText.text = text
            itemView.setOnLongClickListener {
                val activity = context as? MainActivity
                val itemPositionInPage = adapterPosition
                val globalPosition = (pageIndex * 3) + itemPositionInPage
                activity?.removeItem(globalPosition)
                true
            }
        }
    }
}