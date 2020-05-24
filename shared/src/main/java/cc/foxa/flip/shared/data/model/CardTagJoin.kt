package cc.foxa.flip.shared.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "card_tag_join",
    indices = [Index("card_id"), Index("tag_id")],
    foreignKeys = [
        ForeignKey(
            entity = Card::class,
            parentColumns = ["id"],
            childColumns = ["card_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE),
        ForeignKey(
            entity = Tag::class,
            parentColumns = ["id"],
            childColumns = ["tag_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE)],
    primaryKeys = ["card_id", "tag_id"]
)
data class CardTagJoin(
    @ColumnInfo(name = "card_id") var cardId: String,
    @ColumnInfo(name = "tag_id") var tagId: String
)