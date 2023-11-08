package apach_linear_algebra;

import org.apache.commons.math3.linear.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 *
 * https://commons.apache.org/proper/commons-math/userguide/linear.html
 * math3 is newer and better than math2
 */

public class TestMatrixAndVector {


    private RealMatrix m;
    private RealMatrix n;


    @Before
    public void init() {
        double[][] matrixData = { {1d,2d,3d}, {2d,5d,3d}};
        m = MatrixUtils.createRealMatrix(matrixData);

        // One more with three rows, two columns, this time instantiating the
        // RealMatrix implementation class directly.
        double[][] matrixData2 = { {1d,2d}, {2d,5d}, {1d, 7d}};
        n = new Array2DRowRealMatrix(matrixData2);

    }

    @Test
    public void given2DimMatrix_whenGettingRowAndColDimension_thenIsTwo() {
        // Now multiply m by n
        RealMatrix p = m.multiply(n);
        System.out.println(p.getRowDimension());    // 2
        System.out.println(p.getColumnDimension()); // 2

        Assert.assertEquals(2,p.getRowDimension());
        Assert.assertEquals(2,p.getColumnDimension());

        // Invert p, using LU decomposition
        RealMatrix inverse = MatrixUtils.inverse(p);

        System.out.println("inverse = " + inverse);

        Assert.assertEquals(2,inverse.getColumnDimension());
        Assert.assertEquals(2,inverse.getRowDimension());
    }

    @Test public void whenInverting_thenDim2() {
        RealMatrix p = m.multiply(n);
        RealMatrix inverse = MatrixUtils.inverse(p);
        System.out.println("inverse = " + inverse);
        Assert.assertEquals(2,inverse.getColumnDimension());
        Assert.assertEquals(2,inverse.getRowDimension());
    }

    @Test public void given2DimMatrixAndVector_whenMultipling_thenVector() {
        double[][] matrixData = { {1d,1d,1d}, {1d,1d,1d}};
        RealMatrix m = MatrixUtils.createRealMatrix(matrixData);
        RealVector v = new ArrayRealVector(new double[]{1d, 2d,1d});

        RealVector vRes =m.operate(v);
        System.out.println("vRes = " + vRes);

        Assert.assertEquals(2,vRes.getDimension());
    }

}
