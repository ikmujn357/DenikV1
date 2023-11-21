package com.example.denikv1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



interface CestaView {
    fun displayCesty()
    fun addButton()
    fun findButton()
    fun statisticsButton()


}

class CestaViewImp : AppCompatActivity(), CestaView {
    private lateinit var controller: CestaController
    private lateinit var cestaViewModel: CestaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val appDatabase = AppDatabase.getDatabase(applicationContext)
        val cestaDao = appDatabase.cestaDao()
        cestaViewModel = CestaViewModel(cestaDao)
        controller = CestaControllerImpl(CestaModelImpl(), this, cestaViewModel)

        supportActionBar?.elevation = 0f

        displayCesty()
        addButton()
        findButton()
        statisticsButton()

    }

    override fun displayCesty() {
        val taskList = findViewById<RecyclerView>(R.id.recyclerView)
        taskList.layoutManager = LinearLayoutManager(this)
        taskList.adapter = CestaAdapter(controller.getAllCesty())

    }

    override fun addButton() {
        val buttonShowAdd = findViewById<Button>(R.id.button_add)
        buttonShowAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun findButton(){
        val buttonShowFind = findViewById<Button>(R.id.button_find)
        buttonShowFind.setOnClickListener {
            val intent = Intent(this, FindActivity::class.java)
            startActivity(intent)
        }
    }

    override fun statisticsButton(){
        val buttonShowStatistics = findViewById<Button>(R.id.button_statistics)
        buttonShowStatistics.setOnClickListener {
            val intent = Intent(this, ShowStatistics::class.java)
            startActivity(intent)
        }
    }


}









/*

@Entity(tableName = "cesta_table")
data class VylezenaCesta(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nazev: String,
    val obtiznost: String,
)



@Dao
interface CestaDao {
    @Query("SELECT * FROM cesta_table")
    fun getAllCesty(): Flow<List<VylezenaCesta>>

    @Insert
    suspend fun insertCesta(cesta: VylezenaCesta)
}

@Database(entities = [VylezenaCesta::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cestaDao(): CestaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

*/