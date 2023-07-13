package com.sisi.flooding.view.fragment

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.sisi.flooding.GlobalApplication
import com.sisi.flooding.R
import com.sisi.flooding.databinding.FragmentNaverMapBinding
import kotlinx.coroutines.launch

class NaverMapFragment : BaseFragment<FragmentNaverMapBinding>(), OnMapReadyCallback {
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private lateinit var fusedLocationClient: FusedLocationProviderClient
//    private lateinit var retService: HomeService
    private val markerIdLiveData = MutableLiveData<Int>()
    private var placeId: Int = 1

    var permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val permissionRequest = 99

    private val markerDataLiveData = MutableLiveData<MarkerData>()

    data class MarkerData(
        val name: String,
        val address: String,
        val category: String,
        val distance: String,
        val imageLink: String
    )

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNaverMapBinding {
        return FragmentNaverMapBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myLatitude = GlobalApplication.prefs.getString("myLatitude", "37")
        val myLongitude = GlobalApplication.prefs.getString("myLongitude", "126")
        var myState = GlobalApplication.prefs.getString("myState", "0")

        // Retrofit
//        retService = RetrofitClient
//            .getRetrofitInstance()
//            .create(HomeService::class.java)

        // Check permissions
        if (isPermitted()) {
            startProcess()
        } else {
            requestPermissions(permissions, permissionRequest)
        }

//        binding.floatingActionButton.setOnClickListener {
//            myState = "1"
//            GlobalApplication.prefs.setString("myCategory", " ")
//            GlobalApplication.prefs.setString("myState", myState)
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.main_frm, RecommendRestaurantFragment())
//                .addToBackStack(null)
//                .commit()
//        }

        NaverMapSdk.getInstance(requireContext()).client =
            NaverMapSdk.NaverCloudPlatformClient("2s7rl7li7r")

//        binding.cardView.visibility = View.GONE

//        lifecycleScope.launch {
//            try {
//                val response = retService.getMap(myLatitude.toDouble(), myLongitude.toDouble())
//                if (response.isSuccessful) {
//                    Log.d(
//                        "Naver map 성공 보낸 data",
//                        "Success, latitude: $myLatitude, longitude: $myLongitude"
//                    )
//                    val mapData = response.body()?.markerInfoList
//
//                    if (mapData != null) {
//                        for (markerInfo in mapData) {
//                            val marker = Marker()
//                            marker.position = LatLng(markerInfo.latitude, markerInfo.longitude)
//                            marker.tag = markerInfo.id
//
//                            // Customize marker appearance
//                            val customIcon = OverlayImage.fromResource(R.drawable.ic_my_mark)
//                            marker.icon = customIcon
//
//                            // 마커 클릭 이벤트 리스너 설정
//                            marker.setOnClickListener { overlay ->
//                                if (overlay is Marker) {
//                                    val markerId = overlay.tag as? Int
//                                    // 마커 클릭 시 실행 동작
//                                    markerId?.let { id ->
//                                        binding.cardView.visibility = View.VISIBLE
//                                        fetchMarkerData(id, myLatitude.toDouble(), myLongitude.toDouble())
//                                        markerIdLiveData.value = id
//                                    }
//                                }
//                                true
//                            }
//
//                            marker.map = naverMap
//                        }
//                    }
//                } else {
//                    // Error handling
//                    Log.e("Naver map 통신 실패", "Error: ${response.code()} ${response.message()}")
//                }
//            } catch (e: Exception) {
//                // Exception handling
//                Log.e("Naver map 통신 실패2", "Exception: ${e.message}")
//            }
//        }

        // Observe the marker data LiveData
//        markerDataLiveData.observe(viewLifecycleOwner) { markerData ->
//            // Update UI with marker data
////            binding.tvName.text = markerData.name.chunked(20).joinToString("\n")
////            binding.tvAddress.text = markerData.address.chunked(28).joinToString("\n")
////            binding.tvCategory.text = markerData.category
////            binding.distance.text = markerData.distance.toString()
//            if (!markerData?.imageLink.isNullOrEmpty()) {
////                Glide.with(view)
////                    .load(markerData?.imageLink)
////                    .into(binding.ivRestaurantImage)
//            }
//        }
//        // Observe the marker ID LiveData
//        markerIdLiveData.observe(viewLifecycleOwner) { markerId ->
//            placeId = markerId
//        }
//        binding.cardView.setOnClickListener{
//            GlobalApplication.prefs.setString("placeId","$placeId")
////            parentFragmentManager.beginTransaction()
////                .replace(R.id.main_frm, DetailRestaurantFragment())
////                .addToBackStack(null)
////                .commit()
//        }
    }

    // Check if permissions are granted
    private fun isPermitted(): Boolean {
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    perm
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    // Load the map if permissions are granted
    private fun startProcess() {
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.mapView) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.mapView, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionRequest) {
            if (isPermitted()) {
                //startProcess()
            } else {
                requireActivity().finish()
            }
        }
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        val cameraPosition = CameraPosition(
            LatLng(37.5666102, 126.9783881),  // 위치 지정
            16.0 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition
        this.naverMap = naverMap

        //zoom 제한
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 5.0

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        setUpdateLocationListener()
    }

    // 생명주기
    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    @SuppressLint("MissingPermission")
    private fun setUpdateLocationListener() {
        val locationRequest = LocationRequest.create().apply {
            priority = PRIORITY_HIGH_ACCURACY
            interval = 600000 //10분에 한번 업데이트
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                if (result == null) return
                for (location in result.locations) {
                    val latitude = location.latitude.toString()
                    val longitude = location.longitude.toString()

                    Log.d("location: ", "$latitude, $longitude")
                    GlobalApplication.prefs.setString("myLatitude", latitude)
                    GlobalApplication.prefs.setString("myLongitude", longitude)

                    // Update server with new latitude and longitude
//                    updateServerWithLocation(latitude, longitude)

                    setLastLocation(location)

                    naverMap.locationOverlay.run {
                        isVisible = true
                        position = LatLng(location.latitude, location.longitude)
                    }
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private fun setLastLocation(location: Location) {
        val myLocation = LatLng(location.latitude, location.longitude)
        val marker = Marker()
        marker.position = myLocation

        //마커
        val cameraUpdate = CameraUpdate.scrollTo(myLocation)
        naverMap.moveCamera(cameraUpdate)
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 5.0

        marker.map = null
    }


}