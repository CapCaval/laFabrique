package org.capcaval.lafabrique.converter.basicconverters.bytes;

import org.capcaval.lafabrique.converter.ConverterAbstract;

public class DoubleToByteArray extends ConverterAbstract<Double, byte[]> {
	@Override
	public byte[] convert(Double value) {
		// Double is encoded on 8 bytes or 64 bits
		byte[] byteArray = new byte[8];
		
	   	for( int i = 0; i< 8; i++){
    		// calculate the bit shift and then the mask
    		int bitShift = 64 -((i+1)*8);
    		System.out.println(bitShift);
    		long mask = (long)0xff << bitShift;
    		System.out.println(Long.toHexString(mask));
    		long lValue = Double.doubleToLongBits(value);
    		//long lValue = Double.doubleToLongBits(value);
    		
    		byteArray[i] = (byte)((lValue & mask) >> bitShift);
    	}
		
		
		return byteArray;
	}

}
