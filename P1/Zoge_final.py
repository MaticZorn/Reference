import risar
from random import randint
from math import sqrt


def pobarvan(krog):
    c = krog.pen().color().lighter()
    c.setAlpha(192)
    krog.setBrush(c)


def premik():
    random_cifra = randint(-5, 5)
    x1 = random_cifra
    y1 = sqrt(5 ** 2 - random_cifra ** 2)
    return x1, y1


def izdelava_lastnosti(stevilo):
    seznam_zog = []
    for i in range(stevilo):
        barva = risar.nakljucna_barva()
        x, y = risar.nakljucne_koordinate()
        polmer = 10
        x1, y1 = premik()
        sirina = 1
        narejena = risar.krog(x, y, polmer, barva=barva, sirina=sirina)
        pobarvan(narejena)
        zoga = [narejena, x, y, polmer, x1, y1]
        seznam_zog.append(zoga)
    return seznam_zog


def miska():
    x, y = risar.miska
    polmer = 30
    miska = risar.krog(x, y, polmer, barva= risar.nakljucna_barva(), sirina=3)
    return miska


def dotik(x, y, xm, ym):
    if abs(x - xm) <= 35 and abs(y - ym) <= 35:
        risar.stoj()


seznam_zog = izdelava_lastnosti(30)
miska = miska()

for k in range(1, 600):
    for j in seznam_zog:
        # MIŠKA
        if not risar.klik:
            xm, ym = risar.miska
            miska.setPos(xm, ym)
        # ŽOGE
        zoga, x, y, polmer, x1, y1 = j
        if x < polmer:
            x1 = abs(x1)
        if x > risar.maxX - polmer:
            x1 = -abs(x1)
        if y < polmer:
            y1 = abs(y1)
        if y > risar.maxY - polmer:
            y1 = -abs(y1)
        zoga.setPos(x + x1, y + y1)
        j[1], j[-2] = x + x1, x1
        j[2], j[-1] = y + y1, y1
        if risar.klik:
            dotik(x, y, xm, ym)
    risar.cakaj(0.02)

risar.stoj()