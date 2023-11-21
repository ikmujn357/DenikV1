package com.example.denikv1


interface CestaModel {
    fun addCesta(cesta: CestaEntity)
    fun getAllCesty(): List<CestaEntity>

}

class CestaModelImpl() : CestaModel {
    private val cesty = ArrayList<CestaEntity>()
    init {
        cesty.add(CestaEntity("První", 0, "TR", "7+", "Technická", 2, 24, "Pěkná lehká cesta", "Dolez do topu byl super"))
        cesty.add(CestaEntity("Druhá", 1, "RP", "6", "Silová", 2, 48, "Potřeba hodně síly", "Až na začátek to bylo fajn"))
        cesty.add(CestaEntity("Třetí", 0, "TR", "7", "Technická", 4, 5, "Hodně na přemýšlení", "Kroky byly super vymyšlené"))
    }

    override fun addCesta(cesta: CestaEntity) {
        cesty.add(cesta)
    }

    override fun getAllCesty(): List<CestaEntity> {
        return cesty.toList()
    }




}
