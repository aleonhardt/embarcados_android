package com.embedded.energy_comparisson;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
public class MainActivity extends Activity
{
	public int midpoint(int n1, int n2)
	{
		return (n1+n2)/2;
	}
	
	public int iterativeBinarySearch(int[] array, int key, int min, int max)
	{
		while(max >= min)
		{
			int mid = midpoint(min, max);
			
			if(array[mid] < key)
			{
				min = mid+1;
			}
			else if(array[mid] > key)
			{
				max = mid-1;
			}
			else
			{
				return mid;
			}
		}
		return -1;
	}
	
	public int recursiveBinarySearch(int[] array, int key, int min, int max)
	{
		if(max < min)
		{
			return -1;
		}
		else
		{
			int mid = midpoint(min, max);
			
			if(array[mid] > key)
			{
				return recursiveBinarySearch(array, key, min, mid-1);
			}
			else if(array[mid] < key)
			{
				return recursiveBinarySearch(array, key, mid+1, max);
			}
			else
			{
				return mid;
			}
		}
	}
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		int[] array = {0,1,2,3,4,5,6,7,8};
		TextView tv = new TextView(this);
		
		tv.setText(Integer.toString(recursiveBinarySearch(array, 2, 0, 8)));
		setContentView(tv);
	}
} 