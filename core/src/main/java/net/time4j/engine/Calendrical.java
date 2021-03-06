/*
 * -----------------------------------------------------------------------
 * Copyright © 2013-2014 Meno Hochschild, <http://www.menodata.de/>
 * -----------------------------------------------------------------------
 * This file (Calendrical.java) is part of project Time4J.
 *
 * Time4J is free software: You can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * Time4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Time4J. If not, see <http://www.gnu.org/licenses/>.
 * -----------------------------------------------------------------------
 */

package net.time4j.engine;


/**
 * <p>Abstract base class of all plain calendar date types which are
 * convertible via their day epoch numbers. </p>
 *
 * @param   <U> generic type of time unit compatible to {@link ChronoUnit})
 * @param   <D> generic type of self reference
 * @author  Meno Hochschild
 */
/*[deutsch]
 * <p>Abstrakte Basisklasse aller reinen Datumstypen, die &uuml;ber ihre
 * Epochentage ineinander konvertierbar sind. </p>
 *
 * @param   <U> generic type of time unit compatible to {@link ChronoUnit})
 * @param   <D> generic type of self reference
 * @author  Meno Hochschild
 */
public abstract class Calendrical<U, D extends Calendrical<U, D>>
    extends TimePoint<U, D>
    implements Temporal<Calendrical<?, ?>> {

    //~ Methoden ----------------------------------------------------------

    /**
     * <p>Converts this calendar date to the given target type based on
     * the count of days relative to UTC epoch [1972-01-01]. </p>
     *
     * <p>The conversion occurs on the local timeline at noon. This
     * reference time ensures that all date types remain convertible
     * even if a calendar system defines dates not starting at midnight. </p>
     *
     * @param   <T> generic target date type
     * @param   target  chronological type this date shall be converted to
     * @return  converted date of target type t
     * @throws  IllegalArgumentException if the target class does not
     *          have any chronology
     * @throws  ArithmeticException in case of numerical overflow
     */
    /*[deutsch]
     * <p>Konvertiert dieses Datum zum angegebenen Zieltyp auf Basis der
     * Anzahl der Tage relativ zur UTC-Epoche [1972-01-01]. </p>
     *
     * <p>Die Konversion findet auf dem lokalen Zeitstrahl um 12 Uhr mittags
     * als angenommener Referenzzeit statt. Diese Referenzzeit stellt sicher,
     * da&szlig; alle Datumstypen konvertierbar bleiben, auch wenn in einem
     * Kalendersystem ein Tag nicht um Mitternacht startet. </p>
     *
     * @param   <T> generic target date type
     * @param   target  chronological type this date shall be converted to
     * @return  converted date of target type t
     * @throws  IllegalArgumentException if the target class does not
     *          have any chronology
     * @throws  ArithmeticException in case of numerical overflow
     */
    public <T extends Calendrical<?, T>> T transform(Class<T> target) {

        long utcDays = this.getEpochDays();
        Chronology<T> chronology = Chronology.lookup(target);

        if (chronology == null) {
            // kommt normal nie vor, weil sich jede Chrono selbst registriert
            throw new IllegalArgumentException(
                "Cannot find any chronology for given target type: "
                + target.getName());
        }

        CalendarSystem<T> calsys = chronology.getCalendarSystem();

        if (
            (calsys.getMinimumSinceUTC() > utcDays)
            || (calsys.getMaximumSinceUTC() < utcDays)
        ) {
            throw new ArithmeticException(
                "Cannot transform <" + utcDays + "> to: " + target.getName());
        } else {
            return calsys.transform(utcDays);
        }

    }

    @Override
    public boolean isBefore(Calendrical<?, ?> date) {

        return (this.compareByTime(date) < 0);

    }

    @Override
    public boolean isAfter(Calendrical<?, ?> date) {

        return (this.compareByTime(date) > 0);

    }

    @Override
    public boolean isSimultaneous(Calendrical<?, ?> date) {

        if (this == date) {
            return true;
        }

        return (this.compareByTime(date) == 0);

    }

    /**
     * <p>Defines a total respective natural order. </p>
     *
     * <p>This implementation first evaluates the temporal position on the
     * common timeline, that is the epoch day numbers. Only date objects
     * of the same calendrical type are comparable. The order is consistent
     * with {@code equals()} as long as subclasses don't define further
     * state attributes. If objects of different calendrical type are to be
     * compared on the timeline only applications can either use an
     * {@code EpochDays}-instance as {@code Comparator} or use one of
     * the {@code Temporal}-methods {@code isAfter()}, {@code isBefore()}
     * and {@code isSimultaneous()}. </p>
     *
     * @throws  ClassCastException if there are different date types
     * @see     EpochDays#compare(ChronoDisplay, ChronoDisplay)
     * @see     #isBefore(Calendrical)
     * @see     #isAfter(Calendrical)
     */
    /*[deutsch]
     * <p>Definiert eine totale respektive eine nat&uuml;rliche Ordnung. </p>
     *
     * <p>Diese Implementierung wertet die zeitliche Position auf dem
     * gemeinsamen Zeitstrahl aus, also die Epochentage. Nur Datumsobjekte
     * gleichen Kalendertyps k&ouml;nnen miteinander verglichen werden.
     * Die Sortierung ist daher konsistent mit {@code equals()}, solange
     * Subklassen nicht weitere Zustandsattribute definieren. Sollen garantiert
     * Datumsobjekte verschiedenen Typs nur zeitlich verglichen werden, kann
     * entweder eine {@code EpochDays}-Instanz als {@code Comparator}
     * oder eine der {@code Temporal}-Methoden {@code isAfter()},
     * {@code isBefore()} und {@code isSimultaneous()} verwendet werden. </p>
     *
     * @throws  ClassCastException if there are different date types
     * @see     EpochDays#compare(ChronoDisplay, ChronoDisplay)
     * @see     #isBefore(Calendrical)
     * @see     #isAfter(Calendrical)
     */
    @Override
    public int compareTo(D date) {

        Class<?> t1 = this.getChronology().getChronoType();
        Class<?> t2 = date.getChronology().getChronoType();

        if (t1 != t2) {
            throw new ClassCastException(
                "Cannot compare different types of dates, "
                + "use instance of EpochDays as comparator instead.");
        }

        return this.compareByTime(date);

    }

    /**
     * <p>Based on the epoch day number and the calendar system. </p>
     *
     * <p>In other words: Two date object are equal if they have the
     * same temporal position on the local timeline and have the same
     * calendrical type. Subclasses which define further state attributes
     * must override this method. </p>
     *
     * <p>If an only temporal comparison is required then the method
     * {@link #isSimultaneous(Calendrical)} is to be used. </p>
     *
     * @see     Chronology#getChronoType()
     */
    /*[deutsch]
     * <p>Basiert auf den Epochentagen und dem Kalendersystem. </p>
     *
     * <p>Mit anderen Worten: Zwei Datumsobjekte sind genau dann gleich, wenn
     * sie zeitlich gleich UND vom selben Kalendertyp sind. Subklassen, die
     * weitere Zustandsattribute definieren, m&uuml;ssen diese Methode
     * geeignet &uuml;berschreiben. </p>
     *
     * <p>Soll ein rein zeitlicher Vergleich sichergestellt sein, dann
     * ist stattdessen die Methode {@link #isSimultaneous(Calendrical)}
     * zu verwenden. </p>
     *
     * @see     Chronology#getChronoType()
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj instanceof Calendrical) {
            Calendrical<?, ?> that = (Calendrical) obj;
            Class<?> t1 = this.getChronology().getChronoType();
            Class<?> t2 = that.getChronology().getChronoType();
            return (
                (t1 == t2)
                && (this.getEpochDays() == that.getEpochDays())
            );
        } else {
            return false;
        }

    }

    /**
     * <p>Based on the epoch day number. </p>
     */
    /*[deutsch]
     * <p>Basiert auf den Epochentagen. </p>
     */
    @Override
    public int hashCode() {

        long days = this.getEpochDays();
        return (int) (days ^ (days >>> 32));

    }

    /**
     * <p>Yields the start of this calendar day. </p>
     *
     * <p>If a calendar date is combined with a clock time then this
     * method has impact on the conversion of day numbers which occurs
     * on the local timeline at noon. </p>
     *
     * @return  shift in seconds relative to midnight
     * @see     StartOfDay
     */
    /*[deutsch]
     * <p>Ermittelt den Beginn des Tages. </p>
     *
     * <p>Falls zu einer Datumsangabe eine Uhrzeit hinzukommt, hat diese
     * Methode Einflu&szlig; auf die Konversion zwischen Tagesnummern, die
     * immer zur Referenzzeit von 12 Uhr mittags stattfindet. </p>
     *
     * @return  shift in seconds relative to midnight
     * @see     StartOfDay
     */
    public int getStartOfDay() {

        CalendarSystem<D> calsys = this.getCalendarSystem();
        StartOfDay sod = calsys.getStartOfDay();

        if (sod.isFixed()) {
            return sod.getShift(0); // Optimierung
        } else {
            return sod.getShift(calsys.transform(this.getContext()));
        }

    }

    /**
     * <p>Definiert eine rein zeitliche Ordnung. </p>
     *
     * <p>Diese Implementierung wertet die zeitliche Position auf dem
     * gemeinsamen Zeitstrahl aus, also die Epochentage. </p>
     *
     * @return  negative, zero or positive integer if this instance is earlier,
     *          simultaneous or later than given date
     * @exclude
     */
    protected int compareByTime(Calendrical<?, ?> date) {

        long d1 = this.getEpochDays();
        long d2 = date.getEpochDays();

        return ((d1 < d2) ? -1 : ((d1 == d2) ? 0 : 1));

    }

    /**
     * <p>Ermittelt die Anzahl von Tagen seit dem Beginn der
     * UTC-Epoche [1972-01-01]. </p>
     *
     * @return  count of days relative to UTC epoch [1972-01-01]
     */
    long getEpochDays() {

        return this.getCalendarSystem().transform(this.getContext());

    }

    private CalendarSystem<D> getCalendarSystem() {

        return this.getChronology().getCalendarSystem();

    }

}
