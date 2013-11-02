package com.embedded.energy_comparisson;

import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
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
	
	public int[] createArray(int numberElements)
	{
		int[] array=new int[numberElements];
		
		for(int i=0; i<numberElements; i++)
			array[i]=i;
		
		return array;
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Button buttonGo = (Button)findViewById(R.id.buttonGo);
		
		
		final TextView result = (TextView)findViewById(R.id.result);
		final RadioGroup chooseAlg = (RadioGroup)findViewById(R.id.chooseAlg);
		final EditText numberElements = (EditText)findViewById(R.id.numberElements);
		
				
		buttonGo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if(chooseAlg.getCheckedRadioButtonId()>0 && numberElements.getText().length()>0) //se escolheu um algoritmo e colocou o num de elementos do vetor
            	{
	            	int numElements = Integer.parseInt(numberElements.getText().toString());
	        		int[] array = createArray(numElements);
	        		String resultString="";
	        		String fileName="";
	        		
	        		if(chooseAlg.getCheckedRadioButtonId()==R.id.algIterative)
	        			{
	        			fileName="iterative_"+numElements+"_elements";
	        			Debug.startMethodTracing(fileName);
	        			resultString = Integer.toString(iterativeBinarySearch(array, 2, 0, numElements));
	        			Debug.stopMethodTracing();
	        			}
	        		else if (chooseAlg.getCheckedRadioButtonId()==R.id.algRecursive)
	        			{
	        			fileName="recursive_"+numElements+"_elements";
	        			Debug.startMethodTracing(fileName);
	        			resultString = Integer.toString(recursiveBinarySearch(array, 2, 0, numElements));
	        			Debug.stopMethodTracing();
	        			}
	        		result.setText(resultString);
            	}
            	else
            		Toast.makeText(getApplicationContext(), "Choose an algorithm and the number of elements",Toast.LENGTH_SHORT).show();
            }
        });
		
				
		
		
	}
} 