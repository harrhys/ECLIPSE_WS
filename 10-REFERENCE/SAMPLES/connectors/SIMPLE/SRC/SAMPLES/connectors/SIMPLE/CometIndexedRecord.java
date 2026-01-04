/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.connectors.simple;

import java.util.*;

/**
 * IndexedRecord represents an ordered collection of record elements based on the java.util.List
 *interface. This interface allows a client to access elements by their integer index
 *(position in the list) and search for elements in the List.
 */
 
public class CometIndexedRecord implements javax.resource.cci.IndexedRecord {

    private String recordName; 
    private String description;
    private Vector indexedRecord;


    public CometIndexedRecord() {
        indexedRecord = new Vector();
    }

    public CometIndexedRecord(String name) {
        indexedRecord = new Vector();
        recordName = name;
    }

    public String getRecordName() {
        return recordName;
    }
				
    public void setRecordName(String name) {
        recordName = name;
    }

    public String getRecordShortDescription() {
        return description;
    }

    public void setRecordShortDescription(String description) {
        description = description;
    }

    public boolean equals(Object other) {
       return this.equals(other);
    }

    public int hashCode() {
        String result = "" + recordName;
        return result.hashCode();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

  //java.util.List methods

    public void add(int index, Object element) {
        indexedRecord.add(index,element);    
    }

    public boolean add(Object o) {
        return indexedRecord.add(o);
    }

    public boolean addAll(Collection c) {
        return indexedRecord.addAll(c);
    }

    public boolean addAll(int index, Collection c) {
        return indexedRecord.addAll(index, c);
    }

    public void addElement(Object o) {
        indexedRecord.addElement(o);
    }

    public int capacity() {
        return indexedRecord.capacity();
    }
    
    public void clear() {
        indexedRecord.clear();
    }

    public boolean contains(Object elem) {
        return indexedRecord.contains(elem);
    }

    public boolean containsAll(Collection c) {
        return indexedRecord.containsAll(c);
    }

    public Object get(int index) {
        return (Object)indexedRecord.get(index);
    }

 
    public int indexOf(Object elem) {
        return indexedRecord.indexOf(elem);
    }

    public int indexOf(Object elem,int index) {
        return indexedRecord.indexOf(elem,index);
    }

    public boolean isEmpty() {
        return indexedRecord.isEmpty();
    }

    public Iterator iterator() {
        return indexedRecord.iterator();
    }

    public ListIterator listIterator() {
        return indexedRecord.listIterator();
    }

    public ListIterator listIterator(int index) {
        return indexedRecord.listIterator(index);
    }

    public Object lastElement() {
        return indexedRecord.lastElement();
    }

    public int lastIndexOf(Object elem) {
        return indexedRecord.lastIndexOf(elem);
    }

    public int lastIndexOf(Object elem,int index) {
        return indexedRecord.lastIndexOf(elem,index);
    }

    public Object remove(int index) {
        return indexedRecord.remove(index);
    }

    public boolean remove(Object o) {
        return indexedRecord.remove(o);
    }

    public boolean removeAll(Collection c) {
        return indexedRecord.remove(c);
    }

    public boolean retainAll(Collection c){
        return indexedRecord.retainAll(c);
    }

    public Object set(int index, Object element){
        return indexedRecord.set(index,element);
    }

    public int size() {
        return indexedRecord.size();
    }

    public List subList(int fromIndex,int toIndex) {
        return indexedRecord.subList(fromIndex,toIndex);
    }

    public Object[] toArray() {
        return indexedRecord.toArray();
    }

    public Object[] toArray(Object[] a) {
        return indexedRecord.toArray(a);
    }

    public String toString() {
        return indexedRecord.toString();
    }

    public void trimToSize() {
        indexedRecord.trimToSize();
    }

}











