/*
Copyright (C) 2012 by CapCaval.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package org.capcaval.lafabrique.pair;

public class PairImpl<T1, T2> implements Pair<T1, T2>{
	private final T1 firstItem;
	private final T2 secondItem;

	public PairImpl(T1 firstItem, T2 secondItem) {
		this.firstItem = firstItem;
		this.secondItem = secondItem;
	}

	public static <T1, T2> PairImpl<T1, T2> createPair(T1 firstItem, T2 secondItem) {
		return new PairImpl<T1, T2>(firstItem, secondItem);
	}

	@Override
	public T1 firstItem() {
		return firstItem;
	}
	@Override
	public T2 secondItem() {
		return secondItem;
	}
}