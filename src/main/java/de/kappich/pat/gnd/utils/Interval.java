/*
 * Copyright 2009 by Kappich Systemberatung Aachen
 * 
 * This file is part of de.kappich.pat.gnd.
 * 
 * de.kappich.pat.gnd is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * de.kappich.pat.gnd is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with de.kappich.pat.gnd; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package de.kappich.pat.gnd.utils;

/**
 * Eine generische Klasse zur Verwaltung von Intervallen von Zahlen.
 * <p>
 * Die Klasse <code>Interval&lt;E&gt;</code> dient zur Verwaltung von Intervalle der Klasse <code>E</code>,
 * die die Klasse <code>Number</code> erweitert. Jedes Intervall hat eine Untergrenze und eine Obergrenze, 
 * wobei es keine festgelegte Interpretation gibt, ob diese Werte zum Intervall dazu geh�ren oder nicht. 
 * Daneben gibt es noch einen Z�hler, mit dessen Hilfe ansonsten gleiche Intervalle in einer Menge 
 * koexistieren k�nnen.
 * 
 * @author Kappich Systemberatung
 * @version $Revision: 8080 $
 *
 */
public class Interval<E extends Number> implements Comparable<Interval<E>> {
	private E _lowerBound;
	private E _upperBound;
	private int _counter;	// Erlaubt es ansonsten gleiche Intervalle als unterschiedliche Schl�ssel
							// in Maps etc zu verwenden, wenn man den Counter hochz�hlt!
	/**
	 * Konstruiert ein Intervall aus den �bergebenen Grenzen.
	 * 
	 * @param lowerBound die untere Grenze
	 * @param upperBound die obere Grenze
	 */
	public Interval(E lowerBound, E upperBound) {
		super();
		_lowerBound = lowerBound;
		_upperBound = upperBound;
		_counter = 0;
	}
	/**
	 * Der Getter der unteren Grenze.
	 * 
	 * @return die untere Grenze
	 */
	public E getLowerBound() {
		return _lowerBound;
	}
	/**
	 * Der Setter der unteren Grenze.
	 * 
	 * @param die neue untere Grenze
	 */
	public void setLowerBound(E lowerBound) {
		_lowerBound = lowerBound;
	}
	/**
	 * Der Getter der oberen Grenze.
	 * 
	 * @return die obere Grenze
	 */
	public E getUpperBound() {
		return _upperBound;
	}
	/**
	 * Der Setter der unteren Grenze.
	 * 
	 * @param die neue untere Grenze
	 */
	public void setUpperBound(E upperBound) {
		_upperBound = upperBound;
	}
	
	/**
	 * Der Getter des Z�hlers.
	 * 
	 * @return der Z�hlerwert
     */
    public int getCounter() {
    	return _counter;
    }
    
	/**
	 * Der Setter des Z�hlers.
	 * 
	 * @param der neue Z�hlerwert
     */
    public void setCounter(int counter) {
    	_counter = counter;
    }

    /**
     * Gibt <code>true</code> zur�ck, wenn die beiden Intervalle im Sinne abgeschlossener
     * Intervalle eine nicht-leere Schnittmenge haben.
     * 
     * @param o ein Intervall
     * @return <code>true</code> genau dann, wenn die beiden Intervalle im Sinne abgeschlossener
     * Intervalle eine nicht-leere Schnittmenge haben
     */
	public boolean intersects (Interval<E> o) {
		if (_lowerBound.doubleValue() < o._lowerBound.doubleValue()) {
			return (_upperBound.doubleValue() >= o._lowerBound.doubleValue());
		} else {
			return (_lowerBound.doubleValue() <= o._upperBound.doubleValue());
		}
	}
	
	/**
	 * Gibt die Schnittmenge der beiden Intervalle zur�ck oder <code>null</code>, wenn der Schnitt leer ist.
	 * 
	 * @param o ein Intervall
	 * @return die Schnittmenge oder <code>null</code>
	 */
	public Interval<E> intersection (Interval<E> o) {
		if (_lowerBound.doubleValue() < o._lowerBound.doubleValue()) {
			if (_upperBound.doubleValue() >= o._lowerBound.doubleValue()) {
				if ( _upperBound.doubleValue() > o._upperBound.doubleValue()) {
					return new Interval<E> (o._lowerBound, o._upperBound);
				} else {
					return new Interval<E> (o._lowerBound, _upperBound);
				}
			}
			return null;
		} else {
			if (_lowerBound.doubleValue() <= o._upperBound.doubleValue()) {
				if ( _upperBound.doubleValue() <= o._upperBound.doubleValue()) {
					return new Interval<E> (_lowerBound, _upperBound); 
				} else {
					return new Interval<E> (_lowerBound, o._upperBound); 
				}
			} else {
				return null;
			}
		}
	}
	
	/**
	 * Die L�nge eines Intervalls ist die Differenz von oberer und unterer Grenze. Achtung: da
	 * keine �berpr�fung stattfindet, ob diese Grenzen sinnvolle Werte enthalten oder ihre
	 * Relation stimmt, kann das Ergebnis entsprechend ausfallen.
	 * 
	 * @return die L�nge
	 */
	public double length() {
		return (_upperBound.doubleValue() - _lowerBound.doubleValue());
	}
	
	/**
	 * Gibt ein neues Intervall zur�ck, dass dieselben unteren und oberen Schranken
	 * besitzt, und auch denselben Z�hlerwert.
	 * 
	 * @return die Kopie
	 */
	public Interval<E> getCopy() {
		Interval<E> newInterval = new Interval<E> (getLowerBound(), getUpperBound());
		newInterval.setCounter( getCounter());
		return newInterval;
	}
	
	public int compareTo(Interval<E> o) {
		double d = _lowerBound.doubleValue() - o._lowerBound.doubleValue();
		if ( d == 0) {
			d = _upperBound.doubleValue() - o._upperBound.doubleValue();
			if ( d == 0) {
				d = (double) (_counter - o._counter);
			}
		}
		if (d < 0.) {
			return -1;
		} else if (d == 0) {
			return 0;
		} else {
			return 1;
		}
	}
	
	@SuppressWarnings("unchecked")
    @Override
    public boolean equals (Object o) {
		if ( o == null) {
			return false;
		}
		if ( this == o) {
			return true;
		}
    	if( getClass() != o.getClass()) {
    		return false;
        }
		Interval<E> oInterval = (Interval<E>) o;
		if ( !oInterval.getLowerBound().equals(getLowerBound()) ||
			!oInterval.getUpperBound().equals(getUpperBound()) ||
			(_counter != oInterval._counter)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Der Hashcode eines Intervalls h�ngt nur von der oberen und unteren Grenze ab, nicht aber vom Z�hler.
	 * 
	 * @return die Summe der Hashcodes der unteren und oberen Grenze
	 */
	@Override
    public int hashCode() {
		return _lowerBound.hashCode() + _upperBound.hashCode();
	}
	
	@Override
    public String toString() {
		String s = new String();
		s += "[Interval: " + _lowerBound + ", " + _upperBound + ", " + _counter + "]";
		return s;
	}
} 
