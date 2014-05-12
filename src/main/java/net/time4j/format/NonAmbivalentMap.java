/*
 * -----------------------------------------------------------------------
 * Copyright © 2013 Meno Hochschild, <http://www.menodata.de/>
 * -----------------------------------------------------------------------
 * This file (NonAmbivalentMap.java) is part of project Time4J.
 *
 * Time4J is free software: You can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Time4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Time4J. If not, see <http://www.gnu.org/licenses/>.
 * -----------------------------------------------------------------------
 */

package net.time4j.format;

import net.time4j.engine.ChronoElement;

import java.util.HashMap;


/**
 * <p>Spezialimplementierung einer {@code Map}, die kein &Uuml;berschreiben
 * von gespeicherten Eintr&auml;gen zul&auml;sst, wenn die Werte zu einem
 * Schl&uuml;ssel verschieden sind. </p>
 *
 * @author  Meno Hochschild
 */
@SuppressWarnings("serial")
class NonAmbivalentMap
    extends HashMap<ChronoElement<?>, Object> {

    //~ Instanzvariablen --------------------------------------------------

    private boolean checking = true;

    //~ Methoden ----------------------------------------------------------

    @Override
    public Object put(ChronoElement<?> key, Object value) {

        Object obj = super.put(key, value);

        if (
            (key == null)
            || (obj == null)
            || obj.equals(value)
        ) {
            return obj;
        } else if (this.checking) {
            throw new AmbivalentValueException(key);
        } else {
            return obj;
        }

    }

    /**
     * <p>Legt fest, ob zweideutige Werte gepr&uuml;ft werden sollen. </p>
     *
     * @param   checking    boolean
     */
    void setChecking(boolean checking) {

        this.checking = checking;

    }

}