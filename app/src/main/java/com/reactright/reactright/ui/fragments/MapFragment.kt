package com.reactright.reactright.ui.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.reactright.reactright.R
import com.reactright.reactright.databinding.DialogViewBinding
import com.reactright.reactright.databinding.FragmentMainBinding
import com.reactright.reactright.databinding.FragmentMapBinding
import dagger.hilt.android.AndroidEntryPoint

class MapFragment : Fragment(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: FragmentMapBinding
    private lateinit var gMaps: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var handlerAnimation = Handler()

    companion object {
        private const val LOCATION_REQUEST_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater)

        binding.dialogButton.setOnClickListener {
            val view = View.inflate(requireActivity(), R.layout.dialog_view, null)
            val builder = AlertDialog.Builder(requireActivity())
            builder.setView(view)
            val dialog2 = builder.create()
            dialog2.show()
            dialog2.window?.setBackgroundDrawableResource(android.R.color.transparent)

            // Insert currentLatLong into Firestore when the "Yes" button is clicked
            view.findViewById<Button>(R.id.yesbtn).setOnClickListener {
                // Get the reference to your Firestore database
                val db = FirebaseFirestore.getInstance()
                val currentUser = FirebaseAuth.getInstance().currentUser
                val collection = db.collection("LOCATION")

                // Insert the currentLatLong
                val locationData = hashMapOf(
                    "latitude" to lastLocation.latitude,
                    "longitude" to lastLocation.longitude,
                    "user" to currentUser!!.email
                )

                collection.add(locationData)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Your locator has been sent", Toast.LENGTH_SHORT).show()
                        dialog2.dismiss()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Failed to send locator", Toast.LENGTH_SHORT).show()
                    }
            }

            view.findViewById<Button>(R.id.nobtn).setOnClickListener {
                dialog2.dismiss()
            }
        }
        stopPulse()

        return binding.root
    }

    private fun stopPulse(){
        runnable.run()
    }


    private var runnable = object : Runnable{
        override fun run(){
            binding.imgAnimation1.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000)
                .withEndAction {
                    binding.imgAnimation1.scaleX = 1f
                    binding.imgAnimation1.scaleY = 1f
                    binding.imgAnimation1.alpha = 1f
                }
            handlerAnimation.postDelayed(this,1500)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // Initialize Firestore here
        FirebaseApp.initializeApp(requireContext())
    }



    private val callback = OnMapReadyCallback { googleMap ->
        gMaps = googleMap
        gMaps.uiSettings.isZoomControlsEnabled = true
        gMaps.setOnMarkerClickListener(this)
        setupMap()
    }

    private fun setupMap(){

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE)

            return
        }
        gMaps.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener (requireActivity()){  location->
            if (location != null){
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLong)
                println("$currentLatLong")
                gMaps.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val inflater = inflater
        inflater.inflate(R.menu.map_menu,menu)
        true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.normal_map -> {
                gMaps.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.hybrid_map -> {
                gMaps.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }
            R.id.satellite_map -> {
                gMaps.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.terrain_map -> {
                gMaps.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            else ->  return onOptionsItemSelected(item)
        }
    }


    private fun placeMarkerOnMap(currentLatLng: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLng)
        markerOptions.title("SOS Location")
        gMaps.addMarker(markerOptions)
    }

    override fun onMarkerClick(p0: Marker) = false


}