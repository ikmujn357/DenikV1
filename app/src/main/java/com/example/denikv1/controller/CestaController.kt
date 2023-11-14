package com.example.denikv1


interface CestaController {
    fun addCesta(cesta: Cesta)
    fun getAllCesty(): List<Cesta>
    // Další metody pro manipulaci s daty a aktualizaci zobrazení
}

class CestaControllerImpl(private val cestaModel: CestaModel, private val cestaView: CestaView) : CestaController {

    override fun addCesta(cesta: Cesta) {
        cestaModel.addCesta(cesta)
        getAllCesty()
    }

    override fun getAllCesty(): List<Cesta> {
        return cestaModel.getAllCesty()
    }


    // Další metody pro manipulaci s daty a aktualizaci zobrazení
}
