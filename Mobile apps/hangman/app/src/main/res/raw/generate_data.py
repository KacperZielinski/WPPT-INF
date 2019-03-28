import re

# https://pl.wiktionary.org/wiki/Indeks:Polski_-_Najpopularniejsze_slowa_1-2000
infile = open('n.txt', 'r')

for line in infile:
	lines = line.split(' ')
	for l in lines:
		str = "<item>"
		str1 = re.sub("[0-9_=.]", '', l)

		if len(str1) >= 8:
			str += str1
			str += "</item>"
			print(str)

infile.close()

