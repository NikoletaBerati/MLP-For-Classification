import matplotlib.pyplot as plt
import csv

def plot_error(file_path):
    epochs = []
    errors = []

    with open(file_path, 'r') as csv_file:
        csv_reader = csv.reader(csv_file)
        for row in csv_reader:
            epoch, error = map(float, row)
            epochs.append(epoch)
            errors.append(error)

    plt.plot(epochs, errors, label='Error per Epoch', linestyle='-')
    plt.title('Error per Epoch')
    plt.xlabel('Epoch')
    plt.ylabel('Error')
    plt.legend()
    plt.show()

if __name__ == "__main__":
    file_path = "TotalError.csv"
    plot_error(file_path)
