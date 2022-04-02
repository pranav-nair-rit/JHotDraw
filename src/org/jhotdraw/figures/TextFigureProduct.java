package org.jhotdraw.figures;


import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.Font;

public class TextFigureProduct implements Cloneable {
	private transient boolean fSizeIsDirty = true;
	private transient int fWidth;
	private transient int fHeight;

	public void setFSizeIsDirty(boolean fSizeIsDirty) {
		this.fSizeIsDirty = fSizeIsDirty;
	}

	public void markDirty() {
		fSizeIsDirty = true;
	}

	public Dimension textExtent(Font thisFFont, String thisFText) {
		if (!fSizeIsDirty) {
			return new Dimension(fWidth, fHeight);
		}
		FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(thisFFont);
		fWidth = metrics.stringWidth(thisFText);
		fHeight = metrics.getHeight();
		fSizeIsDirty = false;
		return new Dimension(fWidth, fHeight);
	}

	public Object clone() {
		try {
			return (TextFigureProduct) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}