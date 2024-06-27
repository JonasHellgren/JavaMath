package apache_common;

import apache_common.clusterer.KMeansClusterer;

public class RunnerClusterer {

    public static void main(String[] args) {
        int nClusters = 3;
        int nDim = 2;
        KMeansClusterer clusterer= KMeansClusterer.of(nDim, nClusters);
        clusterer.addPoint(new double[]{1.0, 2.0});
        clusterer.addPoint(new double[]{1.5, 1.8});
        clusterer.addPoint(new double[]{5.0, 8.0});
        clusterer.addPoint(new double[]{8.0, 8.0});
        clusterer.addPoint(new double[]{1.0, 0.6});
        clusterer.addPoint(new double[]{9.0, 11.0});
        clusterer.addPoint(new double[]{8.0, 2.0});
        clusterer.addPoint(new double[]{10.0, 2.0});
        clusterer.addPoint(new double[]{9.0, 3.0});

        System.out.println("clusterer = " + clusterer);

    }

}
