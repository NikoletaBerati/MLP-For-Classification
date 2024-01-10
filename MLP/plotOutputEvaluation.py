import matplotlib.pyplot as plt
import csv

filename = "Output.csv"
f = open(filename, 'r')
csv_reader = csv.reader(f)

filename1 = "test_set.csv"
f1 = open(filename1, 'r')
csv_reader1 = csv.reader(f1)

colors = ['magenta','lime','r','b']

correct_x1 = []
correct_x2 = []
correct_category = []

incorrect_x1 = []
incorrect_x2 = []
incorrect_category = []


for row1, row2 in zip(csv_reader, csv_reader1):
    x = [float(val) for val in row1]
    category = float(row2[2])
    

    if x[2] == category:
        correct_x1.append(x[0])
        correct_x2.append(x[1])
        correct_category.append(colors[int(x[2])-1])
    else:
        incorrect_x1.append(x[0])
        incorrect_x2.append(x[1])
        incorrect_category.append(colors[int(x[2])-1])

plt.scatter(correct_x1, correct_x2, color=correct_category, marker='+', s=20)
plt.scatter(incorrect_x1, incorrect_x2, color=incorrect_category, marker='_', s=15)

f.close()
f1.close()
plt.show()
