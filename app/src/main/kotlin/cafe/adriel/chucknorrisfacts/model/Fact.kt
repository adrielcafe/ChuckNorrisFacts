package cafe.adriel.chucknorrisfacts.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fact(
    @Json(name = "value")
    val text: String,
    @Json(name = "category")
    val categories: List<String>? = null
) : Parcelable