package com.example.denikv1


interface CestaController {
    fun addCesta(cesta: CestaEntity)
    fun getAllCesty(): List<CestaEntity>
}

class CestaControllerImpl(
    private val cestaModel: CestaModel,
    private val cestaView: CestaView,
    private val cestaViewModel: CestaViewModel
) : CestaController {

    override fun addCesta(cesta: CestaEntity) {
        cestaModel.addCesta(cesta)
        getAllCesty()
    }

    override fun getAllCesty(): List<CestaEntity> {
        return cestaModel.getAllCesty()
    }



}
