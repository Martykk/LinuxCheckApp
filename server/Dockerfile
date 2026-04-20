# 使用輕量級 Python 作為基底
FROM python:3.9-slim

# 設定容器內的工作目錄
WORKDIR /app

# 複製清單並安裝套件
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# 複製你的程式碼 (注意你的檔名是 sever.py，少了一個 e)
COPY server.py .

# 5. 開放 8000 埠號
EXPOSE 8000

# 啟動指令：讓 Server 監聽所有 IP (0.0.0.0)
CMD ["uvicorn", "server:app", "--host", "0.0.0.0", "--port", "8000"]