package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import java.io.File;

public class TextFileGenerator {
    public static void main(String[] args) {
        String filename = "generated_data3.txt";
        Random random = new Random();

        try {
            FileWriter writer = new FileWriter(new File(filename));

            int numGraphs = random.nextInt(51) + 50; // Генерируем количество графов в файле
            System.out.println("Создано " + numGraphs + " графов.");

            for (int i = 0; i < numGraphs; i++) {
                //writer.write("Это граф" + "\n"); проверил, что графы создаются
                int vertices = random.nextInt(120) + 10; // Сгенерируем количество вершин от 10 до 130
                int edges = random.nextInt(vertices * (vertices - 1) / 2) + 1; // // Случайное количество рёбер

                // Записываем количество вершин и рёбер в файл
                writer.write(vertices + " " + edges + "\n");

                // Записываем каждое ребро (случайные вершины и вес)
                for (int j = 0; j < edges; j++) {
                    int source = random.nextInt(vertices);
                    int destination = random.nextInt(vertices);
                    while (destination == source) {
                        destination = random.nextInt(vertices); // Убедимся, что пункт назначения отличается от источника
                    }
                    int weight = random.nextInt(100); // Сгенерируйте случайный вес для ребра
                    writer.write(source + " " + destination + " " + weight + "\n");
                }
            }

            writer.close();
            System.out.println("Файл успешно создан.");
        } catch (IOException e) {
            System.out.println("Ошибка во время создания файла: " + e.getMessage());
        }
    }
}
