from fastapi import FastAPI
import psutil
import platform

app = FastAPI()

# 這就是你的 APP 按鈕按下去後，要連線的「門牌」
@app.get("/check-status")
def read_status():
    # 抓取真實 CPU 使用率 (間隔 0.1 秒計算平均值)
    cpu_usage = psutil.cpu_percent(interval=0.1)

    # 抓取記憶體使用百分比
    memory_info = psutil.virtual_memory()
    # 這裡可以回傳你 APP 程式碼需要的 model 和 cpu_load
    return {
        "model": "MP510-30",
        "cpu_load": int(cpu_usage),
        "memory_usage": memory_info.percent,
        "status": "Online",
        "uptime": psutil.boot_time()
    }

@app.get("/control/start")
def start_machine():
    # 這裡放控制機台運行的邏輯
    return {"message": "機台已啟動"}
