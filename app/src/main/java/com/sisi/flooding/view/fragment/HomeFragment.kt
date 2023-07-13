package com.sisi.flooding.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sisi.flooding.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

//    val serviceAccount = FileInputStream("path/to/serviceAccountKey.json")
//    val options = FirebaseOptions.Builder()
//        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//        .setDatabaseUrl("https://sisi-7159c-default-rtdb.firebaseio.com/")
//        .build()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //edit text 입력시 firebase 에서 실시간 데이터 가져오기
        var searchText = binding.searchText.text.toString() //edit text 에서 가져오기







      }
}