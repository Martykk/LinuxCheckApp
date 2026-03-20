from fastapi import FastAPI

app = FastAPI()

# 這就是你的 APP 按鈕按下去後，要連線的「門牌」
@app.get("/check-status")
def read_status():
    # 這裡可以回傳你 APP 程式碼需要的 model 和 cpu_load
    return {
        "model": "MP510-30",
        "cpu_load": 25,
        "status": "Online"
    }

@app.get("/control/start")
def start_machine():
    # 這裡放控制機台運行的邏輯
    return {"message": "機台已啟動"}