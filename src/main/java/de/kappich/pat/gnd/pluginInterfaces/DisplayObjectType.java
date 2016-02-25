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

import de.kappich.pat.gnd.displayObjectToolkit.DOTProperty;
import de.kappich.pat.gnd.displayObjectToolkit.DOTSubscriptionData;
import de.kappich.pat.gnd.gnd.LegendTreeNodes;

import de.bsvrz.dav.daf.main.DataState;

import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;

/**
 * Das Interface f�r die Darstellungstypen der Plugins.
 * <p>
 * Der Name eines Darstellungstypen wird bei der Definition vom Benutzer festgelegt. Er wird als 
 * eindeutiges Erkennungszeichen in den Verwaltungsdialogen verwendet, und auch bei der Anzeige in der Legende.
 * Au�erdem hat ein Darstellungstyp eine 'Info', die als Tooltipp etwa in der Legende benutzt wird.
 * <p>
 * Ein Darstellungstyp legt fest wie die {@link DisplayObject DisplayObjects} eines Layers dargestellt
 * werden. Darstellungstypen k�nnen {@link PrimitiveForm Grundfiguren} besitzen, m�ssen es aber nicht.
 * Beispiele f�r Darstellungstypen ohne Grundfiguren sind {@link DOTArea}, {@link DOTComplex} und 
 * {@link DOTLine}. Bei diesen Klassen h�ngen alle {@link DOTProperty Eigenschaften} der Visualisierung 
 * (z.B. die Farbe) direkt am Darstellungstypen. Anders verh�lt es sich bei der Klasse {@link DOTPoint}: 
 * dieser Darstellungstyp hat selbst keine Eigenschaften, sondern ausschlie�lich benutzerdefinierte 
 * Grundfiguren (z.B. ein Rechteck festgelegter Gr��e) und nur an diesen h�ngen die Eigenschaften.
 * Bei der Implementation sollte der Zugriff auf Eigenschaften, die direkt am Darstellungstypen h�ngen,
 * durch <code>null</code> als Wert f�r die Grundfigur geschehen.
 * <p>
 * Jede Grundfigur hat einen Typ, der einerseits definiernde Gr��en (z.B. den Radius bei dem Typ Kreis), 
 * aber auch die m�glichen Visualisierungs-Eigenschaften festlegt (z.B. die F�llfarbe).
 * <p>
 * Eine Visualisierungs-Eigenschaft ist entweder statisch, d.h. unver�nderbar, oder dynamisch, d.h.
 * sie ver�ndert sich in Abh�ngigkeit von Online-Daten.
 *  
 * @author Kappich Systemberatung
 * @version $Revision: 8080 $
 *
 */
public interface DisplayObjectType {
	
	
	/**
	 * Getter f�r den Namen.
	 * 
	 * @return der Name
	 */
	public String getName();
	
	/**
	 * Setter f�r den Namen.
	 * 
	 * @param der Name
	 */
	public void setName( String name);
	
	/**
	 * Getter f�r die Info.
	 * 
	 * @return die Kurzinfo
	 */
	public String getInfo();
	
	/**
	 *  Setter f�r die Info.
	 *  
	 * @param info die Kurzinfo
	 */
	public void setInfo( String info);
	
	/**
	 * Zugriff auf alle auftretenden Grundfigurnamen.
	 * 
	 * @return die Menge aller Grundfigurnamen
	 */
	public Set<String> getPrimitiveFormNames();
	
	/**
	 * Gibt den Grundfigurtyp der Grundfigur zur�ck.
	 * 
	 * @param primitiveFormName der Name einer Grundfigur
	 * @return der Typ der Grundfigur 
	 */
	public String getPrimitiveFormType( String primitiveFormName);
	
	/**
	 * Gibt die Kurzinfo zu der Grundfigur zur�ck.
	 * 
	 * @param primitiveFormName der Name einer Grundfigur
	 * @return die Kurzinfo zu der Grundfigur
	 */
	public String getPrimitiveFormInfo( String primitiveFormName);
	
	/**
	 * L�scht die entsprechende Grundfigur.
	 * 
	 * @param primitiveFormName der Name einer Grundfigur
	 */
	public void removePrimitiveForm ( String primitiveFormName);
	
	/**
	 * Zugriff auf alle dynamischen Properties der Grundfigur.
	 * 
	 * @param primitiveFormName der Name einer Grundfigur
	 * @return die Liste aller dynamischen Eigenschaften der Grundfigur
	 */
	public List<DOTProperty> getDynamicProperties( String primitiveFormName);
	
	/**
	 * Ist die DOTProperty zu der als Object �bergebenen Grundfigur statisch, so erh�lt man
	 * <code>true</code> zur�ck; andernfalls ist die Eigenschaft dynamisch und man erh�lt
	 * <code>false</code>.
	 * 
	 * @param primitiveFormName der Name einer Grundfigur oder <code>null</code>
	 * @param property eine Eigenschaft
	 * @return ist die Eigenschaft statisch?
	 */
	public Boolean isPropertyStatic( String primitiveFormName, DOTProperty property);
	
