package com.iamsdt.shokherschool.retrofit.pojo.page

data class PageResponse(val date: String = "",
                        val template: String = "",
                        val parent: Int = 0,
                        val author: Int = 0,
                        val link: String = "",
                        val type: String = "",
                        val title: Title? = null,
                        val modified: String = "",
                        val id: Int = 0,
                        val slug: String = "",
                        val status: String = "")