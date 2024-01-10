import matplotlib.pyplot as plt
import pandas as pd


df = pd.read_csv("training_set.csv")
df.columns = ["x1","x2","categories"]

colors = ['magenta','lime','r','b']
plt.scatter(list(df['x1']), list(df['x2']), s =20, marker="+", c=list(df['categories'].apply(lambda x: colors[x-1])))

plt.show()