	/**
	 * Setzt die Eigenschaft statisch bzw dynamisch zu sein der �bergebenen Eigenschaft, die gegebenenfalls 
	 * zu der genannten Grundfigur geh�rt. Diese Methode sollte so implementiert werden, dass sie beim �ndern 
	 * die nicht mehr g�ltigen Werte der Eigenschaft nicht l�scht (dadurch wird es m�glich, dass der Benutzer 
	 * diese zwischen statisch und dynamisch hin- und herschaltet ohne seine vorherigen Einstellungen zu
	 * verlieren).
	 * 
	 *  @param primitiveFormName der Name einer Grundfigur oder <code>null</code>
	 *  @param property eine Eigenschaft
	 *  @param b der neue Wert
	 */
	public void setPropertyStatic( String primitiveFormName, DOTProperty property, boolean b);
	
	/**
	 * Gibt den Wert der �bergebenen DOTProperty zur�ck, die gegebenenfalls zu der genannten Grundfigur geh�rt.
	 *  
	 *  @param primitiveFormName der Name einer Grundfigur oder <code>null</code>
	 *  @param property eine Eigenschaft
	 *  @return der Wert der Eigenschaft
	 */
	public Object getValueOfStaticProperty( String primitiveFormName, DOTProperty property);
	
	/**
	 * Setzt den Wert der �bergebenen DOTProperty, die gegebenenfalls zu der genannten Grundfigur geh�rt.
	 * Diese Methode sollte so implementiert werden, dass sie auch auch dann den �bergebenen Wert beh�lt, 
	 * wenn die DOTProperty aktuell nicht statisch ist (dadurch wird es m�glich, dass der Benutzer 
	 * diese zwischen statisch und dynamisch hin- und herschaltet ohne seine vorherigen Einstellungen zu
	 * verlieren). 
	 * 
	 *  @param primitiveFormName der Name einer Grundfigur oder <code>null</code>
	 *  @param property eine Eigenschaft
	 *  @param value der neue Wert
	 */
	public void setValueOfStaticProperty( String primitiveFormName, DOTProperty property, Object value);
	
	/**
	 * Setzt den Wert der �bergebenen DOTProperty, die gegebenenfalls zu der genannten Grundfigur geh�rt, f�r 
	 * das �bergebene Intervall auf das �bergebene DisplayObjectTypeItem. Diese Methode sollte so implementiert 
	 * werden, dass sie auch auch dann den �bergebenen Wert beh�lt, wenn die DOTProperty aktuell nicht dynamisch ist
	 * (dadurch wird es m�glich, dass der Benutzer diese zwischen statisch und dynamisch hin- und herschaltet 
	 * ohne seine vorherigen Einstellungen zu verlieren). 
	 * 
	 *  @param primitiveFormName der Name einer Grundfigur oder <code>null</code>
	 *  @param property eine Eigenschaft
	 *  @param dItem ein Item
	 *  @param lowerBound die untere Schranke
	 *  @param upperBound die obere Schranke
	 */
	public void setValueOfDynamicProperty( String primitiveFormName, DOTProperty property, 
			DisplayObjectTypeItem dItem, Double lowerBound, Double upperBound);
	
	/**
	 * Macht eine tiefe Kopie des DisplayObjectTypes und setzt den Namen um, falls der �bergebene String nicht
	 * <code>null</code> ist. Diese Methode wird beim Erstellen und Bearbeiten von Darstellungstypen verwendet: 
	 * dem Bearbeitungs-Dialog wird eine tiefe Kopie �bergeben und alle �nderungen werden an diesem 
	 * Objekt durchgef�hrt.
	 * 
	 * @param name der neue Name oder <code>null</code>
	 * @return eine Kopie
	 */
	public DisplayObjectType getCopy( String name);
	
	/**
	 * Speichert die Informationen des DisplayObjectTypes unter dem �bergebenen Knoten.
	 * 
	 * @param prefs der Knoten, unter dem die Speicherung durchgef�hrt werden soll
	 */
	public void putPreferences(Preferences prefs);
	
	/**
	 * Initialisiert den DisplayObjectType aus dem �bergebenen Knoten.
	 * 
	 * @param prefs der Knoten, unter dem die Initialisierung durchgef�hrt werden soll
	 */
	public void initializeFromPreferences(Preferences prefs);
	
	/**
	 * L�scht den DisplayObjectType unter dem �bergebenen Knoten.
	 * 
	 * @param prefs der Knoten, unter dem die L�schung durchgef�hrt werden soll
	 */
	public void deletePreferences(Preferences prefs);
	
	/**
	 * Gibt die Selebstbeschreibung des Plugins, zu dem dieser DisplayObjectType geh�rt, zur�ck.
	 * 
	 * @return die Selbstbeschreibung
	 */
	public DisplayObjectTypePlugin getDisplayObjectTypePlugin();
	
