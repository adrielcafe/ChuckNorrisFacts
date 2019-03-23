package cafe.adriel.chucknorrisfacts.model.response

import android.os.Parcelable
import cafe.adriel.chucknorrisfacts.model.Fact
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FactResponse(
    val result: List<Fact> = emptyList()
) : Parcelable
