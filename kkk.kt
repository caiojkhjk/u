# client.py
import socket
import struct
import io
from PIL import Image, ImageTk
import tkinter as tk

SERVER_IP = "192.168.1.130"  # IP do PC que está transmitindo
SERVER_PORT = 5000

root = tk.Tk()
root.title("Tela Remota")
label = tk.Label(root)
label.pack()

# conecta ao servidor
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((SERVER_IP, SERVER_PORT))

def atualizar():
    try:
        # lê tamanho da imagem
        size_data = s.recv(4)
        if not size_data:
            root.after(50, atualizar)
            return
        size = struct.unpack('>I', size_data)[0]

        # lê a imagem
        img_data = b''
        while len(img_data) < size:
            img_data += s.recv(size - len(img_data))

        # converte e exibe
        buf = io.BytesIO(img_data)
        img = Image.open(buf)
        tkimg = ImageTk.PhotoImage(img)
        label.img = tkimg
        label.config(image=tkimg)

    except Exception as e:
        print("Erro:", e)

    root.after(50, atualizar)

root.after(0, atualizar)
root.mainloop()
