package br.com.francielilima.marvelcharacters.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.francielilima.marvelcharacters.domain.model.Character

@Database(
    entities = [Character::class],
    version = 1
)
abstract class MarvelDatabase: RoomDatabase() {

    abstract val characterDao: CharacterDao

    companion object {
        const val DATABASE_NAME = "marvel_db"
    }
}