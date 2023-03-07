package com.maximvs.vknewsclient.domain

import com.maximvs.vknewsclient.R

data class FeedPost(
    val id: Int = 0,
    val communityName: String = "Какая-то группа", // Присваиваю значения всем переменным, для тестирования, в будущем - будут из сети
    val publicationDate: String = "14:00",
    val avatarResId: Int = R.drawable.icons_tt,
    val contentText: String = "Пока просто какой-то текст, случайный",
    val contentImageResId: Int = R.drawable.photo_1,
    val statistics: List<StatisticItem> = listOf(  // Блок статистики с коллекцией элементов статистики
        StatisticItem(type = StatisticType.VIEWS, 966),
        StatisticItem(type = StatisticType.SHARES, 7),
        StatisticItem(type = StatisticType.COMMENTS, 8),
        StatisticItem(type = StatisticType.LICKES, 27)
    )
)

    //  Позже экземпляры этого класса будем получать из инета
