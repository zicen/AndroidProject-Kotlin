package com.hjq.demo.http.api

import com.hjq.http.annotation.HttpHeader
import com.hjq.http.annotation.HttpRename
import com.hjq.http.config.IRequestApi
import com.hjq.http.config.IRequestHost
import com.hjq.http.config.IRequestPath

class OpenAiApi : IRequestApi, IRequestHost, IRequestPath {
    companion object {
        private const val apiKey = "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"
    }

    override fun getApi(): String {
        return ""
    }

    @HttpHeader()
    @HttpRename("Content-Type")
    private val content_type = "application/json"

    @HttpHeader
    private val Authorization = "Bearer ${apiKey}"

    override fun getHost(): String {
        return "https://api.openai.com/v1/chat/completions"
    }

    override fun getPath(): String {
        return ""
    }
}