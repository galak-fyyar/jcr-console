package com.sokolenko.jcrconsole.shared.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * @author Anatoliy Sokolenko
 */
public class PropertyData implements Serializable {
    private String name;

    private PropertyTypeData propertyTypeData;

    private boolean multiple;

    private List<String> values;

    public PropertyData() {
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public PropertyTypeData getPropertyTypeData() {
        return propertyTypeData;
    }

    public void setPropertyTypeData( PropertyTypeData propertyTypeData ) {
        this.propertyTypeData = propertyTypeData;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple( boolean multiple ) {
        this.multiple = multiple;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues( List<String> values ) {
        this.values = values;
    }
}
