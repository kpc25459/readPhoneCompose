package net.dev.readphonecompose

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.CallLog
import android.provider.ContactsContract

object Utils {
    fun readInboxConversations(context: Context): List<String> = readConversations(context, INBOX)
    fun readSentConversations(context: Context): List<String> = readConversations(context, SENT)
    fun readDraftConversations(context: Context): List<String> = readConversations(context, DRAFT)

    fun readContacts(context: Context): List<String> {
        val messages = mutableListOf<String>()

        val cur: Cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )!!

        if (cur.count > 0) {
            while (cur.moveToNext()) {
                val id = cur.getString(cur.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                val name = cur.getString(cur.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))

                if (cur.getInt(cur.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val pCur = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )!!
                    while (pCur.moveToNext()) {
                        val phoneNo = pCur.getString(pCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))

                        messages.add("$name -> $phoneNo")
                    }
                    pCur.close()
                }
            }
        }
        cur.close()

        return messages
    }

    fun readCalls(context: Context): List<String> {

        val messages = mutableListOf<String>()

        //val allCalls = Uri.parse("content://call_log/calls")

        val c: Cursor = context.contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC")!!


        if (c.count > 0) {
            while (c.moveToNext()) {

                val num = c.getString(c.getColumnIndexOrThrow(CallLog.Calls.NUMBER)) // for  number

                val name = c.getString(c.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME)) // for name

                val duration = c.getString(c.getColumnIndexOrThrow(CallLog.Calls.DURATION)) // for duration

                val type = c.getString(c.getColumnIndexOrThrow(CallLog.Calls.TYPE)).toInt() // for call type, Incoming or out going.


                messages.add("$num -> $name -> $duration -> $type")
            }
        }

        c.close()

        return messages
    }

    private fun readConversations(context: Context, scope: String): List<String> {

        val messages = mutableListOf<String>()

        val cursor: Cursor? = context.contentResolver.query(Uri.parse(scope), null, null, null, null)

        cursor ?: throw NullPointerException("cursor")

        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                var msgData = ""
                for (idx in 0 until cursor.columnCount) {
                    msgData += " " + cursor.getColumnName(idx).toString() + ":" + cursor.getString(
                        idx
                    )
                }

                messages.add(msgData)

            } while (cursor.moveToNext())
        } else {
            // empty box, no SMS
        }

        cursor.close()

        return messages
    }

    const val INBOX = "content://sms/inbox";
    const val SENT = "content://sms/sent";
    const val DRAFT = "content://sms/draft";

}



