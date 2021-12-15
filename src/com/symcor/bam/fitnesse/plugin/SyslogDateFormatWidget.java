/** SyslogDateFormatWidget.java $Id$ */
package com.symcor.bam.fitnesse.plugin;

import java.text.SimpleDateFormat;
import java.util.Date;

import fitnesse.wikitext.parser.Matcher;
import fitnesse.wikitext.parser.Symbol;
import fitnesse.wikitext.parser.SymbolType;
import fitnesse.wikitext.parser.Translation;
import fitnesse.wikitext.parser.Translator;

/**
 * A Fitnesse plugin to specify a date in syslog RFC3614 format
 * 
 * <p>
 * This is specified in the Wiki as:
 * 
 * !datesyslog
 * 
 * This will set the value as the current date/time expressed in syslog RFC3614
 * format (ie MMM d HH:mm:ss) Where 'd' will contain the 2 digit month day or if only
 * single digit pad a space.
 * </p>
 * 
 * @author Sean Chalmers <schalmers@symcor.com>
 * @version $Revision$
 */
public class SyslogDateFormatWidget extends SymbolType implements Translation {

	private String currentDateSyslgFmt = null;

	/**
	 * Constructs SyslogDateFormatWidget
	 */
	public SyslogDateFormatWidget() {

		super("SyslogDateFormat");

		this.wikiMatcher(new Matcher().string("!now_syslogfmt"));
		this.htmlTranslation(this);
	}

	/**
	 * @see fitnesse.wikitext.parser.Translation#toTarget(fitnesse.wikitext.parser.Translator,
	 *      fitnesse.wikitext.parser.Symbol)
	 */
	public String toTarget(Translator translator, Symbol symbol) {
		return this.getCurrentDate();
	}

	/**
	 * 
	 * Gets the current date
	 * 
	 * @return The current date as formatted in syslog format
	 */
	private String getCurrentDate() {

		Date currentDate = new Date();
		this.currentDateSyslgFmt = this.formatDate(currentDate);

		return this.currentDateSyslgFmt;
	}

	/**
	 * Format Date into RFC3614 (syslog) format
	 * 
	 * @param date The date object to format
	 * @return The string of the date in syslog format
	 */
	private String formatDate(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("MMM d HH:mm:ss");
		String syslgDate = format.format(date);
		String result = syslgDate;

		// Split the date and extract the day part to see if it is a one or
		// two digit month day
		String[] syslgDateParts = syslgDate.split(" ", 3);
		String monthDay = syslgDateParts[1];
		if (!(monthDay == null || "".equals(monthDay))) {

			// If single digit then prepend space
			if (monthDay.length() == 1) {
				syslgDateParts[1] = " " + monthDay;
			}

			// Build syslog format date
			result = syslgDateParts[0] + "&nbsp;" + syslgDateParts[1] + " " +
					syslgDateParts[2];
		}

		return result;
	}

}
