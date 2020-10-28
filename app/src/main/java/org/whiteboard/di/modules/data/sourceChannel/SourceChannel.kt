package org.whiteboard.di.modules.data.sourceChannel

import android.content.Context
import com.google.gson.Gson
import org.json.JSONObject
import org.whiteboard.BuildConfig
import org.whiteboard.model.SourceModel
import timber.log.Timber
import java.net.URLDecoder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SourceChannelManager @Inject constructor(
    context: Context
) {

    private var source: String = ""
    private var rawSource: String = ""
    private var flavor: String = ""
    private var postFix: String = ""
    private var medium: String = ""
    private var api: String? = null
    private var apiUrl: String = ""
    private var hasCampaign: Boolean = false

    var hasBatch: Boolean = false
    var showRule: Boolean = true
    var giftCode: Boolean = false
    var charkhooneLogin: Boolean = false
    var hasBack: Boolean = !isMarket()

    val isCafeBazaar: Boolean = BuildConfig.FLAVOR == "cafeBazaar"
    val isMyket: Boolean = BuildConfig.FLAVOR == "myket"
    val isGooglePlay: Boolean = BuildConfig.FLAVOR.startsWith("googlePlay")
    val isWhiteBoardAr: Boolean = BuildConfig.FLAVOR.startsWith("whiteboardAr")
    val isWhiteBoardPlus: Boolean = BuildConfig.FLAVOR.startsWith("whiteboardPlus")

    init {


        if (!loadJSONFromAsset(context)) {
            improveSource()
        }

        try {
            if (postFix != "")
                setMediumPart((JSONObject(postFix)))
        } catch (e: java.lang.Exception) {
            setPostfix(postFix)
        }
    }

    fun isJson(str: String): Boolean {
        val json = createJson(str)

        return json.toString() != "{utm:na}"
    }

    fun setPostfix(sou: String?) {
        val json = createJson(correctUtm(sou))

        postFix = correctUtm(json.toString(), true) ?: ""
        setMediumPart(json)

    }

    fun getSource(): String {
        if (postFix != "")
            return """$source$medium|$postFix"""

        return source
    }

    fun getSimpleSource(): String {
        return source
    }

    fun getMedium(): String = medium

    fun getApiUrl(): String = apiUrl

    fun getRawSource(): String = rawSource

    fun readCampaign(): Boolean = hasCampaign

    fun getFlavor(): String = BuildConfig.FLAVOR

    fun isMarket(): Boolean =
        (BuildConfig.FLAVOR == "cafeBazaar") or (BuildConfig.FLAVOR == "myket")

    fun detectCampaign(): String {

        return when {

            !hasCampaign -> "directly(telegram,etc...)"

            postFix.contains("organic") -> "install directly from google play or an apk"

            postFix.contains("not") -> "campaign on Google Play"

            else -> postFix
        }

    }

    private fun createJson(str: String?): JSONObject {
        try {
            val text = URLDecoder.decode(str, "UTF-8").replace("\\{|\\}".toRegex(), "_")

            val temp = when {
                text.isEmpty() -> ""
                text.contains("=") -> text
                else -> "utm=$text"
            }

            val raw = ("{" + temp.replace("&".toRegex(), ",") + "}").replace("\\s".toRegex(), "")

            return (JSONObject(correctUtm(raw, true)!!))

        } catch (ex: Exception) {
            ex.printStackTrace()
            Timber.e(ex)
        }

        return JSONObject("{utm:na}")
    }

    private fun improveSource() {
        if (rawSource.endsWith("X", true)) {
            source = rawSource.substring(0, rawSource.length - 1)
            hasCampaign = false
        } else {
            source = rawSource
            hasCampaign = true
        }
    }

    private fun loadJSONFromAsset(context: Context): Boolean {

        var flag = false

        if (context.assets.list("")?.contains("sc.in") == true) {
            try {

                val reader = context.assets.open("sc.in")

                val size = reader.available()
                val buffer = ByteArray(size)

                reader.read(buffer)
                reader.close()

                val text = String(buffer)

                val sourceModel = Gson().fromJson(text, SourceModel::class.java)

                if (sourceModel.source == "") {
                    rawSource = BuildConfig.Source_Channel
                } else {
                    source = sourceModel.source
                    rawSource = source
                }

                flavor = sourceModel.FLAVOR ?: BuildConfig.FLAVOR
                hasCampaign = sourceModel.campaign
                api = sourceModel.api
                hasBatch = sourceModel.batch
                showRule = sourceModel.showRule
                giftCode = sourceModel.giftCode
                hasBack = sourceModel.hasBack
                charkhooneLogin = sourceModel.charkhooneLogin

            } catch (ex: Exception) {
                ex.printStackTrace()
                flag = false
                if (rawSource == "")
                    rawSource = BuildConfig.Source_Channel

                flavor = BuildConfig.FLAVOR

//            api = Urls.NEW_ANALYTICS_BASE_URL_ME
            }
        } else {
            if (rawSource == "")
                rawSource = BuildConfig.Source_Channel

            flavor = BuildConfig.FLAVOR
        }

        return flag
    }

    private fun setMediumPart(json: JSONObject) {

        medium = try {

            "_${json.getString("af_sub1")}"
        } catch (ex: java.lang.Exception) {
            try {
                "_${json.getString("utm_medium")}"
            } catch (ex: java.lang.Exception) {
                ""
            }
        }

        val gclid = try {
            "_${json.getString("gclid")}"
        } catch (ex: java.lang.Exception) {
            ""
        }

        val extra = try {
            "_${json.getString("utm_content")}"
        } catch (ex: java.lang.Exception) {
            try {
                "_${json.getString("utm_term")}"
            } catch (ex: java.lang.Exception) {
                ""
            }
        }

        if (medium.equals("_organic", true)) {
            charkhooneLogin = true
        }

        if (extra != "") {
            charkhooneLogin = false
            hasCampaign = true
            medium = "_search"
        }

        if (gclid != "") {
            medium = "_adw"
            hasCampaign = true
        }

        if (!hasCampaign)
            medium = ""
    }

    private fun correctUtm(utm: String?, withAc: Boolean = false): String? {

        var acR = ""
        var acL = ""

        if (withAc) {
            acL = "}"
            acR = "{"
        }

        val t1 = utm?.replace("{utm=_", acR)

        return t1?.replace("_}", acL)
    }

    companion object {
        const val REFERRER = "referrer"

    }
}