package com.sisi.flooding.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.sisi.flooding.R
import com.sisi.flooding.databinding.FragmentMypageBinding
import com.sisi.flooding.view.ViewPager2Adapter


class MypageFragment : BaseFragment<FragmentMypageBinding>() {

    private lateinit var viewPager: ViewPager2
    private lateinit var indicators: Array<ImageView?>

    //viewPager2 이미지 초기화
    private val imageList = mutableListOf<Int>().apply {
        add(R.drawable.a1)
        add(R.drawable.a2)
        add(R.drawable.a3)
        add(R.drawable.a4)
        add(R.drawable.a5)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMypageBinding {
        return FragmentMypageBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initViewPager2()


    }

    private fun initViewPager2() {
        viewPager = binding.viewPager.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 1
            adapter = ViewPager2Adapter(this@MypageFragment, imageList)
        }

        bindViewPager2Event()
        setupIndicators(imageList.size)
        setCurrentIndicator(0)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setCurrentIndicator(position)
            }
        })
    }

    private fun bindViewPager2Event() {
        // Handle page selection event
    }

    private fun setupIndicators(count: Int) {
        indicators = arrayOfNulls<ImageView>(count)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(16, 8, 16, 8)
        }

        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext()).apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.bg_indicator_inactive
                    )
                )
                layoutParams = params
            }
            binding.layoutIndicators.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(position: Int) {
        for (i in indicators.indices) {
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    if (i == position) {
                        R.drawable.bg_indicator_active
                    } else {
                        R.drawable.bg_indicator_inactive
                    }
                )
            )
        }
    }

}