import json
import math
bpm = 120

fd = open("pulsusgame.json")
data = json.load(fd)
output = ""

for beat in data["beat"]:
    ms = math.floor((beat[1] * 60_000) / bpm)
    startms = ms - 1_000
    if (startms < 0):
        startms = 0
    lane = beat[0] + 1
    output += str(startms) + "," + str(ms) + "," + str(lane) + '\n'
print(output)