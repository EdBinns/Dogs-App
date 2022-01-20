package com.edbinns.dogsapp.view.fragments

import android.R

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.edbinns.dogsapp.databinding.DialogSearchBinding
import com.edbinns.dogsapp.utils.NetWorkConnectivity
import com.edbinns.dogsapp.viewmodel.SearchDogViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchDialog : DialogFragment() {


    private var _binding: DialogSearchBinding? = null
    private val binding get() = _binding!!
    private val dogsViewModel: SearchDogViewModel by viewModels()

    private val breedsSpinnerList = MutableList(1) { i -> "Seleccione una raza" }
    private val subBreedsSpinnerList = MutableList(1) { i -> "Seleccione una sub-raza" }

    private var breedSelected = ""
    private var subBreedSelected = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DialogSearchBinding.inflate(inflater, container, false)
        setUpSpinnerBreed()
        val result = NetWorkConnectivity.checkForInternet(requireContext())
        dogsViewModel.getAllBreeds(result)
        observer()
        listener()
        return binding.root
    }


    private fun observer() {
        dogsViewModel.allBreeds.observe(viewLifecycleOwner, Observer { list ->
            if (!list.isNullOrEmpty()) {
                showBreedSpiner(list)
            }
        })
        dogsViewModel.allSubBreeds.observe(viewLifecycleOwner, Observer { list ->
            if (!list.isNullOrEmpty()) {
                showSubBreedSpiner(list)
                enabledSubBreedSpinner()
            }else disabledSubBreedSpinner()
        })

        dogsViewModel.isSet.observe(viewLifecycleOwner, Observer { result ->
           closeDialog()
        })
    }

    private fun getSpinnerAdapter(list : List<String>): ArrayAdapter<String>? {
        val adapter: ArrayAdapter<String>? = this.context?.let {
            ArrayAdapter<String>(
                it,
                R.layout.simple_spinner_item,
                list
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }
    private fun setUpSpinnerBreed() {

        val breedsAdapter = getSpinnerAdapter(breedsSpinnerList)

        binding.sBreed.apply {
            adapter = breedsAdapter
            breedsAdapter?.notifyDataSetChanged()
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        enabledButton()
                        breedSelected = breedsSpinnerList[position]
                        subBreedSelected = ""
                        dogsViewModel.getSubBreed(breedSelected)
                    } else {
                        disabledButton()
                        disabledSubBreedSpinner()
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    disabledSubBreedSpinner()
                    disabledButton()
                }
            }
        }

        val subBreedAdapter = getSpinnerAdapter(subBreedsSpinnerList)

        binding.sSubBreed.apply {
            adapter = subBreedAdapter
            subBreedAdapter?.notifyDataSetChanged()
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        //mostrar el segundo spinner o mandar la peticion de la raza actual
                        enabledButton()
                        subBreedSelected = subBreedsSpinnerList[position]
                    }
//
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    disabledButton()
                }
            }
        }
    }

    private fun listener(){
        binding.btnSearch.setOnClickListener {
            println("raza selected $breedSelected")
            dogsViewModel.search(breedSelected,subBreedSelected)
            subBreedSelected = ""
        }
        binding.btnCancel.setOnClickListener {
            closeDialog()
        }
    }

    private fun closeDialog(){
        getFragmentManager()?.beginTransaction()?.remove(this)?.commit();
    }
    private fun showBreedSpiner(breeds: List<String>) {
        breedsSpinnerList.clear()
        breedsSpinnerList.add(0, "Seleccione una raza")
        breedsSpinnerList.addAll(breeds)
    }
    private fun showSubBreedSpiner(subBreeds: List<String>) {
        subBreedsSpinnerList.clear()
        subBreedsSpinnerList.add(0, "Seleccione una sub raza")
        subBreedsSpinnerList.addAll(subBreeds)
    }

    private fun enabledSubBreedSpinner() {
        binding.tvSubBreed.visibility = View.VISIBLE
        binding.sSubBreed.visibility = View.VISIBLE
    }

    private fun disabledSubBreedSpinner() {
        binding.tvSubBreed.visibility = View.GONE
        binding.sSubBreed.visibility = View.GONE
    }

    private fun enabledButton() {
        binding.btnSearch.apply {
            isEnabled = true
        }
    }

    private fun disabledButton() {
        binding.btnSearch.apply {
            isEnabled = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}