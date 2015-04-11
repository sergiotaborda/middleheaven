/**
 * 
 */
package org.middleheaven.quantity.math.vectorspace;

import org.middleheaven.quantity.math.structure.FieldElement;

/**
 * 
 */
public class TransposedMatrix<F extends FieldElement<F, ?>> extends AbstractMatrix<F> {

	private Matrix<F> original;

	public TransposedMatrix(Matrix<F> original, VectorSpace<F> vectorSpace) {
		super(vectorSpace);
		this.original = original;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public final Matrix<F> transpose() {
		return original;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector<F> getRow(int row) {
		return this.original.getColumn(row);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector<F> getDiagonal() {
		return this.original.getDiagonal();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public F get(int r, int c) {
		return this.original.get(c, r);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int rowsCount() {
		return this.original.columnsCount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int columnsCount() {
		return this.original.rowsCount();
	}


}
