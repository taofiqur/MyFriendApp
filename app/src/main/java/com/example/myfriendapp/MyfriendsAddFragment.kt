package com.example.myfriendapp

import android.os.Bundle
import android.os.Message
import android.provider.Settings.Global
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.temporal.TemporalAccessor

class MyfriendsAddFragment : Fragment() {
    private var edtName : TextView? = null
    private var edtEmail: TextView? = null
    private var edtTelp : TextView? = null
    private var edtAddress: TextView? = null
    private var btnSave: Button? = null
    private var spinnerGender: Spinner? = null

    companion object{
        fun newInstance(): MyfriendsAddFragment {
            return MyfriendsAddFragment()
        }
    }

    private var namaInput : String = ""
    private var emailInput : String = ""
    private var telpInput : String = ""
    private var alamatInput : String = ""
    private var genderInput : String = ""

    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_friend_add_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initLocalDB()
    }
    private fun initView() {
        spinnerGender = activity?.findViewById(R.id.spinnerGender)

        edtName = activity?.findViewById(R.id.editnama)
        edtEmail = activity?.findViewById(R.id.editEmail)
        edtTelp = activity?.findViewById(R.id.editTelp)
        edtAddress = activity?.findViewById(R.id.editAddress)

        btnSave = activity?.findViewById(R.id.btnSave)
        btnSave?.setOnClickListener {
        //    (activity as MainActivity).tampilMyFriendsFragment()
            validasiInput()
    }
    }

    private fun setDataSpinnerGener() {
        val adapter = ArrayAdapter.createFromResource((requireActivity()),
        R.array.gender_List,android.R.layout.simple_spinner_dropdown_item)
        spinnerGender?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        edtName = null
        edtEmail = null
        edtTelp = null
        edtAddress = null
        btnSave = null
        spinnerGender = null
    }

    private fun validasiInput() {
        namaInput = edtName?.text.toString()
        emailInput = edtEmail?.text.toString()
        telpInput = edtTelp?.text.toString()
        alamatInput = edtAddress?.text.toString()
        genderInput = spinnerGender?.selectedItem.toString()
     when{
          namaInput.isEmpty() -> edtName?.error = "nama tidak boleh kosong"

          genderInput.equals("pilih kelamin") -> tampilToast("kelamin harus dipilih")

          emailInput.isEmpty() ->edtEmail?.error = "email tidak boleh kosong"

          alamatInput.isEmpty() -> edtAddress?.error = "alamat tidak boleh kosong"

         else -> {
             val teman = Myfriend(
                 nama = namaInput, kelamin =
                 genderInput, email = emailInput, telp = telpInput, alamat = alamatInput
             )
             tambahDataTeman(teman)
         }
     }
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDatabase(requireActivity())
        myFriendDao = db?.myFriendDao()
        setDataSpinnerGener()
    }
    private fun tampilToast(message: String) {Toast.makeText(requireActivity(),
    message,Toast.LENGTH_SHORT).show() }

    private fun tambahDataTeman(teman: Myfriend) : Job {
        return GlobalScope.launch {
            myFriendDao?.tambahTeman(teman)
            (activity as MainActivity).tampilMyFriendsFragment()
        }
    }
}