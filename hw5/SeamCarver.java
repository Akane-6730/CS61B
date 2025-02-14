import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;
    private double[][] energyMatrix;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        width = picture.width();
        height = picture.height();

        // compute energy matrix
        energyMatrix = new double[width][height];
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < height; j += 1) {
                energyMatrix[i][j] = computeEnergy(i, j);
            }
        }
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    private int energyHelper(int dr, int dg, int db) {
        return dr * dr + dg * dg + db * db;
    }

    // compute energy of pixel at column x and row y
    private double computeEnergy(int x, int y) {
        Color left = picture.get((x - 1 + width) % width, y);
        Color right = picture.get((x + 1) % width, y);
        Color up = picture.get(x, (y - 1 + height) % height);
        Color down = picture.get(x, (y + 1) % height);
        int dx = energyHelper(left.getRed() - right.getRed(),
                left.getGreen() - right.getGreen(),
                left.getBlue() - right.getBlue());
        int dy = energyHelper(up.getRed() - down.getRed(),
                up.getGreen() - down.getGreen(),
                up.getBlue() - down.getBlue());
        return dx + dy;
    }

    // return energy of pixel at column x and row y
    public  double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            throw new IndexOutOfBoundsException();
        }
        return energyMatrix[x][y];
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return findSeam(false);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return findSeam(true);
    }

    private int[] findSeam(boolean isVertical) {
        int w, h;
        if (isVertical) {
            w = width;
            h = height;
        } else {
            w = height;
            h = width;
        }

        // M(i,j) - cost of minimum cost path ending at (i, j)
        double[][] M = new double[w][h];
        for (int j = 0; j < h; j += 1) {
            for (int i = 0; i < w; i += 1) {
                if (j == 0) {
                    M[i][j] = isVertical ? energyMatrix[i][j] : energyMatrix[j][i];
                } else {
                    double minPrev = Double.POSITIVE_INFINITY;
                    for (int dx = -1; dx <= 1; dx += 1) {
                        int prevI = i + dx;
                        if (prevI >= 0 && prevI < w) {
                            if (M[prevI][j - 1] < minPrev) {
                                minPrev = M[prevI][j - 1];
                            }
                        }
                    }
                    double currentEnergy = isVertical ? energyMatrix[i][j] : energyMatrix[j][i];
                    M[i][j] = currentEnergy + minPrev;
                }
            }
        }

        int minX = 0;
        for (int x = 1; x < w; x += 1) {
            if (M[x][h - 1] < M[minX][h - 1]) {
                minX = x;
            }
        }

        int[] seam = new int[h];
        seam[h - 1] = minX;
        for (int i = h - 1; i > 0; i -= 1) {
            int prevX = seam[i];
            double minEnergy = Double.POSITIVE_INFINITY;
            int bestX = prevX;

            for (int dx = -1; dx <= 1; dx += 1) {
                int currentX = prevX + dx;
                if (currentX >= 0 && currentX < w) {
                    if (M[currentX][i - 1] < minEnergy) {
                        minEnergy = M[currentX][i - 1];
                        bestX = currentX;
                    }
                }
            }

            seam[i - 1] = bestX;
        }

        return seam;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        validateSeam(seam, width);
        picture = SeamRemover.removeHorizontalSeam(picture, findHorizontalSeam());
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        validateSeam(seam, height);
        picture = SeamRemover.removeVerticalSeam(picture, findVerticalSeam());
    }

    private void validateSeam(int[] seam, int expectedLength) {
        if (seam.length != expectedLength) {
            throw new IllegalArgumentException("Seam length must be " + expectedLength);
        }
        for (int i = 1; i < seam.length; i++) {
            int diff = seam[i] - seam[i - 1];
            if (diff < -1 || diff > 1) {
                throw new IllegalArgumentException("Adjacent seam entries differ by more than 1");
            }
        }
    }
}
