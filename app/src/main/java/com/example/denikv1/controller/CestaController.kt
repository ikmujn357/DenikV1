package com.example.denikv1


interface CestaController {
    // Přidá novou cestu do datového modelu.
    suspend fun addCesta(cesta: CestaEntity)
    // Získá seznam všech cest z datového modelu.
    suspend fun getAllCesta(): List<CestaEntity>
}

class CestaControllerImpl(
    private val cestaModel: CestaModel,
    ) : CestaController {
    // Přidá novou cestu do datového modelu a zavolá metodu pro získání všech cest.
    override suspend fun addCesta(cesta: CestaEntity) {
        cestaModel.insertCesta(cesta)
        getAllCesta()
    }

    // Získá seznam všech cest z datového modelu.
    override suspend fun getAllCesta(): List<CestaEntity> {
        return cestaModel.getAllCesta()
    }



}
