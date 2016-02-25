/*
 * Copyright 2009 by Kappich Systemberatung Aachen
 * 
 * This file is part of de.kappich.pat.gnd.
 * 
 * de.kappich.pat.gnd is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * de.kappich.pat.gnd is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with de.kappich.pat.gnd; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package de.kappich.pat.gnd.pluginInterfaces;

import de.kappich.pat.gnd.displayObjectToolkit.DOTDefinitionDialogFrame;
import de.kappich.pat.gnd.displayObjectToolkit.DOTProperty;

import de.bsvrz.dav.daf.main.config.DataModel;
import de.bsvrz.dav.daf.main.config.SystemObjectType;

/**
 * Das Interface zur Selbstbeschreibung eines GND-Plugins. Ein Plugin besteht aus der Implementation
 * der vier Interfaces in diesem Package. Die Implementation dieses Interfaces liefert den Einstieg in
 * diese Implementationen und weitere Meta-Informationen des Plugins.
 * <p>
 * Jede implementierende Klassen muss einen �ffentlichen Konstruktor mit leerer Argumentenliste haben, 
 * damit Class.newInstance() aufgerufen werden kann. Au�erdem sollte dieser Konstruktor leichtgewichtig sein, 
 * damit er nicht die Performanz senkt. 
 * 
 * @author Kappich Systemberatung
 * @version $Revision: 8094 $
 *
 */
public interface DisplayObjectTypePlugin {
	
	/**
	 * Gibt den eindeutigen Namen des Plugins zur�ck. Die Namen 'Fl�che', 'Komplex', 'Linie' und 'Punkt' 
	 * sind bereits vergeben.
	 * 
	 * @return der eindeutige Name des Plugins
	 */
	public String						getName();		
	
	/**
	 * Gibt den Darstellungstypen des Plugins zur�ck, das ist ein Implementation von {@link DisplayObjectType}.
	 * 
	 * @return ein Objekt des Darstellungstyps dieses Plugins 
	 */
	public DisplayObjectType 			getDisplayObjectType();
	
	/**
	 * Gibt einen Dialog zur Definition und Bearbeitung von Darstellungstypen dieses Plugins zur�ck.
	 * 
	 * @return den Definitions- und Bearbeitungsdialog
	 */
	public DOTDefinitionDialog			getDialog( DOTDefinitionDialogFrame dotDefinitionDialogFrame);
	
	/**
	 * Gibt einen Painter zur�ck, der Darstellungsobjekte mit Darstellungsobjekttypen dieses Plugins,
	 * zeichnen kann.
	 * 
	 * @return ein Objekt des Painters dieses Plugins 
	 */
	public DisplayObjectPainter			getPainter();
	
	/**
	 * Gibt die m�glichen Typen der Grundfiguren, die gezeichnet werden k�nnen, zur�ck, z.B. Rechteck, 
	 * Kreis, Text usw. 
	 * <p>
	 * Ein Plugin kann Grundfiguren besitzen, muss aber nicht. Siehe die Erl�uterungen {@link DisplayObjectType hier}.
	 * 
	 * @return die m�glichen Grundfigurtypen des Plugins
	 */
	public String[] 					getPrimitiveFormTypes();
	
	/**
	 * Gibt die Visualisierungs-Eigenschaften des Grundfigurtyps o zur�ck. Kann mit null aufgerufen werden,
	 * und gibt dann globale Eigenschaften zur�ck. Siehe die Erl�uterungen {@link DisplayObjectType hier}.
	 * 
	 * @return die Eigenschaften der Grundfigur oder des Darstellungstyps selbst
	 */
	public DOTProperty[]				getProperties( Object o);
	
	/**
	 * Gibt 'typ.fl�che', 'typ.linie', 'typ.punkt' oder 'typ.geoReferenzObject' zur�ck, je nachdem,
	 * ob das Plugin f�r Systemobjekte so eingeschr�nkt werden kann (in den ersten drei F�llen) oder
	 * nicht (im letzten Fall).
	 * 
	 * @return der Geometrietyp
	 */
	public String 						getGeometryType();
	
	/**
	 * Pr�ft, ob der �bergebene SystemObjectType von dem Plugin unterst�tzt wird.
	 * 
	 * @param configuration die Konfiguration
	 * @param systemObjectType ein SystemObjectType
	 * @return <code>true</code> genau dann, wenn der Typ vom Plugin unterst�tzt wird
	 */
	public boolean						isSystemObjectTypeSupported( DataModel configuration, SystemObjectType systemObjectType);
	
}
