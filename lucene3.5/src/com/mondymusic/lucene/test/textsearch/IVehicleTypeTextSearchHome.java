package com.uv.smp.persistence.textsearch;

import java.util.List;
import java.util.Locale;

import com.uv.smp.exception.TextSearchException;
import com.uv.smp.model.vehicle.IVehicleType;
import com.uv.smp.persistence.pojos.taxonomy.VehicleCategory;
import com.uv.smp.persistence.pojos.taxonomy.VehicleType;

public interface IVehicleTypeTextSearchHome extends ITextSearchHome<VehicleType,IVehicleType> {

	public List<IVehicleType> search(Locale locale, VehicleCategory vc, String searchTerm, Boolean visible, Integer maxResults, Double cutoff) throws TextSearchException;

}
