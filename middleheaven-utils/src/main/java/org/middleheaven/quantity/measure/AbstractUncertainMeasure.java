package org.middleheaven.quantity.measure;

import org.middleheaven.collections.interval.Interval;
import org.middleheaven.quantity.Quantity;
import org.middleheaven.quantity.convertion.UnitConversion;
import org.middleheaven.quantity.math.Real;
import org.middleheaven.quantity.unit.IncompatibleUnitsException;
import org.middleheaven.quantity.unit.Measurable;
import org.middleheaven.quantity.unit.Unit;

/**
 * The value of a measurement and the associated uncertainty
 *
 * @param <E> the associated <code>Measurable</code>
 * @param <F> the associated underlying <code>Field</code> , normally <code>Real</code>
 */
public abstract class AbstractUncertainMeasure<E extends Measurable> implements UncertainMeasure<E>  {

	protected Real absUncertainty;
	protected Real amount;
	protected Unit<E> unit;

	/**
	 * 
	 * Constructor.
	 * @param amount the amount of the unit measured
	 * @param uncertainty the error associated with the measure. The absolute value of this value will be used.
	 * @param unit the unit used in the measure
	 */
	protected AbstractUncertainMeasure(Real amount, Real uncertainty, Unit<E> unit){
		
		if (amount == null ){
			throw new IllegalArgumentException("Amount is required");
		}
		if (uncertainty == null ){
			throw new IllegalArgumentException("Uncertainty is required");
		}
		if (uncertainty.compareTo(amount.getAlgebricStructure().zero()) < 0 ){
			throw new IllegalArgumentException("Uncertainty is not positive (" + uncertainty + ")");
		}
		if (unit == null ){
			throw new IllegalArgumentException("Unit is required");
		}
		this.amount = amount;
		this.unit = unit;
		this.absUncertainty = uncertainty.abs();
	}
	
	/**
	 * 
	 * @return the amount of uncertainty.
	 */
	public final Real uncertainty() {
		return absUncertainty;
	}
	
	/**
	 * 
	 * @return true is this value is exact (no uncertainty)
	 */
	public final boolean isExact(){
		return absUncertainty.plus(absUncertainty).equals(absUncertainty); //.isZero();
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	public final Unit<E>  unit() {
		return unit;
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public final String toString(){
		return '(' + this.amount.toString() + ' ' + '\u00B1' + ' ' + this.absUncertainty.toString()  + ") " + this.unit.toString();
	}
	

	/**
	 * Two measures are equal if are represented in compatible units and the intervals they represent intersect each other.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Measure)){
			return false;
		}
		AbstractUncertainMeasure m;
		if (!(other instanceof AbstractUncertainMeasure)){
			m = UnitConversion.convert((Measure)other, ((Measure)other).unit());
		} else {
			m = (AbstractUncertainMeasure) other;
		}
		
		if (!this.unit.isCompatible(m.unit) || !m.amount().getAlgebricStructure().equals(this.amount().getAlgebricStructure())){
			return false;
		}
		
		@SuppressWarnings("unchecked")
		AbstractUncertainMeasure<E> r = cast(m);

		
		if (this.isExact()) {
			if (m.isExact()){
				
				return this.amount.equals(m.amount);
			} else {
				Interval<Real> otherInterval = Interval.between( r.amount.minus(r.absUncertainty), r.amount.plus(r.absUncertainty), this.amount().getAlgebricStructure().getComparator());
				
				return otherInterval.contains(this.amount);
			}
		} else {
			if (m.isExact()){
				Interval<Real> thisInterval = Interval.between( this.amount.minus(this.absUncertainty), this.amount.plus(this.absUncertainty), this.amount().getAlgebricStructure().getComparator());
				
				return thisInterval.contains(r.amount);
				
			} else {
			
				Interval<Real> thisInterval = Interval.between( this.amount.minus(this.absUncertainty), this.amount.plus(this.absUncertainty), this.amount().getAlgebricStructure().getComparator());
				Interval<Real> otherInterval = Interval.between( r.amount.minus(r.absUncertainty), r.amount.plus(r.absUncertainty), this.amount().getAlgebricStructure().getComparator());

				return thisInterval.intersects(otherInterval);
			}
		}
		
		
	}
	
	private AbstractUncertainMeasure<E> cast(AbstractUncertainMeasure<E> m) {
		return m;
	}

	/**
	 * Determines if the other measure has the same value, unit and uncertainty has this.
	 * @param other the Measure to compare
	 * @return <code>true</code> if the other measure has the same value, unit and uncertainty. <code>false</code> otherwise.
	 */
	public boolean isIdentical(AbstractUncertainMeasure<E> other){
		return this.amount.equals(other.amount) && this.absUncertainty.equals(other.absUncertainty) && this.unit.equals(other.unit);
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode(){
		return this.amount.hashCode();
	}
	
	/**
	 * 
	 * @param other
	 */
	protected void assertCompatible(Quantity<?> other) {
		if (!this.unit().isCompatible(other.unit())){
			throw new IncompatibleUnitsException(this.unit(),other.unit());
		}
	}

	protected Real timesError (AbstractUncertainMeasure<?> other){
		
		if (this.isExact()) {
			return other.uncertainty().times(this.amount().abs());
		} else if (other.isExact()) {
			return this.uncertainty().times(other.amount().abs());
		} else {
			// deltaZ = (deltaX/|X| + deltaY/|Y|)*|Z| = (deltaX* |Y| + deltaY*|X|)*|Z|
			return this.absUncertainty.times(other.amount.abs()).plus(other.absUncertainty.times(this.amount.abs())).times(this.amount.abs());
		}
		
	}
	
	protected Real overError (AbstractUncertainMeasure<?> other){
		// deltaZ = (deltaX/|X| + deltaY/|Y|)*|Z| = (deltaX + deltaY.|X|/|Y|)*|Z| 
		return this.absUncertainty.plus(other.absUncertainty.times(this.amount().abs().over(other.amount().abs()))).times(this.amount.abs());
	}

	public final Real amount() {
		return amount;
	}




}
