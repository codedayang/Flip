package cc.foxa.flip.shared.data.source

import androidx.room.*
import cc.foxa.flip.shared.data.model.Card
import cc.foxa.flip.shared.data.model.CardTagJoin
import cc.foxa.flip.shared.data.model.Tag

@Dao
interface CardTagDao {

    @Query("SELECT * FROM card ORDER BY id")
    fun getCards(): List<Card>

    @Query("SELECT * FROM tag ORDER BY id")
    fun getTags(): List<Tag>

    @Query("""
        SELECT tag.id, tag.name FROM tag 
        JOIN card_tag_join ON card_tag_join.tag_id = tag.id 
        WHERE card_tag_join.card_id = :id
        ORDER BY id
    """)
    fun getTagsByCard(id: String): List<Tag>

    @Query("SELECT * FROM card WHERE id = :id ORDER BY id")
    fun getCardById(id: String): Card

    @Query(" SELECT * FROM card WHERE front LIKE :query OR back LIKE '%'+:query+'%' ORDER BY id")
    fun searchCardContent(query: String): List<Card>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateCard(card: Card)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateTag(tag: Tag)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateJoin(cardTagJoin: CardTagJoin)

    @Delete
    fun deleteCard(card: Card)

    @Query("DELETE FROM card_tag_join WHERE card_id = :cardId")
    fun deleteJoinByCardId(cardId: String)

    @Delete
    fun deleteTag(tag: Tag)

}