package com.farbig.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class MyArrayList implements List {

	private Object[] elements;

	private int capacity;

	private int size = 0;

	public MyArrayList() {
		capacity = 10;
		elements = new Object[capacity];
	}

	public void addObject(Object o) {
		if (size < capacity) {
			elements[size++] = o;
		} else {
			capacity = 2 * capacity;
			Object[] temp = new Object[capacity];
			for (int i = 0; i < elements.length; i++) {
				temp[i] = elements[i];
			}
			elements = temp;
			elements[size++] = o;
		}
	}

	public Iterator iterator() {
		return new MyIterator();
	}

	class MyIterator implements Iterator {

		private int currentPosition;

		public MyIterator() {
			currentPosition = -1;
		}

		public boolean hasNext() {
			return currentPosition!=size;
		}

		public Object next() {
			return elements[++currentPosition];
		}

		public void remove() {
			elements[currentPosition--]=null;
			size--;
		}

	}

	public int size() {
		// TODO Auto-generated method stub
		Set s;
		Collections c;
		Object o = c.EMPTY_LIST;
		return size;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size==0;
	}

	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean add(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addAll(int index, Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public Object get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object set(int index, Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public void add(int index, Object element) {
		// TODO Auto-generated method stub
		
	}

	public Object remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public ListIterator listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public ListIterator listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public List subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
