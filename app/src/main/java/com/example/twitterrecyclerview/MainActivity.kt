package com.example.twitterrecyclerview
import android.os.Bundle
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.twitterrecyclerview.RecyclerView.SmsMessageAdapter
import com.example.twitterrecyclerview.model.Sms

class MainActivity : AppCompatActivity() {

    private val REQUEST_SMS_PERMISSION = 123
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SmsMessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if SMS permissions are granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) !=
            PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            // Request permissions
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_SMS
                ),
                REQUEST_SMS_PERMISSION
            )
        } else {
            // Permissions already granted, proceed with reading SMS
            readSMS()
        }

        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewSms)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SmsMessageAdapter(readSMS())

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with reading SMS
                readSMS()
            } else {
                // Permission denied, handle it as needed
            }
        }
    }

    private fun readSMS(): List<Sms> {
        val smsUri: Uri = Uri.parse("content://sms/inbox") // You can also use "content://sms/sim" for SIM messages
        val contentResolver: ContentResolver = contentResolver
        val cursor: Cursor? = contentResolver.query(smsUri, null, null, null, null)
        val smsList = mutableListOf<Sms>()

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val address = cursor.getString(cursor.getColumnIndex("address"))
                val date = cursor.getLong(cursor.getColumnIndex("date"))
                val body = cursor.getString(cursor.getColumnIndex("body"))

                val sms = Sms(address, date.toString(), body)
                smsList.add(sms)
            }

            cursor.close()
        }
        Log.d("Test", "List of SMS messages : $smsList ")

        return smsList
    }
}


