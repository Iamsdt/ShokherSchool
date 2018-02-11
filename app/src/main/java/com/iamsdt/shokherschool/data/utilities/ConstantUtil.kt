package com.iamsdt.shokherschool.data.utilities

/**
 * Created by Shudipto Trafder on 11/23/2017.
 * at 11:58 PM
 */
class ConstantUtil{
    companion object {
        //base url
        const val retrofitBaseUrl:String = "https://shokherschool.com/wp-json/wp/v2/"


        const val dateSpName = "DateSp"
        const val dateSpDefaultValue = "2017-01-26T23:32:00"

        const val ServiceRunningDate = "ServiceRunningDate"
        const val ServiceSp = "ServiceSp"

        //intent parse to main activity to details activity
        const val intentDetails = "IntentDetails"

        //theme
        const val colorSp = "colorSp"
        const val themeKey = "themeKey"
        const val NIGHT_MODE_SP_KEY: String = "NightModeSp"
        const val NIGHT_MODE_VALUE_KEY: String = "NightSP"

        //all sp
        const val APP_RUN_FOR_FIRST_TIME = "appRunForFirstTime"

        //event bus
        const val UPDATE_SERVICE = "UpdateService"
        const val DATA_INSERT_SERVICE = "dataInsertService"
        const val POST_DATA_SERVICE = "postDataService"

        //success and error
        const val SUCCESS = "SUCCESS"
        const val ERROR = "ERROR"
    }
}