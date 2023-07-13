package com.sisi.flooding.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.sisi.flooding.GlobalApplication
import com.sisi.flooding.R
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

        binding.textView2.visibility = View.GONE
        binding.textView3.visibility = View.GONE
        binding.textView4.visibility = View.GONE
        binding.imageView2.visibility = View.GONE
        binding.map.visibility = View.VISIBLE

//        GlobalApplication.prefs.setString("myCategory", "Korean Food")
        var viewMap = GlobalApplication.prefs.getString("viewMap", "0")
        var a1 = GlobalApplication.prefs.getString("a1", "0")
        var a2 = GlobalApplication.prefs.getString("a2", "0")
        var a3 = GlobalApplication.prefs.getString("a3", "0")


        if(viewMap == "0"){
            binding.textView2.visibility = View.GONE
            binding.textView3.visibility = View.GONE
            binding.textView4.visibility = View.GONE
            binding.imageView2.visibility = View.GONE
            binding.map.visibility = View.VISIBLE
        } else if (viewMap == "1") {
            binding.textView2.text = a1
            binding.textView3.text = a2
            binding.textView4.text = a3

            binding.textView2.visibility = View.VISIBLE
            binding.textView3.visibility = View.VISIBLE
            binding.textView4.visibility = View.VISIBLE
            binding.imageView2.visibility = View.VISIBLE
            binding.map.visibility = View.GONE
        } else if (viewMap == "2") {
            binding.textView2.text = a1
            binding.textView3.text = a2
            binding.textView4.text = a3

            binding.textView2.visibility = View.VISIBLE
            binding.textView3.visibility = View.VISIBLE
            binding.textView4.visibility = View.VISIBLE
            binding.imageView2.visibility = View.VISIBLE
            binding.map.visibility = View.GONE
        } else {
            binding.textView2.visibility = View.GONE
            binding.textView3.visibility = View.GONE
            binding.textView4.visibility = View.GONE
            binding.imageView2.visibility = View.GONE
            binding.map.visibility = View.VISIBLE
        }


        binding.searchText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val searchText = binding.searchText.text.toString()
                Log.d("Home searchText", searchText)
                // 완료 버튼이 눌렸을 때 실행될 코드 작성
                // text 변수에는 EditText에 입력된 문자열이 저장됩니다.
                GlobalApplication.prefs.setString("viewMap", "0")

                if(searchText == "청파동") {
                    GlobalApplication.prefs.setString("viewMap", "1")
                    GlobalApplication.prefs.setString("a1", "청파동의 침수 건수 결과")
                    GlobalApplication.prefs.setString("a2", "순위 : 128위")
                    GlobalApplication.prefs.setString("a3", "침수 주택 건수 : 12건")
                    Log.d("Home searchText", "청파동")
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .addToBackStack(null)
                        .commit()
                }

                if(searchText == "방배동") {
                    GlobalApplication.prefs.setString("viewMap", "2")
                    GlobalApplication.prefs.setString("a1", "방배동의 침수 건수 결과")
                    GlobalApplication.prefs.setString("a2", "순위 : 6위")
                    GlobalApplication.prefs.setString("a3", "침수 주택 건수 : 1990건")
                    Log.d("Home searchText", "방배동")
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .addToBackStack(null)
                        .commit()
                }


                true
            } else {
                GlobalApplication.prefs.setString("viewMap", "0")
                false
            }
        }














      }
}