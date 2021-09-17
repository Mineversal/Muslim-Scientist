package com.mineversal.muslimscientist

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//Entity annotation to specify the table's name
@Entity(tableName = "scientist")
//Parcelable annotation to make parcelable object
@Parcelize
data class Scientist(
    //PrimaryKey annotation to declare primary key
    //ColumnInfo annotation to specify the column's name
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "detail") var detail: String = "",
    @ColumnInfo(name = "photo") var photo: Int = 0,
    @ColumnInfo(name = "proffesion") var proffesion: String = "",
    @ColumnInfo(name = "era") var era: String = "",
    @ColumnInfo(name = "interest") var interest: String = "",
    @ColumnInfo(name = "wiki") var wiki: String = ""

) : Parcelable {
}
/*

data class Scientist (
    var id: Int,
    var name: String = "",
    var detail: String = "",
    var photo: Int = 0,
    var proffesion: String = "",
    var era: String = "",
    var interest: String = "",
    var wiki: String = ""
)

*/