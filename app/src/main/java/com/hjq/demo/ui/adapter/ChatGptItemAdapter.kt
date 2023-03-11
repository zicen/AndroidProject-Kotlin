package com.hjq.demo.ui.adapter

import android.content.*
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hjq.demo.R
import com.hjq.demo.app.AppAdapter
import com.hjq.demo.http.api.BaiXingChatGptApi
import com.hjq.shape.layout.ShapeConstraintLayout

class ChatGptItemAdapter constructor(context: Context) : AppAdapter<BaiXingChatGptApi.Bean>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder()
    }

    inner class ViewHolder : AppViewHolder(R.layout.chatgpt_item) {

        private val textView: TextView? by lazy { findViewById(R.id.tvContent) }
        private val ivRole: ImageView? by lazy { findViewById(R.id.ivRole) }
        private val container: ShapeConstraintLayout? by lazy { findViewById(R.id.container) }

        override fun onBindView(position: Int) {
            val item = getItem(position)
//            setRadiusByPosition(position)
            textView?.text = item.content
            if (item.role == 1) {
                ivRole?.setImageResource(R.drawable.ic_chatgpt_active)
            } else {
                ivRole?.setImageResource(R.drawable.ic_profile)
            }
        }

        private fun setRadiusByPosition(position: Int) {
            if (position == 0) {
                container?.shapeDrawableBuilder?.let {
                    it.setRadius(20f, 20f, 0f, 0f)
                }
            } else if (position > 0 && position == getCount() - 1) {
                container?.shapeDrawableBuilder?.let {
                    it.setRadius(0f, 0f, 20f, 20f)
                }
            } else {
                container?.shapeDrawableBuilder?.let {
                    it.setRadius(0f, 0f, 0f, 0f)
                }
            }
        }
    }
}