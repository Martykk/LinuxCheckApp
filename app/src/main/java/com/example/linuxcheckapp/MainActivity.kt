package com.example.linuxcheckapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.tooling.preview.Preview
// --- 佈局相關 ---
import androidx.compose.foundation.layout.* // --- Material3 UI 元件 ---
import androidx.compose.material3.* // --- 狀態管理與生命週期 ---
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
// --- 協程 (背景執行網路請求必備) ---
import kotlinx.coroutines.launch
import com.example.linuxcheckapp.ui.theme.LinuxCheckAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LinuxCheckAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ServerMonitorScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServerMonitorScreen() {
    // 狀態變數：用來存放伺服器回傳的文字結果
    var statusText by remember { mutableStateOf("點擊按鈕檢查伺服器...") }
    // 協程作用域：用來啟動非同步任務
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 顯示結果的文字框
        Text(text = statusText, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(20.dp))

        // 觸發請求的按鈕
        Button(onClick = {
            statusText = "連線中..."

            // 啟動協程 (如同在 Linux 背景跑一個 Task)
            scope.launch {
                try {
                    // 呼叫我們定義好的 Retrofit 請求
                    val response = RetrofitClient.instance.getServerStatus()

                    if (response.isSuccessful) {
                        val data = response.body()
                        // 修正點：確保變數名稱與 Data Class 一致，並加上 status 顯示
                        statusText = "連線成功！\n" +
                                "型號: ${data?.model}\n" +
                                "CPU 負載: ${data?.cpu_load}%\n" +
                                "記憶體使用率: ${data?.memory_usage}%\n" +
                                "伺服器狀態: ${data?.status}"
                    } else {
                        statusText = "伺服器錯誤: ${response.code()}"
                    }
                } catch (e: Exception) {
                    // 處理網路斷線或 IP 錯誤
                    statusText = "連線失敗1: ${e.message}"
                }
            }
        }) {
            Text("檢查 Linux 伺服器狀態")
        }
    }
}