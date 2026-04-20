# LinuxCheckApp

Android 遠端監控 App，可即時查看 Linux 機器的 CPU、記憶體使用狀況，並支援遠端啟動機台。

## 架構

```
Android App（Jetpack Compose）
    └── Retrofit + OkHttp（自動帶 API Key）
            └── FastAPI Server（psutil 抓取系統數據）
                    └── Docker 容器部署於 Linux 機器
```

## 功能

- 即時查看 CPU 使用率、記憶體使用率、機台型號、伺服器狀態
- 遠端啟動機台
- API Key 驗證，防止未授權存取
- CI/CD 自動化部署（GitHub Actions + Tailscale + SSH）

## 技術棧

| 層級 | 技術 |
|------|------|
| Android UI | Jetpack Compose、Material3 |
| Android 網路 | Retrofit2、OkHttp、Gson |
| 後端 | FastAPI、psutil |
| 容器化 | Docker |
| CI/CD | GitHub Actions、Tailscale |

## 快速開始

### 後端

```bash
cd server
cp .env.example .env        # 填入你的 API_KEY
pip install -r requirements.txt
uvicorn server:app --host 0.0.0.0 --port 8000
```

或用 Docker：
```bash
docker build -t linux-check-app .
docker run -d -p 8000:8000 -e API_KEY=你的金鑰 linux-check-app
```

### Android App

1. 在專案根目錄的 `local.properties` 加入：
   ```
   API_KEY=你的金鑰（需與後端相同）
   ```
2. 修改 `RetrofitClient.kt` 中的 `BASE_URL` 為你的伺服器 IP
3. 用 Android Studio 編譯並安裝到手機

## API

| Method | Endpoint | 說明 |
|--------|----------|------|
| GET | `/check-status` | 回傳 CPU、記憶體、機台狀態 |
| GET | `/control/start` | 啟動機台 |

所有端點需帶 Header：`X-Api-Key: 你的金鑰`

## CI/CD 設定

在 GitHub Repository → Settings → Secrets 加入：

| Secret | 說明 |
|--------|------|
| `SERVER_IP` | 伺服器 Tailscale IP（100.x.x.x）|
| `SERVER_USER` | SSH 登入帳號 |
| `SSH_PRIVATE_KEY` | SSH 私鑰 |
| `TAILSCALE_AUTHKEY` | Tailscale Auth Key |
| `API_KEY` | API 驗證金鑰 |
