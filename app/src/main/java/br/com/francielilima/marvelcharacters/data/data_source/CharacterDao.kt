package br.com.francielilima.marvelcharacters.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.francielilima.marvelcharacters.domain.model.Character
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character")
    fun getCharacters(): Flow<List<Character>>

    @Query("SELECT * FROM character WHERE id = :id")
    suspend fun getCharacterById(id: Int): Character?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character)

    @Delete
    suspend fun delete(character: Character)
}