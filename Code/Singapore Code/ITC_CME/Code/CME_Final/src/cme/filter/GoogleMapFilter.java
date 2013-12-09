package cme.filter;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Vector;
import cme.util.Constants;

/**
 * Map filter for google map
 * @author Wong Zhen Cong
 * @author Yong Kai Sheng
 */
public class GoogleMapFilter {

    /**
     * Return the filtered image so it will show only the road
     * The map is filtered based on the set of colors specified.
     * The rest of the map is set to a defualt black.
     * @param mapImage map image
     * @return filtered image of the map
     */
    public static BufferedImage filter(BufferedImage mapImage) {
        mapImage = googleRoad(mapImage);
        mapImage = googleRoad2(mapImage, 3);
        return mapImage;
    }

    /**
     * Return a 2D array of road indication, value is true if it is road
     * @param filteredMap filtered image of the map
     * @return 2D array of road indication
     */
    public static boolean[][] getRoadArray(BufferedImage filteredMap) {
        boolean[][] roadArray = new boolean[filteredMap.getWidth()][filteredMap.getHeight()];
        for (int i = 0; i < filteredMap.getWidth(); i++) {
            for (int j = 0; j < filteredMap.getHeight(); j++) {
                if (filteredMap.getRGB(i, j) == Constants.FILTERED_ROAD_COLOR) {
                    roadArray[i][j] = true;
                } else {
                    roadArray[i][j] = false;
                }
            }
        }
        return roadArray;
    }

    //Detect road and set to filtered colour
    private static BufferedImage googleRoad(BufferedImage mapImage) {
        BufferedImage mapImage1 = new BufferedImage(mapImage.getWidth(), mapImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < mapImage.getWidth(); i++) {
            for (int j = 0; j < mapImage.getHeight(); j++) {
                for (int k = 0; k < Constants.ROAD_COLOR.length; k++) {
                    if (Constants.ROAD_COLOR[k] == mapImage.getRGB(i, j)) {
                        mapImage1.setRGB(i, j, Constants.FILTERED_ROAD_COLOR);
                    }
                }
            }
        }
        return mapImage1;
    }

    //Touch up the filtered map by covering up holes
    private static BufferedImage googleRoad2(BufferedImage mapImage, int range) {
        BufferedImage mapImage1 = new BufferedImage(mapImage.getWidth(), mapImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < mapImage.getWidth(); i++) {
            for (int j = 0; j < mapImage.getHeight(); j++) {
                if (mapImage.getRGB(i, j) == Constants.FILTERED_ROAD_COLOR) {
                    //Set the adjacent pixels to filtered colour
                    for (int ii = i - range; ii < i + range; ii++) {
                        for (int jj = j - range; jj < j + range; jj++) {
                            if (ii >= 0 && jj >= 0 && ii < mapImage.getWidth() && jj < mapImage.getHeight()) {
                                mapImage1.setRGB(ii, jj, Constants.FILTERED_ROAD_COLOR);
                            }
                        }
                    }
                }
            }
        }
        return mapImage1;
    }

    /**
     * Return the coordinates of road on the filtered image
     * @param filterImage filtered image of the map
     * @return coordinates of road
     */
    public static Vector getRoadCoordinates(BufferedImage filterImage) {
        Vector road = new Vector();
        for (int i = 0; i < filterImage.getWidth(); i++) {
            for (int j = 0; j < filterImage.getHeight(); j++) {
                if (filterImage.getRGB(i, j) == Constants.FILTERED_ROAD_COLOR) {
                    road.add(new Point(i, j));
                }
            }
        }
        return road;
    }
}