package com.geekbrains.kotlin.data

/*
object NotesRepository {

    private val Notes_LD = MutableLiveData<List<Note>>()
    private val notes: MutableList<Note> = mutableListOf(
            Note(
                    UUID.randomUUID().toString(),
                    "Планы на 02 февраля",
                    "1) Вымыть пол \n2) Позвонить Гоше \n Проверить его математику",
                    Note.Color.GREEN
            ),
            Note(UUID.randomUUID().toString(),
                    "Посмотреть фильм",
                    "Двое пап",
                    Note.Color.RED
            ),
            Note(UUID.randomUUID().toString(),
                    "Книги о театре",
                    "Юрский. Кто держит паузу",
                    Note.Color.BLUE
            ),
            Note(UUID.randomUUID().toString(),
                    "Билеты бесплатно для попавших в беду",
                    "Фонд Милосердие. Ноколоямская 57/1",
                    Note.Color.GREEN
            ),
            Note(UUID.randomUUID().toString(),
                    "",
                    "Рамиль Таймасов",
                    Note.Color.YELLOW
            ),
            Note(UUID.randomUUID().toString(),
                    "Левитанский",
                    "Ну что с того, что я там был.\n" +
                            "Я был давно. Я все забыл.\n" +
                            "Не помню дней. Не помню дат.\n" +
                            "Ни тех форсированных рек...",
                    Note.Color.BLUE
            ),
            Note(UUID.randomUUID().toString(),
                    "Клевый стих",
                    "Орлуша. По небу летят утки",
                    Note.Color.VIOLET
            ),
            Note(UUID.randomUUID().toString(),
                    "Посмотреть",
                    "Фантазии Фарятьева \nПризнание в любви ",
                    Note.Color.WHITE
            )
    )
    init {
        Notes_LD.value = notes
    }

    fun addNode(note:Note){
        val index : Int  = findNote(note)
        Notes_LD.value = if (index==-1){
            notes.add(note)
            notes
        }else{
            notes[index]=note
            notes
        }
    }

    private fun findNote(note:Note):Int{
        for (noteIndex in notes.indices){
            if (notes[noteIndex]==note){
                return noteIndex
            }
        }
        return -1
    }


    fun getNotes():LiveData<List<Note>>{
        return Notes_LD
    }

}
*/