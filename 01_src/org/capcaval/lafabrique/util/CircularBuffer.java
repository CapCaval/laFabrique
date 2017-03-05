package org.capcaval.lafabrique.util;

import java.util.Arrays;

public class CircularBuffer<T> {

	protected T[] buffer = null;
	protected T[] outBuffer = null;
	
	protected int size = 0;
	protected int arrayPointer = 0;
	protected boolean isFull = false;

	
	public CircularBuffer(int size) {
		// keep the size
		this.size = size;
		// allocate the buffer
		this.buffer = this.newArray(size);
		// allocate the work buffer, it will be used for outside use
		this.outBuffer = this.newArray(size);
		
	}

	protected T[] newArray(int size){
		@SuppressWarnings("unchecked")
		final T[] t = (T[])new Object[size];

		return t;
	}
	
	public void add(T object){
		this.buffer[arrayPointer]= object;
		// increase the array pointer
		this.arrayPointer++;
		// perform the modulo
		this.arrayPointer = this.arrayPointer%this.size;
		// set it full if modulo has been performed
		if(this.arrayPointer == 0){
			this.isFull = true;
		}
	}
	
	public T[] getArray(){
		T[] outBuffer = null;
		
		if(this.isFull==true){
			// reuse the working buffer
			outBuffer = this.outBuffer;
			
			int length = this.size - this.arrayPointer;
			System.arraycopy(this.buffer, this.arrayPointer, this.outBuffer, 0, length);
			System.arraycopy(this.buffer, 0, this.outBuffer, length, this.arrayPointer);
			
		}else{ // not full
			// copy all the value inside the array
			outBuffer = Arrays.copyOf(this.buffer, this.arrayPointer);
		}
		
		return outBuffer;
	}
}
