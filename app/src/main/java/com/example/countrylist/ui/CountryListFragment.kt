package com.example.countrylist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countrylist.databinding.FragmentCountryListBinding
import com.example.countrylist.viewmodel.CountriesScreenState
import com.example.countrylist.viewmodel.CountryViewModel
import kotlinx.coroutines.launch

class CountryListFragment : Fragment() {

    private var _binding: FragmentCountryListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[CountryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchCountries()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.countriesScreenState.collect { screenState ->
                    when (screenState) {
                        CountriesScreenState.Loading -> {
                            binding.recyclerview.visibility = View.GONE
                            binding.errorText.visibility = View.GONE
                            binding.loadingIcon.visibility = View.VISIBLE
                        }
                        CountriesScreenState.Error -> {
                            binding.errorText.visibility = View.VISIBLE
                            binding.loadingIcon.visibility = View.GONE
                            binding.recyclerview.visibility = View.GONE
                        }
                        is CountriesScreenState.Success -> {
                            binding.errorText.visibility = View.GONE
                            binding.loadingIcon.visibility = View.GONE
                            binding.recyclerview.visibility = View.VISIBLE

                            val countries = screenState.data
                            binding.recyclerview.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = CountryRecyclerViewAdapter(countries)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}