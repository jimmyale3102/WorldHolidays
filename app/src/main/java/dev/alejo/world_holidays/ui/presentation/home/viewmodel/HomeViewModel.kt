package dev.alejo.world_holidays.ui.presentation.home.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.alejo.world_holidays.R
import dev.alejo.world_holidays.core.Constants.Companion.CODE_200
import dev.alejo.world_holidays.core.Constants.Companion.CODE_204
import dev.alejo.world_holidays.core.Constants.Companion.COLOMBIA_CODE
import dev.alejo.world_holidays.core.DateUtils
import dev.alejo.world_holidays.data.model.Country
import dev.alejo.world_holidays.data.model.HolidayModel
import dev.alejo.world_holidays.domain.GetCountriesUseCase
import dev.alejo.world_holidays.domain.GetHolidayUseCase
import dev.alejo.world_holidays.domain.GetNextPublicHolidayUserCase
import dev.alejo.world_holidays.domain.GetTodayHolidayUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHolidayUseCase: GetHolidayUseCase,
    private val getTodayHolidayUseCase: GetTodayHolidayUseCase,
    private val getNextPublicHolidayUseCase: GetNextPublicHolidayUserCase,
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {

    private lateinit var navHostController: NavHostController
    private lateinit var availableCountries: List<Country>

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _holidayTitle = MutableStateFlow("New Year's Day")
    val holidayTitle: StateFlow<String> = _holidayTitle

    private val _holidayDescription = MutableStateFlow("New Year's Day is the first day of the year, or January 1., in the Gregorian calendar.")
    val holidayDescription: StateFlow<String> = _holidayDescription

    private val _searchValue = MutableStateFlow(Country(name = "", countryCode = ""))
    val searchValue: StateFlow<Country> = _searchValue

    private val _dropdownExpanded = MutableStateFlow(false)
    val dropdownExpanded: StateFlow<Boolean> = _dropdownExpanded

    private val _dropdownOptions = MutableStateFlow(listOf<Country>())
    val dropdownOptions: StateFlow<List<Country>> = _dropdownOptions

    init {
        getCountries()
    }

    fun onCreate(navHostController: NavHostController) {
        this.navHostController = navHostController
    }

    private fun getCountries() {
        _isLoading.value = true
        viewModelScope.launch {
            getCountriesUseCase().collect { countriesResponse ->
                countriesResponse.data?.let { countries ->
                    availableCountries = countries.map { country ->
                        Country(name = country.name.uppercase(), countryCode = country.countryCode)
                    }
                }
            }
            _isLoading.value = false
        }
    }

    fun onSearchChanged(inputTyped: String) {
        _searchValue.value = Country(name = inputTyped, countryCode = "")
        _dropdownOptions.value = availableCountries
            .filter { country ->
                country.name.startsWith(inputTyped) && country.name != inputTyped
            }
            .take(10)
        _dropdownExpanded.value = _dropdownOptions.value.isNotEmpty()
    }

    fun onDropdownDismissRequest() {
        _dropdownExpanded.value = false
    }

    fun onItemSelected() {
        _searchValue.value = Country(
            name = _searchValue.value.name,
            countryCode = availableCountries.find { country ->
                country.name == _searchValue.value.name
            }?.countryCode ?: ""
        )
        validateSearchToGetData()
    }

    private fun validateSearchToGetData() {
        if (_searchValue.value.countryCode.isNotEmpty()) {
            getHolidayByYear(Calendar.getInstance().get(Calendar.YEAR).toString())
        }
    }


    val holidayByYearResponse = MutableLiveData<List<HolidayModel>>()
    val nextPublicHolidayResponse = MutableLiveData<HolidayModel>()
    val todayHolidayResponse = MutableLiveData<String>()
    private val todayHolidayDisplayed = MutableLiveData<Boolean>()
    val isTodayHolidayLoading = MutableLiveData<Boolean>()
    val isGetHolidayByYearLoading = MutableLiveData<Boolean>()
    val isTodayHoliday = MutableLiveData<Boolean>()

    fun onCreated(context: Context) {
        isTodayHoliday.postValue(false)
        todayHolidayDisplayed.postValue(false)
        getHolidayByYear("2022")
        getNextHolidayByYear()
        getTodayHoliday(context)
    }

    private fun getTodayHoliday(context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            getTodayHolidayUseCase(_searchValue.value.countryCode).let { responseCode ->
                _holidayTitle.value = when (responseCode) {
                    CODE_200 -> context.getString(R.string.today_is_holiday)
                    CODE_204 -> context.getString(R.string.today_is_not_holiday)
                    else -> context.getString(R.string.validation_failure)
                }
                _isLoading.value = false
            }
        }
    }

    fun getNextHolidayByYear() {
        viewModelScope.launch {
            val result = getNextPublicHolidayUseCase(COLOMBIA_CODE)
            if (result.isNotEmpty())
                nextPublicHolidayResponse.postValue(
                    if (getTodayHolidayName(result).isEmpty()) result[0] else result[1]
                )
        }
    }

    private fun getHolidayByYear(year: String) {
        viewModelScope.launch {
            // Show loading
            val result = getHolidayUseCase(_searchValue.value.countryCode, year)
            if (result.isNotEmpty()) {
                holidayByYearResponse.postValue(result)
                val todayHolidayName = getTodayHolidayName(result)
                if (todayHolidayDisplayed.value == false && todayHolidayName.isNotEmpty()) {
                    todayHolidayDisplayed.postValue(true)
                    todayHolidayResponse.postValue(todayHolidayName)
                    isTodayHoliday.postValue(true)
                }
            }
            // Remove loading
        }
    }

    private fun getTodayHolidayName(allHolidays: List<HolidayModel>): String {
        val currentDate = Calendar.getInstance()
        val holiday =
            allHolidays.filter { it.date == DateUtils.getStringDateFromDate(currentDate.time) }
        if (holiday.isNotEmpty())
            return if (Locale.getDefault().language == "en") holiday[0].name else holiday[0].localName
        return ""
    }
}