package ru.augui.core;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.Rectangle;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class Core {
    private static final int BUTTON1_DOWN_MASK = InputEvent.BUTTON1_DOWN_MASK;

    public void compareAndClick(String expectedImagePath, String actualImagePath) {
        compareAndClickInternal(expectedImagePath, actualImagePath, null);
    }

    public void compareAndClick(String expectedImagePath, String actualImagePath, String resultImagePath) {
        compareAndClickInternal(expectedImagePath, actualImagePath, resultImagePath);
    }

    private void compareAndClickInternal(String expectedImagePath, String actualImagePath, String resultImagePath) {
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(expectedImagePath);
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actualImagePath);
        ImageComparisonResult result = new ImageComparison(expectedImage, actualImage).compareImages();

        if (!result.getRectangles().isEmpty()) {
            Rectangle diffArea = result.getRectangles().get(0);
            clickOnCenter(diffArea);
        } else {
            System.out.println("Различия между изображениями не найдены.");
        }

        if (resultImagePath != null) {
            saveComparisonResult(resultImagePath, result);
        }
    }

    public void compareAndClickTopLeft(String expectedImagePath, String actualImagePath, String resultImagePath, int searchWidth, int searchHeight) {
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(expectedImagePath);
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actualImagePath);

        int halfWidth = expectedImage.getWidth() / 4;
        int halfHeight = expectedImage.getHeight() / 2;

        BufferedImage expectedTopLeft = expectedImage.getSubimage(0, 0, halfWidth, halfHeight);
        BufferedImage actualTopLeft = actualImage.getSubimage(0, 0, halfWidth, halfHeight);

        ImageComparison imageComparison = new ImageComparison(expectedTopLeft, actualTopLeft)
                .setExcludedAreas(java.util.List.of(
                        new Rectangle(searchWidth, 0, halfWidth - searchWidth, halfHeight),
                        new Rectangle(0, searchHeight, searchWidth, halfHeight - searchHeight)));

        ImageComparisonResult result = imageComparison.compareImages();

        if (!result.getRectangles().isEmpty()) {
            Rectangle diffArea = result.getRectangles().get(0);
            clickOnCenter(diffArea);
        } else {
            System.out.println("Различия между изображениями не найдены в верхнем левом углу.");
        }

        saveComparisonResult(resultImagePath, result);
    }

    public void compareAndClickCenter(String expectedImagePath, String actualImagePath, String resultImagePath,
                                      int topMargin, int bottomMargin, int leftMargin, int rightMargin) {
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(expectedImagePath);
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actualImagePath);

        int width = expectedImage.getWidth();
        int height = expectedImage.getHeight();

        int centerWidth = width - leftMargin - rightMargin;
        int centerHeight = height - topMargin - bottomMargin;

        BufferedImage expectedCenter = expectedImage.getSubimage(leftMargin, topMargin, centerWidth, centerHeight);
        BufferedImage actualCenter = actualImage.getSubimage(leftMargin, topMargin, centerWidth, centerHeight);

        ImageComparison imageComparison = new ImageComparison(expectedCenter, actualCenter)
                .setExcludedAreas(java.util.List.of(
                        new Rectangle(0, 0, centerWidth, topMargin),
                        new Rectangle(0, topMargin, leftMargin, centerHeight),
                        new Rectangle(centerWidth - rightMargin, topMargin, rightMargin, centerHeight),
                        new Rectangle(0, centerHeight - bottomMargin, centerWidth, bottomMargin)));

        ImageComparisonResult result = imageComparison.compareImages();

        if (!result.getRectangles().isEmpty()) {
            Rectangle diffArea = result.getRectangles().get(0);
            clickOnCenterWithOffset(diffArea, leftMargin, topMargin);
        } else {
            System.out.println("Различия между изображениями не найдены в центральной части.");
        }

        saveComparisonResult(resultImagePath, result);
    }

    private void clickOnCenterWithOffset(Rectangle rect, int xOffset, int yOffset) {
        int centerX = rect.getMinPoint().x + (rect.getWidth() / 2) + xOffset;
        int centerY = rect.getMinPoint().y + (rect.getHeight() / 2) + yOffset;

        try {
            Robot robot = new Robot();
            robot.mouseMove(centerX, centerY);
            robot.mousePress(BUTTON1_DOWN_MASK);
            robot.mouseRelease(BUTTON1_DOWN_MASK);
            System.out.println("Клик выполнен по координатам: (" + centerX + ", " + centerY + ")");
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void clickOnCenter(Rectangle rect) {
        int centerX = rect.getMinPoint().x + (rect.getWidth() / 2);
        int centerY = rect.getMinPoint().y + (rect.getHeight() / 2);

        try {
            Robot robot = new Robot();
            robot.mouseMove(centerX, centerY);
            robot.mousePress(BUTTON1_DOWN_MASK);
            robot.mouseRelease(BUTTON1_DOWN_MASK);
            System.out.println("Клик выполнен по координатам: (" + centerX + ", " + centerY + ")");
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void saveComparisonResult(String resultImagePath, ImageComparisonResult result) {
        File resultImageFile = new File(resultImagePath);
        ImageComparisonUtil.saveImage(resultImageFile, result.getResult());
        System.out.println("Результат сравнения сохранен по пути: " + resultImageFile.getAbsolutePath());
    }

}
