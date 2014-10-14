package idv.eason.quicksort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class QuickSort {
	
	public QuickSort(){
		super();
	}
	
	int partition(int arr[], int left, int right){

	      int i = left, j = right;
	      int tmp;
	      int pivot = arr[(left + right) / 2];
	     
	      while (i <= j) {
	            while (arr[i] < pivot)
	                  i++;

	            while (arr[j] > pivot)
	                  j--;

	            if (i <= j) {
	                  tmp = arr[i];
	                  arr[i] = arr[j];
	                  arr[j] = tmp;
	                  i++;
	                  j--;
	            }
	      }
	      return i;
	}
	 

	void quickSort(int arr[], int left, int right) {
	      int index = partition(arr, left, right);
	      if(right-left < 7){
	    	  insertionSort(arr,left,right+1);
	      }else{
		      if (left < index - 1)
		            quickSort(arr, left, index - 1);
	
		      if (index < right)
		            quickSort(arr, index, right);
	      }
	}
	
	void insertionSort(int[] a,int start,int end) {

	      int i, j, newValue;

	      for (i = start; i < end; i++) {

	            newValue = a[i];

	            j = i;

	            while (j > 0 && a[j - 1] > newValue) {

	                  a[j] = a[j - 1];

	                  j--;

	            }

	            a[j] = newValue;

	      }

	}
	
	//檢查Array是否從小排到大
	boolean checkArray(int[] input){
		boolean bl = true;
		for (int i = 1 ; i < input.length ; i++){
			if(input[i-1] > input[i]){
				bl = false;
				break ;
			}
		}
		return bl ;
	}
	
    	
	public static void main(String[] arg){
		System.out.print("請輸入資料數：");
		BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
		String input;
		try {
			input = in.readLine();
			int size = Integer.parseInt(input);
			QuickSort qs = new QuickSort();
			int[] sort = new int[size];
			int[] sort2 = new int[size];
	        for (int i = 0; i < size; i++) {
	        	sort[i] = (int)(Math.random()*size);
	        	sort2[i] = sort[i];
	        }
			long startTime = System.currentTimeMillis();
//			System.out.println(startTime);
			qs.quickSort(sort, 0, sort.length-1);
			long endTime = System.currentTimeMillis();
			//System.out.println(endTime);
			System.out.println(new BigDecimal(endTime-startTime).divide(new BigDecimal("1000"), 3, BigDecimal.ROUND_HALF_UP)+"秒");
			//驗證排序是否正確
			if(qs.checkArray(sort))
				System.out.println("Array從小排到大正確無誤");
			else
				System.out.println("Array並非從小排到大");
			//insertion sort
			System.out.println("insertion Sort：");
			startTime = System.currentTimeMillis();
	        qs.insertionSort(sort2,0,sort2.length);
			endTime = System.currentTimeMillis();
			System.out.println(new BigDecimal(endTime-startTime).divide(new BigDecimal("1000"), 3, BigDecimal.ROUND_HALF_UP)+"秒");
			System.out.println("isSorted:"+qs.checkArray(sort2));
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
