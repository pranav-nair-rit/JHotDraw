package org.jhotdraw.applet;


import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import org.jhotdraw.framework.FigureAttributeConstant;
import org.jhotdraw.util.CommandChoice;
import org.jhotdraw.standard.ChangeAttributeCommand;
import org.jhotdraw.figures.PolyLineFigure;
import java.awt.Color;
import org.jhotdraw.figures.AttributeFigure;
import org.jhotdraw.framework.FigureEnumeration;
import org.jhotdraw.framework.Figure;
import org.jhotdraw.util.ColorMap;
import java.awt.GraphicsEnvironment;
import java.io.Serializable;
import org.jhotdraw.framework.DrawingView;

public class DrawAppletProduct implements Serializable {
	private transient JComboBox fFrameColor;
	private transient JComboBox fFillColor;
	private transient JComboBox fTextColor;
	private transient JComboBox fArrowChoice;
	private transient JComboBox fFontChoice;

	/**
	* Creates the attribute choices. Override to add additional choices.
	*/
	public void createAttributeChoices(JPanel panel, DrawApplet drawApplet) {
		panel.add(new JLabel("Fill"));
		fFillColor = createColorChoice(FigureAttributeConstant.FILL_COLOR, drawApplet);
		panel.add(fFillColor);
		panel.add(new JLabel("Text"));
		fTextColor = createColorChoice(FigureAttributeConstant.TEXT_COLOR, drawApplet);
		panel.add(fTextColor);
		panel.add(new JLabel("Pen"));
		fFrameColor = createColorChoice(FigureAttributeConstant.FRAME_COLOR, drawApplet);
		panel.add(fFrameColor);
		panel.add(new JLabel("Arrow"));
		CommandChoice choice = new CommandChoice();
		fArrowChoice = choice;
		FigureAttributeConstant arrowMode = FigureAttributeConstant.ARROW_MODE;
		choice.addItem(
				new ChangeAttributeCommand("none", arrowMode, new Integer(PolyLineFigure.ARROW_TIP_NONE), drawApplet));
		choice.addItem(new ChangeAttributeCommand("at Start", arrowMode, new Integer(PolyLineFigure.ARROW_TIP_START),
				drawApplet));
		choice.addItem(
				new ChangeAttributeCommand("at End", arrowMode, new Integer(PolyLineFigure.ARROW_TIP_END), drawApplet));
		choice.addItem(new ChangeAttributeCommand("at Both", arrowMode, new Integer(PolyLineFigure.ARROW_TIP_BOTH),
				drawApplet));
		panel.add(fArrowChoice);
		panel.add(new JLabel("Font"));
		fFontChoice = createFontChoice(drawApplet);
		panel.add(fFontChoice);
	}

	public void setupAttributes(DrawingView thisFView) {
		Color frameColor = (Color) AttributeFigure.getDefaultAttribute(FigureAttributeConstant.FRAME_COLOR);
		Color fillColor = (Color) AttributeFigure.getDefaultAttribute(FigureAttributeConstant.FILL_COLOR);
		Integer arrowMode = (Integer) AttributeFigure.getDefaultAttribute(FigureAttributeConstant.ARROW_MODE);
		String fontName = (String) AttributeFigure.getDefaultAttribute(FigureAttributeConstant.FONT_NAME);
		FigureEnumeration fe = thisFView.selection();
		while (fe.hasNextFigure()) {
			Figure f = fe.nextFigure();
			frameColor = (Color) f.getAttribute(FigureAttributeConstant.FRAME_COLOR);
			fillColor = (Color) f.getAttribute(FigureAttributeConstant.FILL_COLOR);
			arrowMode = (Integer) f.getAttribute(FigureAttributeConstant.ARROW_MODE);
			fontName = (String) f.getAttribute(FigureAttributeConstant.FONT_NAME);
		}
		fFrameColor.setSelectedIndex(ColorMap.colorIndex(frameColor));
		fFillColor.setSelectedIndex(ColorMap.colorIndex(fillColor));
		if (arrowMode != null) {
			fArrowChoice.setSelectedIndex(arrowMode.intValue());
		}
		if (fontName != null) {
			fFontChoice.setSelectedItem(fontName);
		}
	}

	/**
	* Creates the color choice for the given attribute.
	*/
	public JComboBox createColorChoice(FigureAttributeConstant attribute, DrawApplet drawApplet) {
		CommandChoice choice = new CommandChoice();
		for (int i = 0; i < ColorMap.size(); i++)
			choice.addItem(new ChangeAttributeCommand(ColorMap.name(i), attribute, ColorMap.color(i), drawApplet));
		return choice;
	}

	/**
	* Creates the font choice. The choice is filled with all the fonts supported by the toolkit.
	*/
	public JComboBox createFontChoice(DrawApplet drawApplet) {
		CommandChoice choice = new CommandChoice();
		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for (int i = 0; i < fonts.length; i++) {
			choice.addItem(
					new ChangeAttributeCommand(fonts[i], FigureAttributeConstant.FONT_NAME, fonts[i], drawApplet));
		}
		return choice;
	}
}