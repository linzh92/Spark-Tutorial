package org.apache.spark.mllib.clustering

import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext._

/**
 * A clustering model for K-means. Each point belongs to the cluster with the closest center.
 */
class KMeansModel(val clusterCenters: Array[Array[Double]]) extends Serializable {
  /** Total number of clusters. */
  def k: Int = clusterCenters.length

  /** Return the cluster index that a given point belongs to. */
  def predict(point: Array[Double]): Int = {
    KMeans.findClosest(clusterCenters, point)._1
  }

  /**
   * Return the K-means cost (sum of squared distances of points to their nearest center) for this
   * model on the given data.
   */
  def computeCost(data: RDD[Array[Double]]): Double = {
    data.map(p => KMeans.pointCost(clusterCenters, p)).sum()
  }
}
