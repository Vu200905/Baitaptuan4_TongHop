package com.example.uthauth // Thay bằng package name của bạn

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// --- TOÀN BỘ LOGIC LƯU VÀ ĐỌC DATASTORE NẰM TRONG FILE NÀY ---

// 1. Tạo một "kho" DataStore duy nhất cho toàn ứng dụng
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_prefs")

// 2. Class Helper để thực hiện việc đọc/ghi vào kho
class AppThemeDataStore(context: Context) {
    private val appContext = context.applicationContext

    companion object {
        val THEME_KEY = stringPreferencesKey("app_theme_key")
    }

    // Lấy dữ liệu theme. Trả về Flow để tự động cập nhật
    val getTheme: Flow<String> = appContext.dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: "Light" // Nếu chưa có thì mặc định là "Light"
    }

    // Lưu dữ liệu theme
    suspend fun saveTheme(themeName: String) {
        appContext.dataStore.edit { preferences ->
            preferences[THEME_KEY] = themeName
        }
    }
}

// 3. ViewModel: "Bộ não" mà giao diện sẽ tương tác
class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val appThemeDataStore = AppThemeDataStore(application)

    // Biến theme mà giao diện sẽ "lắng nghe"
    val theme: StateFlow<String> = appThemeDataStore.getTheme.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "Light" // Giá trị ban đầu
    )

    // Hàm để giao diện gọi khi muốn lưu theme mới
    fun saveTheme(themeName: String) {
        viewModelScope.launch {
            appThemeDataStore.saveTheme(themeName)
        }
    }
}
