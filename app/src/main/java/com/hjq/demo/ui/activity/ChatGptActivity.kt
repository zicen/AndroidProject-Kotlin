package com.hjq.demo.ui.activity

import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ClipboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.hjq.demo.R
import com.hjq.demo.app.AppActivity
import com.hjq.demo.http.api.BaiXingChatGptApi
import com.hjq.demo.http.model.HttpData
import com.hjq.demo.ui.adapter.ChatGptItemAdapter
import com.hjq.http.EasyHttp
import com.hjq.http.listener.HttpCallback
import com.hjq.shape.layout.ShapeRecyclerView
import com.hjq.shape.view.ShapeEditText
import okhttp3.Call
import java.lang.Exception

class ChatGptActivity : AppActivity(), com.hjq.base.BaseAdapter.OnItemLongClickListener {

    private val chatRecycler: ShapeRecyclerView? by lazy { findViewById(R.id.chatRecycler) }
    private val etMessage: ShapeEditText? by lazy { findViewById(R.id.etMessage) }
    private val chatAdapter by lazy { ChatGptItemAdapter(this) }
    private val layoutManager by lazy { LinearLayoutManager(this) }
    private var isThinking = false // 是否在思考中。。。
    override fun getLayoutId(): Int {
        return R.layout.chatgpt_activity
    }

    override fun initView() {
        chatAdapter.setOnItemLongClickListener(this@ChatGptActivity)
        chatRecycler?.layoutManager = layoutManager
        chatRecycler?.adapter = chatAdapter
        etMessage?.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEND) {
                if (isThinking) false
                sendMessage(textView.text.toString().trim())
                true
            }
            false
        }
    }

    /**
     * https://gpt.baixing.com?p=xxx&k=EANY6EKX
     */
    private fun sendMessage(text: String) {
        etMessage?.setText("")
        chatAdapter.addItem(BaiXingChatGptApi.Bean(content = text))
        chatAdapter.addItem(BaiXingChatGptApi.Bean(role = 1, content = "思考中..."))
        EasyHttp.get(this)
            .api(BaiXingChatGptApi().apply { setQuestion(text) })
            .request(object : HttpCallback<HttpData<String?>>(this) {
                override fun onStart(call: Call?) {
                    isThinking = true
                }

                override fun onEnd(call: Call?) {
                    isThinking = false
                }

                override fun onSucceed(data: HttpData<String?>) {
                    data.getData()?.let {
                        updateChatGptReplyMessage(it)
                    }
                }

                override fun onFail(e: Exception?) {
                    e?.message?.let {
                        updateChatGptReplyMessage(it)
                    }
                }
            })
    }

    private fun updateChatGptReplyMessage(it: String) {
        val position = chatAdapter.getCount() - 1
        val item = chatAdapter.getItem(position)
        item.content = it
        chatAdapter.notifyItemChanged(position)
    }

    override fun initData() {

    }

    override fun onItemLongClick(
        recyclerView: RecyclerView?,
        itemView: View?,
        position: Int
    ): Boolean {
        if (position >= chatAdapter.getCount()) return false
        val item = chatAdapter.getItem(position)
        if (item.content?.isNotEmpty() == true) {
            ClipboardUtils.copyText(item.content)
            ToastUtils.showShort("复制到剪贴板成功！")
            return true
        }
        return false
    }


}