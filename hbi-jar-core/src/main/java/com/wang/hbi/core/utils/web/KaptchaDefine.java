package com.wang.hbi.core.utils.web;

import com.google.code.kaptcha.Producer;
import com.wang.hbi.core.memcached.XMemcachedClientForSession;
import com.wang.hbi.core.memcached.XMemcachedConstants;
import com.wang.hbi.core.utils.web.admin.HbiAdminUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码工具类
 * @author HeJiawang
 * @date   20171012
 */
public class KaptchaDefine implements Producer {

	/**
	 * logger
	 */
	private final static Logger logger = LoggerFactory.getLogger(KaptchaDefine.class);

	private int width;
	private int height;
	private int fontSize;
	private Font[] fonts;
	private int charSpace = 5;
	private int noisePoint;
	private int noiseLine;
	private Random random = new Random();

	public KaptchaDefine() {
	}

	public KaptchaDefine(int width, int height, int fontSize, Font[] fonts, int noiseLine, int noisePoint) {
		this.width = width;
		this.height = height;
		this.fontSize = fontSize;
		this.fonts = fonts;
		this.noiseLine = noiseLine;
		this.noisePoint = noisePoint;
	}

	public BufferedImage createImage(String text) {
		BufferedImage bi = renderWord(text, this.width, this.height);
		return bi;
	}

	public Color getRandColor(int fc, int bc) {
		if (fc > 255) fc = 255;
		if (bc > 255) bc = 255;
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

	public BufferedImage renderWord(String word, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

		Graphics   g   = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		Graphics2D g2D = (Graphics2D) g;
		// g2D.setColor(Color.BLACK);
		g2D.setColor(getRandColor(200, 240));
		g2D.fillRect(0, 0, width, height);

		// 画100个噪点(颜色及位置随机)
		for (int i = 0; i < noisePoint; i++) {

			// 随机颜色
			int rInt = random.nextInt(255);
			int gInt = random.nextInt(255);
			int bInt = random.nextInt(255);

			// g2D.setColor(new Color(rInt, gInt, bInt));
			g2D.setColor(Color.WHITE);

			// 随机位置
			int xInt = random.nextInt(width - 3);
			int yInt = random.nextInt(height - 2);

			// 随机旋转角度
			int sAngleInt = random.nextInt(10);
			int eAngleInt = random.nextInt(10);

			// 随机大小
			int wInt = random.nextInt(20);
			int hInt = random.nextInt(6);

			// g2D.fillArc(xInt, yInt, wInt, hInt, sAngleInt, eAngleInt);
			g2D.fillOval(xInt, yInt, wInt, wInt);
		}

		// 画干扰线
		for (int j = 0; j < noiseLine; j++) {
			int xInt = random.nextInt(width - 3);
			int yInt = random.nextInt(height - 2);
			int xInt2 = random.nextInt(width);
			int yInt2 = random.nextInt(height);

			g2D.drawLine(xInt, yInt, xInt2, yInt2);
		}

		g2D.setColor(getRandColor(100, 200));
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		hints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));

		g2D.setRenderingHints(hints);

		FontRenderContext frc = g2D.getFontRenderContext();

		int startPosY = (height - fontSize) / 5 + fontSize;

		char[] wordChars   = word.toCharArray();
		Font[] chosenFonts = new Font[wordChars.length];
		int[]  charWidths  = new int[wordChars.length];
		int    widthNeeded = 0;
		for (int i = 0; i < wordChars.length; i++) {
			chosenFonts[i] = fonts[random.nextInt(fonts.length)];

			char[] charToDraw = {wordChars[i]};

			GlyphVector gv = chosenFonts[i].createGlyphVector(frc, charToDraw);
			charWidths[i] = ((int) gv.getVisualBounds().getWidth());
			if (i > 0) {
				widthNeeded += 2;
			}
			widthNeeded += charWidths[i];
		}

		int startPosX = (width - widthNeeded) / 2;
		for (int i = 0; i < wordChars.length; i++) {
			g2D.setFont(chosenFonts[i]);
			char[] charToDraw = {wordChars[i]};

			g2D.drawChars(charToDraw, 0, charToDraw.length, startPosX, startPosY);
			startPosX = startPosX + charWidths[i] + charSpace;
		}

		return image;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public Font[] getFonts() {
		return fonts;
	}

	public void setFonts(Font[] fonts) {
		this.fonts = fonts;
	}

	public int getCharSpace() {
		return charSpace;
	}

	public void setCharSpace(int charSpace) {
		this.charSpace = charSpace;
	}

	public int getNoisePoint() {
		return noisePoint;
	}

	public void setNoisePoint(int noisePoint) {
		this.noisePoint = noisePoint;
	}

	public int getNoiseLine() {
		return noiseLine;
	}

	public void setNoiseLine(int noiseLine) {
		this.noiseLine = noiseLine;
	}

	@Override
	public String createText() {
		return "";
	}

	/**
	 * 从memchched中获取验证码
	 * @param sessionId key
	 * @return value
	 */
	public static String getTextFromMemcached(String sessionId) {
		String text = null;
		try {
			text = (String) XMemcachedClientForSession.get(HbiAdminUserUtil.CAPTCHA + sessionId);
		} catch (Exception e) {
			logger.error("memcached验证码读取错误", e);
		}
		return text;
	}

	/**
	 * 将验证码存入memcached
	 * @param sessionId key
	 * @param text value
	 */
	public static void setTextToMemcached(String sessionId, final String text) {
		try {
			XMemcachedClientForSession.set(HbiAdminUserUtil.CAPTCHA + sessionId, XMemcachedConstants.TIME_OUT_FIVE_MINUTES, text);
		} catch (Exception e) {
			logger.error("memcached验证码写入错误", e);
		}
	}

	/**
	 * 从mencached删除验证码
	 * @param sessionId key
	 */
	public static void deleteTextToMemcached(String sessionId) {
		try {
			XMemcachedClientForSession.delete(HbiAdminUserUtil.CAPTCHA + sessionId);
		} catch (Exception e) {
			logger.error("memcached验证码删除错误", e);
		}
	}
}
