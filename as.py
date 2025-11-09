from PIL import ImageGrab

# Captura a tela inteira
img = ImageGrab.grab()
img.save("screenshot.png")
print("Screenshot salva!")
