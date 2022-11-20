package edu.ser222.m02_01;

import java.util.Random;

/**
 * This program benchmarks sorting algorithms.
 * It uses a stopwatch to compute the time each sorting algorithm
 * takes on certain types of datasets and prints the results to the console.
 *
 * @author Ethan Wilson, Acuna, Sedgewick
 * @version 11.20.2022
 */


public class CompletedBenchmarkTool implements BenchmarkTool {
    
    /***************************************************************************
     * START - SORTING UTILITIES                                               *
     **************************************************************************/

    // @param a
    public static void insertionSort(Comparable[] a) {
        int N = a.length;
        
        for (int i = 1; i < N; i++)
        {
            // Insert a[i] among a[i-1], a[i-2], a[i-3]... ..          
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--)
                exch(a, j, j-1);
        }
    }
    
    
    public static void shellsort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        
        while (h < N/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...
        
        while (h >= 1) {
            // h-sort the array.
            for (int i = h; i < N; i++) {
                // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)
                exch(a, j, j-h);
            }
            h = h/3;
        }
    }
    
    
    public static void bubbleSort(Comparable[] a){
        int N = a.length;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N - i - 1; j++){
                if(less(a[j + 1], a[j])){
                    exch(a, j + 1, j);
                }
            }
        }
    }
    
    
    public static void selectionSort(Comparable[] a){
        int N = a.length;
        
        for(int i = 0; i < N; i++){
            // Exchange a[i] with smallest entry in a[i+1...N].
            int min = i; // index of minimal entry.
            for(int j = i+1; j < N; j++){
                if(less(a[j], a[min])) min = j;
            }
            exch(a, i, min);
        }
    }
    
    
    public static void mergesort(Comparable[] a){
        Comparable[] aux = new Comparable[a.length];
        mergesort(a, aux, 0, a.length-1);
    }
    
    
    public static void mergesort(Comparable[] a, Comparable[] aux, int lo, int hi){
        if(hi <= lo) return;
        int mid = lo + (hi - lo) / 2; // to prevent int overflow
        
        mergesort(a, aux, lo, mid);
        mergesort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }
    
    
    public static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi){
        int i = lo, j = mid + 1;
        
        for(int k = lo; k <= hi; k++)
            aux[k] = a[k];
        
        for(int k = lo; k <= hi; k++){
            if(i > mid){
                a[k] = aux[j++];
            } else if(j > hi){
                a[k] = aux[i++];
            } else if(less(aux[j], aux[i])){
                a[k] = aux[j++];
            } else{
                a[k] = aux[i++];
            }
        }
    }
    

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }
    
    /***************************************************************************
     * END - SORTING UTILITIES                                                 *
     **************************************************************************/
    
    @Override
    public Integer[] generateTestDataBinary(int size){
        Integer[] result = new Integer[size];
        
        for(int i = 0; i < size/2; i++){
            result[i] = 0;
        }
        for(int i = size/2; i < size; i++){
            result[i] = 1;
        }
        
        return result;
    }
    
    @Override
    public Integer[] generateTestDataHalves(int size){
        Integer[] result = new Integer[size];
        Integer fillValue = 0;
        int start = 0;
        
        while(start < size){
            int length = (size - start)/2;
            if(length == 0){
                length = 1;
            }
            for(int i = 0; i < length; i++){
                result[start + i] = fillValue;
            }
            fillValue++;
            start += length;
        }
        
        return result;
    }
    
    @Override
    public Integer[] generateTestDataHalfRandom(int size){
        Integer[] result = new Integer[size];
        Random rand = new Random();
        
        for(int i = 0; i < size/2; i++){
            result[i] = 0;
        }
        for(int i = size/2; i < size; i++){
            result[i] = rand.nextInt(Integer.MAX_VALUE);
        }
        
        return result;
    }
    
    public Integer[] generateTestDataRandom(int size){
        Integer[] result = new Integer[size];
        Random rand = new Random();
        
        for(int i = 0; i < size; i++){
            result[i] = rand.nextInt(Integer.MAX_VALUE);
        }
        
        return result;
    }

    @Override
    public double computeDoublingFormula(double t1, double t2){
        return log2(t2/t1);
    }
    
    // Computes log base 2 of n
    // Helper function for computeDoublingFormula
    public double log2(double n){
        double result = (Math.log(n) / Math.log(2));
        return result;
    }
    
    @Override
    public double benchmarkInsertionSort(Integer[] small, Integer[] large){
        double smallTime;
        double largeTime;
        
        Stopwatch stopwatch = new Stopwatch();
        insertionSort(small);
        smallTime = stopwatch.elapsedTime();
        stopwatch = new Stopwatch();
        insertionSort(large);
        largeTime = stopwatch.elapsedTime();
        
        return computeDoublingFormula(smallTime, largeTime);
    }
    
    @Override
    public double benchmarkShellsort(Integer[] small, Integer[] large){
        double smallTime;
        double largeTime;
        
        Stopwatch stopwatch = new Stopwatch();
        shellsort(small);
        smallTime = stopwatch.elapsedTime();
        stopwatch = new Stopwatch();
        shellsort(large);
        largeTime = stopwatch.elapsedTime();
        
        return computeDoublingFormula(smallTime, largeTime);
    }
    
    public double benchmarkBubbleSort(Integer[] small, Integer[] large){
        double smallTime;
        double largeTime;
        
        Stopwatch stopwatch = new Stopwatch();
        bubbleSort(small);
        smallTime = stopwatch.elapsedTime();
        stopwatch = new Stopwatch();
        bubbleSort(large);
        largeTime = stopwatch.elapsedTime();
        
        return computeDoublingFormula(smallTime, largeTime);
    }
    
        public double benchmarkSelectionSort(Integer[] small, Integer[] large){
        double smallTime;
        double largeTime;
        
        Stopwatch stopwatch = new Stopwatch();
        selectionSort(small);
        smallTime = stopwatch.elapsedTime();
        stopwatch = new Stopwatch();
        selectionSort(large);
        largeTime = stopwatch.elapsedTime();
        
        return computeDoublingFormula(smallTime, largeTime);
    }
        
        public double benchmarkMergeSort(Integer[] small, Integer[] large){
        double smallTime;
        double largeTime;
        
        Stopwatch stopwatch = new Stopwatch();
        mergesort(small);
        smallTime = stopwatch.elapsedTime();
        stopwatch = new Stopwatch();
        mergesort(large);
        largeTime = stopwatch.elapsedTime();
        
        return computeDoublingFormula(smallTime, largeTime);
    }
    
    public void printBenchmarks(String dataset, double a, double b, double c, double d, double e){
        String result = dataset;
        int spaces = 20 - (dataset.length() + String.format(("%.3f"), a).length());
        for(int i = 0; i < spaces; i++){
            result = result + " ";
        }
        
        result = result + String.format(("%.3f"), a);
        result = result + "        ";
        if(b < 0) result = result.substring(0, result.length() - 1); // Remove last char
        result = result + String.format(("%.3f"), b);
        result = result + "     ";
        if(c < 0) result = result.substring(0, result.length() - 1); // Remove last char
        result = result + String.format(("%.3f"), c);
        result = result + "        ";
        if(d < 0) result = result.substring(0, result.length() - 1); // Remove last char
        result = result + String.format(("%.3f"), d);
        result = result + "        ";
        if(e < 0) result = result.substring(0, result.length() - 1); // Remove last char
        result = result + String.format(("%.3f"), e);
        
        System.out.println(result);
    }
        
    @Override
    public void runBenchmarks(int size){
        Integer[] aSmall = generateTestDataBinary(size);
        Integer[] aLarge = generateTestDataBinary(size*2);
        Integer[] bSmall = generateTestDataHalves(size);
        Integer[] bLarge = generateTestDataHalves(size*2);
        Integer[] cSmall = generateTestDataHalfRandom(size);
        Integer[] cLarge = generateTestDataHalfRandom(size*2);
        Integer[] dSmall = generateTestDataRandom(size);
        Integer[] dLarge = generateTestDataRandom(size*2);
        
        double insertionSortA = benchmarkInsertionSort(aSmall, aLarge);
        double insertionSortB = benchmarkInsertionSort(bSmall, bLarge);
        double insertionSortC = benchmarkInsertionSort(cSmall, cLarge);
        double insertionSortD = benchmarkInsertionSort(dSmall, dLarge);
        
        double shellSortA = benchmarkShellsort(aSmall, aLarge);
        double shellSortB = benchmarkShellsort(bSmall, bLarge);
        double shellSortC = benchmarkShellsort(cSmall, cLarge);
        double shellSortD = benchmarkShellsort(dSmall, dLarge);
        
        double bubbleSortA = benchmarkShellsort(aSmall, aLarge);
        double bubbleSortB = benchmarkShellsort(bSmall, bLarge);
        double bubbleSortC = benchmarkShellsort(cSmall, cLarge);
        double bubbleSortD = benchmarkShellsort(dSmall, dLarge);
        
        double selectionSortA = benchmarkShellsort(aSmall, aLarge);
        double selectionSortB = benchmarkShellsort(bSmall, bLarge);
        double selectionSortC = benchmarkShellsort(cSmall, cLarge);
        double selectionSortD = benchmarkShellsort(dSmall, dLarge);
        
        double mergeSortA = benchmarkShellsort(aSmall, aLarge);
        double mergeSortB = benchmarkShellsort(bSmall, bLarge);
        double mergeSortC = benchmarkShellsort(cSmall, cLarge);
        double mergeSortD = benchmarkShellsort(dSmall, dLarge);
        
        System.out.println("           Insertion    Shellsort    Bubble    Selection    Mergesort");
        printBenchmarks("Bin", insertionSortA, shellSortA, bubbleSortA, selectionSortA, mergeSortA);
        printBenchmarks("Half", insertionSortB, shellSortB, bubbleSortB, selectionSortB, mergeSortB);
        printBenchmarks("RanIntHalf", insertionSortC, shellSortC, bubbleSortC, selectionSortC, mergeSortC);
        printBenchmarks("RanInt", insertionSortD, shellSortD, bubbleSortD, selectionSortD, mergeSortD);        
        
    }
    
    public static void main(String args[]) {
        BenchmarkTool me = new CompletedBenchmarkTool();
        int size = (int)Math.pow(2, 16);

        me.runBenchmarks(size);
    }
}