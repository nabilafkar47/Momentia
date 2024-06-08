package com.dicoding.picodiploma.momentia

import com.dicoding.picodiploma.momentia.data.remote.model.ListStoryItem

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                i.toString(),
                "photoUrl + $i",
                "createdAt $i",
                "createdAt $i",
                "createdAt $i",
                1.0 + i,
                5.0 + i,
            )
            items.add(story)
        }
        return items
    }
}