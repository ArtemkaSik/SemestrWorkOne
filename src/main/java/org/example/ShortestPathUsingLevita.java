package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ShortestPathUsingLevita {

    public static void main(String[] args) {
        // Путь к файлу с данными графа
        String filename = "generated_data3.txt";
        System.out.println("Время " + "Итерации " + "Вершин");
        // Чтение графа из файла
        try {
            Scanner scanner = new Scanner(new File(filename));

            while (scanner.hasNextLine()) {
                if (!scanner.hasNextInt()) {
                    break; // Проверяем, есть ли следующее целое число в файле
                }
                vertices = scanner.nextInt(); // Считываем количество вершин
                int edges = scanner.nextInt(); // Считываем количество рёбер
                graph = new ArrayList<>(vertices);

                // Инициализация списка смежности
                for (int i = 0; i < vertices; i++) {
                    graph.add(new ArrayList<>());
                }

                // Считываем рёбра из файла
                for (int i = 0; i < edges; i++) {
                    if (!scanner.hasNextInt()) {
                        break; // Проверяем, есть ли следующее целое число в файле
                    }
                    int source = scanner.nextInt();
                    int destination = scanner.nextInt();
                    int weight = scanner.nextInt();
                    addEdge(source, destination, weight);
                }

                // Выполнение алгоритма Левита для текущего графа
                levitaAlgorithm(0,vertices,edges);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e.getMessage());
        }
    }

    static class Edge {
        int source; // начальная вершина
        int destination; //конечная вершина
        int weight; // вес ребра

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    static int vertices; //вершины
    static ArrayList<ArrayList<Edge>> graph; //графы

    public static void addEdge(int source, int destination, int weight) {
        graph.get(source).add(new Edge(source, destination, weight));
    }

    public static void levitaAlgorithm(int start, int vertices, int edges) {

        int[] distance = new int[vertices];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[start] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);

        boolean[] inQueue = new boolean[vertices];
        inQueue[start] = true;

        int[] count = new int[vertices];
        Arrays.fill(count, 0);

        int iterations = 0; //счётчик итераций

        long startTime = System.nanoTime(); //засекаем время

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int current = queue.poll();
                inQueue[current] = false;

                for (Edge edge : graph.get(current)) {
                    int newDistance = distance[current] + edge.weight;
                    if (newDistance < distance[edge.destination]) {
                        distance[edge.destination] = newDistance;
                        if (!inQueue[edge.destination]) {
                            queue.offer(edge.destination);
                            inQueue[edge.destination] = true;
                            count[edge.destination]++;
                            if (count[edge.destination] > vertices) {
                                System.out.println("Граф содежит отрицательный цикл");
                            }
                        }
                    }
                    iterations++;
                }

            }
        }

        long endTime = System.nanoTime(); //Фиксируем время
        System.out.println((endTime - startTime) + " " + iterations + " " + (vertices)); //Выписываем время, итерации, кол-во вершин
    }
}