package com.example.myfriendapp

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyfriendsFragment : Fragment() {
    lateinit var listTeman : List<Myfriend>
    private var fabAddFriend: FloatingActionButton? = null

    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null
    private var listMyFriends: RecyclerView? = null

    companion object{
        fun newInstance():MyfriendsFragment {
            return MyfriendsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_friend_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initLocalDB()
    }
    private fun initView() {
        fabAddFriend = activity?.findViewById(R.id.fabAddFriend)
        listMyFriends = activity?.findViewById(R.id.ListMyFriend)
        fabAddFriend?.setOnClickListener{ (activity as MainActivity).tampilMyFriendsAddFragment()
        }

     //   simulasiDataTeman()
      //  tampilTeman()
        ambilDataTeman()
    }

    private fun ambilDataTeman(){

        listTeman = ArrayList()
        myFriendDao?.ambilSemuaTeman()?.observe(requireActivity()) { r -> listTeman = r
            when {
                listTeman?.size == 0 -> tampilToast("Belum ada data teman")
                else -> {
                    tampilTeman()
                }
            }
        }
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDatabase(requireActivity())
        myFriendDao = db?.myFriendDao()
    }

    private fun tampilToast(message: String) {
        Toast.makeText(requireActivity(),message,Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        fabAddFriend = null
        listMyFriends = null
    }

    private fun simulasiDataTeman(){
        listTeman = ArrayList()

     //   listTeman.add(Myfriend(0, "Topik", "laki laki", "topik@gmail.com", "087761574626","Sawoajajar"))
      //  listTeman.add(Myfriend(1, "Tegar", "perempuan","tegar@gmail.com", "081276574827","Sawojajar"))
    }

    private fun tampilTeman() {
//        val listMyFriends = view?.findViewById<RecyclerView>(R.id.ListMyFriend)
        listMyFriends?.layoutManager = LinearLayoutManager(activity)
        listMyFriends?.adapter = MyFriendAdapter(requireActivity(), listTeman as ArrayList<Myfriend>)
    }
    }
