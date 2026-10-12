package com.yakubin;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Sort {

    private static void downHeap(int[] a, int k, int n) {
        int temp = a[k];

        while (k*2+1 < n) {
            int y = k*2+1;
            if (y < n-1 && a[y] < a[y+1]) y++;
            if (temp >= a[y]) break;
            a[k] = a[y]; k = y;
        }
        a[k] = temp;
    }

    public static int[] heapSort(int[] a, int n) {
        int i, temp;

        for (i =  n/2-1; i >= 0; i--) downHeap(a, i, n);
        for (i = n-1; i > 0; i--) {
            temp = a[i]; a[i] = a[0]; a[0] = temp;
            downHeap(a, 0, i);
        }
        return a;
    }

    public static void draw(Pane root, int[] a) {

        root.getChildren().clear();

        double width = root.getWidth();
        double height = root.getHeight();

        int n = a.length;
        int levels = (int) (Math.log(n) / Math.log(2)) + 1;

        double verticalStep = height / (levels + 1);

        int maxNodesLastLevel = (int) Math.pow(2, levels - 1);
        double leafStep = width / (maxNodesLastLevel + 1);

        double radius = Math.min(20, leafStep / 2 - 2);
        radius = Math.max(radius, 6);

        double[] x = new double[n];
        double[] y = new double[n];

        for (int i = 0; i < n; i++) {

            int level = (int) (Math.log(i + 1) / Math.log(2));
            int firstIndex = (int) Math.pow(2, level) - 1;
            int indexInLevel = i - firstIndex;

            int factor = (int) Math.pow(2, levels - level - 1);

            x[i] = (indexInLevel * 2 + 1) * leafStep * factor / 2;
            y[i] = verticalStep * (level + 1);
        }

        for (int i = 0; i < n; i++) {

            int left = 2 * i + 1; int right = 2 * i + 2;

            if (left < n) {
                Line line = new Line(x[i], y[i], x[left], y[left]);
                root.getChildren().add(line);
            }

            if (right < n) {
                Line line = new Line(x[i], y[i], x[right], y[right]);
                root.getChildren().add(line);
            }
        }

        for (int i = 0; i < n; i++) {

            Circle c = new Circle(x[i], y[i], radius);
            c.setFill(Color.WHITE);
            c.setStroke(Color.BLACK);

            Text t = new Text(x[i] - radius / 3, y[i] + radius / 4, String.valueOf(a[i]));

            root.getChildren().addAll(c, t);
        }
    }

    public static List<int[]> getSteps(int[] a) {
        List<int[]> steps = new ArrayList<>();
        int[] arr = a.clone(); int n = arr.length;

        steps.add(arr.clone());

        for (int i = n / 2 - 1; i >= 0; i--) {
            downHeap(arr, i, n);
            steps.add(arr.clone());
        }

        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            steps.add(Arrays.copyOf(arr, i));

            downHeap(arr, 0, i);
            steps.add(Arrays.copyOf(arr, i));
        }
        steps.add(arr);
        return steps;
    }

    public static int countSteps(int[] a) {
        int steps = 0; int n = a.length; int[] arr = a.clone();

        for (int i = n / 2 - 1; i >= 0; i--) {
            steps += downHeapWithCounter(arr, i, n);
        }
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            steps++;
            steps += downHeapWithCounter(arr, 0, i);
        }
        return steps;
    }

    private static int downHeapWithCounter(int[] a, int k, int n) {
        int count = 0;
        int temp = a[k];
        while (k * 2 + 1 < n) {
            count++;
            int y = k * 2 + 1;
            if (y < n - 1 && a[y] < a[y + 1]) y++;
            if (temp >= a[y]) break;
            a[k] = a[y]; k = y;
        }
        a[k] = temp;
        return count;
    }

    public static long measureTime(int[] a) {
        int[] arr = a.clone();
        long start = System.nanoTime();
        heapSort(arr, arr.length);
        long end = System.nanoTime();
        return end - start;
    }
}

