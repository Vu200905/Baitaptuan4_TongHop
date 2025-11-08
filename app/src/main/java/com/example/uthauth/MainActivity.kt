package com.example.uthauth
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uthauth.ui.theme.BlueThemeBackground
import com.example.uthauth.ui.theme.DarkThemeBackground
import com.example.uthauth.ui.theme.PinkThemeBackground
import com.example.uthauth.ui.theme.UTHAuthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            // Lắng nghe theme được lưu từ ViewModel
            val selectedTheme by themeViewModel.theme.collectAsState()

            // Áp dụng theme đã chọn cho toàn bộ ứng dụng
            UTHAuthTheme(selectedTheme = selectedTheme) { // Thay bằng tên Theme của bạn
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SettingScreen() // Hiển thị màn hình chọn theme
                }
            }
        }
    }
}

@Composable
fun SettingScreen(themeViewModel: ThemeViewModel = viewModel()) {
    // Biến tạm để lưu lựa chọn của người dùng trên UI
    var tempSelectedTheme by remember { mutableStateOf("Light") }
    val currentTheme by themeViewModel.theme.collectAsState()

    // Khi theme thật sự thay đổi, cập nhật lại lựa chọn trên UI
    LaunchedEffect(currentTheme) {
        tempSelectedTheme = currentTheme
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Setting", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(8.dp))
        Text("Choose your preferred theme display", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Spacer(Modifier.height(48.dp))

        // Hàng chứa các nút tròn chọn màu
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Bạn có thể thêm nút "Light" nếu muốn
            ThemeChip(color = BlueThemeBackground, isSelected = tempSelectedTheme == "Blue") { tempSelectedTheme = "Blue" }
            ThemeChip(color = PinkThemeBackground, isSelected = tempSelectedTheme == "Pink") { tempSelectedTheme = "Pink" }
            ThemeChip(color = DarkThemeBackground, isSelected = tempSelectedTheme == "Dark") { tempSelectedTheme = "Dark" }
        }

        Spacer(Modifier.height(64.dp))

        // Nút Apply
        Button(
            onClick = {
                // Khi nhấn Apply, gọi ViewModel để lưu theme vĩnh viễn
                themeViewModel.saveTheme(tempSelectedTheme)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Apply")
        }
    }
}

@Composable
fun ThemeChip(color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = 3.dp,
                // Nếu được chọn thì viền có màu primary của theme, nếu không thì màu xám
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                shape = CircleShape
            )
            .clickable(onClick = onClick)
    )
}