	/**
	 * Erzeugt den Teilbaum der Legende, f�r diesen Darstellungstyp.
	 * 
	 * @return der Teilbaum der Legende
	 */
	public LegendTreeNodes getLegendTreeNodes();
	
	/**
	 * Gibt alle Anmeldungen, die dieser DisplayObjectTyp ben�tigt, zur�ck.
	 * 
	 * @return alle Anmeldungen 
	 */
	public Set<DOTSubscriptionData> getSubscriptionData();
	
	/**
	 * Gibt die Attributnamen, f�r die Werte ben�tigt werden, zu der �bergebenen Eigenschaft
	 * und der �bergebenen Anmeldung zur�ck.
	 * 
	 *  @param primitiveFormName der Name einer Grundfigur oder <code>null</code>
	 *  @param property eine Eigenschaft
	 *  @param subscriptionData eine Anmeldung
	 *  @return alle Attributname
	 */
	public List<String> getAttributeNames( String primitiveFormName, DOTProperty property, 
			DOTSubscriptionData subscriptionData);
	
	/**
	 * Gibt die Namen aller von diesem DisplayObject verwendeten Farben zur�ck.
	 * 
	 * @return Die Menge aller Namen aller benutzten Farben
	 */
	public Set<String> getUsedColors();
	
	/**
	 * Ein Interface f�r die kleinste Einheit beim Zuordnen von Anmeldedaten (Attributgruppe, Aspekt,
	 * Attribut) zu Eigenschaftswerten und deren Beschreibung.
	 * 
	 * @author Kappich Systemberatung
	 * @version $Revision: 8080 $
	 *
	 */
	public interface DisplayObjectTypeItem {
		/**
		 * Getter der Attributgruppe.
		 * 
		 * @return die Attributgruppe
		 */
		public String getAttributeGroup();
		/**
		 * Getter des Aspekts.
		 * 
		 * @return der Aspekt
		 */
		public String getAspect();
		/**
		 * Getter f�r den Attributnamen.
		 * 
		 * @return der Attributname
		 */
		public String getAttributeName();
		/**
		 * Getter f�r die Beschreibung.
		 * 
		 * @return die Beschreibung
		 */
		public String getDescription();
		/**
		 * Getter f�r den aktuellen Wert der Eigenschaft, f�r die dieser DisplayObjectTypeItem Daten verwaltet.
		 * 
		 * @return der Wert der Eigenscahft
		 */
		public Object getPropertyValue();
		/**
		 * Erstellt eine tiefe Kopie des DisplayObjectTypeItems.
		 * 
		 * @return die Kopie
		 */
		public DisplayObjectTypeItem getCopy();
	}
	
	/**
	 * Ist der R�ckgabewert nicht null, so ist dieser DisplayObjectTypeItem f�r die �bergebenen Daten anwendbar.
	 * Diese Methode wird von einem {@link DisplayObject} aufgerufen, wenn neue Online-Daten vorliegen, die
	 * eine �nderung der Visualisierungs-Eigenschaft zur Folge haben k�nnte. Der im R�ckgabewert enthaltene
	 * Wert (z.B. eine Farbe) wird dann vom {@link DisplayObjectTypePainter Painter} zur Visualisierung verwendet.
	 * 
	 * @param primitiveFormName der Name einer Grundfigur oder <code>null</code> 
	 * @param subscriptionData Attributgruppe und Aspekt
	 * @param attributeName Attribut
	 * @param value Wert des Attributs
	 */
	public DisplayObjectTypeItem isValueApplicable( 
			String primitiveFormName, DOTProperty property, 
			DOTSubscriptionData subscriptionData, 
			String attributeName, double value);
	
	/**
	 * Ist der R�ckgabewert nicht null, so ist dieser DisplayObjectTypeItem f�r die �bergebenen Daten anwendbar.
	 * Diese Methode wird von einem {@link DisplayObject} aufgerufen, wenn zur gegebenen {@link DOTSubscriptionData
	 * Anmeldung} neue Daten geschickt wurden, die aber keine Werte f�r die Attribute enthalten, sondern Informationen
	 * �ber den {@link DataState Zustand}. Der im R�ckgabewert enthaltene Wert (z.B. eine Farbe) wird dann vom 
	 * {@link DisplayObjectTypePainter Painter} zur Visualisierung verwendet.
	 * 
	 * @param primitiveFormName der Name einer Grundfigur oder <code>null</code>
	 * @param property die Eigenschaft
	 * @param subscriptionData Attributgruppe und Aspekt
	 * @param dataState Zustand des Datensatzes
	 */
	public DisplayObjectTypeItem getDisplayObjectTypeItemForState(
			final String primitiveFormName, final DOTProperty property,
			final DOTSubscriptionData subscriptionData, final DataState dataState);
}
