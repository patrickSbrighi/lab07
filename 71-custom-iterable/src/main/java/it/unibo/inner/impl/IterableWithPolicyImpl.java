package it.unibo.inner.impl;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {
    private final List<T> elements;
    private Predicate<T> predicate;    
    
    public IterableWithPolicyImpl(T[] elementsArray, Predicate<T> predicate){
        this.elements = List.of(elementsArray);
        this.predicate = predicate;
    }

    /**
     * @param elementsArray
     */
    public IterableWithPolicyImpl(T[] elementsArray){
        this(elementsArray, new Predicate<>() {

            @Override
            public boolean test(Object elem) {
                return true;
            }
            
        });
    }
   
    @Override
    public Iterator<T> iterator() {
        return new IterateElements();
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.predicate = filter;
    }

    private class IterateElements implements Iterator<T>{
        private int index = 0;
        @Override
        public boolean hasNext() {
            while(this.index < elements.size()){
                T element = elements.get(this.index);
                if(predicate.test(element)){
                    return true;
                }
                this.index++;
            }
            return false;
        }

        @Override
        public T next() {
            if(hasNext()){
                return elements.get(this.index++);
            }
            throw new NoSuchElementException();
        }

    }

    @Override
    public String toString() {
        StringBuilder stringBuild = new StringBuilder();
        stringBuild.append('[');
        for(T elem: this.elements){
            stringBuild.append(elem.toString() + ", ");
        }
        stringBuild.delete(stringBuild.length()-2, stringBuild.length());
        stringBuild.append(']');
        return stringBuild.toString();
    }

}
