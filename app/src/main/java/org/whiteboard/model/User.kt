package org.whiteboard.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "users")
@JsonObject
@Parcelize
open class User(

    @PrimaryKey
    @JsonField(name = ["Id"])
    var id: Int? = null,

    @ColumnInfo
    @JsonField(name = ["FirstName"])
    var firstName: String? = null,

    @ColumnInfo
    @JsonField(name = ["LastName"])
    var lastName: String? = null,

    @ColumnInfo
    @JsonField(name = ["Email"])
    var email: String? = null,

    @ColumnInfo
    @JsonField(name = ["UserName"])
    var userName: String? = null,

    @ColumnInfo
    @JsonField(name = ["PhoneNo"])
    var phone: String? = null,

    @Ignore
    @JsonField(name = ["Token"])
    var token: String? = null,

    @ColumnInfo
    @JsonField(name = ["AvatarUrl"])
    var avatar: String? = null,

    @Ignore
    @JsonField(name = ["Packages"])
    var packages: List<PackagesItem?>? = null,

    @Ignore
    @JsonField(name = ["InviteCode"])
    var inviteCode: String? = null

) : Parcelable {

    fun getFullName() = "${firstName ?: ""} ${lastName ?: ""}"

    fun getSubscriptionRemainingTime(): Long {
        var remainingTime = 0L
        packages?.forEach {
            remainingTime += it?.remainingTime ?: 0
        }
        return remainingTime
    }
}


@Parcelize
@JsonObject
data class PackagesItem(

    @JsonField(name = ["Id"])
    var id: Int? = null,

    @JsonField(name = ["PackageId"])
    var packageId: Int? = null,

    @JsonField(name = ["Name", "PackageName"])
    var name: String? = null,

    @JsonField(name = ["Sku"])
    var sku: String? = null,

    @JsonField(name = ["SkuToken"])
    var skuToken: String? = null,

    @JsonField(name = ["RemainingTime"])
    var remainingTime: Int? = null,

    @JsonField(name = ["Price"])
    var price: Int? = null

) : Parcelable