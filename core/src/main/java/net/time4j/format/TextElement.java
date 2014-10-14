/*
 * -----------------------------------------------------------------------
 * Copyright © 2013-2014 Meno Hochschild, <http://www.menodata.de/>
 * -----------------------------------------------------------------------
 * This file (TextElement.java) is part of project Time4J.
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

package net.time4j.format;

import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoValues;

import java.io.IOException;


/**
 * <p>A chronological element which can be formatted as text or can be parsed
 * from a text. </p>
 *
 * @param   <V> generic type of element values
 * @author  Meno Hochschild
 */
/*[deutsch]
 * <p>Repr&auml;sentiert ein chronologisches Element, das als Text dargestellt
 * und interpretiert werden kann. </p>
 *
 * @param   <V> generic type of element values
 * @author  Meno Hochschild
 */
public interface TextElement<V>
    extends ChronoElement<V> {

    //~ Methoden ----------------------------------------------------------

    /**
     * <p>Converts the element value in given context to a formatted text. </p>
     *
     * <p>Implementation note: The concrete element value is obtainable by the
     * expression {@link ChronoValues#get(ChronoElement) context.get(this)}.
     * </p>
     *
     * @param   context     time context with the value of this element
     * @param   buffer      format buffer any text output will be sent to
     * @param   attributes  query for control attributes
     * @throws  IOException if writing to buffer fails
     */
    /*[deutsch]
     * <p>Wandelt dieses im angegebenen Zeitwertkontext enthaltene Element zu
     * einem Text um. </p>
     *
     * <p>Implementierungshinweis: Der konkrete Elementwert ist durch den
     * Ausdruck {@link ChronoValues#get(ChronoElement) context.get(this)}
     * gegeben. </p>
     *
     * @param   context     time context with the value of this element
     * @param   buffer      format buffer any text output will be sent to
     * @param   attributes  query for control attributes
     * @throws  IOException if writing to buffer fails
     */
    void print(
        ChronoValues context,
        Appendable buffer,
        AttributeQuery attributes
    ) throws IOException;

    /**
     * <p>Interpretes the given text as element value. </p>
     *
     * <p>Implementation note: Any implementation will start first at the
     * position {@link ParseLog#getPosition() status.getPosition()} and
     * either set the new position after successful parsing or return
     * {@code null} in case of error. </p>
     *
     * @param   text        text to be parsed
     * @param   status      current parsing position
     * @param   attributes  query for control attributes
     * @return  parsed element value or {@code null} if parsing
     *          was not successful
     */
    /*[deutsch]
     * <p>Interpretiert den angegebenen Text ab einer bestimmten Position
     * als Elementwert. </p>
     *
     * <p>Implementierungshinweis: Eine Implementierung wird den Text
     * erst ab der angegebenen Position {@link ParseLog#getPosition()
     * status.getPosition()} auswerten und nach erfolgreicher Interpretierung
     * den Index neu setzen oder im Fehlerfall {@code null} zur&uuml;ckgeben.
     * </p>
     *
     * @param   text        text to be parsed
     * @param   status      current parsing position
     * @param   attributes  query for control attributes
     * @return  parsed element value or {@code null} if parsing
     *          was not successful
     */
    V parse(
        CharSequence text,
        ParseLog status,
        AttributeQuery attributes
    );

}
