/**
 *  SwLogger - Swing based appender for log4j
 *  Copyright (C) 2009 dpr
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.dpr.swappender.components;

import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

import org.dpr.swappender.utils.LogUtils;

/**
 * Used to show log detail in a textarea
 * 
 * @author C. Roger
 * 
 */
public class DetailPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4473460891636042049L;
	/**
	 * text display
	 */
	JTextArea detailArea;
	/**
	 * icna and/or label
	 */
	JLabel categorie;

	/**
	 * 
	 */
	public DetailPanel() {

		BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(bl);
		JPanel titrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel titre = new JLabel(LogUtils.getLocale("detail.category") + ": ");
		categorie = new JLabel("");

		titrePanel.add(titre);
		titrePanel.add(categorie);
		detailArea = new JTextArea(5, 40);
		JScrollPane scrollPane = new JScrollPane(detailArea);
		detailArea.setWrapStyleWord(true);
		detailArea.setLineWrap(true);
		// disable autoscroll !!
		((DefaultCaret) ((JTextArea) detailArea).getCaret())
				.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		detailArea.setEditable(false);
		add(titrePanel);
		add(scrollPane);
		setVisible(false);

	}

	/**
	 * Set value of textarea and label
	 * 
	 * @param line
	 * @param levelColumn
	 */
	public void setValue(String line, int levelColumn) {
		detailArea.setText("");
		String[] lines = line.split(LogUtils.SPLITCHAR_REGEX);
		int i = 0;
		for (String val : lines) {
			if (i != levelColumn) {
				detailArea.append(val);
				detailArea.append(System.getProperty("line.separator"));
			} else {
				categorie.setText(LogUtils.getLevelText(val));
				categorie.setIcon(LogUtils.getLevelImageIcon(val));
			}
			i++;
		}

		setVisible(true);
	}

}
