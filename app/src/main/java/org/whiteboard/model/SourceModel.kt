package org.whiteboard.model

import com.google.gson.annotations.SerializedName

class SourceModel(
    @SerializedName("buildVariants")
    val FLAVOR: String? = null,
    @SerializedName("source")
    val source: String = "",
    @SerializedName("campaign")
    val campaign: Boolean = false,
    @SerializedName("batch")
    val batch: Boolean = false,
    @SerializedName("giftCode")
    val giftCode: Boolean = false,
    @SerializedName("hasBack")
    val hasBack: Boolean = false,
    @SerializedName("showRule")
    val showRule: Boolean = false,
    @SerializedName("charkhooneLogin")
    val charkhooneLogin: Boolean = false,
    @SerializedName("api")
    val api: String? = null
)