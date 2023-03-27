package dev.alejo.world_holidays.ui.presentation.home.viewmodel

import android.content.Context
import android.util.Log
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
import dev.alejo.world_holidays.core.Response
import dev.alejo.world_holidays.data.model.HolidayModel
import dev.alejo.world_holidays.domain.GetCountriesUseCase
import dev.alejo.world_holidays.domain.GetHolidayUseCase
import dev.alejo.world_holidays.domain.GetNextPublicHolidayUserCase
import dev.alejo.world_holidays.domain.GetTodayHolidayUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHolidayUseCase: GetHolidayUseCase,
    private val getTodayHolidayUseCase: GetTodayHolidayUseCase,
    private val getNextPublicHolidayUseCase: GetNextPublicHolidayUserCase,
    private val getCountriesUseCase: GetCountriesUseCase
): ViewModel(){

    private lateinit var navHostController: NavHostController
    val all = listOf("aaa", "baa", "aab", "abb", "bab", "bbbbb")

    private val _searchValue = MutableStateFlow("")
    val searchValue: StateFlow<String> = _searchValue

    private val _dropdownExpanded = MutableStateFlow(false)
    val dropdownExpanded: StateFlow<Boolean> = _dropdownExpanded

    private val _dropdownOptions = MutableStateFlow(listOf<String>())
    val dropdownOptions: StateFlow<List<String>> = _dropdownOptions

    init {
        getCountries()
    }

    fun onCreate(navHostController: NavHostController) {
        this.navHostController = navHostController
    }

    private fun getCountries() {
        viewModelScope.launch {
            getCountriesUseCase().collect {
                Log.e(
                    "Countries",
                    it.data?.let {
                        it.size.toString()
                    } ?: it.message ?: "Error"
                )
                it.data?.let {
                    it.sortedBy { it.name }.forEach {
                        println(it.toString())
                    }
                }
            }
        }
    }

    fun onSearchChanged(inputTyped: String) {
        _dropdownExpanded.value = true
        _searchValue.value = inputTyped
        _dropdownOptions.value = all
            .filter { it.uppercase().startsWith(inputTyped) && it.uppercase() != inputTyped }
            .take(3)
    }

    fun onDropdownDismissRequest() {
        _dropdownExpanded.value = false
    }

    fun onItemSelected() {
        // Start searching with current _searchValue
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
        val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
        getHolidayByYear(currentYear)
        getNextHolidayByYear()
        getTodayHoliday(context)
    }

    private fun getTodayHoliday(context: Context) {
        viewModelScope.launch {
            isTodayHolidayLoading.postValue(true)
            getTodayHolidayUseCase(COLOMBIA_CODE).let { responseCode ->
                isTodayHolidayLoading.postValue(false)
                if(responseCode != CODE_200) {
                    todayHolidayResponse.postValue(
                        when(responseCode) {
                            CODE_200 -> context.getString(R.string.today_is_holiday)
                            CODE_204 -> context.getString(R.string.today_is_not_holiday)
                            else -> context.getString(R.string.validation_failure)
                        }
                    )
                }
            }
            todayHolidayDisplayed.postValue(true)
        }
    }

    fun getNextHolidayByYear() {
        viewModelScope.launch {
            val result = getNextPublicHolidayUseCase(COLOMBIA_CODE)
            if(result.isNotEmpty())
                nextPublicHolidayResponse.postValue(
                    if(getTodayHolidayName(result).isEmpty()) result[0] else result[1]
                )
        }
    }

    fun getHolidayByYear(year: String) {
        viewModelScope.launch {
            isGetHolidayByYearLoading.postValue(true)
            val result = getHolidayUseCase(COLOMBIA_CODE, year)
            if(result.isNotEmpty()) {
                holidayByYearResponse.postValue(result)
                val todayHolidayName = getTodayHolidayName(result)
                if(todayHolidayDisplayed.value == false && todayHolidayName.isNotEmpty()) {
                    todayHolidayDisplayed.postValue(true)
                    todayHolidayResponse.postValue(todayHolidayName)
                    isTodayHoliday.postValue(true)
                }
            }
            isGetHolidayByYearLoading.postValue(false)
        }
    }

    private fun getTodayHolidayName(allHolidays: List<HolidayModel>): String {
        val currentDate = Calendar.getInstance()
        val holiday = allHolidays.filter { it.date == DateUtils.getStringDateFromDate(currentDate.time) }
        if(holiday.isNotEmpty())
            return if(Locale.getDefault().language == "en") holiday[0].name else holiday[0].localName
        return ""
    }

}