package com.example.tokotlin.lesson9

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.tokotlin.R
import com.example.tokotlin.room.App.Companion.getHistoryWeatherDAO
import com.example.tokotlin.room.HistoryWeatherEntity
import com.example.tokotlin.room.ID
import com.example.tokotlin.room.NAME
import com.example.tokotlin.room.TEMPERATURE


private const val URI_ALL = 1
private const val URI_ID = 2
private const val ENTITY_PATH = "HistoryWeatherEntity"


class EducationContentProvider:ContentProvider() {


    private var authorities:String? = null
    private lateinit var uriMatcher:UriMatcher

    private var entityContentType:String? = null
    private var entityContentItemType:String? = null


    private lateinit var contentUri:Uri

    override fun onCreate(): Boolean {
        authorities = context?.resources?.getString(R.string.authorities)
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher.addURI(authorities, ENTITY_PATH, URI_ALL)
        uriMatcher.addURI(authorities, "${ENTITY_PATH}/#", URI_ID)
        entityContentType = "vnd.android.cursor.dir/vnd.$authorities.$ENTITY_PATH"
        entityContentItemType = "vnd.android.cursor.item/vnd.$authorities.$ENTITY_PATH"
        contentUri = Uri.parse("content://${authorities}/${ENTITY_PATH}")
        return true
    }



    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        val historyWeatherDAO = getHistoryWeatherDAO()
        val entity = mapper(values)
        historyWeatherDAO.insert(entity)
        val resultUri = ContentUris.withAppendedId(contentUri, entity.id)
        context?.contentResolver?.notifyChange(resultUri, null)
        return resultUri
    }

    private fun mapper(values:ContentValues?):HistoryWeatherEntity{
        values?.let {
            val id = values[ID] as Long
            val name = values[NAME] as String
            val temperature = values[TEMPERATURE] as Int
            return HistoryWeatherEntity(id, name,temperature)
        }

        return HistoryWeatherEntity()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

}