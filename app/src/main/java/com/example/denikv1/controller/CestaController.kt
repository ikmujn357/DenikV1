package com.example.denikv1


interface CestaController {
    suspend fun addCesta(cesta: CestaEntity)
    suspend fun getAllCesta(): List<CestaEntity>
}

class CestaControllerImpl(
    private val cestaModel: CestaModel,
    //private val cestaView: CestaView,
    //private val cestaViewModel: CestaViewModel
) : CestaController {

    override suspend fun addCesta(cesta: CestaEntity) {
        cestaModel.insertCesta(cesta)
        getAllCesta()
    }

    override suspend fun getAllCesta(): List<CestaEntity> {
        return cestaModel.getAllCesta()
    }



}
