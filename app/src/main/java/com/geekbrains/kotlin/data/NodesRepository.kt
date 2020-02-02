package com.geekbrains.kotlin.data

import com.geekbrains.kotlin.data.entity.Note

object NotesRepository {
    private val notes: List<Note> = listOf(
            Note(
                    "Планы на 02 февраля",
                    "1) Вымыть пол \n2) Позвонить Гоше \n Проверить его математику",
                    0xfff06292.toInt()
            ),
            Note(
                    "Посмотреть фильм",
                    "Двое пап",
                    0xff9575cd.toInt()
            ),
            Note(
                    "Книги о театре",
                    "Юрский. Кто держит паузу",
                    0xff64b5f6.toInt()
            ),
            Note(
                    "Билеты бесплатно для попавших в беду",
                    "Фонд Милосердие. Ноколоямская 57/1",
                    0xff4db6ac.toInt()
            ),
            Note(
                    "",
                    "Рамиль Таймасов",
                    0xffb2ff59.toInt()
            ),
            Note(
                    "Левитанский",
                    "Ну что с того, что я там был.\n" +
                            "Я был давно. Я все забыл.\n" +
                            "Не помню дней. Не помню дат.\n" +
                            "Ни тех форсированных рек...",
                    0xffffeb3b.toInt()
            ),
            Note(
                    "Клевый стих",
                    "Орлуша. По небу летят утки",
                    0xffb2ff59.toInt()
            ),
            Note(
                    "Посмотреть",
                    "Фантазии Фарятьева \nПризнание в любви ",
                    0xffb2ff59.toInt()
            )
    )

    fun getNotes(): List<Note>{
        return notes
    }
}