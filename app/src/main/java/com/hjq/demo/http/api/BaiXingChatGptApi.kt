package com.hjq.demo.http.api

import com.hjq.http.annotation.HttpRename
import com.hjq.http.config.IRequestApi
import com.hjq.http.config.IRequestHost
import com.hjq.http.config.IRequestPath

class BaiXingChatGptApi : IRequestApi, IRequestHost, IRequestPath {

    override fun getApi(): String {
        return ""
    }

    /** 问题 */
    @HttpRename("p")
    private var question: String? = null

    /** KEY */
    private val k = "EANY6EKX"

    fun setQuestion(question: String?): BaiXingChatGptApi = apply {
        this.question = question
    }

    data class Bean(
        var role: Int = 0, // 角色类型 0 用户 1 chatgpt
        var content: String? = null
    )

    override fun getHost(): String {
        return "https://gpt.baixing.com"
    }

    override fun getPath(): String {
        return ""
    }
}