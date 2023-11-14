package com.example.denikv1


interface CestaModel {
    fun addCesta(cesta: Cesta)
    fun getAllCesty(): List<Cesta>
    // Další metody pro práci s daty
}

class CestaModelImpl : CestaModel {
    private val cesty = ArrayList<Cesta>()
    init {
        cesty.add(Cesta("Pata cesta", "8+"))
        cesty.add(Cesta("Ctvrta cesta", "6"))
        cesty.add(Cesta("Treti cesta", "4"))
        cesty.add(Cesta("Druha cesta", "7-"))
        cesty.add(Cesta("Prvni cesta", "9"))
    }

    override fun addCesta(cesta: Cesta) {
        cesty.add(cesta)
    }

    override fun getAllCesty(): List<Cesta> {
        return cesty.toList()
    }
    // Další metody pro práci s daty
}
