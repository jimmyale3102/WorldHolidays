package dev.alejo.world_holidays.ui.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.alejo.world_holidays.R
import dev.alejo.world_holidays.core.Constants.Companion.CODE_200
import dev.alejo.world_holidays.core.Constants.Companion.CODE_204
import dev.alejo.world_holidays.core.Constants.Companion.CODE_400
import dev.alejo.world_holidays.core.navigation.Screen
import dev.alejo.world_holidays.core.uitls.DateUtils
import dev.alejo.world_holidays.core.uitls.UiText
import dev.alejo.world_holidays.core.uitls.UiText.DynamicString
import dev.alejo.world_holidays.core.uitls.UiText.StringResource
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

    private val _holidayTitle: MutableStateFlow<UiText> = MutableStateFlow(DynamicString(""))
    val holidayTitle: StateFlow<UiText> = _holidayTitle

    private val _searchValue = MutableStateFlow(Country(name = "", countryCode = ""))
    val searchValue: StateFlow<Country> = _searchValue

    private val _dropdownExpanded = MutableStateFlow(false)
    val dropdownExpanded: StateFlow<Boolean> = _dropdownExpanded

    private val _dropdownOptions = MutableStateFlow(listOf<Country>())
    val dropdownOptions: StateFlow<List<Country>> = _dropdownOptions

    private val _holidaysByYear = MutableStateFlow(emptyList<HolidayModel>())
    val holidaysByYear: StateFlow<List<HolidayModel>> = _holidaysByYear

    private val _nextHoliday: MutableStateFlow<UiText> = MutableStateFlow(DynamicString(""))
    val nextHoliday: StateFlow<UiText> = _nextHoliday

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
            getNextHolidayByYear()
            getTodayHoliday()
        }
    }

    private fun getTodayHoliday() {
        viewModelScope.launch {
            _isLoading.value = true
            getTodayHolidayUseCase(_searchValue.value.countryCode).let { responseCode ->
                if (responseCode != CODE_200) {
                    _holidayTitle.value = when (responseCode) {
                        CODE_204 -> StringResource(resId = R.string.today_is_not_holiday)
                        CODE_400 -> StringResource(resId = R.string.validation_failure)
                        else -> StringResource(resId = R.string.country_code_unknown)
                    }
                }
                _isLoading.value = false
            }
        }
    }

    private fun getNextHolidayByYear() {
        viewModelScope.launch {
            val result = getNextPublicHolidayUseCase(_searchValue.value.countryCode)
            if (result.isNotEmpty()) {
                val nextHoliday =
                    if (getTodayHolidayName(result).isEmpty()) result[0] else result[1]
                _nextHoliday.value = DynamicString(
                    "Upcoming holiday is on %s and it\'s for %s".format(
                        DateUtils.getNextHolidayFormatted(nextHoliday.date),
                        nextHoliday.name
                    )
                )
            }
        }
    }

    fun getHolidayByYear(year: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = getHolidayUseCase(_searchValue.value.countryCode, year)
            if (result.isNotEmpty()) {
                _holidaysByYear.value = result
                val todayHolidayName = getTodayHolidayName(result)
                if (todayHolidayName.isNotEmpty()) {
                    _holidayTitle.value = DynamicString(value = todayHolidayName)
                }
            }
            _isLoading.value = false
        }
    }

    private fun getTodayHolidayName(allHolidays: List<HolidayModel>): String {
        val currentDate = Calendar.getInstance()
        val holiday = allHolidays
            .filter { it.date == DateUtils.getStringDateFromDate(currentDate.time) }
        if (holiday.isNotEmpty())
            return holiday[0].name
        return ""
    }

    fun navigateToAboutScreen() {
        navHostController.navigate(Screen.About.route)
    }
}